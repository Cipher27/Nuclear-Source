package com.runescape.worldmap;

/**
 * Class: RSApplet.java
 * Originally: Applet_Sub1.java
 * */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class RSApplet extends Applet
		implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

	public void stop() {
		if (exitCounter >= 0)
			exitCounter = 4000 / delayTime;
	}

	public void resetCounters() {

	}

	public void drawLoading(int process, String text) {
		while (graphics == null) {
			graphics = getGameComponent().getGraphics();
			try {
				getGameComponent().repaint();
			} catch (Exception exception) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception exception1) {
			}
		}
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = getGameComponent().getFontMetrics(font);
		Font font1 = new Font("Helvetica", 0, 13);
		@SuppressWarnings("unused")
		FontMetrics fontmetrics1 = getGameComponent().getFontMetrics(font1);
		if (shouldRedraw) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, width, height);
			shouldRedraw = false;
		}
		Color color = new Color(140, 17, 17);
		int y = height / 2 - 18;
		graphics.setColor(color);
		graphics.drawRect(width / 2 - 152, y, 304, 34);
		graphics.fillRect(width / 2 - 150, y + 2, process * 3, 30);
		graphics.setColor(Color.black);
		graphics.fillRect((width / 2 - 150) + process * 3, y + 2, 300 - process * 3, 30);
		graphics.setFont(font);
		graphics.setColor(Color.white);
		graphics.drawString(text, (width - fontmetrics.stringWidth(text)) / 2, y + 22);
	}

	public void mouseReleased(MouseEvent mouseevent) {
		idleTime = 0;
		clickMode2 = 0;
	}

	public void keyPressed(KeyEvent keyevent) {
		idleTime = 0;
		int keycode = keyevent.getKeyCode();
		int keyChar = keyevent.getKeyChar();
		if (keyChar < 30)
			keyChar = 0;
		if (keycode == 37)
			keyChar = 1;
		if (keycode == 39)
			keyChar = 2;
		if (keycode == 38)
			keyChar = 3;
		if (keycode == 40)
			keyChar = 4;
		if (keycode == 17)
			keyChar = 5;
		if (keycode == 8)
			keyChar = 8;
		if (keycode == 127)
			keyChar = 8;
		if (keycode == 9)
			keyChar = 9;
		if (keycode == 10)
			keyChar = 10;
		if (keycode >= 112 && keycode <= 123)
			keyChar = (1008 + keycode) - 112;
		if (keycode == 36)
			keyChar = 1000;
		if (keycode == 35)
			keyChar = 1001;
		if (keycode == 33)
			keyChar = 1002;
		if (keycode == 34)
			keyChar = 1003;
		if (keyChar > 0 && keyChar < 128)
			keyboardInput[keyChar] = 1;
		if (keyChar > 4) {
			charQueue[writeIndex] = keyChar;
			writeIndex = writeIndex + 1 & 0x7f;
		}
	}

	public void startRunnable(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	public void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	public void exit() {
		exitCounter = -2;
		clean();
		if (rsFrame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			try {
				System.exit(0);
			} catch (Throwable throwable) {
			}
		}
	}

	public void update(Graphics g) {
		if (graphics == null)
			graphics = g;
		shouldRedraw = true;
		resetCounters();
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
		idleTime = 0;
		xDragged = -1;
		yDragged = -1;
	}

	public void windowOpened(WindowEvent windowevent) {
	}

	public void windowDeiconified(WindowEvent windowevent) {
	}

	public void windowActivated(WindowEvent windowevent) {
	}

	public void startUp() {
	}

	public void start() {
		if (exitCounter >= 0)
			exitCounter = 0;
	}

	public void createFrame(int width, int height) {
		this.width = width;
		this.height = height;
		rsFrame = new RSFrame(this, width, height);
		graphics = getGameComponent().getGraphics();
		fullScreen = new RSImageProducer(width, height, getGameComponent());
		startRunnable(this, 1);
	}

	public int pollKeyboardInput() {
		int i = -1;
		if (writeIndex != readIndex) {
			i = charQueue[readIndex];
			readIndex = readIndex + 1 & 0x7f;
		}
		return i;
	}

	public void process() {
	}

	public Component getGameComponent() {
		if (rsFrame != null)
			return rsFrame;
		else
			return this;
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();
		if (rsFrame != null) {
			x -= 4;
			y -= 22;
		}
		idleTime = 0;
		xPressed = x;
		yPressed = y;
		clickTime = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			clickMode1 = 2;
			clickMode2 = 2;
		} else {
			clickMode1 = 1;
			clickMode2 = 1;
		}
	}

	public void mouseDragged(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();
		if (rsFrame != null) {
			x -= 4;// Didn't these fuck something up?
			y -= 22;
		}
		idleTime = 0;
		xDragged = x;
		yDragged = y;
	}

	public void initializeFrame(int width, int height) {
		this.width = width;
		this.height = height;
		graphics = getGameComponent().getGraphics();
		fullScreen = new RSImageProducer(width, height, getGameComponent());
		startRunnable(this, 1);
	}

	public void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (rsFrame != null) {
			i -= 4;
			j -= 22;
		}
		idleTime = 0;
		xDragged = i;
		yDragged = j;
	}

	public void keyTyped(KeyEvent keyevent) {
	}

	public void windowDeactivated(WindowEvent windowevent) {
	}

	public void paint(Graphics g) {
		if (graphics == null)
			graphics = g;
		shouldRedraw = true;
		resetCounters();
	}

	public void destroy() {
		exitCounter = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception exception) {
		}
		if (exitCounter == -1)
			exit();
	}

	public void clean() {
	}

	public RSApplet() {
		exitCounter = 0;
		delayTime = 20;
		minDelay = 1;
		aLongArray4 = new long[10];
		fps = 0;
		debug = false;
		mapFuncTypes = new MapFuncType[6];
		shouldRedraw = true;
		awtFocus = true;
		idleTime = 0;
		clickMode2 = 0;
		xDragged = 0;
		yDragged = 0;
		clickMode1 = 0;
		xPressed = 0;
		yPressed = 0;
		clickTime = 0L;
		clickType = 0;
		xClick = 0;
		yClick = 0;
		aLong26 = 0L;
		keyboardInput = new int[128];
		charQueue = new int[128];
		readIndex = 0;
		writeIndex = 0;
	}

	public void tick() {
	}

	public void focusLost(FocusEvent focusevent) {
		awtFocus = false;
		for (int i = 0; i < 128; i++)
			keyboardInput[i] = 0;

	}

	public void keyReleased(KeyEvent keyevent) {
		idleTime = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036')
			c = '\0';
		if (i == 37)
			c = '\001';
		if (i == 39)
			c = '\002';
		if (i == 38)
			c = '\003';
		if (i == 40)
			c = '\004';
		if (i == 17)
			c = '\005';
		if (i == 8)
			c = '\b';
		if (i == 127)
			c = '\b';
		if (i == 9)
			c = '\t';
		if (i == 10)
			c = '\n';
		if (c > 0 && c < '\200')
			keyboardInput[c] = 0;
	}

	public void windowClosed(WindowEvent windowevent) {
	}

	public void run() {
		getGameComponent().addMouseListener(this);
		getGameComponent().addMouseMotionListener(this);
		getGameComponent().addKeyListener(this);
		getGameComponent().addFocusListener(this);
		if (rsFrame != null)
			rsFrame.addWindowListener(this);
		drawLoading(0, "Loading...");
		startUp();
		int i = 0;
		int frameRatio = 256;
		int delay = 1;
		int frameCount = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < 10; k1++)
			aLongArray4[k1] = System.currentTimeMillis();

		while (exitCounter >= 0) {
			if (exitCounter > 0) {
				exitCounter--;
				if (exitCounter == 0) {
					exit();
					return;
				}
			}
			int i2 = frameRatio;
			int j2 = delay;
			frameRatio = 300;
			delay = 1;
			long time = System.currentTimeMillis();
			if (aLongArray4[i] == 0L) {
				frameRatio = i2;
				delay = j2;
			} else if (time > aLongArray4[i])
				frameRatio = (int) ((long) (2560 * delayTime) / (time - aLongArray4[i]));
			if (frameRatio < 25)
				frameRatio = 25;
			if (frameRatio > 256) {
				frameRatio = 256;
				delay = (int) ((long) delayTime - (time - aLongArray4[i]) / 10L);
			}
			if (delay > delayTime)
				delay = delayTime;
			aLongArray4[i] = time;
			i = (i + 1) % 10;
			if (delay > 1) {
				for (int k2 = 0; k2 < 10; k2++)
					if (aLongArray4[k2] != 0L)
						aLongArray4[k2] += delay;

			}
			if (delay < minDelay)
				delay = minDelay;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException interruptedexception) {
				j1++;
			}
			for (; frameCount < 256; frameCount += frameRatio) {
				clickType = clickMode1;
				xClick = xPressed;
				yClick = yPressed;
				aLong26 = clickTime;
				clickMode1 = 0;
				tick();
				readIndex = writeIndex;
			}

			frameCount &= 0xff;
			if (delayTime > 0)
				fps = (1000 * frameRatio) / (delayTime * 256);
			process();
			if (debug) {
				System.out.println("ntime:" + time);
				for (int l2 = 0; l2 < 10; l2++) {
					int i3 = ((i - l2 - 1) + 20) % 10;
					System.out.println("otim" + i3 + ":" + aLongArray4[i3]);
				}

				System.out.println("fps:" + fps + " ratio:" + frameRatio + " count:" + frameCount);
				System.out.println("del:" + delay + " deltime:" + delayTime + " mindel:" + minDelay);
				System.out.println("intex:" + j1 + " opos:" + i);
				debug = false;
				j1 = 0;
			}
		}
		if (exitCounter == -1)
			exit();
	}

	public void focusGained(FocusEvent focusevent) {
		awtFocus = true;
		shouldRedraw = true;
		resetCounters();
	}

	public void windowIconified(WindowEvent windowevent) {
	}

	public int exitCounter;
	public int delayTime;
	public int minDelay;
	public long aLongArray4[];
	public int fps;
	public boolean debug;
	public int width;
	public int height;
	public Graphics graphics;
	public RSImageProducer fullScreen;
	public MapFuncType mapFuncTypes[];
	public RSFrame rsFrame;
	public boolean shouldRedraw;
	public boolean awtFocus;
	public int idleTime;

	public int clickMode2;
	public int xDragged;
	public int yDragged;

	public int clickMode1;
	public int xPressed;
	public int yPressed;
	public long clickTime;
	public int clickType;
	public int xClick;
	public int yClick;
	public long aLong26;
	public int keyboardInput[];
	public int charQueue[];
	public int readIndex;
	public int writeIndex;
	public static boolean aBoolean31;
}
