package com.rs.game.player.content.thieving.pyramidplunder;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.controlers.Controler;

/**
 * 
 * A Pyramid Plunder {@link Controller} object.<br><br>
 * The controller will make calls to the {@link Game} object
 * to dictate interaction with the environment while in the minigame.
 * @author David
 *
 */
public class PPController extends Controler
{
	/** The {@link Game} object dictating the player and environment behavior. */
	private Game game;

	@Override
	public void start() {
		
		if(getArguments()[0] != null && getArguments()[0] instanceof Game)
		{
			this.game = (Game) getArguments()[0];
			player.sm("xd");
		}
		
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object)
	{
		return game.processObjectInteraction(object);
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) 
	{
		player.sm("xd");
		removeControler();
		return true;
	}
	
	@Override
	public boolean checkWalkStep(int lastX, int lastY, int nextX, int nextY)
	{
		return true;
	}
	
	/**
	 * Accessor method to retrieve the associated {@link Game} object
	 * @return {@code game}
	 */
	public Game getGame()
	{
		return game;
	}
}