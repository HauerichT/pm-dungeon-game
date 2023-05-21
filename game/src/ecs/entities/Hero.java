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

    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;


    public Hero() {
        super();
        PositionComponent psc = new PositionComponent(this);
        PlayableComponent pc = new PlayableComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupSkillComponent();
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
        pc.setSkillSlot3(thirdSkill);
        setupInventoryComponent();
        setupHealthComponent();
    }

    private void setupSkillComponent() {
        SkillComponent skillComponent = new SkillComponent(this);

        int dmg = 1;
        firstSkill =
            new Skill(new MeleeSkill(
                "knight/melee/",
                new Damage(dmg, DamageType.PHYSICAL, null),
                new Point(1, 1),
                SkillTools::getHeroPosition),
                1);

        secondSkill = new Skill(new BoomerangSkill(SkillTools::getCursorPositionAsPoint), 2);
        thirdSkill = new Skill(new LaserSkill(SkillTools::getCursorPositionAsPoint), 2);


        skillComponent.addSkill(firstSkill);
        skillComponent.addSkill(secondSkill);
        skillComponent.addSkill(thirdSkill);

    }

    private void setupInventoryComponent() {
        InventoryComponent inventory = new InventoryComponent(this, 5);
    }

    private void setupVelocityComponent() {
        String pathToRunRight = "knight/runRight";
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        String pathToRunLeft = "knight/runLeft";
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        float ySpeed = 0.25f;
        float xSpeed = 0.25f;
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
        int health = 20;
        hc.setMaximalHealthpoints(health);
        hc.setCurrentHealthpoints(health);
    }

    private void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
