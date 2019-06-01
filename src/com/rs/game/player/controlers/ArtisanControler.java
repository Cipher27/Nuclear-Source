package com.rs.game.player.controlers;

import com.rs.game.World;
import com.rs.game.WorldTile;

public class ArtisanControler extends Controler {
	


	@Override
	public void start() {
		player.setNextWorldTile(new WorldTile(3039, 3339, 0));
		player.getArtisanWorkshop().sendOverlayInterface();
		player.getPackets().sendIComponentText(1073, 11, ""+World.artisanBonusExp);
	}
	
	
	@Override
	public void process() {
		player.getPackets().sendIComponentText(1073, 11, ""+World.artisanBonusExp); 
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getInterfaceManager().closeOverlay(player.getInterfaceManager().resizableScreen ? true : false); 
		player.closeInterfaces();
		player.getControlerManager().forceStop();
		return true;
	}
/*	@Override
	public void handelNPCOption1(){
		
	}*/
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean login() {
		player.getArtisanWorkshop().sendOverlayInterface();
		player.getPackets().sendIComponentText(1073, 11, ""+World.artisanBonusExp);
		return false;
	}
	
	@Override
	public void magicTeleported(int teleType) {
		player.getInterfaceManager().closeOverlay(player.getInterfaceManager().resizableScreen ? true : false);
		player.closeInterfaces();
		player.getControlerManager().forceStop();
	}
	
}
