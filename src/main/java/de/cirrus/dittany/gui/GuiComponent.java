package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;
import de.cirrus.dittany.Input;

public class GuiComponent {
	public int x, y, w, h;
	public boolean visible = true;
	public boolean enabled = true;
	public GuiContainer parent;

	public GuiComponent(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void tick(Input input) {
	}

	public void render(Bitmap screen) {
	}

	public int getScreenX() {
		return x + (parent == null ? 0 : parent.getScreenX());
	}

	public int getScreenY() {
		return y + (parent == null ? 0 : parent.getScreenY());
	}

	public boolean contains(int xp, int yp) {
		int xx = getScreenX();
		int yy = getScreenY();
		return xp >= xx && yp >= yy && xp < xx + w && yp < yy + h;
	}
}
