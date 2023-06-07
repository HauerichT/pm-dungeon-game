package ecs.entities.monster;

import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.fight.RangeAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.skill.FireballSkill;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Monster;
import tools.Point;


public class BossMonster extends Monster {
    AIComponent ai = null;

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
        setupHitboxComponent();
    }
    private void setupRangeAIComponent() {
        ai = new AIComponent(this);
        ai.setIdleAI(new RadiusWalk(4.0f,1));
        ai.setFightAI(
            new RangeAI(
                3.0f,
                new Skill(
                    new FireballSkill(
                        SkillTools::getHeroPosition,
                        new Damage(10, DamageType.PHYSICAL, null)),1
                    )));
    }

    private void setupHitboxComponent() {
        HealthComponent health = (HealthComponent) this.getComponent(HealthComponent.class).orElseThrow();
        new HitboxComponent(this,
            (you, other, direction) -> {}
            ,(you, other, direction) -> {
            if (this.getHealth() <= health.getMaximalHealthpoints() / 2) {
                this.removeComponent(ai.getClass());
                this.setupMeleeAIComponent();
            }
        }
        );
    }
    private void setupMeleeAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new RadiusWalk(1.0f, 1));
        ai.setFightAI(
            new MeleeAI(
                0.8f,
                new Skill(
                    new MeleeSkill(
                        "knight/melee",
                        new Damage(10, DamageType.PHYSICAL, null),
                        new Point(1, 1),
                        SkillTools::getHeroPosition),
                    3)));
    }
}
