package com.runescape.worldmap;

/**
 * Class: Main.java
 * Originally: Applet_Sub1_Sub1.java
 * */

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;

import com.jagex.fs.IndexDecompressor;
import com.teemuzz.adet.Area;
import com.teemuzz.adet.def.Overlay;
import com.teemuzz.adet.def.OverlayLoader;
import com.teemuzz.adet.def.Underlay;
import com.teemuzz.adet.def.UnderlayLoader;
import com.vision.rs.DefinitionManager;
import com.vision.rs.Language;
import com.vision.rs.io.RSBuffer;

@SuppressWarnings("serial")
public class WorldMap extends RSApplet {
	public static DefinitionManager definitionManager;
	public static final Area[][][] areas = new Area[4][128][256];
	public static int drawPlane = 0;
	public static final int WIDTH = 1900, HEIGHT = 1000;
	private int mapCount;
	private int missingCount;

	private IndexDecompressor mapDecompressor;
	private boolean[][] xteaMissing = new boolean[256][256];
	private boolean showMissingXTEA = true;
	private int selectedX = 3200, selectedY = 3200;

	private static UnderlayLoader undrlayLoader;
	private static OverlayLoader overlayLoader;

	public static void main(String args[]) throws Exception {
		definitionManager = new DefinitionManager(true, Language.ENGLISH, "./cache/", false);

		IndexDecompressor ix2 = definitionManager.getDecompr(2);
		undrlayLoader = new UnderlayLoader(null, ix2);
		overlayLoader = new OverlayLoader(null, ix2);

		new WorldMap().createFrame(WIDTH, HEIGHT);
	}

	public void init() {
		initializeFrame(635, 503);
	}

	public void startUp() {
		drawLoading(10, "Please wait... Loading data");
		mapDecompressor = definitionManager.getDecompr(5);
		loadXTea();
		// TODO: LoadMaps
		drawLoading(20, "Please wait... Loading Map");
		loadMap();
		keyHeight = height - 33;// 503 -33
		Archive archive = getArchive();
		drawLoading(100, "Please wait... Rendering Map");
		ByteBuffer byteVector = new ByteBuffer(archive.getFile("size.dat", null));
		// DLCX, DLCY, URCX, URCY
		byteVector.g2_u();
		byteVector.g2_u();
		byteVector.g2_u();
		byteVector.g2_u();
		xViewPos = 3200;
		yViewPos = (urcy) - 3400;
		overviewWidth = (int) (width / 3.5);// 180;
		overviewHeight = (urcx * overviewWidth) / urcy;
		anInt154 = width - overviewHeight - 5;
		anInt155 = height - overviewWidth - 20;
		byteVector = new ByteBuffer(archive.getFile("labels.dat", null));
		labelCount = byteVector.g2_u();
		for (int i = 0; i < labelCount; i++) {
			labels[i] = byteVector.gjstr();
			labelX[i] = byteVector.g2_u();
			labelY[i] = byteVector.g2_u();
			labelSize[i] = byteVector.g1_u();
		}

		byteVector = new ByteBuffer(archive.getFile("floorcol.dat", null));
		int size = byteVector.g2_u();
		underlay_colors = new int[size + 1];
		overlay_colors = new int[size + 1];
		for (int ix = 0; ix < size; ix++) {
			underlay_colors[ix + 1] = byteVector.g4_u();
			overlay_colors[ix + 1] = byteVector.g4_u();
		}

		// byte underlay[] = archive.getFile("underlay.dat", null);
		// byte underlay_data[][] = new byte[urcx][urcy];
		// readUnderlay(underlay, underlay_data);
		// byte overlay[] = archive.getFile("overlay.dat", null);
		// overlay_result = new int[urcx][urcy];
		// overlayTypes = new byte[urcx][urcy];
		// readOverlay(overlay, overlay_result, overlayTypes);

		byte loc[] = archive.getFile("loc.dat", null);
		// wallTypes = new byte[urcx][urcy];
		// mapFuncSceneTypes = new byte[urcx][urcy];
		// sceneryTypes = new byte[urcx][urcy];
		// parseScenetypes(loc, wallTypes, mapFuncSceneTypes, sceneryTypes);

		try {
			for (int ix = 0; ix < 100; ix++)
				scenetypes[ix] = new MapSceneType(archive, "mapscene", ix);
		} catch (Exception _ex) {
		}
		try {
			for (int ix = 0; ix < 100; ix++)
				mapFunctions[ix] = new MapFuncType(archive, "mapfunction", ix);
		} catch (Exception _ex) {
		}
		mapTextDrawingArea = new TextDrawingArea(archive, "b12_full", false);
		aSprite_126 = new Sprite(11, true, this);// TODO: Fonts?
		aSprite_127 = new Sprite(12, true, this);
		aSprite_128 = new Sprite(14, true, this);
		aSprite_129 = new Sprite(17, true, this);
		aSprite_130 = new Sprite(19, true, this);
		aSprite_131 = new Sprite(22, true, this);
		aSprite_132 = new Sprite(26, true, this);
		aSprite_133 = new Sprite(30, true, this);

		// underlay_result = new int[urcx][urcy];
		// shadeUnderlay(underlay_data, underlay_result);
		overview = new MapFuncType(overviewHeight, overviewWidth);
		overview.method45();
		drawMap(0, 0, urcx, urcy, 0, 0, overviewHeight, overviewWidth);
		DrawingArea.drawRect(0, 0, overviewHeight, overviewWidth, 0);
		DrawingArea.drawRect(1, 1, overviewHeight - 2, overviewWidth - 2, boxTopColor);
		super.fullScreen.initializeDrawingArea();
	}

	private void loadMap() {
		int lastBiggestX = 0;
		int lastBiggestY = 0;
		int totalCount = 256 * 256;
		int currentCount = 0;
		int lowestX = 0;
		int lowestY = 0;
		for (int xArea = 0; xArea < 256; xArea++) {
			for (int yArea = 0; yArea < 256; yArea++) {
				if (mapDecompressor.fileExists("m" + xArea + "_" + yArea)) {
					int fileId = mapDecompressor.getFileId("m" + xArea + "_" + yArea);
					byte[] fileData = mapDecompressor.getFile(fileId, 0);
					if (fileData != null) {
						if (lowestX == 0 && lowestY == 0) {
							lowestX = xArea;
							lowestY = yArea;
						}
						if (lastBiggestX < xArea)
							lastBiggestX = xArea;
						if (lastBiggestY < yArea)
							lastBiggestY = yArea;
						parseMapData(fileData, xArea, yArea);// TODO
					} else {
						if (xteaMissing[xArea][yArea]) {
							System.out.println("XTEA key " + ((xArea << 8) + yArea) + " is fake.");
						}
					}
					mapDecompressor.reloadEntries(fileId);
				}
				currentCount++;
			}
			urcx = (lastBiggestX) << 6;
			urcy = (lastBiggestY + 3) << 6;
			double process = (currentCount * 100) / totalCount;
			int loadingStatus = (int) (20 + (process / 3));
			drawLoading(loadingStatus, "Loading map files - " + ((int) process) + "%");
		}
	}

