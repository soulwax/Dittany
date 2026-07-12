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
	public static final int ADVANCE = 17;
	public static final int SMALL_GLYPH_SIZE = 8;
	public static final int SMALL_ADVANCE = 9;

	public static Bitmap render(String text, int color) {
		return render(text, color, GLYPH_SIZE, ADVANCE);
	}

	public static Bitmap render(String text, int color, int glyphSize, int advance) {
		if (glyphSize != GLYPH_SIZE && glyphSize != SMALL_GLYPH_SIZE) {
			throw new IllegalArgumentException("glyphSize must be 8 or 16");
		}
		int width = text.isEmpty() ? 1 : (text.length() - 1) * advance + glyphSize;
		Bitmap result = new Bitmap(width, glyphSize);
		Bitmap[][] font = glyphSize == SMALL_GLYPH_SIZE ? Art.i.font8x8 : Art.i.font;

		for (int i = 0; i < text.length(); i++) {
			int index = CHARS.indexOf(text.charAt(i));
			if (index < 0) index = CHARS.indexOf('?');
			Bitmap glyph = font[index % 19][index / 19];
			drawGlyph(result, glyph, i * advance, color, glyphSize);
		}
		return result;
	}

	private static void drawGlyph(Bitmap target, Bitmap glyph, int xp, int color, int glyphSize) {
		int sourceScale = glyph.w / glyphSize;
		for (int y = 0; y < glyphSize; y++) {
			for (int x = 0; x < glyphSize; x++) {
				int sourceX = x * sourceScale;
				int sourceY = y * sourceScale;
				boolean filled = false;
				for (int yy = 0; yy < sourceScale && !filled; yy++) {
					for (int xx = 0; xx < sourceScale; xx++) {
						if ((glyph.pixels[sourceX + xx + (sourceY + yy) * glyph.w] >>> 24) != 0) {
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
