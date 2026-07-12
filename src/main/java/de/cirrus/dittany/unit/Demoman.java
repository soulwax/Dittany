package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.StickyBombLauncher;

public class Demoman extends Mob {
	public Demoman(Player player) {
		super(3, player);
		maxHealth = health = 175;
		speed = 93;

		weapon = new StickyBombLauncher(this);
	}
}
