package com.rs.game.player.content.rots;

import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.map.MapBuilder;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.utils.Utils;
/**
 * 
 * @author paolo
 *
 */
public class RiseOfTheSix extends Controler {

	
	private CopyOnWriteArrayList<Player> team;
	private int[] boundChunks;
	//map part
	int sizeX = 2; // horizontal size
	int sizeY = 6; // vertical size

	int chunkX = 308; // bottom left chunk x
    int chunkY = 754; // bottom left chunk y

	@Override
	public void start() {
	   loadMap();
	}
	/**
	 * loads the map instance
	 * might have to redo this, took this from another server
	 */
    public void loadMap() {
	boundChunks = MapBuilder.findEmptyChunkBound(sizeX, sizeY);
	//Long Hall.
	MapBuilder.copyAllPlanesMap(chunkX, chunkY, boundChunks[0], boundChunks[1], sizeX, sizeY);
	//Long Hall End Extra.
	MapBuilder.copyAllPlanesMap(310, 754, boundChunks[0], boundChunks[1]+6, 2, 2);
	//Long Hall - Entrance to Main Arena.
	MapBuilder.copy2RatioSquare(306, 756, boundChunks[0], boundChunks[1]+8, 3);
	//Main Area
	MapBuilder.copy2RatioSquare(304, 752, boundChunks[0], boundChunks[1]-2, 2 );
	MapBuilder.copy2RatioSquare(304, 758, boundChunks[0] + 2, boundChunks[1]+8, 1);
	MapBuilder.copyAllPlanesMap(288, 736, boundChunks[0] + 4, boundChunks[1]+8, 8, 8);
	MapBuilder.copy2RatioSquare(310, 752, boundChunks[0] - 2, boundChunks[1]+6, 3);
    }
	/*
	* Gives the surviving plays their rewards
	*/
	public void giveReward(){
		if(team.size() == 0)
			return;
		for (int i = 0; i < 4; i++){
			Player survivor = team.get(i);
			if(survivor == null)
				continue;
			survivor.getInventory().addItem(RotsRewards.COMMON_REWARDS[Utils.random(0,RotsRewards.COMMON_REWARDS.length)]);
			if(Utils.random(15) == 1) //rare rewards
					survivor.getInventory().addItem(RotsRewards.RARE_REWARDS[Utils.random(0,RotsRewards.RARE_REWARDS.length)]);
				else
					survivor.getInventory().addItem(RotsRewards.OTHER_REWARDS[Utils.random(0,RotsRewards.OTHER_REWARDS.length)]);
			if(Utils.random(30) == 1) // pet unlocking
				survivor.getInventory().addItem(RotsRewards.PET_REWARDS[Utils.random(0,RotsRewards.PET_REWARDS.length)]);
			
		}
	}
	
	@Override
	public void process() {
	}

	
}
