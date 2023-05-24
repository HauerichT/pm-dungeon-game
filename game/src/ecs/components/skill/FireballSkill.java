package ecs.components.skill;

import ecs.damage.Damage;
import tools.Point;

/**
 * FireballSkill is a skill that the Hero will learn to use at a higher lv.
 */

public class FireballSkill extends DamageProjectileSkill {
    public FireballSkill(ITargetSelection targetSelection,Damage dmg) {
        super(
                "skills/fireball/fireBall_Down/",
                0.5f,
                dmg,
                new Point(10, 10),
                targetSelection,
                5f);

    }
}
