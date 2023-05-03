package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.items.ItemData;
import graphic.Animation;

import java.util.List;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {

<<<<<<< HEAD
    private float dmg = 3.0f;
    private final int fireballCoolDown = 5;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;

    private int counter = 0;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
=======
    private float xSpeed = 0.3f;
    private float ySpeed = 0.3f;
    private int health = 50;
    private int dmg = 2;

>>>>>>> monster
    private Skill firstSkill;

    private InventoryComponent inventory;


    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        setupHealthComponent();
        pc.setSkillSlot1(firstSkill);
        setupInventoryComponent();
        setupHitboxComponent();
    }

<<<<<<< HEAD
    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 3);
    }

=======
>>>>>>> monster
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

    private void setupFireballSkill() {
        int fireballCoolDown = 5;
        firstSkill =
                new Skill(
                        new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

<<<<<<< HEAD
=======
    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> System.out.println("heroCollisionEnter"),
                (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }

    public float getHealth() {
        return health;
    }

    public float getDmg() {
        return dmg;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
>>>>>>> monster
}
