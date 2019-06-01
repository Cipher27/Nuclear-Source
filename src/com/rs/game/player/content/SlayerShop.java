package com.rs.game.player.content;


import com.rs.game.player.Player;
import com.rs.game.player.actions.slayer.Slayer;
import com.rs.game.player.actions.slayer.SlayerMaster;

public class SlayerShop {

 public transient final static int BUY_INTERFACE = 164,
	                               ABILITIES_INTERFACE = 378
								 , ASSIGNMENT_INTERFACE = 161;
		
		
	public static void sendSlayerShop(Player player) {
    player.getInterfaceManager().sendInterface(164);
	player.getPackets().sendItemOnIComponent(164, 27, 13263, 1);//Slayer Helmet
	player.getPackets().sendItemOnIComponent(164, 30, 23732, 1);//huge exp lamp
	player.getPackets().sendItemOnIComponent(164, 38, 13281, 1);//ring of slaying
	player.getPackets().sendItemOnIComponent(164, 40, 744, 1); // heart token
	player.getPackets().sendItemOnIComponent(164, 29, 23733, 1);//small exp lamp
        player.getPackets().sendIComponentText(164, 20,  " "+ player.getPointsManager().getSlayerPoints()+" ");
        player.getPackets().sendIComponentText(164, 26,  "Unlock Slayer helmet crafting");
        player.getPackets().sendIComponentText(164, 28,  "Buy exp lamps");
        player.getPackets().sendIComponentText(164, 37,  "Buy a slayer ring");
        player.getPackets().sendIComponentText(164, 39,  "Convert points into power tokens");
        player.getPackets().sendIComponentText(164, 31,  "Slayer Points:");
        player.getPackets().sendIComponentText(164, 32,  "200 Points"); //slayer exp
        player.getPackets().sendIComponentText(164, 33,  "300 Points"); //slayer helmet
        player.getPackets().sendIComponentText(164, 36,  "100/250 Points"); //Exp lamps
        player.getPackets().sendIComponentText(164, 34,  "50 Points"); //slayer ring
        player.getPackets().sendIComponentText(164, 35,  "250 Points"); //hart token
    }
	public static void sendAbillityTab(Player player) {
    player.getInterfaceManager().sendInterface(378);

	
        player.getPackets().sendIComponentText(378, 79,  " "+ player.getPointsManager().getSlayerPoints()+" ");
		//slayer helm upgrade
		player.getPackets().sendItemOnIComponent(378, 103, 22546, 1);  //you need to send them on 103 and 104 otherwise you'll get a arrow.
		player.getPackets().sendItemOnIComponent(378, 104, 22546, 1);
        player.getPackets().sendIComponentText(378, 83,  "Slayer helm upgrade");
        player.getPackets().sendIComponentText(378, 90,  "250 Points.");
		
		player.getPackets().sendItemOnIComponent(378, 105, 18337, 1);
        player.getPackets().sendIComponentText(378, 87,  "Learn how to add extra items to your toolbelt");
        player.getPackets().sendIComponentText(378, 99,  "150 Points.");
		
	
        player.getPackets().sendItemOnIComponent(378, 101, 34978, 1); // vorago cave
        player.getPackets().sendIComponentText(378, 88,  "Unlock new slayer monsters");
        player.getPackets().sendIComponentText(378, 100,  "400 Points.");
		//sirenic scales.
		player.getPackets().sendItemOnIComponent(378, 92, 13723, 1); //slayer teletabs x
        player.getPackets().sendIComponentText(378, 84,  "Unlock how to make slayer teletabs.");
        player.getPackets().sendIComponentText(378, 91,  "250 Points.");
		
		player.getPackets().sendItemOnIComponent(378, 93, 26122, 1); //obsidian shard
	    player.getPackets().sendItemOnIComponent(378, 94, 29863, 1); //sirenic scale
	    player.getPackets().sendItemOnIComponent(378, 95, 28627, 1);  //tectonic energie
	    player.getPackets().sendItemOnIComponent(378, 96, 30027, 1); //male energie
        player.getPackets().sendIComponentText(378, 85,  "Unlock new drops.");
        player.getPackets().sendIComponentText(378, 97,  "300 Points.");
		
		player.getPackets().sendItemOnIComponent(378, 102, 31188, 1);
        player.getPackets().sendIComponentText(378, 86,  "Seedicide.");
        player.getPackets().sendIComponentText(378, 98,  "150 Points.");
    }
	
