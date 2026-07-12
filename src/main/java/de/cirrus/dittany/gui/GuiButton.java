package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;
import de.cirrus.dittany.Input;

public class GuiButton extends GuiComponent {
	public Bitmap icon;
	public boolean hovered;
	public boolean down;
	public boolean clicked;
	public int color = 0xff303840;
	public int hoverColor = 0xff586878;
	public int downColor = 0xff202830;
	public int borderColor = 0xffa0a8b0;

	public GuiButton(int x, int y, int w, int h, Bitmap icon) {
		super(x, y, w, h);
		this.icon = icon;
	}

	public void tick(Input input) {
		clicked = false;
		hovered = input.onScreen && contains(input.x, input.y);
		if (hovered && input.b0Clicked) down = true;
		if (input.b0Released) {
			clicked = down && hovered;
			down = false;
		}
	}

	public void render(Bitmap screen) {
		if (!visible) return;
		int xx = getScreenX();
		int yy = getScreenY();
		int col = down && hovered ? downColor : hovered ? hoverColor : color;
		screen.fill(xx, yy, xx + w - 1, yy + h - 1, col);
		screen.box(xx, yy, xx + w - 1, yy + h - 1, borderColor);
		if (icon != null) screen.draw(icon, xx + (w - icon.w) / 2, yy + (h - icon.h) / 2 + (down ? 1 : 0));
	}
}
