package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;

public class GuiImage extends GuiComponent {
	public Bitmap image;

	public GuiImage(int x, int y, Bitmap image) {
		super(x, y, image.w, image.h);
		this.image = image;
	}

	public void render(Bitmap screen) {
		if (visible) screen.draw(image, getScreenX(), getScreenY());
	}
}
