package de.cirrus.dittany.unit;

import de.cirrus.dittany.Player;
import de.cirrus.dittany.weapon.Revolver;

public class Spy extends Mob {
	public Spy(Player player) {
		super(8, player);
		maxHealth = health = 125;
		speed = 100;

		weapon = new Revolver(this);
	}
}
