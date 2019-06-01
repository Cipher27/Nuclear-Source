package com.rs.game.player.content.instances;

import com.rs.game.WorldTile;

public final class InstanceTiles {
	
	private final WorldTile entrance, exit;
	
	public InstanceTiles(final WorldTile entrance, final WorldTile exit) {
		this.entrance = entrance;
		this.exit = exit;
	}
	
	public final WorldTile getEntranceTile() {
		return entrance;
	}
	
	public final WorldTile getExitTile() {
		return exit;
	}
	
}