package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;

public class GuiPanel extends GuiContainer {
	public int color = 0xff20242a;
	public int borderColor = 0xffd8d8d8;
	public boolean border = true;

	public GuiPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void render(Bitmap screen) {
		if (!visible) return;
		int xx = getScreenX();
		int yy = getScreenY();
		screen.fill(xx, yy, xx + w - 1, yy + h - 1, color);
		if (border) screen.box(xx, yy, xx + w - 1, yy + h - 1, borderColor);
		super.render(screen);
	}
}
