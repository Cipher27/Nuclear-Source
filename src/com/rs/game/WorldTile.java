package com.rs.game;

import java.io.Serializable;

import com.rs.Settings;
import com.rs.utils.Utils;


public class WorldTile implements Serializable {

	private static final long serialVersionUID = -6567346497259686765L;

	protected short x;

	protected short y;

	private short xStart;

	private short yStart;
	protected byte plane, planeStart;
	private int mask;

	public int getXInChunk() {
		return x & 0x7;
	}

	public int getYInChunk() {
		return y & 0x7;
	}

	public WorldTile(int x, int y) {
		this.x = this.xStart = (short) x;
		this.y = this.yStart = (short) y;
		this.plane = this.planeStart = (byte) 0;
	}

	public WorldTile(int x, int y, int plane) {
		this.x = this.xStart = (short) x;
		this.y = this.yStart = (short) y;
		this.plane = this.planeStart = (byte) plane;
	}

	public WorldTile(int plane, int x, int y, int mask) {
		this.mask = mask;
		this.x = (short) x;
		this.y = (short) y;
		this.plane = (byte) plane;
	}

	public WorldTile(WorldTile tile) {
		this.x = this.xStart = tile.x;
		this.y = this.yStart = tile.y;
		this.plane = this.planeStart = tile.plane;
	}

	public WorldTile(WorldTile tile, int randomize) {
		this.x = this.xStart = (short) (tile.x + Utils.getRandom(randomize * 2) - randomize);
		this.y = this.yStart = (short) (tile.y + Utils.getRandom(randomize * 2) - randomize);
		this.plane = this.planeStart = tile.plane;
	}

	public WorldTile(int hash) {
		this.x = this.xStart = (short) (hash >> 14 & 0x3fff);
		this.y = this.yStart = (short) (hash & 0x3fff);
		this.plane = this.planeStart = (byte) (hash >> 28);
	}

	public static WorldTile create(final int x, final int y, final int plane) {
		return new WorldTile(x, y, plane);
	}

	public WorldTile getStartWorldTile() {
		return new WorldTile(this.xStart, this.yStart, this.planeStart);
	}

	public WorldTile(WorldTile[] controlerWorldTile) {
		// TODO Auto-generated constructor stub
	}

	public void moveWorldTile(int xOffset, int yOffset, int planeOffset) {
		x += xOffset;
		y += yOffset;
		plane += planeOffset;
	}

	public final void setWorldTile(WorldTile tile) {
		setWorldTile(tile.x, tile.y, tile.plane);
	}

	public final void setWorldTile(int x, int y, int plane) {
		this.x = this.xStart = (short) x;
		this.y = this.yStart = (short) y;
		this.plane = this.planeStart = (byte) plane;
	}

	public int getX() {
		return x;
	}

	public int getXInRegion() {
		return x & 0x3F;
	}

	public int getYInRegion() {
		return y & 0x3F;
	}

	public int getY() {
		return y;
	}

	public int getPlane() {
		if (plane > 3)
			return 3;
		return plane;
	}

	public int getChunkX() {
		return (x >> 3);
	}

	public int getChunkY() {
		return (y >> 3);
	}

	public int getRegionX() {
		return (x >> 6);
	}

	public int getRegionY() {
		return (y >> 6);
	}

	public int getRegionId() {
		return ((getRegionX() << 8) + getRegionY());
	}

	public int getLocalX(WorldTile tile, int mapSize) {
		return x - 8 * (tile.getChunkX() - (Settings.MAP_SIZES[mapSize] >> 4));
	}

	public int getLocalY(WorldTile tile, int mapSize) {
		return y - 8 * (tile.getChunkY() - (Settings.MAP_SIZES[mapSize] >> 4));
	}

	public int getLocalX(WorldTile tile) {
		return getLocalX(tile, 0);
	}

	public int getLocalY(WorldTile tile) {
		return getLocalY(tile, 0);
	}

	public int getLocalX() {
		return getLocalX(this);
	}

	public int getLocalY() {
		return getLocalY(this);
	}

	public int getRegionHash() {
		return getRegionY() + (getRegionX() << 8) + (plane << 16);
	}

	public int getTileHash() {
		return y + (x << 14) + (plane << 28);
	}

	public boolean withinDistance(WorldTile tile, int distance) {
		if (tile == null || tile.plane != plane)
			return false;
		int deltaX = tile.x - x, deltaY = tile.y - y;
		return deltaX <= distance && deltaX >= -distance && deltaY <= distance && deltaY >= -distance;
	}

