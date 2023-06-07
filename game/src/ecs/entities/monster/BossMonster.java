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


public class BossMonster extends Monster {
    AIComponent aic = null;

    /**
     * Konstruktor
     * Creates a new BossMonster which is more Powerfull as a normal Monster
     */

    public BossMonster(){
        super("character/monster/BossMonster/idleLeft",
            "character/monster/BossMonster/runRight",
            "character/monster/BossMonster/runLeft",
            "character/monster/BossMonster/runRight",
            new RadiusWalk(1.0f, 1),
            0.02f,
            0.02f,
            10,
            20,
            200,
            "BossMonster");
        setupRangeAIComponent();

    }
    private void setupRangeAIComponent() {
        aic = new AIComponent(this);
        aic.setIdleAI(new RadiusWalk(6.0f,1));
        aic.setFightAI(
            new RangeAI(
                7.0f,
                new Skill(
                    new FireballSkill(
                        SkillTools::getHeroPosition,
                        new Damage(2, DamageType.PHYSICAL, this)),4
                    )));
    }


    private void setupMeleeAIComponent() {
        aic.setIdleAI(new RadiusWalk(3.0f, 1));
        aic.setFightAI(
            new MeleeAI(
                0.8f,
                new Skill(
                    new MeleeSkill(
                        "knight/melee",
                        new Damage(4, DamageType.PHYSICAL, null),
                        new Point(1, 1),
                        SkillTools::getHeroPosition),
                    1)));
    }
    public boolean changeAIComponent(){
        setupMeleeAIComponent();
        this.setHorizontalSpeed(0.1f);
        this.setVerticalSpeed(0.1f);
        this.setupVelocityComponent();
        return true;
    }
}