	public void parseMapData(byte[] mapdata, int xPos, int yPos) {
		RSBuffer buffer = new RSBuffer(mapdata);
		for (int zOff = 0; zOff < 4; zOff++) {
			areas[zOff][xPos][yPos] = new Area(zOff, xPos, yPos);
			for (int xOff = 0; xOff < 64; xOff++) {
				for (int yOff = 0; yOff < 64; yOff++) {
					try {
						parseMap(xPos, yPos, zOff, xOff, yOff, buffer);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void parseMap(int xArea, int yArea, int zPosition, int xOff, int yOff, RSBuffer mapdata) {
		while (true) {
			int opcode = mapdata.getU();
			if (opcode == 0) {
				return;
			} else if (opcode == 1) {
				mapdata.get();
				return;
			}
			if (opcode <= 49) {
				Area area = areas[zPosition][xArea][yArea];
				area.getOverlayTypes()[xOff][yOff] = (byte) mapdata.get();
				area.getOverlayCutTypes()[xOff][yOff] = (byte) ((-2 + opcode) / 4);
				area.getTileRotations()[xOff][yOff] = (byte) ((-2 + opcode) & 3);
			} else if (opcode <= 81) {
				Area area = areas[zPosition][xArea][yArea];
				area.getTileFlags()[xOff][yOff] = (byte) (opcode - 49);
			} else {
				Area area = areas[zPosition][xArea][yArea];
				area.getUnderlayTypes()[xOff][yOff] = (byte) (opcode - 81);
			}
		}
	}

	public void shadeUnderlay(byte underlay_data[][], int underlay_result[][]) {
		int upCornerX = urcx;
		int upCornerY = urcy;
		int container[] = new int[upCornerY];
		for (int k = 0; k < upCornerY; k++)
			container[k] = 0;

		for (int xPos = 5; xPos < upCornerX - 5; xPos++) {
			byte next5y[] = underlay_data[xPos + 5];
			byte previous5y[] = underlay_data[xPos - 5];
			for (int i1 = 0; i1 < upCornerY; i1++)
				container[i1] += underlay_colors[next5y[i1] & 0xff] - underlay_colors[previous5y[i1] & 0xff];

			if (xPos > 10 && xPos < upCornerX - 10) {
				int baseRed = 0;
				int baseGreen = 0;
				int baseBlue = 0;
				int res[] = underlay_result[xPos];
				for (int yPos = 5; yPos < upCornerY - 5; yPos++) {
					int prevTile = container[yPos - 5];
					int nextTile = container[yPos + 5];
					baseRed += (nextTile >> 20) - (prevTile >> 20);
					baseGreen += (nextTile >> 10 & 0x3ff) - (prevTile >> 10 & 0x3ff);
					baseBlue += (nextTile & 0x3ff) - (prevTile & 0x3ff);
					if (baseBlue > 0)
						res[yPos] = fade((double) baseRed / 8533D, (double) baseGreen / 8533D,
								(double) baseBlue / 8533D);
				}
			}
		}
	}

	private void loadXTea() {
		int count = 0;
		for (int xArea = 0; xArea < 256; xArea++) {
			for (int yArea = 0; yArea < 256; yArea++) {
				if (mapDecompressor.fileExists("l" + xArea + "_" + yArea)) {
					xteaMissing[xArea][yArea] = true;
					count++;
				}
			}
		}
		mapCount = count;
		missingCount = count;
		for (File file : new File("./data/xtea/").listFiles()) {
			int arrayId = Integer.parseInt(file.getName().replaceAll(".txt", ""));
			int xArea = arrayId >> 8;
			int yArea = arrayId & 0xff;
			if (xteaMissing[xArea][yArea]) {
				xteaMissing[xArea][yArea] = false;
				missingCount--;
			}
		}
	}

	// public void parseScenetypes(byte data[], byte sceneTypes[][], byte
	// funcTypes[][], byte sceneryTypeIdContainer[][]) {
	// for (int pos = 0; pos < data.length;) {
	// int xPos = (data[pos++] & 0xff) * 64;
	// int yPosMap = (data[pos++] & 0xff) * 64;
	// if (xPos > 0 && yPosMap > 0 && xPos + 64 < urcx && yPosMap + 64 < urcy) {
	// for (int yPos = 0; yPos < 64; yPos++) {
	// byte locType[] = sceneTypes[yPos + xPos];
	// byte mapFuncTypes[] = funcTypes[yPos + xPos];
	// byte sceneryTypes[] = sceneryTypeIdContainer[yPos + xPos];
	// int aIx = urcy - yPosMap - 1;
	// for (int cyc = -64; cyc < 0; cyc++) {
	// do {
	// int type = data[pos++] & 0xff;
	// if (type == 0)
	// break;
	// if (type < 29)
	// locType[aIx] = (byte) type;
	// else if (type < 160) {
	// mapFuncTypes[aIx] = (byte) (type - 28);
	// } else {
	// sceneryTypes[aIx] = (byte) (type - 159);
	// anIntArray138[anInt137] = yPos + xPos;
	// anIntArray139[anInt137] = aIx;
	// locTypes[anInt137] = type - 160;
	// anInt137++;
	// }
	// } while (true);
	// aIx--;
	// }
	// }
	// } else {
	// for (int j1 = 0; j1 < 64; j1++) {
	// for (int k1 = -64; k1 < 0; k1++) {
	// byte skipType;
	// do
	// skipType = data[pos++];
	// while (skipType != 0);
	// }
	// }
	// }
	// }
	// }

	public void readUnderlay(byte source[], byte dest[][]) {
		for (int i = 0; i < source.length;) {
			int xPos = (source[i++] & 0xff) * 64;
			int yPos = (source[i++] & 0xff) * 64;
			if (xPos > 0 && yPos > 0 && xPos + 64 < urcx && yPos + 64 < urcy) {
				for (int l = 0; l < 64; l++) {
					byte destination[] = dest[l + xPos];
					int aIx = urcy - yPos - 1;
					for (int j1 = -64; j1 < 0; j1++)
						destination[aIx--] = source[i++];
				}
			} else {
				i += 4096;
			}
		}
	}

	public void readOverlay(byte fileData[], int overlayResult[][], byte tileTypes[][]) {
		for (int ix = 0; ix < fileData.length;) {
			int xPos = (fileData[ix++] & 0xff) * 64;
			int yPos = (fileData[ix++] & 0xff) * 64;
			if (xPos > 0 && yPos > 0 && xPos + 64 < urcx && yPos + 64 < urcy) {
				for (int tile = 0; tile < 64; tile++) {
					int result[] = overlayResult[tile + xPos];
					byte types[] = tileTypes[tile + xPos];
					int aIx = urcy - yPos - 1;
					for (int k1 = -64; k1 < 0; k1++) {
						byte idx = fileData[ix++];
						if (idx != 0) {
							types[aIx] = fileData[ix++];
							int overlayColor = 0;
							if (idx > 0)
								overlayColor = overlay_colors[idx];
							result[aIx--] = overlayColor;
						} else {
							result[aIx--] = 0;
						}
					}
				}
			} else {
				for (int i1 = -4096; i1 < 0; i1++) {
					byte byte1 = fileData[ix++];
					if (byte1 != 0)
						ix++;
				}
			}
		}
	}

	public int fade(double d, double d1, double d2) {
		double baseRed = d2;
		double baseGreen = d2;
		double baseBlue = d2;
		if (d1 != 0.0D) {
			double d6;
			if (d2 < 0.5D)
				d6 = d2 * (1.0D + d1);
			else
				d6 = (d2 + d1) - d2 * d1;
			double d7 = 2D * d2 - d6;
			double d8 = d + 0.33333333333333331D;
			if (d8 > 1.0D)
				d8--;
			double d9 = d;
			double d10 = d - 0.33333333333333331D;
			if (d10 < 0.0D)
				d10++;
			if (6D * d8 < 1.0D)
				baseRed = d7 + (d6 - d7) * 6D * d8;
			else if (2D * d8 < 1.0D)
				baseRed = d6;
			else if (3D * d8 < 2D)
				baseRed = d7 + (d6 - d7) * (0.66666666666666663D - d8) * 6D;
			else
				baseRed = d7;
			if (6D * d9 < 1.0D)
				baseGreen = d7 + (d6 - d7) * 6D * d9;
			else if (2D * d9 < 1.0D)
				baseGreen = d6;
			else if (3D * d9 < 2D)
				baseGreen = d7 + (d6 - d7) * (0.66666666666666663D - d9) * 6D;
			else
				baseGreen = d7;
			if (6D * d10 < 1.0D)
				baseBlue = d7 + (d6 - d7) * 6D * d10;
			else if (2D * d10 < 1.0D)
				baseBlue = d6;
			else if (3D * d10 < 2D)
				baseBlue = d7 + (d6 - d7) * (0.66666666666666663D - d10) * 6D;
			else
				baseBlue = d7;
		}
		int red = (int) (baseRed * 256D);
		int green = (int) (baseGreen * 256D);
		int blue = (int) (baseBlue * 256D);
		return (red << 16) + (green << 8) + blue;
	}

	public void clean() {
		try {
			underlay_colors = null;
			overlay_colors = null;
			// underlay_result = null;
			// overlay_result = null;
			// overlayTypes = null;
			// wallTypes = null;
			// sceneryTypes = null;
			mapFuncSceneTypes = null;
			scenetypes = null;
			mapFunctions = null;
			mapTextDrawingArea = null;
			mapFuncX = null;
			mapFuncY = null;
			mapFunctionID = null;
			anIntArray138 = null;
			anIntArray139 = null;
			locTypes = null;
			overview = null;
			labels = null;
			labelX = null;
			labelY = null;
			labelSize = null;
			iconExplainers = null;
			System.gc();
			return;
		} catch (Throwable _ex) {
			return;
		}
	}

	public void tick() {
		if (super.keyboardInput[1] == 1) {
			xViewPos = (int) ((double) xViewPos - 16D / distance);
			updateGraphics = true;
		}
		if (super.keyboardInput[2] == 1) {
			xViewPos = (int) ((double) xViewPos + 16D / distance);
			updateGraphics = true;
		}
		if (super.keyboardInput[3] == 1) {
			yViewPos = (int) ((double) yViewPos - 16D / distance);
			updateGraphics = true;
		}
		if (super.keyboardInput[4] == 1) {
			yViewPos = (int) ((double) yViewPos + 16D / distance);
			updateGraphics = true;
		}
		for (int keyInput = 1; keyInput > 0;) {
			keyInput = pollKeyboardInput();
			// if (keyInput != -1)
			// System.out.println(keyInput);
			if (keyInput == 49) {
				currentZoom = 3D;
				updateGraphics = true;
			}
			if (keyInput == 1008) {
				drawPlane = 0;
				updateGraphics = true;
			}
			if (keyInput == 1009) {
				drawPlane = 1;
				updateGraphics = true;
			}
			if (keyInput == 1010) {
				drawPlane = 2;
				updateGraphics = true;
			}
			if (keyInput == 1011) {
				drawPlane = 3;
				updateGraphics = true;
			}
			if (keyInput == 50) {
				currentZoom = 4D;
				updateGraphics = true;
			}
			if (keyInput == 51) {
				currentZoom = 6D;
				updateGraphics = true;
			}
			if (keyInput == 52) {
				currentZoom = 8D;
				updateGraphics = true;
			}
			if (keyInput == 53) {
				currentZoom = 16D;
				updateGraphics = true;
			}
			if (keyInput == 107 || keyInput == 75) {
				drawKey = !drawKey;
				updateGraphics = true;
			}
			if (keyInput == 111 || keyInput == 79) {
				drawOverview = !drawOverview;
				updateGraphics = true;
			}
			if (super.rsFrame != null && keyInput == 101) {
				System.out.println("Starting export...");
				MapFuncType mapfunc = new MapFuncType(urcx * 2, urcy * 2);
				mapfunc.method45();
				drawMap(0, 0, urcx, urcy, 0, 0, urcx * 2, urcy * 2);
				super.fullScreen.initializeDrawingArea();
				int pixelCount = mapfunc.anIntArray202.length;
				byte rawRgb[] = new byte[pixelCount * 3];
				int ix = 0;
				for (int l2 = 0; l2 < pixelCount; l2++) {
					int color = mapfunc.anIntArray202[l2];
					rawRgb[ix++] = (byte) (color >> 16);
					rawRgb[ix++] = (byte) (color >> 8);
					rawRgb[ix++] = (byte) color;
				}

				System.out.println("Saving to disk");
				try {
					BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
							new FileOutputStream("map-" + urcx * 2 + "-" + urcy * 2 + "-rgb.raw"));
					bufferedoutputstream.write(rawRgb);
					bufferedoutputstream.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				System.out.println("Done export: " + urcx * 2 + "," + urcy * 2);
			}
		}

		if (super.clickType == 1) {
			xClickPos = super.xClick;
			yClickPos = super.yClick;
			lastCenterX = xViewPos;
			lastCenterY = yViewPos;
			int xCorner = (lastCenterX) - (int) (WIDTH / distance);
			int yCorner = (urcy) - lastCenterY + (int) (HEIGHT / distance);
			// if (xCorner != 65535 && yCorner != 65535) {
			// int xClickCoord = (int) (xCorner + (xClickPos / (distance / 2)));
			// int yClickCoord = (int) (yCorner - (yClickPos / (distance / 2)));
			if (xCorner != 65535 && yCorner != 65535) {
				int xClickCoord = (int) (xCorner + (xClickPos / (distance / 2)));
				int yClickCoord = (int) (yCorner - (yClickPos / (distance / 2)));
				selectedX = xClickCoord;
				selectedY = yClickCoord;
				System.out.println("Click on tile: " + xClickCoord + " " + yClickCoord);
			}
			if (super.xClick > 170 && super.xClick < 220 && super.yClick > height - 32 && super.yClick < height) {
				currentZoom = 3D;
				xClickPos = -1;
			}
			if (super.xClick > 230 && super.xClick < 280 && super.yClick > height - 32 && super.yClick < height) {
				currentZoom = 4D;
				xClickPos = -1;
			}
			if (super.xClick > 290 && super.xClick < 340 && super.yClick > height - 32 && super.yClick < height) {
				currentZoom = 6D;
				xClickPos = -1;
			}
			if (super.xClick > 350 && super.xClick < 400 && super.yClick > height - 32 && super.yClick < height) {
				currentZoom = 8D;
				xClickPos = -1;
			}
			if (super.xClick > 410 && super.xClick < 460 && super.yClick > height - 32 && super.yClick < height) {
				currentZoom = 16D;
				xClickPos = -1;
			}
			if (super.xClick > xKeyDrawPos && super.yClick > yKeyDrawPos + keyHeight
					&& super.xClick < xKeyDrawPos + keyWidth && super.yClick < height) {
				drawKey = !drawKey;
				xClickPos = -1;
			}
			if (super.xClick > anInt154 && super.yClick > anInt155 + overviewWidth
					&& super.xClick < anInt154 + overviewHeight && super.yClick < height) {
				drawOverview = !drawOverview;
				xClickPos = -1;
			}
			if (drawKey) {
				if (super.xClick > xKeyDrawPos && super.yClick > yKeyDrawPos && super.xClick < xKeyDrawPos + keyWidth
						&& super.yClick < yKeyDrawPos + keyHeight)
					xClickPos = -1;
				if (super.xClick > xKeyDrawPos && super.yClick > yKeyDrawPos && super.xClick < xKeyDrawPos + keyWidth
						&& super.yClick < yKeyDrawPos + 18 && newKeyListIndex > 0)
					newKeyListIndex -= 25;
				if (super.xClick > xKeyDrawPos && super.yClick > (yKeyDrawPos + keyHeight) - 18
						&& super.xClick < xKeyDrawPos + keyWidth && super.yClick < yKeyDrawPos + keyHeight
						&& newKeyListIndex < 50)
					newKeyListIndex += 25;
			}
			updateGraphics = true;
		}
		if (drawKey) {
			anInt148 = -1;
			if (super.xDragged > xKeyDrawPos && super.xDragged < xKeyDrawPos + keyWidth) {
				int j = yKeyDrawPos + 21 + 5;
				for (int j1 = 0; j1 < (keyHeight / 18); j1++)// ??
					if (j1 + keyListIndex >= iconExplainers.length
							|| !iconExplainers[j1 + keyListIndex].equals("???")) {
						if (super.yDragged >= j && super.yDragged < j + 17) {
							anInt148 = j1 + keyListIndex;
							if (super.clickType == 1) {
								flashingLocType = j1 + keyListIndex;
								blinkTimer = 50;
							}
						}
						j += 17;
					}

			}
			if (anInt148 != anInt149) {
				anInt149 = anInt148;
				updateGraphics = true;
			}
		}
		if ((super.clickMode2 == 1 || super.clickType == 1) && drawOverview) {
			int k = super.xClick;
			int k1 = super.yClick;
			if (super.clickMode2 == 1) {
				k = super.xDragged;
				k1 = super.yDragged;
			}
			if (k > anInt154 && k1 > anInt155 && k < anInt154 + overviewHeight && k1 < anInt155 + overviewWidth) {
				xViewPos = ((k - anInt154) * urcx) / overviewHeight;
				yViewPos = ((k1 - anInt155) * urcy) / overviewWidth;
				xClickPos = -1;
				updateGraphics = true;
			}
		}
		if (super.clickMode2 == 1 && xClickPos != -1) {
			xViewPos = lastCenterX + (int) (((double) (xClickPos - super.xDragged) * 2D) / currentZoom);
			yViewPos = lastCenterY + (int) (((double) (yClickPos - super.yDragged) * 2D) / currentZoom);
			updateGraphics = true;
		}
		if (distance < currentZoom) {
			updateGraphics = true;
			distance += distance / 30D;
			if (distance > currentZoom)
				distance = currentZoom;
		}
		if (distance > currentZoom) {
			updateGraphics = true;
			distance -= distance / 30D;
			if (distance < currentZoom)
				distance = currentZoom;
		}
		if (keyListIndex < newKeyListIndex) {
			updateGraphics = true;
			keyListIndex++;
		}
		if (keyListIndex > newKeyListIndex) {
			updateGraphics = true;
			keyListIndex--;
		}
		if (blinkTimer > 0) {
			updateGraphics = true;
			blinkTimer--;
		}
		int l = xViewPos - (int) ((double) width / distance);
		int l1 = yViewPos - (int) ((double) height / distance);
		int i2 = xViewPos + (int) ((double) width / distance);
		int k2 = yViewPos + (int) ((double) height / distance);
		if (l < 0)
			xViewPos = (int) ((double) width / distance);
		if (l1 < 0)
			yViewPos = (int) ((double) height / distance);
		if (i2 > urcx)
			xViewPos = urcx - (int) ((double) width / distance);
		if (k2 > urcy)
			yViewPos = urcy - (int) ((double) height / distance);
	}

	public void process() {
		if (updateGraphics) {
			updateGraphics = false;
			redrawCounter = 0;
			DrawingArea.clear();
			int i = xViewPos - (int) ((double) width / distance);
			int j = yViewPos - (int) ((double) height / distance);
			int k = xViewPos + (int) ((double) width / distance);
			int l = yViewPos + (int) ((double) height / distance);
			drawMap(i, j, k, l, 0, 0, width, height);
			if (drawOverview) {
				overview.method48(anInt154, anInt155);
				DrawingArea.fillOpacRect(anInt154 + (overviewHeight * i) / urcx, anInt155 + (overviewWidth * j) / urcy,
						((k - i) * overviewHeight) / urcx, ((l - j) * overviewWidth) / urcy, 0xff0000, 128);
				DrawingArea.drawRect(anInt154 + (overviewHeight * i) / urcx, anInt155 + (overviewWidth * j) / urcy,
						((k - i) * overviewHeight) / urcx, ((l - j) * overviewWidth) / urcy, 0xff0000);
				if (blinkTimer > 0 && blinkTimer % 10 < 5) {
					for (int ix = 0; ix < anInt137; ix++)
						if (locTypes[ix] == flashingLocType) {
							int xScreenPos = anInt154 + (overviewHeight * anIntArray138[ix]) / urcx;
							int yScreenPos = anInt155 + (overviewWidth * anIntArray139[ix]) / urcy;
							DrawingArea.drawDot(xScreenPos, yScreenPos, 2, 0xffff00, 256);
						}
				}
			}
			if (drawKey) {
				createBox(xKeyDrawPos, yKeyDrawPos, keyWidth, 18, 0x999999, 0x777777, 0x555555, "Prev page");
				createBox(xKeyDrawPos, yKeyDrawPos + 18, keyWidth, keyHeight - 36, 0x999999, 0x777777, 0x555555, "");
				createBox(xKeyDrawPos, (yKeyDrawPos + keyHeight) - 18, keyWidth, 18, 0x999999, 0x777777, 0x555555,
						"Next page");
				int yPos = yKeyDrawPos + 3 + 18;
				for (int ix = 0; ix < (keyHeight / 18); ix++) {
					if (ix + keyListIndex < mapFunctions.length && ix + keyListIndex < iconExplainers.length) {
						if (iconExplainers[ix + keyListIndex].equals("???"))
							continue;
						mapFunctions[ix + keyListIndex].draw(xKeyDrawPos + 3, yPos);
						mapTextDrawingArea.drawString(iconExplainers[ix + keyListIndex], xKeyDrawPos + 21, yPos + 14,
								0);
						int j2 = 0xffffff;
						if (anInt148 == ix + keyListIndex)
							j2 = 0xbbaaaa;
						if (blinkTimer > 0 && blinkTimer % 10 < 5 && flashingLocType == ix + keyListIndex)
							j2 = 0xffff00;
						mapTextDrawingArea.drawString(iconExplainers[ix + keyListIndex], xKeyDrawPos + 20, yPos + 13,
								j2);
					}
					yPos += 17;
				}

			}
			createBox(anInt154, anInt155 + overviewWidth, overviewHeight, 18, boxTopColor, boxColor, boxBottomColor,
					"Overview");
			createBox(xKeyDrawPos, yKeyDrawPos + keyHeight, keyWidth, 18, boxTopColor, boxColor, boxBottomColor, "Key");
			if (currentZoom == 3D)
				createBox(170, height - 32, 50, 30, selectedTopColor, selectedBoxColor, selectedBottomColor, "37%");
			else
				createBox(170, height - 32, 50, 30, boxTopColor, boxColor, boxBottomColor, "37%");
			if (currentZoom == 4D)
				createBox(230, height - 32, 50, 30, selectedTopColor, selectedBoxColor, selectedBottomColor, "50%");
			else
				createBox(230, height - 32, 50, 30, boxTopColor, boxColor, boxBottomColor, "50%");
			if (currentZoom == 6D)
				createBox(290, height - 32, 50, 30, selectedTopColor, selectedBoxColor, selectedBottomColor, "75%");
			else
				createBox(290, height - 32, 50, 30, boxTopColor, boxColor, boxBottomColor, "75%");
			if (currentZoom == 8D)
				createBox(350, height - 32, 50, 30, selectedTopColor, selectedBoxColor, selectedBottomColor, "100%");
			else
				createBox(350, height - 32, 50, 30, boxTopColor, boxColor, boxBottomColor, "100%");
			if (currentZoom == 16D)
				createBox(410, height - 32, 50, 30, selectedTopColor, selectedBoxColor, selectedBottomColor, "200%");
			else
				createBox(410, height - 32, 50, 30, boxTopColor, boxColor, boxBottomColor, "200%");
		}
		redrawCounter--;
		if (redrawCounter <= 0) {
			super.fullScreen.drawGraphics(super.graphics, 0, 0);
			redrawCounter = 50;
		}
	}

	public void resetCounters() {
		redrawCounter = 0;
	}

	public void createBox(int drawWidth, int drawHeight, int width, int height, int topLineColor, int insideColor,
			int bottomLineColor, String text) {
		DrawingArea.drawRect(drawWidth, drawHeight, width, height, 0);
		drawWidth++;
		drawHeight++;
		width -= 2;
		height -= 2;
		DrawingArea.fillRect(drawWidth, drawHeight, width, height, insideColor);
		DrawingArea.drawHorizontal(drawWidth, drawHeight, width, topLineColor);
		DrawingArea.drawVertical(drawWidth, drawHeight, height, topLineColor);
		DrawingArea.drawHorizontal(drawWidth, (drawHeight + height) - 1, width, bottomLineColor);
		DrawingArea.drawVertical((drawWidth + width) - 1, drawHeight, height, bottomLineColor);
		mapTextDrawingArea.drawCenteredString(text, drawWidth + width / 2 + 1, drawHeight + height / 2 + 1 + 4, 0);
		mapTextDrawingArea.drawCenteredString(text, drawWidth + width / 2, drawHeight + height / 2 + 4, 0xffffff);
	}

	public void drawMap(int xOffset, int yOffset, int xEnd, int yEnd, int i1, int j1, int k1, int l1) {
		int xTiles = xEnd - xOffset;
		int yTiles = yEnd - yOffset;
		int xScale = (k1 - i1 << 16) / xTiles;
		int yScale = (l1 - j1 << 16) / yTiles;
		for (int xT = 0; xT < xTiles; xT++) {
			int xTile = xT + xOffset;
			int xArea = xTile >> 6;
			Area[] areas = new Area[] { null };
			if (xArea < WorldMap.areas[0].length)
				areas = WorldMap.areas[drawPlane][xArea];
			int xDraw = xScale * xT >> 16;
			int drawWidth = xScale * (xT + 1) >> 16;
			int j4 = drawWidth - xDraw;
			if (j4 > 0) {
				xDraw += i1;
				drawWidth += i1;
				// int underlayData[] = underlay_result[xT + xOffset];
				// int overlayData[] = overlay_result[xT + xOffset];
				// byte olTypes[] = overlayTypes[xT + xOffset];
				for (int yT = 0; yT < yTiles; yT++) {
					int yTile = yOffset + yT;
					yTile = urcy - (yTile + 1);
					int yArea = yTile >> 6;
					Area area = yArea < areas.length ? areas[yArea] : null;
					if (area != null) {
						int xLoc = xTile - (xArea << 6);
						int yLoc = yTile - (yArea << 6);
						// TODO:
						int underlayColor = 0;
						int underlayId = (area.getUnderlayTypes()[xLoc][yLoc] & 0xff) - 1;
						if (underlayId >= 0) {
							Underlay def = undrlayLoader.loadDefinition(underlayId);
							underlayColor = def.rawColor;
						} else {
							underlayColor = 0;
						}

						int overDrawColor = -1;
						int overlay = (area.getOverlayTypes()[xLoc][yLoc] & 0xff) - 1;
						if (overlay > 0) {
							Overlay def = overlayLoader.loadDefinition(overlay);
							overDrawColor = def.color;
							if (overDrawColor == 0xFF00FF)
								overDrawColor = 0;
						}

						int yDraw = yScale * yT >> 16;
						int drawHeight = yScale * (yT + 1) >> 16;
						int l9 = drawHeight - yDraw;
						if (l9 > 0) {
							yDraw += j1;
							drawHeight += j1;
							// overlayData[yT + yOffset];
							if (overlay == 0) {
								DrawingArea.fillRect(xDraw, yDraw, drawWidth - xDraw, drawHeight - yDraw,
										underlayColor);
							} else {
								byte cut = area.getOverlayCutTypes()[xLoc][yLoc];
								if (cut == 0 || j4 <= 1 || l9 <= 1)
									DrawingArea.fillRect(xDraw, yDraw, j4, l9,
											overDrawColor == -1 ? underlayColor : overDrawColor);
								else
									drawTile(DrawingArea.pixels, yDraw * DrawingArea.width + xDraw, underlayColor,
											overDrawColor, j4, l9, cut, area.getTileRotations()[xLoc][yLoc]);
							}
							if (xTile == selectedX && yTile == selectedY) {
								DrawingArea.fillRect(xDraw, yDraw, drawWidth - xDraw, drawHeight - yDraw, 0xFF0000);
							}
						}
					} else {
						int yDraw = yScale * yT >> 16;
						int drawHeight = yScale * (yT + 1) >> 16;
						int l9 = drawHeight - yDraw;
						if (l9 > 0) {
							yDraw += j1;
							drawHeight += j1;
							DrawingArea.fillRect(xDraw, yDraw, drawWidth - xDraw, drawHeight - yDraw, 0);
						}
						/*
						 * Old original code
						 * 
						 * int yDraw = yScale * yT >> 16; int drawHeight =
						 * yScale * (yT + 1) >> 16; int l9 = drawHeight - yDraw;
						 * if (l9 > 0) { yDraw += j1; drawHeight += j1; int
						 * olType = overlayData[yT + yOffset]; if (olType == 0)
						 * { DrawingArea.fillRect(xDraw, yDraw, drawWidth -
						 * xDraw, drawHeight - yDraw, underlayData[yT +
						 * yOffset]); } else { byte overlayType = olTypes[yT +
						 * yOffset]; int tilecut = overlayType & 0xfc; if
						 * (tilecut == 0 || j4 <= 1 || l9 <= 1)
						 * DrawingArea.fillRect(xDraw, yDraw, j4, l9, olType);
						 * else drawTile(DrawingArea.pixels, yDraw *
						 * DrawingArea.width + xDraw, underlayData[yT +
						 * yOffset], olType, j4, l9, tilecut >> 2, overlayType &
						 * 3); } if (xTile == yTile) {
						 * DrawingArea.fillRect(xDraw, yDraw, drawWidth - xDraw,
						 * drawHeight - yDraw, 0xFF0000); } }
						 * 
						 */
					}
				}
			}
		}

		if (xEnd - xOffset > k1 - i1)
			return;

		int k3 = 0;
		for (int xTile = 0; xTile < xTiles; xTile++)

		{
			int xDraw = xScale * xTile >> 16;
			int i5 = xScale * (xTile + 1) >> 16;
			int width = i5 - xDraw;
			if (width > 0) {
				// byte walls[] = wallTypes[xTile + xOffset];
				// byte sceneTypes[] = mapFuncSceneTypes[xTile + xOffset];
				// byte typeData[] = sceneryTypes[xTile + xOffset];
				// for (int ix = 0; ix < yTiles; ix++) {
				// int y = yScale * ix >> 16;
				// int i11 = yScale * (ix + 1) >> 16;
				// int height = i11 - y;
				// if (height > 0) {
				// int wallType = walls[ix + yOffset] & 0xff;
				// if (wallType != 0) {
				// int x;
				// if (width == 1)
				// x = xDraw;
				// else
				// x = i5 - 1;
				// int yDraw;
				// if (height == 1)
				// yDraw = y;
				// else
				// yDraw = i11 - 1;
				// int color = 0xcccccc;
				// if (wallType >= 5 && wallType <= 8 || wallType >= 13 &&
				// wallType <= 16
				// || wallType >= 21 && wallType <= 24 || wallType == 27 ||
				// wallType == 28) {
				// color = 0xcc0000;
				// wallType -= 4;
				// }
				// if (wallType == 1)
				// DrawingArea.drawVertical(xDraw, y, height, color);
				// else if (wallType == 2)
				// DrawingArea.drawHorizontal(xDraw, y, width, color);
				// else if (wallType == 3)
				// DrawingArea.drawVertical(x, y, height, color);
				// else if (wallType == 4)
				// DrawingArea.drawHorizontal(xDraw, yDraw, width, color);
				// else if (wallType == 9) {
				// DrawingArea.drawVertical(xDraw, y, height, 0xffffff);
				// DrawingArea.drawHorizontal(xDraw, y, width, color);
				// } else if (wallType == 10) {
				// DrawingArea.drawVertical(x, y, height, 0xffffff);
				// DrawingArea.drawHorizontal(xDraw, y, width, color);
				// } else if (wallType == 11) {
				// DrawingArea.drawVertical(x, y, height, 0xffffff);
				// DrawingArea.drawHorizontal(xDraw, yDraw, width, color);
				// } else if (wallType == 12) {
				// DrawingArea.drawVertical(xDraw, y, height, 0xffffff);
				// DrawingArea.drawHorizontal(xDraw, yDraw, width, color);
				// } else if (wallType == 17)
				// DrawingArea.drawHorizontal(xDraw, y, 1, color);
				// else if (wallType == 18)
				// DrawingArea.drawHorizontal(x, y, 1, color);
				// else if (wallType == 19)
				// DrawingArea.drawHorizontal(x, yDraw, 1, color);
				// else if (wallType == 20)
				// DrawingArea.drawHorizontal(xDraw, yDraw, 1, color);
				// else if (wallType == 25) {
				// for (int j14 = 0; j14 < height; j14++)
				// DrawingArea.drawHorizontal(xDraw + j14, yDraw - j14, 1,
				// color);
				//
				// } else if (wallType == 26) {
				// for (int k14 = 0; k14 < height; k14++)
				// DrawingArea.drawHorizontal(xDraw + k14, y + k14, 1, color);
				//
				// }
				// }
				// int sceneType = sceneTypes[ix + yOffset] & 0xff;
				// if (sceneType != 0)
				// scenetypes[sceneType - 1].draw(xDraw - width / 2, y - height
				// / 2, width * 2, height * 2);
				// int mapFuncId = typeData[ix + yOffset] & 0xff;
				// if (mapFuncId != 0) {
				// mapFunctionID[k3] = mapFuncId - 1;
				// mapFuncX[k3] = xDraw + width / 2;
				// mapFuncY[k3] = y + height / 2;
				// k3++;
				// }
				// }
				// }
			}
		}

		for (

		int index = 0; index < k3; index++)
			if (mapFunctions[mapFunctionID[index]] != null)
				mapFunctions[mapFunctionID[index]].draw(mapFuncX[index] - 7, mapFuncY[index] - 7);

		if (blinkTimer > 0)

		{// Flash icon.
			for (int j5 = 0; j5 < k3; j5++)
				if (mapFunctionID[j5] == flashingLocType) {
					mapFunctions[mapFunctionID[j5]].draw(mapFuncX[j5] - 7, mapFuncY[j5] - 7);
					if (blinkTimer % 10 < 5) {
						DrawingArea.drawDot(mapFuncX[j5], mapFuncY[j5], 15, 0xffff00, 128);
						DrawingArea.drawDot(mapFuncX[j5], mapFuncY[j5], 7, 0xffffff, 256);
					}
				}

		}
		if (distance == currentZoom && drawLabels)

		{
			for (int ix = 0; ix < labelCount; ix++) {
				int j6 = labelX[ix];
				int l6 = urcy - labelY[ix];
				int drawWidth = i1 + ((k1 - i1) * (j6 - xOffset)) / (xEnd - xOffset);
				int drawHeight = j1 + ((l1 - j1) * (l6 - yOffset)) / (yEnd - yOffset);
				int lblSize = labelSize[ix];
				int lblColor = 0xffffff;
				Sprite font = null;
				if (lblSize == 0) {
					if (distance == 3D)
						font = aSprite_126;
					if (distance == 4D)
						font = aSprite_127;
					if (distance == 6D)
						font = aSprite_128;
					if (distance == 8D)
						font = aSprite_129;
					if (distance == 16D)
						font = aSprite_130;
				}
				if (lblSize == 1) {
					if (distance == 3D)
						font = aSprite_128;
					if (distance == 4D)
						font = aSprite_129;
					if (distance == 6D)
						font = aSprite_130;
					if (distance == 8D)
						font = aSprite_131;
					if (distance == 16D)
						font = aSprite_132;
				}
				if (lblSize == 2) {
					lblColor = 0xffaa00;
					if (distance == 3D)
						font = aSprite_130;
					if (distance == 4D)
						font = aSprite_131;
					if (distance == 6D)
						font = aSprite_132;
					if (distance == 8D)
						font = aSprite_133;
					if (distance == 16D)
						font = aSprite_133;
				}
				if (font != null) {
					String label = labels[ix];
					int lines = 1;
					for (int ii = 0; ii < label.length(); ii++)
						if (label.charAt(ii) == '/')
							lines++;
					// TODO: Unsure
					drawHeight -= (font.height() * (lines - 1)) / 2;
					drawHeight += font.method44() / 2;
					do {
						int breaker = label.indexOf("/");
						if (breaker == -1) {
							font.drawCenteredString(label, drawWidth, drawHeight, lblColor, true);
							break;
						}
						String part = label.substring(0, breaker);
						font.drawCenteredString(part, drawWidth, drawHeight, lblColor, true);
						drawHeight += font.height();
						label = label.substring(breaker + 1);
					} while (true);
				}
			}

		}
		if (debugMode)

		{
			for (int xChunkId = 0; xChunkId < (urcx) / 64; xChunkId++) {
				for (int yChunkId = 0; yChunkId < (urcy) / 64; yChunkId++) {
					int xTileBase = xChunkId * 64;
					int yTileBase = urcy - yChunkId * 64;
					int xDraw = i1 + ((k1 - i1) * (xTileBase - xOffset)) / (xEnd - xOffset);
					int yDraw = j1 + ((l1 - j1) * (yTileBase - 64 - yOffset)) / (yEnd - yOffset);
					int boxWidth = i1 + ((k1 - i1) * ((xTileBase + 64) - xOffset)) / (xEnd - xOffset);
					int boxHeight = j1 + ((l1 - j1) * (yTileBase - yOffset)) / (yEnd - yOffset);
					DrawingArea.drawRect(xDraw, yDraw, boxWidth - xDraw, boxHeight - yDraw, 0xffffff);
					mapTextDrawingArea.drawRightAlignedString(xChunkId + "_" + yChunkId, boxWidth - 5, boxHeight - 5,
							0xffffff);
					if (xChunkId == 33 && yChunkId >= 71 && yChunkId <= 73)
						mapTextDrawingArea.drawCenteredString("u_pass", (boxWidth + xDraw) / 2, (boxHeight + yDraw) / 2,
								0xff0000);
				}
			}
		}
		if (showMissingXTEA)

		{
			for (int xChunkId = 0; xChunkId < (urcx) / 64; xChunkId++) {
				for (int yChunkId = 0; yChunkId < (urcy) / 64; yChunkId++) {
					int xTileBase = xChunkId * 64;
					int yTileBase = urcy - yChunkId * 64;
					int xDraw = i1 + ((k1 - i1) * (xTileBase - xOffset)) / (xEnd - xOffset);
					int yDraw = j1 + ((l1 - j1) * (yTileBase - 64 - yOffset)) / (yEnd - yOffset);
					int boxWidth = i1 + ((k1 - i1) * ((xTileBase + 64) - xOffset)) / (xEnd - xOffset);
					int boxHeight = j1 + ((l1 - j1) * (yTileBase - yOffset)) / (yEnd - yOffset);
					if (xteaMissing[xChunkId][yChunkId]) {
						DrawingArea.drawRect(xDraw, yDraw, boxWidth - xDraw, boxHeight - yDraw, 0xff0000);
						mapTextDrawingArea.drawCenteredString("MISSING", (boxWidth + xDraw) / 2,
								(boxHeight + yDraw) / 2, 0xffffff);
						mapTextDrawingArea.drawRightAlignedString("" + ((xChunkId << 8) + yChunkId), boxWidth - 5,
								boxHeight - 5, 0xffffff);
						DrawingArea.fillOpacRect(xDraw, yDraw, boxWidth - xDraw, boxHeight - yDraw, 0xff0000, 30);
					}
				}
			}
		}

	}

	public void drawTile(int pixels[], int index, int underlayType, int overlayType, int preferredWidth, int i1,
			int tileType, int tileRotation) {
		int block = DrawingArea.width - preferredWidth;
		if (tileType == 9) {
			tileType = 1;
			tileRotation = tileRotation + 1 & 3;
		}
		if (tileType == 10) {
			tileType = 1;
			tileRotation = tileRotation + 3 & 3;
		}
		if (tileType == 11) {
			tileType = 8;
			tileRotation = tileRotation + 3 & 3;
		}
		if (tileType == 1) {
			if (tileRotation == 0) {
				for (int i2 = 0; i2 < i1; i2++) {
					for (int i10 = 0; i10 < preferredWidth; i10++)
						if (i10 <= i2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;
					index += block;
				}
				return;
			}
			if (tileRotation == 1) {
				for (int j2 = i1 - 1; j2 >= 0; j2--) {
					for (int j10 = 0; j10 < preferredWidth; j10++)
						if (j10 <= j2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k2 = 0; k2 < i1; k2++) {
					for (int k10 = 0; k10 < preferredWidth; k10++)
						if (k10 >= k2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l2 = i1 - 1; l2 >= 0; l2--) {
					for (int l10 = 0; l10 < preferredWidth; l10++)
						if (l10 >= l2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			} else {
				return;
			}
		}
		if (tileType == 2) {
			if (tileRotation == 0) {
				for (int i3 = i1 - 1; i3 >= 0; i3--) {
					for (int i11 = 0; i11 < preferredWidth; i11++)
						if (i11 <= i3 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j3 = 0; j3 < i1; j3++) {
					for (int j11 = 0; j11 < preferredWidth; j11++)
						if (j11 >= j3 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k3 = 0; k3 < i1; k3++) {
					for (int k11 = preferredWidth - 1; k11 >= 0; k11--)
						if (k11 <= k3 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l3 = i1 - 1; l3 >= 0; l3--) {
					for (int l11 = preferredWidth - 1; l11 >= 0; l11--)
						if (l11 >= l3 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			} else {
				return;
			}
		}
		if (tileType == 3) {
			if (tileRotation == 0) {
				for (int i4 = i1 - 1; i4 >= 0; i4--) {
					for (int i12 = preferredWidth - 1; i12 >= 0; i12--)
						if (i12 <= i4 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j4 = i1 - 1; j4 >= 0; j4--) {
					for (int j12 = 0; j12 < preferredWidth; j12++)
						if (j12 >= j4 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k4 = 0; k4 < i1; k4++) {
					for (int k12 = 0; k12 < preferredWidth; k12++)
						if (k12 <= k4 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l4 = 0; l4 < i1; l4++) {
					for (int l12 = preferredWidth - 1; l12 >= 0; l12--)
						if (l12 >= l4 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			} else {
				return;
			}
		}
		if (tileType == 4) {
			if (tileRotation == 0) {
				for (int i5 = i1 - 1; i5 >= 0; i5--) {
					for (int i13 = 0; i13 < preferredWidth; i13++)
						if (i13 >= i5 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j5 = 0; j5 < i1; j5++) {
					for (int j13 = 0; j13 < preferredWidth; j13++)
						if (j13 <= j5 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k5 = 0; k5 < i1; k5++) {
					for (int k13 = preferredWidth - 1; k13 >= 0; k13--)
						if (k13 >= k5 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l5 = i1 - 1; l5 >= 0; l5--) {
					for (int l13 = preferredWidth - 1; l13 >= 0; l13--)
						if (l13 <= l5 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			} else {
				return;
			}
		}
		if (tileType == 5) {
			if (tileRotation == 0) {
				for (int i6 = i1 - 1; i6 >= 0; i6--) {
					for (int i14 = preferredWidth - 1; i14 >= 0; i14--)
						if (i14 >= i6 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j6 = i1 - 1; j6 >= 0; j6--) {
					for (int j14 = 0; j14 < preferredWidth; j14++)
						if (j14 <= j6 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k6 = 0; k6 < i1; k6++) {
					for (int k14 = 0; k14 < preferredWidth; k14++)
						if (k14 >= k6 >> 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l6 = 0; l6 < i1; l6++) {
					for (int l14 = preferredWidth - 1; l14 >= 0; l14--)
						if (l14 <= l6 << 1)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			} else {
				return;
			}
		}
		if (tileType == 6) {
			if (tileRotation == 0) {
				for (int i7 = 0; i7 < i1; i7++) {
					for (int i15 = 0; i15 < preferredWidth; i15++)
						if (i15 <= preferredWidth / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j7 = 0; j7 < i1; j7++) {
					for (int j15 = 0; j15 < preferredWidth; j15++)
						if (j7 <= i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k7 = 0; k7 < i1; k7++) {
					for (int k15 = 0; k15 < preferredWidth; k15++)
						if (k15 >= preferredWidth / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l7 = 0; l7 < i1; l7++) {
					for (int l15 = 0; l15 < preferredWidth; l15++)
						if (l7 >= i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
		}
		if (tileType == 7) {
			if (tileRotation == 0) {
				for (int i8 = 0; i8 < i1; i8++) {
					for (int i16 = 0; i16 < preferredWidth; i16++)
						if (i16 <= i8 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j8 = i1 - 1; j8 >= 0; j8--) {
					for (int j16 = 0; j16 < preferredWidth; j16++)
						if (j16 <= j8 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k8 = i1 - 1; k8 >= 0; k8--) {
					for (int k16 = preferredWidth - 1; k16 >= 0; k16--)
						if (k16 <= k8 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l8 = 0; l8 < i1; l8++) {
					for (int l16 = preferredWidth - 1; l16 >= 0; l16--)
						if (l16 <= l8 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
		}
		if (tileType == 8) {
			if (tileRotation == 0) {
				for (int i9 = 0; i9 < i1; i9++) {
					for (int i17 = 0; i17 < preferredWidth; i17++)
						if (i17 >= i9 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 1) {
				for (int j9 = i1 - 1; j9 >= 0; j9--) {
					for (int j17 = 0; j17 < preferredWidth; j17++)
						if (j17 >= j9 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 2) {
				for (int k9 = i1 - 1; k9 >= 0; k9--) {
					for (int k17 = preferredWidth - 1; k17 >= 0; k17--)
						if (k17 >= k9 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}

				return;
			}
			if (tileRotation == 3) {
				for (int l9 = 0; l9 < i1; l9++) {
					for (int l17 = preferredWidth - 1; l17 >= 0; l17--)
						if (l17 >= l9 - i1 / 2)
							pixels[index++] = overlayType;
						else
							pixels[index++] = underlayType;

					index += block;
				}
			}
		}
	}

	public Archive getArchive() {
		byte data[] = null;
		String cacheDir = null;
		try {
			cacheDir = findcachedir();
			data = FileData.ReadFile(cacheDir + "worldmap.dat");
			if (!shaValidate(data))
				data = null;
			if (data != null)
				return new Archive(data);
		} catch (Throwable _ex) {
		}
		data = request();
		if (cacheDir != null && data != null)
			try {
				write(cacheDir + "worldmap.dat", data);
			} catch (Throwable _ex) {
			}
		return new Archive(data);
	}

	public byte[] request() {
		drawLoading(0, "Requesting map");
		try {
			String digest = "";
			for (int i = 0; i < 10; i++)
				digest = digest + Constants.sha_digest[i];

			DataInputStream datainputstream = new DataInputStream(
					(new URL(getCodeBase(), "worldmap" + digest + ".dat")).openStream());
			int previousCompletion = 0;
			int offset = 0;
			int length = Constants.mapFileLength;
			byte data[] = new byte[length];
			while (offset < length) {
				int blocklen = length - offset;
				if (blocklen > 1000)
					blocklen = 1000;
				int readcount = datainputstream.read(data, offset, blocklen);
				if (readcount < 0) {
					datainputstream.close();
					throw new IOException("End of file");
				}
				offset += readcount;
				int completion = (offset * 100) / length;
				if (completion != previousCompletion)
					drawLoading(completion, "Loading map - " + completion + "%");
				previousCompletion = completion;
			}
			datainputstream.close();
			return data;
		} catch (IOException ioexception) {
			System.out.println("Error loading");
			ioexception.printStackTrace();
			return null;
		}
	}

	public String findcachedir() {
		String cache[] = { "c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/", "e:/winnt/",
				"f:/windows/", "f:/winnt/", "c:/", "~/", "/tmp/", "./data/" };
		String store = ".scapeemu_cache_32";
		for (int ix = 0; ix < cache.length; ix++)
			try {
				String root = cache[ix];
				if (root.length() > 0) {
					File rootFile = new File(root);
					if (!rootFile.exists())
						continue;
				}
				File file = new File(root + store);
				if (file.exists() || file.mkdir())
					return root + store + "/";
			} catch (Exception _ex) {

			}
		return null;
	}

	public byte[] read(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			return null;
		} else {
			int length = (int) file.length();
			byte buffer[] = new byte[length];
			DataInputStream datainputstream = new DataInputStream(
					new BufferedInputStream(new FileInputStream(filename)));
			datainputstream.readFully(buffer, 0, length);
			datainputstream.close();
			return buffer;
		}
	}

	public void write(String filename, byte data[]) throws IOException {
		FileOutputStream fileoutputstream = new FileOutputStream(filename);
		fileoutputstream.write(data, 0, data.length);
		fileoutputstream.close();
	}

	public boolean shaValidate(byte data[]) throws Exception {
		if (data == null)
			return false;
		MessageDigest messagedigest = MessageDigest.getInstance("SHA");
		messagedigest.reset();
		messagedigest.update(data);
		byte digest[] = messagedigest.digest();
		for (int i = 1; i < 20; i++) {
			if (digest[i] != Constants.sha_digest[i]) {
				return false;
			}
		}
		return true;
	}

	public WorldMap() {
		boxTopColor = 0x887755;
		boxColor = 0x776644;
		boxBottomColor = 0x665533;
		selectedTopColor = 0xaa0000;
		selectedBoxColor = 0x990000;
		selectedBottomColor = 0x880000;
		updateGraphics = true;
		scenetypes = new MapSceneType[100];
		mapFunctions = new MapFuncType[100];
		mapFuncX = new int[2000];
		mapFuncY = new int[2000];
		mapFunctionID = new int[2000];
		anIntArray138 = new int[2000];
		anIntArray139 = new int[2000];
		locTypes = new int[2000];
		xKeyDrawPos = 5;
		yKeyDrawPos = 13;
		keyWidth = 140;
		// keyHeight = 470;// 503 -33
		drawKey = false;
		anInt148 = -1;
		anInt149 = -1;
		flashingLocType = -1;
		drawOverview = false;
		anInt164 = 1000;
		labels = new String[anInt164];
		labelX = new int[anInt164];
		labelY = new int[anInt164];
		labelSize = new int[anInt164];
		distance = 6D;
		currentZoom = 6D;
	}

	public static boolean debugMode = true;
	public int boxTopColor;
	public int boxColor;
	public int boxBottomColor;
	public int selectedTopColor;
	public int selectedBoxColor;
	public int selectedBottomColor;
	public boolean updateGraphics;
	public int redrawCounter;
	public static int urcx;
	public static int urcy;
	public int underlay_colors[];
	public int overlay_colors[];

	public int underlay_result[][];
	public int overlay_result[][];
	public byte overlayTypes[][];
	// public byte wallTypes[][];
	// public byte sceneryTypes[][];
	public byte mapFuncSceneTypes[][];
	public MapSceneType scenetypes[];
	public MapFuncType mapFunctions[];
	public TextDrawingArea mapTextDrawingArea;
	public Sprite aSprite_126;
	public Sprite aSprite_127;
	public Sprite aSprite_128;
	public Sprite aSprite_129;
	public Sprite aSprite_130;
	public Sprite aSprite_131;
	public Sprite aSprite_132;
	public Sprite aSprite_133;
	public int mapFuncX[];
	public int mapFuncY[];
	public int mapFunctionID[];
	public int anInt137;
	public int anIntArray138[];
	public int anIntArray139[];
	public int locTypes[];
	public int xKeyDrawPos;
	public int yKeyDrawPos;
	public int keyWidth;
	public int keyHeight;
	public int keyListIndex;
	public int newKeyListIndex;
	public boolean drawKey;
	public int anInt148;
	public int anInt149;
	public int flashingLocType;
	public int blinkTimer;
	public int overviewWidth;
	public int overviewHeight;
	public int anInt154;
	public int anInt155;
	public boolean drawOverview;
	public MapFuncType overview;
	public int xClickPos;
	public int yClickPos;
	public int lastCenterX;
	public int lastCenterY;
	public static boolean drawLabels = true;
	public int labelCount;
	public int anInt164;
	public String labels[];
	public int labelX[];
	public int labelY[];
	public int labelSize[];
	public double distance;
	public double currentZoom;
	public static int xViewPos;
	public static int yViewPos;
	public String iconExplainers[] = { "General Store", "Sword Shop", "Magic Shop", "Axe Shop", "Helmet Shop", "Bank",
			"Quest Start", "Amulet Shop", "Mining Site", "Furnace", "Anvil", "Combat Training", "Dungeon", "Staff Shop",
			"Platebody Shop", "Platelegs Shop", "Scimitar Shop", "Archery Shop", "Shield Shop", "Altar", "Herbalist",
			"Jewelery", "Gem Shop", "Crafting Shop", "Candle Shop", "Fishing Shop", "Fishing Spot", "Clothes Shop",
			"Apothecary", "Silk Trader", "Kebab Seller", "Pub/Bar", "Mace Shop", "Tannery", "Rare Trees",
			"Spinning Wheel", "Food Shop", "Cookery Shop", "Mini-Game", "Water Source", "Cooking Range", "Skirt Shop",
			"Potters Wheel", "Windmill", "Mining Shop", "Chainmail Shop", "Silver Shop", "Fur Trader", "Spice Shop",
			"Agility Training", "Vegetable Store", "Slayer Master", "Hair Dressers", "Farming patch", "Makeover Mage",
			"Guide", "Transportation", "???", "Farming shop", "Loom", "Brewery" };

}
