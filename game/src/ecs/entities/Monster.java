package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.components.xp.XPComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.monster.BossMonster;
import graphic.Animation;
import starter.Game;
import tools.Point;

import java.util.Objects;

/** Used to create Monster. (Superclass) */
public abstract class Monster extends Entity {

    private float horizontalSpeed;
    private float verticalSpeed;

    private int health;
    private int dmg;
    private int exp;

    private final String pathToIdleLeft;
    private final String pathToIdleRight;
    private final String pathToRunLeft;
    private final String pathToRunRight;

    private final IIdleAI idleAI;
    String monster;

    public Monster(
            String idleLeft,
            String idleRight,
            String runLeft,
            String runRight,
            IIdleAI idleAI,
            float horizontalSpeed,
            float verticalSpeed,
            int dmg,
            int health,
            int exp,
            String monster) {
        super();
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;

        this.idleAI = idleAI;

        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;

        this.dmg = dmg;
        this.health = health;
        this.exp = exp;
        this.monster = monster;

        setupVelocityComponent();
        setupAnimationComponent();
        setupPositionComponent();
        setupHealthComponent();
        setupXPComponent();
        if (monster != "BossMonster"){
            setupMeleeAIComponent();
            setupHitboxComponent();
        }
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupMeleeAIComponent() {
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
                                        SkillTools::getHeroPosition),
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

    private void setupXPComponent() {
        XPComponent monsterXP = new XPComponent(this);
        monsterXP.setLootXP(exp);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    public int getHealth() {
        return health;
    }
}