	public static void sendAssigmentTab(Player player) {
	player.getInterfaceManager().sendInterface(161);	
	player.getPackets().sendIComponentText(161, 19,  " "+ player.getPointsManager().getSlayerPoints()+" ");
	for(int i = 0; i < player.getBlockedSlayerTasks().size();i ++)
		player.getPackets().sendIComponentText(161, 31+ i,  ""+player.getBlockedSlayerTasks().get(i));
	}

	
	
	public static void handleButtons(Player player, int componentId) {
		switch (componentId) {
		case 17:
		sendAssigmentTab(player);
		//player.getPackets().sendGameMessage("You switch to the next page.");
		 break;
		case 24: //Slayer XP
		case 25:
		case 32:
		player.getDialogueManager().startDialogue("SlayerExp");
		break;
		case 26: //slayer helmet
		case 27:
		case 33:
		player.getDialogueManager().startDialogue("SlayerHelmCraftD");
		 break;
		case 16: //Slayer Page 2
				sendAbillityTab(player);
			//	player.getPackets().sendGameMessage("You switch to the next page.");
				break;
		case 35:
		case 39: //power crystals
        case 40:
		  player.getDialogueManager().startDialogue("HeartTokens");
			break;
		case 37: //slayer ring
		case 38:
		case 34:
			player.getDialogueManager().startDialogue("SlayerShopD", 13281, 50);
			break;
    
		case 28: //exp lamps
        case 29:
        case 30:			
	 player.getDialogueManager().startDialogue("ExpLamps");
			break;
			}
		} 	
	 public static void handleButtons3(Player player, int componentId) {
		switch (componentId) {
		case 23: //reassign
		    if(!player.hasTask){
		    	player.getDialogueManager().startDialogue("SimpleNPCMessage", 9085, "There is now task to reassing.");
		    	return;
		    }
			if(player.getPointsManager().getSlayerPoints() >= 30){
				if(player.getSkills().getCombatLevel() <= 50)
					 Slayer.assignTask(player, SlayerMaster.TURAEL);
				 else 
					 Slayer.assignTask(player, SlayerMaster.KURADEL);
				player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 30);
				player.getDialogueManager().startDialogue("SimpleNPCMessage", 9085, "Your new slayer task is " + player.currentSlayerTask.getTaskMonstersLeft() + " " + player.currentSlayerTask.getTask().simpleName +".");
			   sendAssigmentTab(player); 
			} else
				player.sm("You need atleast 30 points to reassign a task.");
		break;
		case 24: //block task
			if(player.getBlockedSlayerTasks().size() == 5){
				player.sm("You have reached the max of blocked slayer tasks, remove one if you would like to block your current.");
			} else {
				if(player.getPointsManager().getSlayerPoints() >= 100){
					player.getPointsManager().setSlayerPoints(player.getPointsManager().getSlayerPoints() - 100);
				player.getBlockedSlayerTasks().add(0,player.currentSlayerTask.getTask().simpleName);
				player.getDialogueManager().startDialogue("SimpleNPCMessage", 9085, "You succesfully added "+player.currentSlayerTask.getTask().simpleName+ " to your block list.");
				player.hasTask = false;
				sendAssigmentTab(player);
				} else {
					player.sm("You need 100 slayer points to block a task.");
				}
			}
			break;
		case  14:
		sendAbillityTab(player);
		//player.getPackets().sendGameMessage("You switch to the next page.");
		break;
		case  15:
		sendSlayerShop(player);
		//player.getPackets().sendGameMessage("You switch to the next page.");
		break;
		}
	 }
		
    public static void handleButtons2(Player player, int componentId) {
		switch (componentId) {
		case 14:
		sendAssigmentTab(player);
		//player.getPackets().sendGameMessage("You switch to the next page.");
		break;
	    
		case 15: //Slayer Page 1
                sendSlayerShop(player);
				//player.getPackets().sendGameMessage("You switch to the previous page.");
				break;
			case 76: //Slayer upgrade
             player.getDialogueManager().startDialogue("SlayerhelmUpgradeD");
			break;
			
		case 75: //Seedicide
			
			player.getDialogueManager().startDialogue("SlayerShopD", 31188, 150);
			break;
		case 74:
			player.getDialogueManager().startDialogue("SlayerDropsD");
		   break;
			case 73: //Slayer Tabs
             player.getDialogueManager().startDialogue("SlayerTabsD");
			  break;
			case 78: //
             player.getDialogueManager().startDialogue("EliteDungeonD");
			break;
			
			case 77: //upgrade for bonecrusher
				  player.getDialogueManager().startDialogue("SlayerToolbeltOptiosD");	
				break;
			}
			}	
		
}