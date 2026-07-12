package de.cirrus.dittany.gui;

public class GuiLabel extends GuiImage {
	public String text;
	public int color;
	public int glyphSize;
	public int advance;

	public GuiLabel(int x, int y, String text, int color) {
		this(x, y, text, color, BitmapFont.GLYPH_SIZE, BitmapFont.ADVANCE);
	}

	public GuiLabel(int x, int y, String text, int color, int glyphSize, int advance) {
		super(x, y, BitmapFont.render(text, color, glyphSize, advance));
		this.text = text;
		this.color = color;
		this.glyphSize = glyphSize;
		this.advance = advance;
	}

	public void setText(String text) {
		if (this.text.equals(text)) return;
		this.text = text;
		image = BitmapFont.render(text, color, glyphSize, advance);
		w = image.w;
		h = image.h;
	}
}
