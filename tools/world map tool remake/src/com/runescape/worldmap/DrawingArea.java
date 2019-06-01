package com.runescape.worldmap;
/**
 * Class: DrawingArea.java Originally: Class1_Sub1_Sub1.java
 * */

public class DrawingArea extends Node_Sub1 {

	public static void drawHorizontal(int x, int y, int drawWidth, int d) {
		if (y < boundY || y >= extentY)
			return;
		if (x < boundX) {
			drawWidth -= boundX - x;
			x = boundX;
		}
		if (x + drawWidth > extentX)
			drawWidth = extentX - x;
		int pixelIndex = x + y * width;
		for (int pixelOff = 0; pixelOff < drawWidth; pixelOff++)
			pixels[pixelIndex + pixelOff] = d;

	}

	public static void drawVertical(int x, int y, int height, int color) {
		if (x < boundX || x >= extentX)
			return;
		if (y < boundY) {
			height -= boundY - y;
			y = boundY;
		}
		if (y + height > extentY)
			height = extentY - y;
		int base = x + y * width;
		for (int draw = 0; draw < height; draw++)
			pixels[base + draw * width] = color;

	}

	public DrawingArea() {
	}

	public static void setBounds(int i, int j, int k, int l) {
		if (i < 0)
			i = 0;
		if (j < 0)
			j = 0;
		if (k > width)
			k = width;
		if (l > height)
			l = height;
		boundX = i;
		boundY = j;
		extentX = k;
		extentY = l;
	}

	public static void fillOpacRect(int i, int j, int k, int l, int i1, int j1) {
		if (i < boundX) {
			k -= boundX - i;
			i = boundX;
		}
		if (j < boundY) {
			l -= boundY - j;
			j = boundY;
		}
		if (i + k > extentX)
			k = extentX - i;
		if (j + l > extentY)
			l = extentY - j;
		int k1 = 256 - j1;
		int l1 = (i1 >> 16 & 0xff) * j1;
		int i2 = (i1 >> 8 & 0xff) * j1;
		int j2 = (i1 & 0xff) * j1;
		int j3 = width - k;
		int k3 = i + j * width;
		for (int l3 = 0; l3 < l; l3++) {
			for (int i4 = -k; i4 < 0; i4++) {
				int k2 = (pixels[k3] >> 16 & 0xff) * k1;
				int l2 = (pixels[k3] >> 8 & 0xff) * k1;
				int i3 = (pixels[k3] & 0xff) * k1;
				int j4 = ((l1 + k2 >> 8) << 16) + ((i2 + l2 >> 8) << 8)
						+ (j2 + i3 >> 8);
				pixels[k3++] = j4;
			}

			k3 += j3;
		}

	}

	public static void drawRect(int x, int y, int width, int l, int color) {
		drawHorizontal(x, y, width, color);
		drawHorizontal(x, (y + l) - 1, width, color);
		drawVertical(x, y, l, color);
		drawVertical((x + width) - 1, y, l, color);
	}

	/**
	 * Done
	 * 
	 * @param ai
	 * @param w
	 * @param h
	 */
	public static void setArea(int ai[], int w, int h) {
		pixels = ai;
		width = w;
		height = h;
		setBounds(0, 0, w, h);
	}

	public static void fillRect(int x, int y, int k, int l, int i1)// ?
	{
		if (x < boundX) {
			k -= boundX - x;
			x = boundX;
		}
		if (y < boundY) {
			l -= boundY - y;
			y = boundY;
		}
		if (x + k > extentX)
			k = extentX - x;
		if (y + l > extentY)
			l = extentY - y;
		int j1 = width - k;
		int k1 = x + y * width;
		for (int l1 = -l; l1 < 0; l1++) {
			for (int i2 = -k; i2 < 0; i2++)
				pixels[k1++] = i1;

			k1 += j1;
		}

	}

	public static void clear() {
		int i = width * height;
		for (int j = 0; j < i; j++)
			pixels[j] = 0;

	}

	public static void drawDot(int i, int j, int size, int color, int opacity) {
		int j1 = 256 - opacity;
		int k1 = (color >> 16 & 0xff) * opacity;
		int l1 = (color >> 8 & 0xff) * opacity;
		int i2 = (color & 0xff) * opacity;
		int i3 = j - size;
		if (i3 < 0)
			i3 = 0;
		int j3 = j + size;
		if (j3 >= height)
			j3 = height - 1;
		for (int k3 = i3; k3 <= j3; k3++) {
			int l3 = k3 - j;
			int i4 = (int) Math.sqrt(size * size - l3 * l3);
			int j4 = i - i4;
			if (j4 < 0)
				j4 = 0;
			int k4 = i + i4;
			if (k4 >= width)
				k4 = width - 1;
			int l4 = j4 + k3 * width;
			for (int i5 = j4; i5 <= k4; i5++) {
				int j2 = (pixels[l4] >> 16 & 0xff) * j1;
				int k2 = (pixels[l4] >> 8 & 0xff) * j1;
				int l2 = (pixels[l4] & 0xff) * j1;
				int j5 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8)
						+ (i2 + l2 >> 8);
				pixels[l4++] = j5;
			}
		}
	}

	public static int pixels[];
	public static int width;
	public static int height;
	public static int boundY = 0;
	public static int extentY = 0;
	public static int boundX = 0;
	public static int extentX = 0;
	public static int centerX;
	public static int centerY;

}
