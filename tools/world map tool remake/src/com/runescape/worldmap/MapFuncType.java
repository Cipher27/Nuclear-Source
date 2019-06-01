package com.runescape.worldmap;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class MapFuncType extends DrawingArea {

	public void method45() {
		DrawingArea.setArea(anIntArray202, anInt203, anInt204);
	}

	public void draw(int onX, int onY) {
		onX += anInt205;
		onY += anInt206;
		int k = onX + onY * DrawingArea.width;
		int l = 0;
		int i1 = anInt204;
		int j1 = anInt203;
		int k1 = DrawingArea.width - j1;
		int l1 = 0;
		if (onY < DrawingArea.boundY) {
			int i2 = DrawingArea.boundY - onY;
			i1 -= i2;
			onY = DrawingArea.boundY;
			l += i2 * j1;
			k += i2 * DrawingArea.width;
		}
		if (onY + i1 > DrawingArea.extentY)
			i1 -= (onY + i1) - DrawingArea.extentY;
		if (onX < DrawingArea.boundX) {
			int j2 = DrawingArea.boundX - onX;
			j1 -= j2;
			onX = DrawingArea.boundX;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if (onX + j1 > DrawingArea.extentX) {
			int k2 = (onX + j1) - DrawingArea.extentX;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if (j1 <= 0 || i1 <= 0) {
			return;
		} else {
			method47(DrawingArea.pixels, anIntArray202, 0, l, k, j1, i1, k1, l1);
			return;
		}
	}

	public void method47(int ai[], int ai1[], int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if (i != 0)
					ai[k++] = i;
				else
					k++;
				i = ai1[j++];
				if (i != 0)
					ai[k++] = i;
				else
					k++;
				i = ai1[j++];
				if (i != 0)
					ai[k++] = i;
				else
					k++;
				i = ai1[j++];
				if (i != 0)
					ai[k++] = i;
				else
					k++;
			}

			for (int k2 = l; k2 < 0; k2++) {
				i = ai1[j++];
				if (i != 0)
					ai[k++] = i;
				else
					k++;
			}

			k += j1;
			j += k1;
		}

	}

	public MapFuncType(int i, int j) {
		anIntArray202 = new int[i * j];
		anInt203 = anInt207 = i;
		anInt204 = anInt208 = j;
		anInt205 = anInt206 = 0;
	}

	public MapFuncType(Archive archive, String filename, int groupId) {
		ByteBuffer file = new ByteBuffer(archive.getFile(filename + ".dat", null));
		ByteBuffer indexfile = new ByteBuffer(archive.getFile("index.dat", null));
		indexfile.offset = file.g2_u();
		anInt207 = indexfile.g2_u();
		anInt208 = indexfile.g2_u();
		int j = indexfile.g1_u();
		int ai[] = new int[j];
		for (int k = 0; k < j - 1; k++) {
			ai[k + 1] = indexfile.g3_u();
			if (ai[k + 1] == 0)
				ai[k + 1] = 1;
		}

		for (int ix = 0; ix < groupId; ix++) {
			indexfile.offset += 2;
			file.offset += indexfile.g2_u() * indexfile.g2_u();
			indexfile.offset++;
		}

		anInt205 = indexfile.g1_u();
		anInt206 = indexfile.g1_u();
		anInt203 = indexfile.g2_u();
		anInt204 = indexfile.g2_u();
		int i1 = indexfile.g1_u();
		int j1 = anInt203 * anInt204;
		anIntArray202 = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++)
				anIntArray202[k1] = ai[file.g1_u()];
		} else if (i1 == 1) {
			for (int l1 = 0; l1 < anInt203; l1++) {
				for (int i2 = 0; i2 < anInt204; i2++)
					anIntArray202[l1 + i2 * anInt203] = ai[file.g1_u()];
			}
		}
	}

	public void method48(int i, int j) {
		i += anInt205;
		j += anInt206;
		int k = i + j * DrawingArea.width;
		int l = 0;
		int i1 = anInt204;
		int j1 = anInt203;
		int k1 = DrawingArea.width - j1;
		int l1 = 0;
		if (j < DrawingArea.boundY) {
			int i2 = DrawingArea.boundY - j;
			i1 -= i2;
			j = DrawingArea.boundY;
			l += i2 * j1;
			k += i2 * DrawingArea.width;
		}
		if (j + i1 > DrawingArea.extentY)
			i1 -= (j + i1) - DrawingArea.extentY;
		if (i < DrawingArea.boundX) {
			int j2 = DrawingArea.boundX - i;
			j1 -= j2;
			i = DrawingArea.boundX;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if (i + j1 > DrawingArea.extentX) {
			int k2 = (i + j1) - DrawingArea.extentX;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if (j1 <= 0 || i1 <= 0) {
			return;
		} else {
			method49(DrawingArea.pixels, anIntArray202, l, k, j1, i1, k1, l1);
			return;
		}
	}

	public void method49(int ai[], int ai1[], int i, int j, int k, int l, int i1, int j1) {
		int k1 = -(k >> 2);
		k = -(k & 3);
		for (int l1 = -l; l1 < 0; l1++) {
			for (int i2 = k1; i2 < 0; i2++) {
				ai[j++] = ai1[i++];
				ai[j++] = ai1[i++];
				ai[j++] = ai1[i++];
				ai[j++] = ai1[i++];
			}

			for (int j2 = k; j2 < 0; j2++)
				ai[j++] = ai1[i++];

			j += i1;
			i += j1;
		}

	}

	public int anIntArray202[];
	public int anInt203;
	public int anInt204;
	public int anInt205;
	public int anInt206;
	public int anInt207;
	public int anInt208;
}
