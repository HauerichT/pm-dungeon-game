package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class MeleeSkill extends DamageMeleeSkill {
    public MeleeSkill(ITargetSelection selectionFunction) {
        super("character/knight/attack",
            new Damage(1, DamageType.PHYSICAL, null),
            new Point(1, 1),
            selectionFunction);
    }
}
