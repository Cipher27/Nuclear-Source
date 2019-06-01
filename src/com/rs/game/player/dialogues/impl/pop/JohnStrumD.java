package com.rs.game.player.dialogues.impl.pop;


import com.rs.game.item.Item;
import com.rs.game.player.content.ports.Voyages;
import com.rs.game.player.content.ports.buildings.PortUpgrades;
import com.rs.game.player.content.ports.buildings.PortUpgrades.Totems;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

/**
 * Handles The Partner's (John Strum) dialogue.
 */
public class JohnStrumD extends Dialogue {
	// The NPC ID.
	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getPorts().firstTimer) {
			player.getHintIconsManager().removeUnsavedHintIcon();
			Dialogue.closeNoContinueDialogue(player);
			sendNPCDialogue(npcId, NORMAL, "Welcome to the port of Hellion, adventurer. My name is John Strum and I will help you manage this port.");
		} else {
			sendNPCDialogue(npcId, NORMAL, "Hello there!");
			stage = 9;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendPlayerDialogue(GLANCE_DOWN, "Managing? me ?");
			stage = 0;
			break;
		case 0:
			sendNPCDialogue(npcId, NORMAL, "That's right, this is your port now.");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, CALM, "Now hear me out.. as an ex-port master I will be your manager. "
					+ "I will help with managing your ships, voyages, resources and crew members.");
			stage = 2;
			break;
		case 2:
			sendNPCDialogue(npcId, CALM, "The more voyages you do, the more regions will be available and more rewards will come.");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, CALM,
					"Every voyage will give you chrimes and sometimes you will get a special voyage which will give special loot"
							+ " These rare resources can be turned into "
							+ "extremely strong armor and weapons at the smithery or recipes who can be learned.");
			stage = 4;
			break;
		case 4:
			sendNPCDialogue(npcId, CALM,
					"To go on voyages you'll need a ship. You may have up to 5 ships, you get one for free. The other ships can be unlocked by upgrading your office.");
			stage = 5;
			break;
		case 5:
			sendItemDialogue(995, 330, "John has given you 330 Chime to start off with.");
			stage = 6;
			break;
		case 6:
			sendNPCDialogue(npcId, CALM, "That was all I had.. To earn more Chime you will have to successfully send "
					+ "your ships on voyages. Unsuccessful voyages won't give you anything and you ship might need a repair.");
			player.getPorts().firstTimer = false;
			player.getPorts().chime += 330;
			stage = 7;
			break;
		case 7:
			sendPlayerDialogue(NORMAL, "Thank you, John. Could I start managing my ships " + "right now?");
			stage = 8;
			break;
		case 8:
			sendNPCDialogue(npcId, NORMAL, "Certainly, you'll reveice a captain and 3 crew memebers aswell! Oh and I almost forgot but take this aswell.");
			stage = 250;
			break;
		case 250:
			sendItemDialogue(26358, 1, "You received a Captain's log, for easy port managing.");
			player.getInventory().addItem(new Item(26358));
			stage = 9;
			break;
		//options 
		case 9:
			sendOptionsDialogue("Select an option", "Voyages", "Port upgrades","Crew");
			stage = 10;
			break;
	   
		case 10:
		switch (componentId) {
			case OPTION_1:
			if(player.getPorts().getShips().size() == 1)
			sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),"back");
			else if(player.getPorts().getShips().size() == 2)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName());
			else if(player.getPorts().getShips().size() == 3)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName());
			else if(player.getPorts().getShips().size() == 4)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName());
			else if(player.getPorts().getShips().size() == 5)
				sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName(),""+player.getPorts().getShips().get(4).getName());
			stage = 12;
			/*	} else {
					sendOptions("Pick an option", "Normal Voyages", "Special Voyages ("+player.getPorts().getSpecialVoyages().size()+")");
					stage = 69;
				}*/
				break;
			case OPTION_2:
			   sendOptionsDialogue("Select an option", "Totems", "Workshop", "Office", "Warehouse", "more");
			   stage = 11;
				break;
			case OPTION_3:
				player.getPorts().sendCrewInterface();
				end();
				break;
			case OPTION_4:
				sendNPCDialogue(npcId, NORMAL, "Easy : 90%, Normal : 75% Medium : 55%, Hard : 35%, Extreme 20% succes rate.");
				stage = 99;
				break;
			case OPTION_5:
				if(player.getPorts().getSpecialVoyages().size() == 0){
					sendNPCDialogue(npcId, NORMAL, "You have no have no special voyages atm.");
					stage = 99;
				} else {
				/*	if(player.getPorts().getSpecialVoyages().size() == 1)
						sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0),"back");
					else if(player.getPorts().getSpecialVoyages().size() == 2)
						sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0), ""+player.getPorts().getSpecialVoyages().get(1),"back");
					else if(player.getPorts().getSpecialVoyages().size() == 3)
						sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0), ""+player.getPorts().getSpecialVoyages().get(1), ""+player.getPorts().getSpecialVoyages().get(2),"back");
					else if(player.getPorts().getSpecialVoyages().size() == 4)
						sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0), ""+player.getPorts().getSpecialVoyages().get(1), ""+player.getPorts().getSpecialVoyages().get(2), ""+player.getPorts().getSpecialVoyages().get(3),"back");
					else if(player.getPorts().getSpecialVoyages().size() == 5)
						sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0), ""+player.getPorts().getSpecialVoyages().get(1), ""+player.getPorts().getSpecialVoyages().get(2), ""+player.getPorts().getSpecialVoyages().get(3), ""+player.getPorts().getSpecialVoyages().get(4));
					stage = 14;*/
					if(player.getPorts().getShips().size() == 1)
						sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),"back");
						else if(player.getPorts().getShips().size() == 2)
							sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName());
						else if(player.getPorts().getShips().size() == 3)
							sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName());
						else if(player.getPorts().getShips().size() == 4)
							sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName());
						else if(player.getPorts().getShips().size() == 5)
							sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName(),""+player.getPorts().getShips().get(4).getName());
						stage = 50;
					break;
				}
				break;
		}
			break;
		/**
		 * special voyages
		 */
		case 50:
			switch (componentId) {
			case OPTION_1:
				if (player.getPorts().hasFirstShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(0))){
						player.getPorts().handleFirstShipReward(player.getPorts().getShips().get(0));
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound");
						break;
					} else {
						if(player.getPorts().getSpecialVoyages().size() == 1)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 2)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 3)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 4)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(), ""+player.getPorts().getSpecialVoyages().get(3).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 5)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(), ""+player.getPorts().getSpecialVoyages().get(3).getName(), ""+player.getPorts().getSpecialVoyages().get(4).getName());
						stage = 51;
						break;
					}
				} else 
				sendNPCDialogue(npcId, CALM, "Your ship has not yet returned.");
				break;
			case OPTION_2:
				if (player.getPorts().hasSecondShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(1))){
						player.getPorts().handleSecondShipReward(player.getPorts().getShips().get(1));
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound");
						break;
					} else {
						if(player.getPorts().getSpecialVoyages().size() == 1)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 2)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 3)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 4)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(), ""+player.getPorts().getSpecialVoyages().get(3).getName(),"back");
						else if(player.getPorts().getSpecialVoyages().size() == 5)
							sendOptionsDialogue("special voyage", ""+player.getPorts().getSpecialVoyages().get(0).getName(), ""+player.getPorts().getSpecialVoyages().get(1).getName(), ""+player.getPorts().getSpecialVoyages().get(2).getName(), ""+player.getPorts().getSpecialVoyages().get(3).getName(), ""+player.getPorts().getSpecialVoyages().get(4).getName());
						stage = 52;
						break;
					}
				} else 
				sendNPCDialogue(npcId, CALM, "Your ship has not yet returned.");
				break;
			}
			break;
			//first ship, others todo
		case 51: 
			player.getPorts().firstShipTime = Utils.currentTimeMillis();
			player.getPorts().firstShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(0));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getSpecialVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(1));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getSpecialVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(2));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getSpecialVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(3));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getSpecialVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(4));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getSpecialVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				stage = 99;
				break;
			}
			break;
		//second ship
		case 52:
			player.getPorts().secondShipTime = Utils.currentTimeMillis();
			player.getPorts().secondShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(0));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getSpecialVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(1));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getSpecialVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(2));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getSpecialVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(3));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getSpecialVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getSpecialVoyages().get(4));
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getSpecialVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				stage = 99;
				break;
			}
			break;
			//third
		case 53: 
			player.getPorts().thirdShipTime = Utils.currentTimeMillis();
			player.getPorts().thirdShipReward = false;
			stage = 98;
			switch (componentId) {

			}
			break;
		//fourth
		case 54: 
			player.getPorts().fourthShipTime = Utils.currentTimeMillis();
			player.getPorts().fourthShipReward = false;
			stage = 98;
			switch (componentId) {
			
			}
			break;
		//fith
		case 55: 
			player.getPorts().fifthShipTime = Utils.currentTimeMillis();
			player.getPorts().fifthShipReward = false;
			stage = 98;
			switch (componentId) {
			
			}
			break;
			/**
			 * boosts
			 */
		case 11:
		switch (componentId) {
			case OPTION_1:
			 sendOptionsDialogue("Select an option", 
			 "Increases the chance of receiving Receipe scroll voyages : 1500", 
			 "Increases the chance of receiving XP voyages.", 
			 "Increases the amount of resources rewarded from a voyage.", 
			 "Increases the chance of receiving a random event in the port.", 
			 "Increases the chance of receiving trade good voyages.");
			stage = 150;
			break;
			case OPTION_2:
				sendOptionsDialogue("Workshop current tier : "+(player.getPorts().getWorkShop() == null ? "0" :player.getPorts().getWorkShop().ordinal()+1)+"","What are the upgrade benefits of the workshop?","Upgrade");
				stage = 170;
			break;
			case OPTION_3:
				sendOptionsDialogue("Office current tier : "+(player.getPorts().getOffice()==null ? "0" :player.getPorts().getOffice().ordinal()+1)+"", "What are the upgrade benefits of the office?","Upgrade");
				stage = 180;
			break;
			case OPTION_4:
				sendOptionsDialogue("Warehouse current tier : 0","What are the upgrade benefits of the warehouse?","Upgrade");
				stage = 160;
			break;
			case OPTION_5:
			sendOptionsDialogue("Select an option", "Bar", "Lodgings", "Shipwright", "back");
			stage = 149;
			break;
		}
			break;
		case 149:
			switch (componentId) {
				case OPTION_1:
					sendOptionsDialogue("Bar current tier : "+(player.getPorts().getBar() == null ? "0" :player.getPorts().getBar().ordinal()+1)+"","What are the upgrade benefits of the bar?","Upgrade");
					stage = 162;
					break;
				case OPTION_2:
					sendOptionsDialogue("Lodgings current tier : 0","What are the upgrade benefits of the upgrade benefits of the Lodgings?","Upgrade");
				   stage = 9;
					break;
				case OPTION_3:
					sendOptionsDialogue("Shipwright current tier : 0","What are the upgrade benefits of the Shipwright?","Upgrade");
					stage  = 166;
					break;
				case OPTION_4:
					  sendOptionsDialogue("Select an option", "Totems", "Workshop", "Office", "Warehouse", "more");
					   stage = 9;
					break;
			}
				break;
		//totems
		case 150:
		switch (componentId) {
		case OPTION_1:
			if(player.getPorts().chime >= Totems.TELESCOPE.getCost()){
				player.getPorts().setTotem(Totems.TELESCOPE);
				end();
			} else {
				player.sm("You need atleast 1500 chrimes to buy this.");
				end();
			}
			break;
		case OPTION_2:
			if(player.getPorts().chime >= Totems.PARROT.getCost())
				player.getPorts().setTotem(Totems.PARROT);
			break;
		case OPTION_3:
			if(player.getPorts().chime >= Totems.CHERRY_TREE.getCost())
				player.getPorts().setTotem(Totems.CHERRY_TREE);
			break;
		case OPTION_4:
			if(player.getPorts().chime >= Totems.PANDORAS_BOX.getCost())
				player.getPorts().setTotem(Totems.PANDORAS_BOX);
			break;
		case OPTION_5:
			if(player.getPorts().chime >= Totems.JADE_STATIE.getCost())
				player.getPorts().setTotem(Totems.JADE_STATIE);
			break;
		}
		break;
		case 160:
			switch (componentId) {
			case OPTION_1:
				sendNPCDialogue(npcId, NORMAL, ""+player.getPorts().getMarket().extra);
				stage = 9;
				break;
			case OPTION_2:
				if(player.getPorts().chime > PortUpgrades.Market.getCost(player.getPorts().getMarket())){
					PortUpgrades.UpgradeMarket(player);
					sendNPCDialogue(npcId, NORMAL, "You have succesfully upgraded your market");
					stage = 99;
					}else 
						sendNPCDialogue(npcId, NORMAL, "You don't have enough chrimes to upgrade this.");
						stage = 99;
				break;
			}
		break;
		case 162:
			switch (componentId) {
			case OPTION_1:
				sendNPCDialogue(npcId, NORMAL, "The bar is used as a relaxing place for your crew, also you'll unlock The Barmaid who can help you more with recipes you can find during your trips.");
				stage = 9;
				break;
			case OPTION_2:
				if(player.getPorts().chime > PortUpgrades.Market.getCost(player.getPorts().getMarket())){
					PortUpgrades.UpgradeMarket(player);
					sendNPCDialogue(npcId, NORMAL, "You have succesfully upgraded your market");
					stage = 99;
					}else 
						sendNPCDialogue(npcId, NORMAL, "You don't have enough chrimes to upgrade this.");
						stage = 99;
				break;
			}
		break;
		case 166:
			switch (componentId) {
			case OPTION_1:
				sendNPCDialogue(npcId, NORMAL, "The Shipwright reduces the duration of your voyages, it also reduces the repair time and you unlock The Occultist.");
				stage = 9;
				break;
			case OPTION_2:
				break;
			}
		break;
		//worhsop
		case 170:
			switch (componentId) {
			case OPTION_1:
				sendNPCDialogue(npcId, NORMAL, "The workshop building consists of a workbench, anvil, bank box, scrimshaw crafter, and cooking station.");
				stage = 9;
				break;
			case OPTION_2:
				if(player.getPorts().chime > PortUpgrades.workShop.getCost(player.getPorts().getWorkShop())){
				PortUpgrades.upgradeWorkshop(player);
				sendNPCDialogue(npcId, NORMAL, "You have succesfully upgraded your WorkShop");
				}else 
					sendNPCDialogue(npcId, NORMAL, "You don't have enough chrimes to upgrade this.");
					stage = 99;
				break;
			}
		break;
		//office
		case 180:
			switch (componentId) {
			case OPTION_1:
				sendNPCDialogue(npcId, NORMAL, "Office upgrades unlock more ships, which can be used to send out more voyages simultaneously.");
				stage = 9;
				break;
			case OPTION_2:
			if(player.getPorts().chime > PortUpgrades.Office.getCost(player.getPorts().getOffice())){
				PortUpgrades.upgradeOffice(player);
				sendNPCDialogue(npcId, NORMAL, "You have succesfully upgraded your Office.");
			}else 
				sendNPCDialogue(npcId, NORMAL, "You don't have enough chrimes to upgrade this.");
				break;
			}
		break;
		//ship buying
		case 12:
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				if (player.getPorts().hasFirstShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(0))){
						if(player.getPorts().handleFirstShipReward(player.getPorts().getShips().get(0)))
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound");
						else 
							sendNPCDialogue(npcId, NORMAL, "Your ship failed his trip.");
						break;
					} else {
						player.getPorts().selectedShip = player.getPorts().getShips().get(0);
						player.getPorts().sendVoyageInterface();
						end();
						break;
					}
				} else 
				sendNPCDialogue(npcId, CALM, "Your ship has not yet returned come back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes.");
				break;
			case OPTION_2:
				if(player.getPorts().getShips().size() == 1){
					sendOptionsDialogue("Select an option", "Voyages", "Port upgrades","Crew");
					stage = 10;
					break;
				}
				if (player.getPorts().hasSecondShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(1))){
						if(player.getPorts().handleSecondShipReward(player.getPorts().getShips().get(1)))
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound.");
						else 
							sendNPCDialogue(npcId, NORMAL, "Your ship failed his trip.");
						break;
					} else {
						player.getPorts().selectedShip = player.getPorts().getShips().get(1);
						player.getPorts().sendVoyageInterface();
						end();
						break;
					}
				} else 
					sendNPCDialogue(npcId, CALM, "Your ship has not yet returned come back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes.");
				break;
			case OPTION_3:
				if (player.getPorts().hasThirdShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(2))){
						if(player.getPorts().handleThirdShipReward(player.getPorts().getShips().get(2)))
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound.");
						else 
							sendNPCDialogue(npcId, NORMAL, "Your ship failed his trip.");
						break;
					} else {
						player.getPorts().selectedShip = player.getPorts().getShips().get(2);
						player.getPorts().sendVoyageInterface();
						end();
						break;
					}
				} else 
					sendNPCDialogue(npcId, CALM, "Your ship has not yet returned come back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes.");
				break;
			case OPTION_4:
				if (player.getPorts().hasFourthShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(3))){
						if(player.getPorts().handleFourthShipReward(player.getPorts().getShips().get(3)))
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound.");
						else 
							sendNPCDialogue(npcId, NORMAL, "Your ship failed his trip.");
						break;
					} else {
						player.getPorts().selectedShip = player.getPorts().getShips().get(3);
						player.getPorts().sendVoyageInterface();
						end();
						break;
					}
				} else 
					sendNPCDialogue(npcId, CALM, "Your ship has not yet returned come back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes.");
				break;
			case OPTION_5:
				if (player.getPorts().hasFifthShipReturned()) {
					if(player.getPorts().getCurrentTrips().containsKey(player.getPorts().getShips().get(4))){
						if(player.getPorts().handleFifthShipReward(player.getPorts().getShips().get(4)))
						sendNPCDialogue(npcId, NORMAL, "Congratulations! Your ship returned safe and sound.");
						else 
							sendNPCDialogue(npcId, NORMAL, "Your ship failed his trip.");
						break;
					} else {
						player.getPorts().selectedShip = player.getPorts().getShips().get(4);
						player.getPorts().sendVoyageInterface();
						end();
						break;
					}
				} else 
					sendNPCDialogue(npcId, CALM, "Your ship has not yet returned come back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes.");
				break;
			} //end of switch
		break;
		case 14: //first ship voyage time select
			player.getPorts().firstShipTime = Utils.currentTimeMillis();
			player.getPorts().firstShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(0));
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(1));
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(2));
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(3));
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(0), player.getPorts().getPossibleVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(0).getName()+" on a voyage, check back in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes!");
				player.getPorts().firstShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(4));
				stage = 99;
				break;
			}
			break;
		//second ship
		case 15:
			player.getPorts().secondShipTime = Utils.currentTimeMillis();
			player.getPorts().secondShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getPossibleVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(0));
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getPossibleVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(1));
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getPossibleVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(2));
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getPossibleVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(3));
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(1), player.getPorts().getPossibleVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(1).getName()+" on a voyage, check back in "+player.getPorts().getSecondVoyageTimeLeft()+" minutes!");
				player.getPorts().secondShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(4));
				stage = 99;
				break;
			}
			break;
		case 16: 
			player.getPorts().thirdShipTime = Utils.currentTimeMillis();
			player.getPorts().thirdShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(2), player.getPorts().getPossibleVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(2).getName()+" on a voyage, check back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes!");
				player.getPorts().thirdShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(0));
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(2), player.getPorts().getPossibleVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(2).getName()+" on a voyage, check back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes!");
				player.getPorts().thirdShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(1));
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(2), player.getPorts().getPossibleVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(2).getName()+" on a voyage, check back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes!");
				player.getPorts().thirdShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(2));
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(2), player.getPorts().getPossibleVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(2).getName()+" on a voyage, check back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes!");
				player.getPorts().thirdShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(3));
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(2), player.getPorts().getPossibleVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(2).getName()+" on a voyage, check back in "+player.getPorts().getThirdVoyageTimeLeft()+" minutes!");
				player.getPorts().thirdShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(4));
				stage = 99;
				break;
			}
			break;
		//fourth
		case 17: 
			player.getPorts().fourthShipTime = Utils.currentTimeMillis();
			player.getPorts().fourthShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(3), player.getPorts().getPossibleVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(3).getName()+" on a voyage, check back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes!");
				player.getPorts().fourthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(0));
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(3), player.getPorts().getPossibleVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(3).getName()+" on a voyage, check back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes!");
				player.getPorts().fourthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(1));
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(3), player.getPorts().getPossibleVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(3).getName()+" on a voyage, check back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes!");
				player.getPorts().fourthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(2));
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(3), player.getPorts().getPossibleVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(3).getName()+" on a voyage, check back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes!");
				player.getPorts().fourthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(3));
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(3), player.getPorts().getPossibleVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(3).getName()+" on a voyage, check back in "+player.getPorts().getFourthVoyageTimeLeft()+" minutes!");
				player.getPorts().fourthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(4));
				stage = 99;
				break;
			}
			break;
		//fith
		case 18: 
			player.getPorts().fifthShipTime = Utils.currentTimeMillis();
			player.getPorts().fifthShipReward = false;
			stage = 98;
			switch (componentId) {
			case OPTION_1:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(4), player.getPorts().getPossibleVoyages().get(0));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(4).getName()+" on a voyage, check back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes!");
				player.getPorts().fifthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(0));
				stage = 99;
				break;
			case OPTION_2:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(4), player.getPorts().getPossibleVoyages().get(1));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(4).getName()+" on a voyage, check back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes!");
				player.getPorts().fifthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(1));
				stage = 99;
				break;
			case OPTION_3:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(4), player.getPorts().getPossibleVoyages().get(2));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(4).getName()+" on a voyage, check back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes!");
				player.getPorts().fifthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(2));
				stage = 99;
				break;
			case OPTION_4:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(4), player.getPorts().getPossibleVoyages().get(3));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(4).getName()+" on a voyage, check back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes!");
				player.getPorts().fifthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(3));
				stage = 99;
				break;
			case OPTION_5:
				player.getPorts().sendShipVoyage(player.getPorts().getShips().get(4), player.getPorts().getPossibleVoyages().get(4));
				sendNPCDialogue(npcId, NORMAL, "I've sent ship "+player.getPorts().getShips().get(4).getName()+" on a voyage, check back in "+player.getPorts().getFifthVoyageTimeLeft()+" minutes!");
				player.getPorts().fifthShipVoyage = Voyages.getTime(player.getPorts().getPossibleVoyages().get(4));
				stage = 99;
				break;
			}
			break;
		case 98:
			if(player.getPorts().getShips().size() == 1)
				sendOptionsDialogue(Colors.red+"Ship Management", "Ship : "+player.getPorts().getShips().get(0).getName()+" returns in "+player.getPorts().getFirstVoyageTimeLeft()+" minutes","back");
				else if(player.getPorts().getShips().size() == 2)
					sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName());
				else if(player.getPorts().getShips().size() == 3)
					sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName());
				else if(player.getPorts().getShips().size() == 4)
					sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName());
				else if(player.getPorts().getShips().size() == 5)
					sendOptionsDialogue(Colors.red+"Ship Management", ""+player.getPorts().getShips().get(0).getName(),""+player.getPorts().getShips().get(1).getName(),""+player.getPorts().getShips().get(2).getName(),""+player.getPorts().getShips().get(3).getName(),""+player.getPorts().getShips().get(4).getName());
				stage = 12;
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