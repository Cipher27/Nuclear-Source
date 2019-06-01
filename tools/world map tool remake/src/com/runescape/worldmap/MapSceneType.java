package com.runescape.worldmap;
public class MapSceneType extends DrawingArea {

	public MapSceneType(Archive archive, String type, int fileIndex) {
		ByteBuffer parent = new ByteBuffer(archive.getFile(type + ".dat", null));
		ByteBuffer indexfile = new ByteBuffer(archive.getFile("index.dat", null));
		indexfile.offset = parent.g2_u();
		anInt215 = indexfile.g2_u();
		anInt216 = indexfile.g2_u();
		int j = indexfile.g1_u();
		anIntArray210 = new int[j];
		for (int k = 0; k < j - 1; k++)
			anIntArray210[k + 1] = indexfile.g3_u();

		for (int l = 0; l < fileIndex; l++) {
			indexfile.offset += 2;
			parent.offset += indexfile.g2_u() * indexfile.g2_u();
			indexfile.offset++;
		}

		anInt213 = indexfile.g1_u();
		anInt214 = indexfile.g1_u();
		anInt211 = indexfile.g2_u();
		anInt212 = indexfile.g2_u();
		int i1 = indexfile.g1_u();
		int j1 = anInt211 * anInt212;
		aByteArray209 = new byte[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++)
				aByteArray209[k1] = parent.g1();
		} else if (i1 == 1) {
			for (int l1 = 0; l1 < anInt211; l1++) {
				for (int i2 = 0; i2 < anInt212; i2++)
					aByteArray209[l1 + i2 * anInt211] = parent.g1();
			}
		}
	}

	public void draw(int i, int j, int k, int l) {
		try {
			int i1 = anInt211;
			int j1 = anInt212;
			int k1 = 0;
			int l1 = 0;
			int i2 = (i1 << 16) / k;
			int j2 = (j1 << 16) / l;
			int k2 = anInt215;
			int l2 = anInt216;
			i2 = (k2 << 16) / k;
			j2 = (l2 << 16) / l;
			i += ((anInt213 * k + k2) - 1) / k2;
			j += ((anInt214 * l + l2) - 1) / l2;
			if ((anInt213 * k) % k2 != 0)
				k1 = (k2 - (anInt213 * k) % k2 << 16) / k;
			if ((anInt214 * l) % l2 != 0)
				l1 = (l2 - (anInt214 * l) % l2 << 16) / l;
			k = (k * (anInt211 - (k1 >> 16))) / k2;
			l = (l * (anInt212 - (l1 >> 16))) / l2;
			int i3 = i + j * DrawingArea.width;
			int j3 = DrawingArea.width - k;
			if (j < DrawingArea.boundY) {
				int k3 = DrawingArea.boundY - j;
				l -= k3;
				j = 0;
				i3 += k3 * DrawingArea.width;
				l1 += j2 * k3;
			}
			if (j + l > DrawingArea.extentY)
				l -= (j + l) - DrawingArea.extentY;
			if (i < DrawingArea.boundX) {
				int l3 = DrawingArea.boundX - i;
				k -= l3;
				i = 0;
				i3 += l3;
				k1 += i2 * l3;
				j3 += l3;
			}
			if (i + k > DrawingArea.extentX) {
				int i4 = (i + k) - DrawingArea.extentX;
				k -= i4;
				j3 += i4;
			}
			method51(DrawingArea.pixels, aByteArray209, anIntArray210, k1, l1, i3, j3, k, l, i2, j2, i1);
		} catch (Exception exception) {
			System.out.println("error in sprite clipping routine");
		}
	}

	public void method51(int ai[], byte abyte0[], int ai1[], int i, int j, int k, int l, int i1, int j1, int k1, int l1,
			int i2) {
		try {
			int j2 = i;
			for (int k2 = -j1; k2 < 0; k2++) {
				int l2 = (j >> 16) * i2;
				for (int i3 = -i1; i3 < 0; i3++) {
					byte byte0 = abyte0[(i >> 16) + l2];
					if (byte0 != 0)
						ai[k++] = ai1[byte0 & 0xff];
					else
						k++;
					i += k1;
				}

				j += l1;
				i = j2;
				k += l;
			}

		} catch (Exception exception) {
			System.out.println("error in plot_scale");
		}
	}

	public byte aByteArray209[];
	public int anIntArray210[];
	public int anInt211;
	public int anInt212;
	public int anInt213;
	public int anInt214;
	public int anInt215;
	public int anInt216;
}
