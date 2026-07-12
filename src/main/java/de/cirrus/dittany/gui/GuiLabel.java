package de.cirrus.dittany.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import de.cirrus.dittany.Bitmap;

public class GuiLabel extends GuiImage {
	public String text;
	public int color;

	public GuiLabel(int x, int y, String text, int color) {
		super(x, y, createImage(text, color));
		this.text = text;
		this.color = color;
	}

	public void setText(String text) {
		if (this.text.equals(text)) return;
		this.text = text;
		image = createImage(text, color);
		w = image.w;
		h = image.h;
	}

	private static Bitmap createImage(String text, int color) {
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 10);
		BufferedImage measure = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D mg = measure.createGraphics();
		mg.setFont(font);
		FontMetrics metrics = mg.getFontMetrics();
		int width = Math.max(1, metrics.stringWidth(text));
		int height = metrics.getHeight();
		mg.dispose();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g.setFont(font);
		g.setColor(new Color(color, true));
		g.drawString(text, 0, metrics.getAscent());
		g.dispose();
		return new Bitmap(image);
	}
}
