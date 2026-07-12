package de.cirrus.dittany.gui;

import java.util.ArrayList;
import java.util.List;

import de.cirrus.dittany.Bitmap;
import de.cirrus.dittany.Input;

public class GuiContainer extends GuiComponent {
	public List<GuiComponent> children = new ArrayList<GuiComponent>();

	public GuiContainer(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void add(GuiComponent child) {
		if (child == this) return;
		if (child.parent != null) child.parent.remove(child);
		child.parent = this;
		children.add(child);
	}

	public void remove(GuiComponent child) {
		if (children.remove(child)) child.parent = null;
	}

	public void tick(Input input) {
		if (!visible || !enabled) return;
		for (GuiComponent child : children) {
			if (child.visible && child.enabled) child.tick(input);
		}
	}

	public void render(Bitmap screen) {
		if (!visible) return;
		for (GuiComponent child : children) {
			if (child.visible) child.render(screen);
		}
	}
}