	/*
	 * public boolean withinDistance(Entity entity, int distance) { if
	 * (entity.getPlane() != getPlane()) return false; int thisSize = 1; if
	 * (this instanceof Entity) { thisSize = entity.getSize(); } int toSize =
	 * entity.getSize(); int distanceX = getX() - entity.getX(); int distanceY =
	 * getY() - entity.getY(); //assume x1 < x2 int distanceX1 = getX() -
	 * entity.getX(); return !(distanceX > sizeX || distanceX < -1 || distanceY
	 * > sizeY || distanceY < -1); }
	 */

	public boolean withinWiderDistance(WorldTile tile) {
		if (tile == null || tile.plane != plane)
			return false;
		// int deltaX = tile.x - x, deltaY = tile.y - y;
		return Math.abs(tile.x - x) <= 28 && Math.abs(tile.y - y) <= 28;// deltaX
																		// <= 14
																		// &&
																		// deltaX
																		// >=
																		// -15
																		// &&
																		// deltaY
																		// <= 14
																		// &&
																		// deltaY
																		// >=
																		// -15;
	}	
	
	public boolean withinDistance(WorldTile tile) {
		if (tile == null || tile.plane != plane)
			return false;
		// int deltaX = tile.x - x, deltaY = tile.y - y;
		return Math.abs(tile.x - x) <= 14 && Math.abs(tile.y - y) <= 14;// deltaX
																		// <= 14
																		// &&
																		// deltaX
																		// >=
																		// -15
																		// &&
																		// deltaY
																		// <= 14
																		// &&
																		// deltaY
																		// >=
																		// -15;
	}

	public boolean withinDistance(WorldTile tile, int distance, int sizeX, int sizeY) {
		if (tile.getPlane() != getPlane())
			return false;
		int distanceX = getX() - tile.getX();
		int distanceY = getY() - tile.getY();
		return !(distanceX > sizeX || distanceX < -1 || distanceY > sizeY || distanceY < -1);
	}

	public int getCoordFaceX(int sizeX) {
		return getCoordFaceX(sizeX, -1, -1);
	}

	public static int getCoordFaceX(int x, int sizeX, int sizeY, int rotation) {
		return x + ((rotation == 1 || rotation == 3 ? sizeY : sizeX) - 1) / 2;
	}

	public static int getCoordFaceY(int y, int sizeX, int sizeY, int rotation) {
		return y + ((rotation == 1 || rotation == 3 ? sizeX : sizeY) - 1) / 2;
	}

	public int getCoordFaceX(int sizeX, int sizeY, int rotation) {
		return x + ((rotation == 1 || rotation == 3 ? sizeY : sizeX) - 1) / 2;
	}

	public int getCoordFaceY(int sizeY) {
		return getCoordFaceY(-1, sizeY, -1);
	}

	public int getCoordFaceY(int sizeX, int sizeY, int rotation) {
		return y + ((rotation == 1 || rotation == 3 ? sizeX : sizeY) - 1) / 2;
	}

	public WorldTile getWorldTile() {
		return new WorldTile(this.x, this.y, this.plane);
	}

	public WorldTile transform(int x, int y, int plane) {
		return new WorldTile(this.x + x, this.y + y, this.plane + plane);
	}

	public void set(WorldTile tile) {
		this.x = tile.x;
		this.y = tile.y;
		this.plane = tile.plane;
	}

	/**
	 * Checks if this world tile's coordinates match the other world tile.
	 * 
	 * @param other
	 *            The world tile to compare with.
	 * @return {@code True} if so.
	 */
	public boolean matches(WorldTile other) {
		return x == other.x && y == other.y && plane == other.plane;
	}

	public boolean withinArea(int x1, int y1, int x2, int y2) {
		return getX() >= x1 && getY() >= y1 && getX() <= x2 && getY() <= y2;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public final void setLocation(WorldTile tile) {
		this.x = (short) tile.x;
		this.y = (short) tile.y;
		this.plane = (byte) tile.plane;
	}

	public final void setLocation(int x, int y, int plane) {
		this.x = (short) x;
		this.y = (short) y;
		this.plane = (byte) plane;
	}

	public void moveLocation(int xOffset, int yOffset, int planeOffset) {
		x += xOffset;
		y += yOffset;
		plane += planeOffset;
	}
	
	/**
     * returs the tile, idk why this is not here
     * @return
     */
    public WorldTile getTile() {
    	return new WorldTile(x,y,plane);
      }

}
