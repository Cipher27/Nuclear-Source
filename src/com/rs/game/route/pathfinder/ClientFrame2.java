package com.rs.game.route.pathfinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;

import com.rs.cache.Cache;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.WorldTile;


public class ClientFrame2 extends JFrame implements MouseMotionListener, MouseListener, KeyListener {

	public static Dimension DEF_DIMENSION = new Dimension(1500, 1000);
	private static final long serialVersionUID = -8393191732938721333L;
	
	public List<Integer> regionIds = new ArrayList<Integer>();
	
	private Long lastPaintTime = System.currentTimeMillis(),
			lastGarbageCollect = System.currentTimeMillis();
	
	private static int ZOOM = 2;
	
	private int startX;
	private int startY;
	private int plane;
	
	private int mapWidth, mapHeight;

	public ClientFrame2() {
		super("Map Viewer 1.0");
	}
	
	public static void main(String[] args) throws Throwable {
		try {
			Cache.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClientFrame2 clientFrame = (new ClientFrame2()).open();
        clientFrame.start(3300, 3300, 0);
	}
	
	public ClientFrame2 open() {
		setResizable(false);
		setSize(DEF_DIMENSION);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setVisible(true);
		return this;
	}

	public void start(int startX, int startY, int plane) {
		this.startX = startX;
		this.startY = startY;
		this.plane = plane;
		setMapDimensions();
		//repaint();
		paint(getGraphics());
	}
	
	public void setMapDimensions() {
		this.mapWidth = (int) DEF_DIMENSION.getWidth() / ZOOM;
		this.mapHeight = (int) DEF_DIMENSION.getHeight() / ZOOM;
	}
	
	boolean first = true;

	@Override
	public void paint(Graphics g) {
		// ONLY PAINT EVERY 100 MS
		if (!first && lastPaintTime + 100 > System.currentTimeMillis())
			return;
		lastPaintTime = System.currentTimeMillis();
		// GARBAGE COLLECT
		if (lastGarbageCollect + 5000 > System.currentTimeMillis()) {
			
		} else {
			/*Map<Integer, Region> savedRegions = Collections
					.synchronizedMap(new HashMap<Integer, Region>());
			for(Entry<Integer, Region> entry : World.getRegions().entrySet()) {
				if (regionIds.contains(entry.getKey())) {
					
				}
			}*/
			Object[] array = World.getRegions().entrySet().toArray();
			for(Object object : array) {
				@SuppressWarnings("unchecked")
				Entry<Integer, Region> entry = (Entry<Integer, Region>) object;
				if (!regionIds.contains(entry.getKey())) {
					World.getRegions().remove(entry.getKey());
				}
			}
			//World.getRegions().clear();
			System.gc();
			lastGarbageCollect = System.currentTimeMillis();
		}
		if (first) {
			// LOAD BLACK BACKGROUND
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int) DEF_DIMENSION.getWidth(), (int) DEF_DIMENSION.getHeight());
			first = false;
		}
		// PUT WHITE DOTS IN CLIPPED AREAS AND BLACK DOTS IN NON-CLIPPED AREAS
		Color[][] colors = new Color[mapWidth][mapHeight];
		regionIds.clear();
		for (int x = getStartX(); x < getStartX() + mapWidth; x++) {
			for (int y = getStartY(); y < getStartY() + mapHeight; y++) {
				int regionId = new WorldTile(x, y, 0).getRegionId();
				final Region region = World.getRegion(regionId);
				region.loadRegionMap();
				regionIds.add(regionId);
				int localX = x - getStartX();
				int localY = y - getStartY();
				if (x < 0 || y < 0 || World.getMask(plane, x, y) > 0) {
					colors[localX][localY] = Color.WHITE;
				} else {
					colors[localX][localY] = Color.BLACK;
				}
			}
		}
		BufferedImage image = createImage(colors, mapWidth, mapHeight, true);
		// because Java likes to flip it's y coordinate system
		g.drawImage(image, 0, DEF_DIMENSION.height, DEF_DIMENSION.width, 0, image.getMinX(), image.getMinY(), image.getMinX()+image.getWidth(), image.getMinY()+image.getHeight(), null);
		g.setColor(Color.black);
		
