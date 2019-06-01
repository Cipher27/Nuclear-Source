package com.rs.game.player.content.ports;

import java.io.Serializable;

import com.rs.game.player.Player;



public class SpecialRewards  implements Serializable{

	/**
	 * serializable stuff
	 */
	private static final long serialVersionUID = -6678463390002175016L;
	/**
	 * enum of the recipes
	 */
	public enum RECIPES {ROCKTAIL_SOUP,TESTU_HELM,TETSU_BODY,TETSU_LEGS,DEATH_LOTUS_HOOD,DEATH_LOTUS_CHEST,DEATH_LOTUS_CHAPS,SEASINGERS_HOOD,SEASINGERS_ROBE_TOP,SEASINGERS_ROBE_BOTTOM};
	/**
	 * sets the recipe to the player
	 * @param recip
	 * @param player
	 */
	public static void setRecipe(RECIPES recip, Player player){
		player.getPorts().setCurrentRecipe(recip);
	}
	
}