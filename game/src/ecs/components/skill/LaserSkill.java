package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

/**
 * LaserSkill is a ranged combat possibility for the Hero
 */
public class LaserSkill extends DamageProjectileSkill {

    /**
     *
     * @param targetSelection
     * @param dmg Value for the damage of the skill
     */
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