		// SHOW COORDINATES AND ZOOM
		g.fillRect(0, 0, 130, 80);
		g.setColor(Color.GREEN);
		g.drawString(getStartX()+", "+getStartY()+", "+plane, 20, 50);
		g.drawString("Zoom: "+ZOOM, 25, 70);
	}
	
	public static BufferedImage createImage(Color[][] color, int width, int height, boolean isColor) {
	    BufferedImage ret;
	    if (isColor)
	        ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    else
	        ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	    WritableRaster raster = ret.getRaster();
	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            if (isColor) {
	                raster.setSample(j, i, 0, color[j][i].getRed());
	                raster.setSample(j, i, 1, color[j][i].getGreen());
	                raster.setSample(j, i, 2, color[j][i].getBlue());
	            }
	            else
	                raster.setSample(j, i, 0, color[j][i].getRed());
	        }
	    }
	    return ret;
	}
	
	public int getStartX() {
		return startX + dragX + totalDragX;
	}
	
	public int getStartY() {
		return startY + dragY + totalDragY;
	}
	
	public int getX(int mouseX) {
		int frameX = mouseX;
		int localX = frameX / ZOOM;
		int x = localX + getStartX();
		return x;
	}
	
	public int getY(int mouseY) {
		//because Java likes to flip it's y coordinate system
		int frameY = (int) (DEF_DIMENSION.getHeight() - mouseY);
		int localY = frameY / ZOOM;
		int y = localY + getStartY();
		return y;
	}
	
	public static int pressedX = 0, pressedY = 0, dragX = 0, dragY = 0, totalDragX = 0, totalDragY = 0;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Clicked at X: "+getX(arg0.getX())+" Y: "+getY(arg0.getY()));
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		pressedX = arg0.getX();
		pressedY = arg0.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		totalDragX += dragX;
		totalDragY += dragY;
		dragX = 0;
		dragY = 0;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		dragX = pressedX - arg0.getX();
		dragY = arg0.getY() - pressedY;
		paint(this.getGraphics());
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	public final static int W = 119, A = 97, S = 115, D = 100;
	
	public final static int ctrlW = 23, ctrlA = 1, ctrlS = 19, ctrlD = 4;
	
	public final static int PLUS = 43, MINUS = 45, MINUS2 = 31, BACKSLASH = 92, ONE = 49, TWO = 50, THREE = 51, FOUR = 52, FIVE = 53, SIX = 54, SEVEN = 55;
	
	public static boolean holdingW = false, holdingA = false, holdingS = false, holdingD = false, holdingMINUS = false, holdingPLUS = true, hotKeys = true;
	
	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyChar();
		switch(c) {
			case ctrlW:
			case W:
				holdingW = false;
				break;
			case ctrlA:
			case A:
				holdingA = false;
				break;
			case ctrlS:
			case S:
				holdingS = false;
				break;
			case ctrlD:
			case D:
				holdingD = false;
				break;
			case PLUS:
				ZOOM++;
				setMapDimensions();
				paint(getGraphics());
				break;
			case MINUS:
				if (ZOOM == 1)
					return;
				ZOOM--;
				setMapDimensions();
				paint(getGraphics());
				break;
			case MINUS2:
				if (ZOOM == 1)
					return;
				ZOOM--;
				setMapDimensions();
				paint(getGraphics());
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int c = e.getKeyChar();
		switch(c) {
			case ctrlW:
			case W:
				if (!holdingW)
					holdingW = true;
				break;
			case ctrlA:
			case A:
				if (!holdingA)
					holdingA = true;
				break;
			case ctrlS:
			case S:
				if (!holdingS)
					holdingS = true;
				break;
			case ctrlD:
			case D:
				if (!holdingD)
					holdingD = true;
				break;
			case PLUS:
				if (!holdingPLUS)
					holdingPLUS = true;
				break;
			case MINUS:
				if (!holdingMINUS)
					holdingMINUS = true;
				break;
		}
		if (holdingW || holdingA || holdingS || holdingD) {
			process();
		}
	}
	
	public static long lastProcess;
	
	public void process() {
		if (lastProcess < System.currentTimeMillis()) {
			lastProcess = System.currentTimeMillis() + 100;
			int xMove = 0;
			int yMove = 0;
			if (holdingW) {
				yMove = 10;
			} else if (holdingA) {
				xMove = -10;
			}
			if (holdingS) {
				yMove = -10;
			} else if (holdingD) {
				xMove = 10;
			}
			if (xMove != 0 || yMove != 0) {
				totalDragX += xMove;
				totalDragY += yMove;
				paint(getGraphics());
			}
		}
	}
}