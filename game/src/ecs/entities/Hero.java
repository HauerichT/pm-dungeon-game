package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {

    private final int fireballCoolDown = 5;
    private float xSpeed = 0.3f;
    private float ySpeed = 0.3f;
    private float health = 50;
    private float dmg = 1.0f;

    private Skill firstSkill;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);
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

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

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

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
}
