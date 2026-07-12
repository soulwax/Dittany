package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.RocketLauncher;

public class Soldier extends Mob {
	public Soldier(Player player) {
		super(1, player);
		maxHealth = health = 200;
		speed = 80;

		weapon = new RocketLauncher(this);
	}
}
