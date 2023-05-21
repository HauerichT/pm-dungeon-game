package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class LaserSkill extends DamageProjectileSkill {
    public LaserSkill(ITargetSelection targetSelection) {
        super(
            "skills/laser",
            0.5f,
            new Damage(1, DamageType.FIRE, null),
            new Point(1, 1),
            targetSelection,
            5f);
    }
}
