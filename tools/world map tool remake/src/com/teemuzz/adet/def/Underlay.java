package com.teemuzz.adet.def;

import com.vision.rs.Definition;
import com.vision.rs.io.RSBuffer;

public class Underlay extends Definition {
	public int rawColor;
	public int color;
	private int anInt2871;
	private int anInt2873;
	private int anInt2872;

	@Override
	protected void parseOpcode(int opcode, RSBuffer buffer) {
		switch (opcode) {
		case 1:
			// rawColor
			color = rawColor = buffer.get24BitUInt();
			modifyColor(rawColor);
			break;
		case 2:
		case 3:
			buffer.getShort();
			break;
		case 4:
			break;
		default:
			break;
		}

	}

	private void modifyColor(int i) {
		double d = (double) ((i & 0xff0e1b) >> 16) / 256D;
		double d1 = (double) ((0xff0f & i) >> 8) / 256D;
		double d2 = (double) (0xff & i) / 256D;
		double d3 = d;
		if (d3 > d1) {
			d3 = d1;
		}
		if (d2 < d3) {
			d3 = d2;
		}
		double d4 = d;
		if (d4 < d1) {
			d4 = d1;
		}
		if (d2 > d4) {
			d4 = d2;
		}
		double d5 = 0.0D;
		double d6 = 0.0D;
		double d7 = (d3 + d4) / 2D;
		if (d3 != d4) {
			if (d7 < 0.5D) {
				d6 = (-d3 + d4) / (d3 + d4);
			}
			if (d != d4) {
				if (d4 != d1) {
					if (d2 == d4) {
						d5 = 4D + (d - d1) / (-d3 + d4);
					}
				} else {
					d5 = 2D + (-d + d2) / (d4 - d3);
				}
			} else {
				d5 = (-d2 + d1) / (-d3 + d4);
			}
			if (d7 >= 0.5D) {
				d6 = (-d3 + d4) / ((-d4 + 2D) - d3);
			}
		}
		anInt2871 = (int) (256D * d6);
		anInt2873 = (int) (256D * d7);
		d5 /= 6D;
		if (~anInt2873 > -1) {
			anInt2873 = 0;
		} else if (anInt2873 > 255) {
			anInt2873 = 255;
		}
		if (d7 <= 0.5D) {
			anInt2872 = (int) (d7 * d6 * 512D);
		} else {
			anInt2872 = (int) (512D * (d6 * (-d7 + 1.0D)));
		}
		if (anInt2871 >= 0) {
			if (anInt2871 > 255) {
				anInt2871 = 255;
			}
		} else {
			anInt2871 = 0;
		}
		if (anInt2872 < 1) {
			anInt2872 = 1;
		}
		color = (int) (d5 * (double) anInt2872);
	}
}
