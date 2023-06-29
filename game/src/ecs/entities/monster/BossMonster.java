package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.fight.RangeAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Monster;
import tools.Point;

/** used to create a BossMonster */
public class BossMonster extends Monster {
    transient AIComponent aic = null;

    /**
     * Konstruktor
     *
     * <p>creates a new BossMonster which is more Powerfull as a normal Monster
     */
    public BossMonster() {
        super(
                "character/monster/BossMonster/idleLeft",
                "character/monster/BossMonster/runRight",
                "character/monster/BossMonster/runLeft",
                "character/monster/BossMonster/runRight",
                new RadiusWalk(1.0f, 1),
                0.02f,
                0.02f,
                10,
                10,
                200);
        setupRangeAIComponent();
    }

    private void setupRangeAIComponent() {
        aic = new AIComponent(this);
        aic.setIdleAI(new RadiusWalk(5.0f, 1));
        aic.setFightAI(
                new RangeAI(
                        6.0f,
                        new Skill(
                                new FireballSkill(
                                        SkillTools::getHeroPosition,
                                        new Damage(2, DamageType.PHYSICAL, this)),
                                4)));
    }

    private void setupMeleeAIComponent() {
        aic.setIdleAI(new RadiusWalk(3.0f, 1));
        aic.setFightAI(
                new MeleeAI(
                        1.2f,
                        new Skill(
                                new MeleeSkill(
                                        "knight/melee",
                                        new Damage(4, DamageType.PHYSICAL, null),
                                        new Point(1, 1),
                                        SkillTools::getHeroPosition),
                                1)));
    }
    /** Changes the AIComponent of the BossMonster from Range to Melee */
    public boolean changeAIComponent() {
        setupMeleeAIComponent();
        this.setHorizontalSpeed(0.1f);
        this.setVerticalSpeed(0.1f);
        this.setupVelocityComponent();
        return true;
    }
}
