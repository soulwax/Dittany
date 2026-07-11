package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.Scattergun;

public class Scout extends Mob {
	public Scout(Player player) {
		super(0, player);
		maxHealth = health = 125;
		speed = 133;
		visRange = 10;

		weapon = new Scattergun(this);
	}
}
