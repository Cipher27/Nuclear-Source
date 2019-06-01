package com.teemuzz.adet;

/**
 * Represents a 64x64 game map area.
 * 
 * @author Teemu
 *
 */
public class Area {
	private byte[][] overlayTypes = new byte[64][64];
	private byte[][] overlayCutTypes = new byte[64][64];
	private byte[][] underlayTypes = new byte[64][64];
	private byte[][] tileRotations = new byte[64][64];
	private byte[][] tileFlags = new byte[64][64];

	public Area(int zOff, int xPos, int yPos) {

	}

	public byte[][] getOverlayTypes() {
		return overlayTypes;
	}

	public byte[][] getOverlayCutTypes() {
		return overlayCutTypes;
	}

	public byte[][] getUnderlayTypes() {
		return underlayTypes;
	}

	public byte[][] getTileRotations() {
		return tileRotations;
	}

	public byte[][] getTileFlags() {
		return tileFlags;
	}
}
