package com.rs.game.player.dialogues.impl.dungeoneering;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.dialogues.Dialogue;
/**
 * 
 * @author Homeboy927
 *
 */

public class UnBind extends Dialogue{

	String[] itemNames;
	ItemsContainer<Item> bindedItems;
	
	@Override
	public void start() {
		bindedItems = player.getDungManager().getBindedItems();
		itemNames = new String[bindedItems.getSize()];
		for(int i = 0; i < bindedItems.getSize();i++){
			Item item = bindedItems.get(i);
			if(item != null){
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				itemNames[i] = iDef.getName();
			}else{
				itemNames[i] = "null";
			}
		}
		sendOptionsDialogue("Unbind Item", 
				itemNames[0], 
				itemNames[1], 
				itemNames[2], 
				itemNames[3], 
				itemNames[4]);
		stage = -1;
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(componentId){
		case OPTION_1:
			if(itemNames[0].equals("null")){
				player.sendMessage("You do not have any items bound to this slot.");
			}else{
				Item item = bindedItems.get(0);
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				player.getDungManager().unbind(item);
				player.sendMessage("You have unbound the item " + iDef.getName() + ".");
			}
			player.getInterfaceManager().closeChatBoxInterface();
			end();
			break;
		case OPTION_2:
			if(itemNames[1].equals("null")){
				player.sendMessage("You do not have any items bound to this slot.");
				player.getInterfaceManager().closeChatBoxInterface();
				end();
			}else{
				Item item = bindedItems.get(1);
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				player.getDungManager().unbind(item);
				player.sendMessage("You have unbound the item " + iDef.getName() + ".");
			}
			player.getInterfaceManager().closeChatBoxInterface();
			end();
				break;
		case OPTION_3:
			if(itemNames[2].equals("null")){
				player.sendMessage("You do not have any items bound to this slot.");
				player.getInterfaceManager().closeChatBoxInterface();
				end();
			}else{
				Item item = bindedItems.get(2);
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				player.getDungManager().unbind(item);
				player.sendMessage("You have unbound the item " + iDef.getName() + ".");
			}
			player.getInterfaceManager().closeChatBoxInterface();
			end();
			break;
		case OPTION_4:
			if(itemNames[3].equals("null")){
				player.sendMessage("You do not have any items bound to this slot.");
				player.getInterfaceManager().closeChatBoxInterface();
				end();
			}else{
				Item item = bindedItems.get(3);
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				player.getDungManager().unbind(item);
				player.sendMessage("You have unbound the item " + iDef.getName() + ".");
			}
			player.getInterfaceManager().closeChatBoxInterface();
			end();
			break;
		case OPTION_5:
			if(itemNames[4].equals("null")){
				player.sendMessage("You do not have any items bound to this slot.");
				player.getInterfaceManager().closeChatBoxInterface();
				end();
			}else{
				Item item = bindedItems.get(4);
				ItemDefinitions iDef = ItemDefinitions.getItemDefinitions(item.getId());
				player.getDungManager().unbind(item);
				player.sendMessage("You have unbound the item " + iDef.getName() + ".");
			}
			player.getInterfaceManager().closeChatBoxInterface();
			end();
			break;
		}
	}
	
	@Override
	public void finish() {

	}

}
