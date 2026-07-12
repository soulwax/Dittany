package de.cirrus.dittany.unit.order;

import de.cirrus.dittany.unit.Mob;
import de.cirrus.dittany.unit.Unit;

public class HuntOrder extends MoveOrder {
	private Mob target;
	private int time = 0;

	public HuntOrder() {
		super(0, 0);
	}

	public void init(Mob unit) {
		this.unit = unit;
		retarget();
	}

	private void retarget() {
		target = findNearestEnemy();
		if (target == null) return;
		x = target.x;
		y = target.y;
		super.init(unit);
	}

	private Mob findNearestEnemy() {
		Mob nearest = null;
		for (Unit u : unit.level.units) {
			if (u instanceof Mob && u.team != unit.team) {
				Mob m = (Mob) u;
				if (m.isAlive() && (nearest == null || m.distanceToSqr(unit) < nearest.distanceToSqr(unit))) {
					nearest = m;
				}
			}
		}
		return nearest;
	}

	public void tick() {
		if (target == null) return;
		if (++time % 30 == 0) retarget();

		double r = unit.weapon.maxRange / 2;
		if (unit.distanceToSqr(target) > r * r) super.tick();
	}

	public boolean finished() {
		return target == null || !target.isAlive();
	}
}
