package com.rs.game.player.dialogues.impl.pop.ship;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Colors;


public class SelectedShipD extends Dialogue {


	@Override
	public void start() {
		if(player.getPorts().getShips().size() == 1)
			sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),"Back");
			else if(player.getPorts().getShips().size() == 2)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName());
			else if(player.getPorts().getShips().size() == 3)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName());
			else if(player.getPorts().getShips().size() == 4)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName());
			else if(player.getPorts().getShips().size() == 5)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName(),""+player.getPorts().getShips().get(4).getName());
			stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
		if(componentId == OPTION_1){
			if (player.getPorts().hasFifthShipReturned()) {
			player.getDialogueManager().startDialogue("SimpleMessage", "Changed to ship one.");
			player.getPorts().selectedShip = player.getPorts().getShips().get(0);
			player.getPorts().sendVoyageInterface();
			stage = 99;
			} else {
				player.getDialogueManager().startDialogue("SimpleMessage", "This ship has not yet returned");
				stage = 99;
			}
		} else if(componentId == OPTION_2){
			if(player.getPorts().getShips().size() <2){
				end();
				return;
			}
			if(player.getPorts().hasSecondShipReturned()){
			player.getDialogueManager().startDialogue("SimpleMessage", "Changed to ship two.");
			player.getPorts().selectedShip = player.getPorts().getShips().get(1);
			player.getPorts().sendVoyageInterface();
			stage = 99;
			}else {
				player.getDialogueManager().startDialogue("SimpleMessage", "This ship has not yet returned");
				stage = 99;
			}
		} else if(componentId == OPTION_3){
			if(player.getPorts().hasThirdShipReturned()){
			player.getDialogueManager().startDialogue("SimpleMessage", "Changed to ship three.");
			player.getPorts().selectedShip = player.getPorts().getShips().get(2);
			player.getPorts().sendVoyageInterface();
			stage = 99;
			}	else {
				player.getDialogueManager().startDialogue("SimpleMessage", "This ship has not yet returned");
				stage = 99;
			}
		} else if(componentId == OPTION_4){
			if(player.getPorts().hasFourthShipReturned()){
			player.getDialogueManager().startDialogue("SimpleMessage", "Changed to ship four.");
			player.getPorts().selectedShip = player.getPorts().getShips().get(3);
			player.getPorts().sendVoyageInterface();
			stage = 99;
			}	else {
				player.getDialogueManager().startDialogue("SimpleMessage", "This ship has not yet returned");
				stage = 99;
			}
		} else if(componentId == OPTION_5){
			if(player.getPorts().hasFifthShipReturned()){
			player.getDialogueManager().startDialogue("SimpleMessage", "Changed to ship five.");
			player.getPorts().selectedShip = player.getPorts().getShips().get(4);
			player.getPorts().sendVoyageInterface();
			stage = 99;
			}	else {
				player.getDialogueManager().startDialogue("SimpleMessage", "This ship has not yet returned");
				stage = 99;
			}
		}
		break;
		case 99:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}
}