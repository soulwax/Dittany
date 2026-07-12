package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.Minigun;

public class Heavy extends Mob {
	public Heavy(Player player) {
		super(4, player);
		maxHealth = health = 300;
		speed = 77;

		weapon = new Minigun(this);
	}
}
