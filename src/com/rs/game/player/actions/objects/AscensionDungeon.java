package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.agility.Agility;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * 
 * @author paolo
 *
 */

public class AscensionDungeon {
	
	

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 84724:
		case 84702:
		case 84715:
		case 84729:
		case 84731:
		case 84726:
		case 84728:
		case 84730:
		case 84727:
		return true;
		default:
		return false;
		}
	}
	
	public static void jumpOne(final Player player, final WorldObject object) {
		// first obsticle lvl 80
		final WorldTile NextTile1 = new WorldTile(1135, 611, 1);
		final WorldTile NextTile2 = new WorldTile(1131, 611, 1);
		// second obsticle lvl 70
		final WorldTile NextTile5 = new WorldTile(1096, 628, 1);
		final WorldTile NextTile6 = new WorldTile(1096, 626, 1);
		// third obsticle lvl 60
		final WorldTile NextTile3 = new WorldTile(1067, 652, 1);
		final WorldTile NextTile4 = new WorldTile(1065, 652, 1);
		// fourth obsticle lvl 90
		final WorldTile NextTile7 = new WorldTile(1150, 689, 1);
		final WorldTile NextTile8 = new WorldTile(1152, 689, 1);
		// fith obsticle lvl 90
		final WorldTile NextTile9 = new WorldTile(1178, 654, 1);
		final WorldTile NextTile10 = new WorldTile(1178, 652, 1);
		// start of 1st jump
		if(object.getX() == 1131 && object.getY() == 611) {
			if (!Agility.hasLevel(player, 80)) {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need 80 agility to use this.");
				return;
			}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile1);
				player.addWalkSteps(1131, 611);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(15461));
			}
			if(time == 10) {
				player.setNextWorldTile(NextTile1);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
				player.addWalkSteps(1136, 611);
			    stop();
			}
		    }
		}, 0, 0);
		
	} else if(object.getX() == 1136 && object.getY() == 611) {
		if (!Agility.hasLevel(player, 80)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 80 agility to use this.");
			return;
		}else {
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile2);
				player.addWalkSteps(1135, 611);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(15461));
			}
			if(time == 10) {
				player.setNextWorldTile(NextTile2);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
				player.addWalkSteps(1131, 611);
			    stop();
			    
			}
		    }
		}, 0, 0);
		}
		// end of 1st jump
		// start of 2nd jump
	} else if(object.getX() == 1096 && object.getY() == 626) {
		if (!Agility.hasLevel(player, 70)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 70 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile5);
				player.addWalkSteps(1096, 626);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile5);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
	
	} else if(object.getX() == 1096 && object.getY() == 628) {
		if (!Agility.hasLevel(player, 70)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 70 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile6);
				player.addWalkSteps(1096, 628);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile6);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
		// end of 2nd jump
	// start of 3rd jump
	} else if(object.getX() == 1065 && object.getY() == 652) {
		if (!Agility.hasLevel(player, 60)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 60 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile3);
				player.addWalkSteps(1067, 652);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile3);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
	
	} else if(object.getX() == 1067 && object.getY() == 652) {
		if (!Agility.hasLevel(player, 60)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 60 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile4);
				player.addWalkSteps(1065, 652);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile4);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
	//end of 3rd jump
		// start of 4th jump
	} else if(object.getX() == 1150 && object.getY() == 689) {
		if (!Agility.hasLevel(player, 90)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 90 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile8);
				player.addWalkSteps(1150, 689);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile8);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
	
	} else if(object.getX() == 1152 && object.getY() == 689) {
		if (!Agility.hasLevel(player, 90)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 90 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile7);
				player.addWalkSteps(1152, 689);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile7);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
		// end of 4th jump
		// start of 5th jump
	} else if(object.getX() == 1178 && object.getY() == 654) {
		if (!Agility.hasLevel(player, 90)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 90 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile10);
				player.addWalkSteps(1178, 654);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile10);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
	
	} else if(object.getX() == 1178 && object.getY() == 652) {
		if (!Agility.hasLevel(player, 90)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need 90 agility to use this.");
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int time;
		    @Override
		    public void run() {
		    	time ++;
			if (time == 1) {
				player.setNextFaceWorldTile(NextTile9);
				player.addWalkSteps(1178, 652);
			}
			if(time == 3) {
				player.setNextAnimation(new Animation(13495));
			}
			if(time == 6) {
				player.setNextWorldTile(NextTile9);
				player.setNextAnimation(new Animation(-1));
	
			}
			if(time == 11) {
			    stop();
			}
		    }
		}, 0, 0);
		}
		// end of 5th jump
	}
	
	public static void jumpGap(Player player){
			if (player.getSkills().getLevel(Skills.AGILITY) >= 80) {
				if (player.getY() == 654 || player.getY() == 655) {
					player.setNextWorldTile(new WorldTile(1178, 652, 1));
				}
				if ((player.getX() == 1178 && player.getY() == 652) || (player.getX() == 1178 && player.getY() == 651)) {
					player.setNextWorldTile(new WorldTile(1178, 654, 1));
				}
				if (player.getX() >= 1135 && player.getX() <= 1137) {
					player.setNextWorldTile(new WorldTile(1132, 611, 1));
				}
				if (player.getX() >= 1130 && player.getX() <= 1132) {
					player.setNextWorldTile(new WorldTile(1135, 611, 1));
				}
				if ((player.getX() == 1096 && player.getY() == 625) || (player.getX() == 1096 && player.getY() == 626)) {
					player.setNextWorldTile(new WorldTile(1096, 628, 1));
				}
				if ((player.getX() == 1096 && player.getY() == 628) || (player.getX() == 1096 && player.getY() == 629)) {
					player.setNextWorldTile(new WorldTile(1096, 625, 1));
				}
				if (player.getX() == 1064 || player.getY() == 1065) {
					player.setNextWorldTile(new WorldTile(1067, 652, 1));
				}
				if (player.getX() == 1067 || player.getX() == 1068) {
					player.setNextWorldTile(new WorldTile(1065, 652, 1));
				}
				if (player.getX() == 1149 || player.getX() == 1150) {
					player.setNextWorldTile(new WorldTile(1152, 689, 1));
				}
				if (player.getX() == 1152 || player.getX() == 1153) {
					player.setNextWorldTile(new WorldTile(1150, 689, 1));
				}
			} else {
				player.sm("You need an agility level of 80 to jump this gap.");
			}
	}
	

	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if(id == 84702)
			player.setNextWorldTile(new WorldTile(1095,579,1));
		else if(id == 84724)
			player.setNextWorldTile(new WorldTile(2501,2886,0));
		else if(id == 84715)
			jumpOne(player, object);
		else if(id == 84726){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28445, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.PRIMUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Primus to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		} else if(id == 84727){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28447, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.SECUNDUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Secundus to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		} else if(id == 84730){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28453, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.QUINTUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Quintus to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		} else if(id == 84728){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28449, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.TERTIUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Tertius to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		} else if(id == 84731){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28455, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.SEXTUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Sextus to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		} else if(id == 84729){
			if (player.getSkills().getLevel(18) >= 95) {
				if (player.getInventory().containsItem(28451, 1)) {
					player.getDialogueManager().startDialogue("InstancedLegionDialogue", Instance.QUARTUS);
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "This room requires a Ascension Keystone Quartus to enter.");
				}
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need a slayer level of 95 to enter this room.");
			}
		}
	}

}
