package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class LaserSkill extends DamageProjectileSkill {
    public LaserSkill(ITargetSelection targetSelection,Damage dmg) {
        super(
            "skills/laser",
            0.5f,
            dmg,
            new Point(1, 1),
            targetSelection,
            5f);
    }
}
