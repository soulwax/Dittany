package de.cirrus.dittany.gui;

import de.cirrus.dittany.Art;
import de.cirrus.dittany.Bitmap;

public class BitmapFont {
	public static final String CHARS =
			" !\"#$%&'()*+,-./012" +
			"3456789:;<=>?@ABCDE" +
			"FGHIJKLMNOPQRSTUVWX" +
			"YZ[\\]^_`abcdefghijk" +
			"lmnopqrstuvwxyz{|}~";

	public static final int GLYPH_SIZE = 16;
	public static final int ADVANCE = 12;

	public static Bitmap render(String text, int color) {
		int width = text.isEmpty() ? 1 : (text.length() - 1) * ADVANCE + GLYPH_SIZE;
		Bitmap result = new Bitmap(width, GLYPH_SIZE);

		for (int i = 0; i < text.length(); i++) {
			int index = CHARS.indexOf(text.charAt(i));
			if (index < 0) index = CHARS.indexOf('?');
			Bitmap glyph = Art.i.font[index % 19][index / 19];
			drawGlyph(result, glyph, i * ADVANCE, color);
		}
		return result;
	}

	private static void drawGlyph(Bitmap target, Bitmap glyph, int xp, int color) {
		for (int y = 0; y < GLYPH_SIZE; y++) {
			for (int x = 0; x < GLYPH_SIZE; x++) {
				boolean filled = false;
				for (int yy = 0; yy < 2 && !filled; yy++) {
					for (int xx = 0; xx < 2; xx++) {
						if ((glyph.pixels[x * 2 + xx + (y * 2 + yy) * glyph.w] >>> 24) != 0) {
							filled = true;
							break;
						}
					}
				}
				if (filled && xp + x < target.w) target.setPixel(xp + x, y, color);
			}
		}
	}
}
