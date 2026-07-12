package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;
import de.cirrus.dittany.Input;

public class SettingsMenu extends GuiPanel {
	public static final int FONT_SIZE = BitmapFont.SMALL_GLYPH_SIZE;
	public static final int FONT_ADVANCE = BitmapFont.SMALL_ADVANCE;

	public GuiButton scaleDownButton;
	public GuiButton scaleUpButton;
	public GuiButton fpsButton;
	public GuiButton resumeButton;
	public GuiButton quitButton;
	public GuiLabel scaleValue;
	public GuiLabel fpsValue;
	public int scale;
	public boolean showFps;
	public boolean resume;
	public boolean quit;

	public SettingsMenu(int screenW, int screenH, int scale, boolean showFps) {
		super((screenW - 150) / 2, (screenH - 190) / 2, 150, 190);
		this.scale = scale;
		this.showFps = showFps;
		color = 0xff181c20;
		borderColor = 0xffffffff;

		addCenteredLabel("SETTINGS", 8);
		addCenteredLabel("WINDOW SCALE", 31);

		scaleDownButton = new GuiButton(25, 48, 28, 20, null);
		add(scaleDownButton);
		addButtonLabel(scaleDownButton, "-");

		scaleUpButton = new GuiButton(97, 48, 28, 20, null);
		add(scaleUpButton);
		addButtonLabel(scaleUpButton, "+");

		scaleValue = new GuiLabel(0, 54, "", 0xffffffff, FONT_SIZE, FONT_ADVANCE);
		add(scaleValue);

		addCenteredLabel("SHOW FPS", 79);
		fpsButton = new GuiButton(35, 96, 80, 20, null);
		add(fpsButton);
		fpsValue = new GuiLabel(0, 0, "", 0xffffffff, FONT_SIZE, FONT_ADVANCE);
		add(fpsValue);

		resumeButton = new GuiButton(35, 132, 80, 20, null);
		add(resumeButton);
		addButtonLabel(resumeButton, "RESUME");

		quitButton = new GuiButton(35, 160, 80, 20, null);
		add(quitButton);
		addButtonLabel(quitButton, "QUIT");

		updateValues();
	}

	private void addCenteredLabel(String text, int y) {
		GuiLabel label = new GuiLabel(0, y, text, 0xffffffff, FONT_SIZE, FONT_ADVANCE);
		label.x = (w - label.w) / 2;
		add(label);
	}

	private void addButtonLabel(GuiButton button, String text) {
		GuiLabel label = new GuiLabel(0, 0, text, 0xffffffff, FONT_SIZE, FONT_ADVANCE);
		label.x = button.x + (button.w - label.w) / 2;
		label.y = button.y + (button.h - label.h) / 2;
		add(label);
	}

	public void tick(Input input) {
		resume = false;
		quit = false;
		super.tick(input);

		if (scaleDownButton.clicked && scale > 1) scale--;
		if (scaleUpButton.clicked && scale < 5) scale++;
		if (fpsButton.clicked) showFps = !showFps;
		resume = resumeButton.clicked;
		quit = quitButton.clicked;
		updateValues();
	}

	private void updateValues() {
		scaleValue.setText(scale + "X");
		scaleValue.x = (w - scaleValue.w) / 2;
		fpsValue.setText(showFps ? "ON" : "OFF");
		fpsValue.x = fpsButton.x + (fpsButton.w - fpsValue.w) / 2;
		fpsValue.y = fpsButton.y + (fpsButton.h - fpsValue.h) / 2;
	}

	public void render(Bitmap screen) {
		screen.xOffs = 0;
		screen.yOffs = 0;
		super.render(screen);
	}
}
