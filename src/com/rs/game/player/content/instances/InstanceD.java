package com.rs.game.player.content.instances;


import com.rs.game.player.content.instances.Instances.RespawnDelay;
import com.rs.game.player.content.instances.Instances.Visibilities;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public final class InstanceD extends Dialogue {

	private InstancedEncounter instance;
	private Instances previous;
	private Object[] instance_parameters = new Object[3];
	public static final int RESPAWN_DELAY = 0, VISIBILITIES = 1, PASSWORD = 2;
	//TODO removal of instance / check instance is valid and proceed from there
	//TODO joining another players instance
	
	@Override
	public void start() {
		if (parameters != null && parameters.length > 0) {
			if (parameters[0] != null && parameters[0] instanceof InstancedEncounter) {
				instance = (InstancedEncounter) parameters[0];
				if (instance == null) {
					end();
					return;
				}
				previous = Instances.ACTIVE_INSTANCES.get(player.getDisplayName().toLowerCase());
				if (previous != null) {
					if (previous.getInstance() != instance) {
						stage = -2;
						sendOptionsDialogue("You already have an active instance! Would you like to start a new one?", "Yes.","No.", "Join an existing instance.");
					} else {
						stage = -3;
						sendOptionsDialogue("You already have an instance active here. Would you like to rejoin it?", "Yes.", "No.", "Join an existing instance.");
					}
				} else
					sendOptionsDialogue("Would you like to instantiate this boss encounter?", "Create an instance room.", "Enter boss room.", "Join an existing instance.");
			} else if (parameters[0] != null && parameters[0] instanceof Object[]) {
				instance_parameters = (Object[]) parameters[0];
				if (instance_parameters == null) {
					end();
					return;
				}
				if (parameters.length > 1 && parameters[1] != null && parameters[1] instanceof InstancedEncounter) {
					instance = (InstancedEncounter) parameters[1];
					if (instance == null) {
						end();
						return;
					}
				}
				sendOptionsDialogue("Are you ready to initiate your instance?", "Yes.", "No.");
				stage = 4;
			}
		} else
			end();
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (instance == null) {
			end();
			return;
		}
		final int price = (int) (instance.getCost() * (player.isExtremeDonator()  ? 0.5 : player.isDonator() || player.IronMan == true ? 0.75 : 1));
		switch (stage) {
		case -3:
			switch (componentId) {
			case OPTION_1:
				if (previous != null)
					previous.joinInstance(player, null);//TODO
				end();
				break;
			case OPTION_2:
				instance.enterInstance(player, true, 0);
				end();
				break;
			case OPTION_3:
				end();
				sendJoinInstance();
				break;
			}
			break;
		case -2://active instance already and instance != previous
			switch (componentId) {
			case OPTION_1://chooses to destroy active instance
				if (previous != null)
					previous.forcefullyDestroyInstance();//TODO got to prevent teleportation of players
				stage = -1;
				sendOptionsDialogue("Would you like to instantiate this boss encounter?", "Yes.", "No.");
				break;
			case OPTION_2://chooses to leave active instance as is, allowing it to remain
				instance.enterInstance(player, true, 0);
				end();
				break;
			case OPTION_3:
				end();
				sendJoinInstance();
				break;
			}
			break;
		case -1:
			switch (componentId) {
			case OPTION_1://proceed to instance and check all inputs etc
				if (price > 0)
					sendOptionsDialogue("You will be charged "+Utils.getFormattedNumber(price)+" to instantiate this boss. Do you accept?", "Yes.", "No.");
				else
					sendOptionsDialogue("It is free to instantiate this boss encounter. Do you wish to proceed?", "Yes.", "No.");
				stage = 0;
				break;
			case OPTION_2://enter room normally
				instance.enterInstance(player, true, 0);
				end();
				break;
			case OPTION_3:
				end();
				sendJoinInstance();
				break;
			}
			break;
		case 0:
			switch (componentId) {
			case OPTION_1://proceed
				stage = 1;
				sendOptionsDialogue("Select the respawn rate of NPC's within this instance.", "Fast.", "Medium.", "Slow.");
				break;
			case OPTION_2://end
				end();
				break;
			}
			break;
		case 1:
			switch (componentId) {
			case OPTION_1://fast
				instance_parameters[RESPAWN_DELAY] = RespawnDelay.FAST;
				break;
			case OPTION_2://medium
				instance_parameters[RESPAWN_DELAY] = RespawnDelay.MEDIUM;
				break;
			case OPTION_3://slow
				instance_parameters[RESPAWN_DELAY] = RespawnDelay.SLOW;
				break;
			}
			stage = 2;
			sendOptionsDialogue("Select the visibility of this instance.", "Public.", "Private.");
			break;
		case 2:
			final boolean isPublic = componentId == OPTION_1;
			instance_parameters[VISIBILITIES] = (isPublic ? Visibilities.PUBLIC : Visibilities.PRIVATE);
			stage = 3;
			sendOptionsDialogue("Do you wish to enter a password for your instance?", "Yes.", "No.");
			break;
		case 3:
			switch (componentId) {
			case OPTION_1://password protected
				end();
				sendSetPassword();
				break;
			case OPTION_2://not password protected
				instance_parameters[PASSWORD] = null;
				break;
			}
			sendOptionsDialogue("Are you ready to initiate your instance?", "Yes.", "No.");
			stage = 4;
			break;
		case 4:
			switch (componentId) {
			case OPTION_1:
				try {
					if (instance.enterInstance(player, false, price))
						new Instances(player, instance, (RespawnDelay) instance_parameters[RESPAWN_DELAY], (Visibilities) instance_parameters[VISIBILITIES], (String) instance_parameters[PASSWORD]);
				} catch (Exception e) {
					player.getPackets().sendGameMessage("An error occured whilst attempting to add you to the instance. Please report this to Hc747.");
				}
				end();
				break;
			case OPTION_2:
				player.getPackets().sendGameMessage("Your instance setup has been cancelled!");
				end();
				break;
			}
			break;
		}
		
	}
	
	private void sendJoinInstance() {
		player.getPackets().sendInputNameScript("Enter the name of the instance owner: ");
		player.getTemporaryAttributtes().put("instance_owner", Boolean.TRUE);
		player.getTemporaryAttributtes().put("instance",instance);
	}
	
	private void sendSetPassword() {
		player.getPackets().sendInputNameScript("Enter your desired password: ");
		player.getTemporaryAttributtes().put("instance_password", instance_parameters);
		player.getTemporaryAttributtes().put("instance",instance);
	}

	@Override
	public void finish() {
		
	}
	
	
	
}