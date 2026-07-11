package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.Shotgun;

public class Engineer extends Mob {
	public Engineer(Player player) {
		super(5, player);
		maxHealth = health = 125;
		speed = 100;

		weapon = new Shotgun(this);
	}
}
