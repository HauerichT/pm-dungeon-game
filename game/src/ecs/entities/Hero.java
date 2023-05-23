package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero
 * with all its components and attributes .
 */
public class Hero extends Entity {

    private float xSpeed = 0.25f;
    private float ySpeed = 0.25f;
    private int health = 20;
    private int dmg = 1;
    private int mana = 10;

    private Skill meleeSkill;
    private Skill speedSkill;

    private InventoryComponent inventory;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupMeleeSkill();
        setupSpeedSkill();
        pc.setSkillSlot1(meleeSkill);
        pc.setSkillSlot2(speedSkill);
        setupInventoryComponent();
        setupHealthComponent();
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 5);
    }

    private void setupVelocityComponent() {
        String pathToRunRight = "knight/runRight";
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        String pathToRunLeft = "knight/runLeft";
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        String pathToIdleRight = "knight/idleRight";
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        String pathToIdleLeft = "knight/idleLeft";
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setOnDeath(entity -> Game.toggleGameOver());
        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);
    }

    private void setupMeleeSkill() {
        MeleeSkill skill =
                new MeleeSkill(
                        "knight/melee/",
                        new Damage(this.dmg, DamageType.PHYSICAL, null),
                        new Point(1, 1),
                        SkillTools::getHeroPosition);
        meleeSkill = new Skill(skill, 1);
    }

    private void setupSpeedSkill() {
        SpeedSkill skill = new SpeedSkill(4);
        speedSkill = new Skill(skill, 5);
        mana = mana - 5;
        System.out.println("Das Mana des Heros beträgt " + mana);
    }

    private void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
