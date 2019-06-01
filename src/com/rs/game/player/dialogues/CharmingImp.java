package com.rs.game.player.dialogues;

public class CharmingImp extends Dialogue {

	int npcId;
	final int BLUE = 0, GREEN = 1, GOLD = 2, CRIMSON = 3;
	
	@Override
	public void start() {
	    if (player.selectedCharms == null)
	    player.selectedCharms = new boolean[4];
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, ""+(player.getAppearence().isMale() ? "Master " : "Madam ")+player.getDisplayName()+"! Which charms would you like me to collect? You can choose to filter them!" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue("Select a charm type.",
					""+(player.selectedCharms[GOLD] ? "<col=00ff00>" : "<col=ff0000>")+"Gold charms.",
					""+(player.selectedCharms[GREEN] ? "<col=00ff00>" : "<col=ff0000>")+"Green charms.",
					""+(player.selectedCharms[CRIMSON] ? "<col=00ff00>" : "<col=ff0000>")+"Crimson charms.",
					""+(player.selectedCharms[BLUE] ? "<col=00ff00>" : "<col=ff0000>")+"Blue charms.",
					"That'll be all.");
		} else if (stage == 0) {
			switch (componentId) {
			case OPTION_1:
				player.selectedCharms[GOLD] = !player.selectedCharms[GOLD];
				end();
				break;
			case OPTION_2:
				player.selectedCharms[GREEN] = !player.selectedCharms[GREEN];
				end();
				break;
			case OPTION_3:
				player.selectedCharms[CRIMSON] = !player.selectedCharms[CRIMSON];
				end();
				break;
			case OPTION_4:
				player.selectedCharms[BLUE] = !player.selectedCharms[BLUE];
				end();
				break;
			case OPTION_5:
				end();
				break;
			}
		}
	}

	@Override
	public void finish() {

	}

}
