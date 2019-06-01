package com.runescape.worldmap;
/**
 * Class: RSFrame.java
 * Originally: Frame_Sub1.java
 * */

import java.awt.*;

@SuppressWarnings("serial")
public class RSFrame extends Frame {

	@SuppressWarnings("deprecation")
	public RSFrame(RSApplet applet_sub1, int i, int j) {
		anRSApplet_32 = applet_sub1;
		setTitle("World Map");
		setResizable(false);
		show();
		toFront();
		resize(i + 8, j + 28);
	}

	public void paint(Graphics g) {
		anRSApplet_32.paint(g);
	}

	public void update(Graphics g) {
		anRSApplet_32.update(g);
	}

	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
	}

	public RSApplet anRSApplet_32;
}
