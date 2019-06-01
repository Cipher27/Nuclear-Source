package com.rs.game.player.content.shooting;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.custom.ActivityHandler;
import com.rs.utils.Utils;
 
	/**
	 * 
	 * @author paolo
	 *
	 */
public class ShootingStar {

		private final int START_ID = 38661;
        /**
         * vars
         */
		private WorldTile tile;
		private int skillId;
		private boolean found;
		private boolean canClaim;
		private int stage = 0;
		private boolean isPrismatic;
		private int lifes;
		
		/*
		 * generats a new star (random)
		 */
		public ShootingStar(){
			setTile(LOCATION[Utils.random(0, LOCATION.length)]);
			setFound(false);
			setStage(7);
			//	if(Utils.random(3) == 0)
			//	isPrismatic = true;
			//else
				setSkillId(Utils.random(0,24));
			setCanClaim(false);
			spawn();
			lifes = 20 * World.getPlayers().size();
			ActivityHandler.setShootingStar("A "+Skills.SKILL_NAME[getSkillId()]+" star has crashed at home.");
			
		}
		/**
		 * discover the rock
		 * @param player
		 */
		public void findStar(Player player){
			player.getSkills().addXp(getSkillId(), player.getSkills().getLevel(getSkillId())*75);
			player.sm("You discovered the shooting star and received some bonus exp.");
			setFound(true);
		}
		/**
		 * removes the star.
		 */
		public void removeStar(){
			World.spawnNPC(8091, getTile(), -1, true, true,true);
			ActivityHandler.setShootingStar("The star has been mined.");
		}
		public void mine(){
			if(lifes == 0){
				if(stage == 0){
					if(!canClaim){
						canClaim = true;
					World.spawnObject(new WorldObject(123,10,0,getTile()));
					removeStar();
					} 
				} else {
					World.spawnObject(new WorldObject(START_ID + (7 - getStage()),10,0,getTile()));
					stage--;
					lifes = 20 * World.getPlayers().size();
				}
			} else
				lifes--;
		}
		/**
		 * spawns the star.
		 */
		public void spawn(){
			World.spawnObject(new WorldObject(38660, 10, 0 , getTile()), true);
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just crashed ["+(isPrismatic ? "Prismatic" : Skills.SKILL_NAME[getSkillId()])+"]", false);
		
		}
		public int getReqLevel(){
			return stage * 10;
		}

		/**
         * array of the possible tiles
         */
		public final static WorldTile[] LOCATION =  { 
				new WorldTile(2353,3638,0), //home
		
		};
		
        /**
		 * @return the tile
		 */
		public WorldTile getTile() {
			return tile;
		}

		/**
		 * @param tile the tile to set
		 */
		public void setTile(WorldTile tile) {
			this.tile = tile;
		}

		/**
		 * @return the skillId
		 */
		public int getSkillId() {
			return skillId;
		}

		/**
		 * @param skillId the skillId to set
		 */
		public void setSkillId(int skillId) {
			this.skillId = skillId;
		}

		/**
		 * @return the stage
		 */
		public int getStage() {
			return stage;
		}

		/**
		 * @param stage the stage to set
		 */
		public void setStage(int stage) {
			this.stage = stage;
		}

		/**
		 * @return the canClaim
		 */
		public boolean isCanClaim() {
			return canClaim;
		}

		/**
		 * @param canClaim the canClaim to set
		 */
		public void setCanClaim(boolean canClaim) {
			this.canClaim = canClaim;
		}

		/**
		 * @return the found
		 */
		public boolean isFound() {
			return found;
		}

		/**
		 * @param found the found to set
		 */
		public void setFound(boolean found) {
			this.found = found;
		}

}
