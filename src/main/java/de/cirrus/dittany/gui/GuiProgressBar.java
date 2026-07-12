package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;

public class GuiProgressBar extends GuiComponent {
	public int value;
	public int max = 100;
	public int color = 0xff30c050;
	public int backgroundColor = 0xff601818;
	public int borderColor = 0xffffffff;

	public GuiProgressBar(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void render(Bitmap screen) {
		if (!visible) return;
		int xx = getScreenX();
		int yy = getScreenY();
		int fill = max <= 0 ? 0 : Math.max(0, Math.min(w, w * value / max));
		screen.fill(xx, yy, xx + w - 1, yy + h - 1, backgroundColor);
		if (fill > 0) screen.fill(xx, yy, xx + fill - 1, yy + h - 1, color);
		screen.box(xx, yy, xx + w - 1, yy + h - 1, borderColor);
	}
}
