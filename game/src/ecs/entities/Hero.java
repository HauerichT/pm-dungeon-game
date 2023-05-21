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
    private InventoryComponent inventory;

    private Skill firstSkill;
    private Skill secondSkill;

    public Hero() {
        super();
        new PositionComponent(this);
        PlayableComponent pc = new PlayableComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupSkillComponent();
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
        setupInventoryComponent();
        setupHealthComponent();
    }

    private void setupSkillComponent() {
        SkillComponent skillComponent = new SkillComponent(this);

        firstSkill =
                new Skill(new FireballSkill(SkillTools::getCursorPositionAsPoint),1);
                        /*new MeleeSkill(
                                "knight/melee/",
                                new Damage(this.dmg, DamageType.PHYSICAL, null),
                                new Point(1, 1),
                                SkillTools::getHeroPosition),
                        1);*/
        secondSkill =
                new Skill(
                        new BoomerangSkill(SkillTools::getCursorPositionAsPoint), 0);

        skillComponent.addSkill(firstSkill);
        skillComponent.addSkill(secondSkill);
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

    private void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
