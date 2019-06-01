package com.rs.game.player.content.perks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.player.Player;
import com.rs.game.player.content.perks.ItemValues.DestroyAbles;
import com.rs.game.player.content.perks.Perks.Perk;
import com.rs.game.player.content.perks.Perks.Type;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

/**
 * 
 * @author paolo
 *
 */
public class PerkManager implements Serializable {

	/**
	 * Generated serial UID.
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4772303527229990786L;
	/**
	 * The player instance.
	 */
	private transient Player player;
	
	public List<Perk> perks;
    public static int INTERFACE_ID = 3048;
	/**
	  * Constructor
	  **/
	public PerkManager(){
			perks = new ArrayList<Perk>();
	}
	/**
	 * The player instance saving to.
	 * @param player The player.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}	
	/*
	*checks if the item can be destroyed for points 
	*/
	public boolean isDestroyAble(int id){
		final DestroyAbles destroyable = DestroyAbles.forId(id);
		if(destroyable != null)
			return true;
		player.sm(Colors.red+"This item is not convertable.");
		return false;
	} 
/**
 * -destroys the item and gives points
 * @param itemId
 */
	public void destroy(int itemId){
		if(!isDestroyAble(itemId))
			return;
		if(!player.getInventory().contains(itemId))
			return;
		final DestroyAbles destroyable = DestroyAbles.forId(itemId);
		player.getInventory().deleteItem(itemId,1);
		player.getPointsManager().setPerkPoints(player.getPointsManager().getPerkPoints() + destroyable.getAmount());
		player.sm("Your item was succesfully converter into "+Colors.orange+(destroyable.getAmount() == 1 ?(destroyable.getAmount()+" point") :(destroyable.getAmount()+" points"))+" </col>, you now have a total of "+player.getPointsManager().getPerkPoints()+" perk points.");
	}
	/**
	 * sends the interface of the item values
	 */
	public void sendValueInterface(){
		 player.getInterfaceManager().sendInterface(3045);
		 player.getPackets().sendIComponentText(3045, 1, "You have "+player.getPointsManager().getPerkPoints()+" perk points");
	}
	/**
	 * send the combat perk interface
	 */
    private boolean skillingPerks = false;
	public void sendCombatPerks(){
		skillingPerks = false;
		player.getInterfaceManager().sendInterface(INTERFACE_ID);
		//empty's the inter
		for(int i2 = 18 ; i2 <27 ; i2++)
			 player.getPackets().sendIComponentText(INTERFACE_ID, i2, "");
		for(int i2 = 27 ; i2 <36 ; i2++)
			 player.getPackets().sendIComponentSprite(INTERFACE_ID, i2, 27247);
		int comp = 18;
		//sends the perks on it
		for (Perk perk : Perk.values()) {
			if(perk.type == Type.COMBAT){	
				player.getPackets().sendItemOnIComponent(INTERFACE_ID, comp + 9, perk.pictureId, 1);
				player.getPackets().sendIComponentText(INTERFACE_ID, comp, ""+perk.getName()+":"+perk.getPrice());
		 comp++;
			}
		}
		}
   /**
    * opens the skilling perks
    */
	public void sendSkillingPerks(){
		skillingPerks = true;
		player.getInterfaceManager().sendInterface(INTERFACE_ID);
		//empty's the inter
		for(int i2 = 18 ; i2 <27 ; i2++)
		 player.getPackets().sendIComponentText(INTERFACE_ID, i2, "");
		for(int i2 = 27 ; i2 <36 ; i2++)
			 player.getPackets().sendIComponentSprite(INTERFACE_ID, i2, 27247);
		int comp = 18;
		for (Perk perk : Perk.values()) {
			if(perk.type == Type.SKILLING){	
				 player.getPackets().sendIComponentText(INTERFACE_ID, comp, ""+perk.getName()+":"+perk.getPrice());
		         player.getPackets().sendItemOnIComponent(INTERFACE_ID, comp + 9, perk.pictureId, 1);
				 comp++;
			}
		}
		}
	public int getCombatPerksAmount(){
		int amount =0;
		for(Perk perk : Perk.values()){
			if(perk.type == Type.COMBAT)
				amount ++;
		}
		return amount;
	}
	/**
	 * handles the button clicking
	 * @param buttonId
	 */
    public void handleButtons(int buttonId){
    	int comp = 9;
    	for (int i = (skillingPerks ? getCombatPerksAmount() : 0); i < Perk.values().length; i ++){
    		Perk perk = Perk.values()[i];	
				if(buttonId == comp){ //verry bad way 
					player.getDialogueManager().startDialogue("PerkOptions",perk);
					return;
				}
				comp++;
		}
    }
	/**
	 * handles the key finding
	 */
	public void handelKeys(){
		if(Utils.random(100) == 0){
			player.sm("Your keyfinder perk found you a crystal key part.");
			player.getInventory().addItem(Utils.random(2) == 0 ? 985 : 987 ,1);
		}
	}
	
	

   
}

