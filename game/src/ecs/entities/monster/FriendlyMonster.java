package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * Friendly Monster that will spawn if the hero use his "friendly monster" skill.
 */
public class FriendlyMonster extends Entity {
    private float horizontalSpeed = 0.2f;
    private float verticalSpeed = 0.2f;

    private int health = 4;
    private int dmg = 1;

    private final String pathToIdleLeft = "character/monster/tot/idleLeft";
    private final String pathToIdleRight = "character/monster/zombie/runRight";
    private final String pathToRunLeft = "character/monster/zombie/runLeft";
    private final String pathToRunRight = "character/monster/zombie/runRight";

    private final IIdleAI idleAI = new FollowHeroWalk();

    public FriendlyMonster(){
        super();
        setupVelocityComponent();
        setupAnimationComponent();
        setupPositionComponent();
        setupAIComponent();
        setupHitboxComponent();
        setupHealthComponent();

    }
    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(idleAI);
        ai.setFightAI(
            new MeleeAI(
                0.8f,
                new Skill(
                    new MeleeSkill(
                        "knight/melee",
                        new Damage(this.dmg, DamageType.PHYSICAL, null),
                        new Point(1, 1),
                        SkillTools::getMonsterPosition),
                    3)));
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
        new VelocityComponent(this, horizontalSpeed, verticalSpeed, moveLeft, moveRight);
    }

    private void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(this.health + Game.getLevelCounter() / 5);
        hc.setCurrentHealthpoints(this.health + Game.getLevelCounter() / 5);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

}
