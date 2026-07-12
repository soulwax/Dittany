package de.cirrus.dittany.gui;

public class GuiLabel extends GuiImage {
	public String text;
	public int color;

	public GuiLabel(int x, int y, String text, int color) {
		super(x, y, BitmapFont.render(text, color));
		this.text = text;
		this.color = color;
	}

	public void setText(String text) {
		if (this.text.equals(text)) return;
		this.text = text;
		image = BitmapFont.render(text, color);
		w = image.w;
		h = image.h;
	}
}
