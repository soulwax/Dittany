package de.cirrus.dittany.gui;

import de.cirrus.dittany.Bitmap;
import de.cirrus.dittany.Input;

public class SettingsMenu extends GuiPanel {
	public GuiButton resumeButton;
	public GuiButton quitButton;
	public boolean resume;
	public boolean quit;

	public SettingsMenu(int screenW, int screenH) {
		super((screenW - 120) / 2, (screenH - 96) / 2, 120, 96);
		color = 0xff181c20;
		borderColor = 0xffffffff;

		GuiLabel title = new GuiLabel(0, 9, "SETTINGS", 0xffffffff);
		title.x = (w - title.w) / 2;
		add(title);

		resumeButton = new GuiButton(20, 32, 80, 20, null);
		add(resumeButton);
		GuiLabel resumeLabel = new GuiLabel(0, 0, "RESUME", 0xffffffff);
		resumeLabel.x = resumeButton.x + (resumeButton.w - resumeLabel.w) / 2;
		resumeLabel.y = resumeButton.y + (resumeButton.h - resumeLabel.h) / 2;
		add(resumeLabel);

		quitButton = new GuiButton(20, 62, 80, 20, null);
		add(quitButton);
		GuiLabel quitLabel = new GuiLabel(0, 0, "QUIT", 0xffffffff);
		quitLabel.x = quitButton.x + (quitButton.w - quitLabel.w) / 2;
		quitLabel.y = quitButton.y + (quitButton.h - quitLabel.h) / 2;
		add(quitLabel);
	}

	public void tick(Input input) {
		resume = false;
		quit = false;
		super.tick(input);
		resume = resumeButton.clicked;
		quit = quitButton.clicked;
	}

	public void render(Bitmap screen) {
		screen.xOffs = 0;
		screen.yOffs = 0;
		super.render(screen);
	}
}
