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
        aic.setIdleAI(new RadiusWalk(4.0f,1));
        aic.setFightAI(
            new RangeAI(
                3.0f,
                new Skill(
                    new FireballSkill(
                        SkillTools::getHeroPosition,
                        new Damage(1, DamageType.PHYSICAL, this)),2
                    )));
    }


    private void setupMeleeAIComponent() {
        aic.setIdleAI(new RadiusWalk(1.0f, 1));
        aic.setFightAI(
            new MeleeAI(
                0.8f,
                new Skill(
                    new MeleeSkill(
                        "knight/melee",
                        new Damage(1, DamageType.PHYSICAL, null),
                        new Point(1, 1),
                        SkillTools::getHeroPosition),
                    3)));
    }
    public boolean changeAIComponent(){
        setupMeleeAIComponent();
        return true;
    }
}
