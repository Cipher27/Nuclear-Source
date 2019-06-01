package com.rs.game.player.content.instances;

public final class InstanceNPC {
	
	private final int xOffset, yOffset, zCoordinate, npcId;
	
	public InstanceNPC(final int npcId, final int xOffset, final int yOffset, final int zCoordinate) {
		this.npcId = npcId;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zCoordinate = zCoordinate;
	}
	
	public InstanceNPC(final int npcId, final int xOffset, final int yOffset) {
		this(npcId, xOffset, yOffset, 0);
	}
	
	public final int getXOffset() {
		return xOffset;
	}
	
	public final int getYOffset() {
		return yOffset;
	}
	
	public final int getZCoordinate() {
		return zCoordinate;
	}
	
	public final int getNPCId() {
		return npcId;
	}
	
}