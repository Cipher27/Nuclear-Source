package com.rs.game.player.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.player.Player;

public class RecipesHandler implements Serializable {

	

	/**
	 * ser num
	 */
	private static final long serialVersionUID = 5376445853690500043L;
	
	public static int INTERFACE_ID = 3049;
	/**
	 * player stuff
	 */
	private transient Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * list of unlocked recipes
	 */
	public List<Recipes> recipes;
	/**
	 * constructor
	 */
	public RecipesHandler(){
		recipes = new ArrayList<Recipes>();
	}
	/**
	 * enum of all the recipes
	 * @author paolo
	 *
	 */
	public enum Recipes {
		
		SUPER_SARADOMIN("Super Saradomin","It's an upgraded version of the regular saradomin brew","Combine a regular saradomin brew(3) (obtainble as a drop from the Saradomin boss) with a wine of saradmin.","You can receive this recipe as a drop from the World Gorger",28191),
		ROCKTAIL_SOUP("Rocktail soup","A verry strong soup with a healing effect.","You need 1 rocktail and a spiece.","You can unlock this from player owned ports",26313),
		OVERLOAD_SALVE("Overload salve","A combination of the regular overload,prayer renewal,super antiposoin and super antifire.","It is made by combining a overload (4), prayer renewal (4), prayer potion (4), super antifire (4), antifire (4) and super antipoison (4) in a crystal flask","You can create this after unlocking the Master Decanter achievement.",33198),
		HOLY_OVERLOAD("Holy overload","A combination of the regular overload and a prayer renewal.","Combine an overload(4) with an prayer renewal (4) with a crystal flask.","Power tokens (todo)",33246),
		SUPER_GROWTH("Ultra-growth","A potion that lets your crops grown instant","","You can buy this from Livid farm",30770),
		LUCKY_POTION("Lucky potion","A potion that will apply the ring of wealth effect for 30min.","Combine a uncut dragonstone with a ranar potion (unf)","You can receive this recipe as a drop from the World Gorger",35050);
		public String name,descriprion;
		public String recipe;
		private String obtainMethod;
		int itemId;
		private Recipes(String name, String desc,String recipe,String obtain,int id){
			this.name = name;
			this.descriprion = desc;
			this.recipe = recipe;
			this.setObtainMethod(obtain);
			this.itemId = id;
		}
		/**
		 * @return the obtainMethod
		 */
		public String getObtainMethod() {
			return obtainMethod;
		}
		/**
		 * @param obtainMethod the obtainMethod to set
		 */
		public void setObtainMethod(String obtainMethod) {
			this.obtainMethod = obtainMethod;
		}
	}
	
	public void unlockRecipe(Recipes recipe){
		if(recipes.contains(recipe))
			return;
		recipes.add(recipe);
        player.sm("You have succesfully learned this new recipe.");
	}
	
	public static void sendInterface(Player player){
		player.getInterfaceManager().sendInterface(INTERFACE_ID);
		//empty's the inter
		for(int i2 = 12 ; i2 <20 ; i2++)
			 player.getPackets().sendIComponentText(INTERFACE_ID, i2, "");
		for(int i2 = 21 ; i2 <30 ; i2++)
			 player.getPackets().sendIComponentSprite(INTERFACE_ID, i2, 27247);
		int comp = 12;
		//sends 
		for (Recipes recipe : Recipes.values()) {
				player.getPackets().sendItemOnIComponent(INTERFACE_ID, comp + 9, recipe.itemId, 1);
				player.getPackets().sendIComponentText(INTERFACE_ID, comp, ""+recipe.name);
		 comp++;
			}
		}
	/**
	 * handles the button clicking
	 * @param buttonId
	 */
    public void handleButtons(int buttonId){
    	int comp = 3;
		if(buttonId == 1){
			player.getInterfaceManager().closeScreenInterface();
			return;
		}
    	for (int i = 0; i < Recipes.values().length; i ++){
    		Recipes perk = Recipes.values()[i];	
				if(buttonId == comp){ //rewrite
					player.getDialogueManager().startDialogue("RecipeOptions",perk);
					return;
				}
				comp++;
		}
    }
	
	

	

}