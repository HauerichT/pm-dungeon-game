package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
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
    public static long currentLV;

    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private Skill fourthSkill;
    private Skill fifthSkill;
    private Skill sixthSkill;


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
        pc.setSkillSlot4(fourthSkill);
        pc.setSkillSlot5(fifthSkill);
        pc.setSkillSlot6(sixthSkill);
        setupInventoryComponent();
        setupHealthComponent();
        setupXPComponent();
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
        fourthSkill = new Skill(new SpeedSkill(4), 5);
        fifthSkill = new Skill(new FriendlyMonsterSkill(), 20);
        sixthSkill = new Skill(new FireballSkill(SkillTools::getCursorPositionAsPoint),1);
        System.out.println("Das Mana des Heros betraegt " + mana);

        skillComponent.addSkill(firstSkill);
        skillComponent.addSkill(secondSkill);
        skillComponent.addSkill(thirdSkill);
        skillComponent.addSkill(fourthSkill);
        skillComponent.addSkill(fifthSkill);
        skillComponent.addSkill(sixthSkill);


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


        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);
    }


    private void setupHitBoxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> {
                if (other.getClass() == Monster.class) {
                    setupVelocityComponent();
                }
            },
            (you, other, direction) -> setupVelocityComponent());
    }

    private void setupXPComponent(){
        XPComponent heroXP = new XPComponent(this,null);
        heroXP.setCurrentLevel(0);
        heroXP.setCurrentXP(0);

    }


}
