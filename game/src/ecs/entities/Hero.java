package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.items.IOnCollect;
import ecs.items.ItemData;
import graphic.Animation;

import java.util.List;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {


    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private float xSpeed = 0.3f;
    private float ySpeed = 0.3f;
    private int health = 50;
    private int dmg = 2;

    private Skill meleeSkill;

    private InventoryComponent inventory;


    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupMeleeSkill();
        pc.setSkillSlot1(meleeSkill);
    }


    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 3);
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
        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);
    }

    private void setupMeleeSkill() {
         meleeSkill = new Skill(new MeleeSkill(SkillTools::getCursorPositionAsPoint), 1);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> System.out.println("heroCollisionEnter"),
                (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }
}
