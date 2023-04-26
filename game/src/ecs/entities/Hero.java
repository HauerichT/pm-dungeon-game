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

    private float dmg = 3.0f;
    private final int fireballCoolDown = 5;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;

    private int counter = 0;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
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
        pc.setSkillSlot1(firstSkill);
        setupInventoryComponent();
        setupHitboxComponent();
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 3);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

}
