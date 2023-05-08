package ecs.entities;


import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import starter.Game;
import tools.Point;


public abstract class Monster extends Entity {

    private float horizontalSpeed;
    private float verticalSpeed;

    private int health;
    private int dmg;

    private final String pathToIdleLeft;
    private final String pathToIdleRight;
    private final String pathToRunLeft;
    private final String pathToRunRight;

    private final IIdleAI idleAI;

    public Monster(String idleLeft, String idleRight, String runLeft, String runRight, IIdleAI idleAI, float horizontalSpeed, float verticalSpeed, int dmg, int health) {
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
        //ai.setFightAI(new MeleeAI(2.0f, new Skill(new MeleeSkill("", new Damage(this.dmg, DamageType.PHYSICAL, null), new Point(1,1), SkillTools::getHeroPosition),2)));
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
        hc.setMaximalHealthpoints(this.health + Game.getLevelCounter()/5);
        hc.setCurrentHealthpoints(this.health + Game.getLevelCounter()/5);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

}

