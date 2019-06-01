package com.runescape.worldmap;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class TextDrawingArea extends DrawingArea {

	public int getTextPixelWidth(String s) {
		if (s == null)
			return 0;
		int width = 0;
		for (int index = 0; index < s.length(); index++)
			width += charWidths[s.charAt(index)];
		return width;
	}

	public void drawString(String s, int width, int height, int k) {
		if (s == null)
			return;
		height -= lastKnownHeight;
		for (int l = 0; l < s.length(); l++) {
			char c = s.charAt(l);
			if (c != ' ')
				method56(indexedPixels[c], width + anIntArray220[c], height
						+ anIntArray221[c], widths[c], heights[c], k);
			width += charWidths[c];
		}
	}

	public TextDrawingArea(Archive archive, String fontFile, boolean flag) {
		indexedPixels = new byte[256][];
		widths = new int[256];
		heights = new int[256];
		anIntArray220 = new int[256];
		anIntArray221 = new int[256];
		charWidths = new int[256];
		lastKnownHeight = 0;
		aBoolean225 = false;
		ByteBuffer class1_sub1_sub2 = new ByteBuffer(archive.getFile(fontFile
				+ ".dat", null));
		ByteBuffer index = new ByteBuffer(archive.getFile("index.dat", null));
		index.offset = class1_sub1_sub2.g2_u() + 4;
		int j = index.g1_u();
		if (j > 0)
			index.offset += 3 * (j - 1);
		for (int idx = 0; idx < 256; idx++) {
			anIntArray220[idx] = index.g1_u();
			anIntArray221[idx] = index.g1_u();
			int width = widths[idx] = index.g2_u();
			int height = heights[idx] = index.g2_u();
			int j1 = index.g1_u();
			int pixelCount = width * height;
			indexedPixels[idx] = new byte[pixelCount];
			if (j1 == 0) {
				for (int pixelIdx = 0; pixelIdx < pixelCount; pixelIdx++)
					indexedPixels[idx][pixelIdx] = class1_sub1_sub2.g1();

			} else if (j1 == 1) {
				for (int i2 = 0; i2 < width; i2++) {
					for (int k2 = 0; k2 < height; k2++)
						indexedPixels[idx][i2 + k2 * width] = class1_sub1_sub2
								.g1();

				}

			}
			if (height > lastKnownHeight && idx < 128)
				lastKnownHeight = height;
			anIntArray220[idx] = 1;
			charWidths[idx] = width + 2;
			int j2 = 0;
			for (int l2 = height / 7; l2 < height; l2++)
				j2 += indexedPixels[idx][l2 * width];

			if (j2 <= height / 7) {
				charWidths[idx]--;
				anIntArray220[idx] = 0;
			}
			j2 = 0;
			for (int i3 = height / 7; i3 < height; i3++)
				j2 += indexedPixels[idx][(width - 1) + i3 * width];

			if (j2 <= height / 7)
				charWidths[idx]--;
		}

		if (flag)
			charWidths[32] = charWidths[73];
		else
			charWidths[32] = charWidths[105];
	}

	public void drawRightAlignedString(String s, int width, int height, int k) {
		drawString(s, width - getTextPixelWidth(s), height, k);
	}

	public void drawCenteredString(String str, int width, int height, int k) {
		drawString(str, width - getTextPixelWidth(str) / 2, height, k);
	}

	public void method56(byte abyte0[], int i, int j, int k, int l, int i1) {
		int j1 = i + j * DrawingArea.width;
		int k1 = DrawingArea.width - k;
		int l1 = 0;
		int i2 = 0;
		if (j < DrawingArea.boundY) {
			int j2 = DrawingArea.boundY - j;
			l -= j2;
			j = DrawingArea.boundY;
			i2 += j2 * k;
			j1 += j2 * DrawingArea.width;
		}
		if (j + l >= DrawingArea.extentY)
			l -= ((j + l) - DrawingArea.extentY) + 1;
		if (i < DrawingArea.boundX) {
			int k2 = DrawingArea.boundX - i;
			k -= k2;
			i = DrawingArea.boundX;
			i2 += k2;
			j1 += k2;
			l1 += k2;
			k1 += k2;
		}
		if (i + k >= DrawingArea.extentX) {
			int l2 = ((i + k) - DrawingArea.extentX) + 1;
			k -= l2;
			l1 += l2;
			k1 += l2;
		}
		if (k <= 0 || l <= 0) {
			return;
		} else {
			method57(DrawingArea.pixels, abyte0, i1, i2, j1, k, l, k1, l1);
			return;
		}
	}

	public void method57(int pxls[], byte abyte0[], int i, int j, int k, int l,
			int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				if (abyte0[j++] != 0)
					pxls[k++] = i;
				else
					k++;
				if (abyte0[j++] != 0)
					pxls[k++] = i;
				else
					k++;
				if (abyte0[j++] != 0)
					pxls[k++] = i;
				else
					k++;
				if (abyte0[j++] != 0)
					pxls[k++] = i;
				else
					k++;
			}

			for (int k2 = l; k2 < 0; k2++)
				if (abyte0[j++] != 0)
					pxls[k++] = i;
				else
					k++;

			k += j1;
			j += k1;
		}

	}

	public byte indexedPixels[][];
	public int widths[];
	public int heights[];
	public int anIntArray220[];
	public int anIntArray221[];
	public int charWidths[];
	public int lastKnownHeight;
	public boolean aBoolean225;
}
