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
public class Hero extends Entity implements ILevelUp{

    private float xSpeed = 0.25f;
    private float ySpeed = 0.25f;
    private  int health = 20;
    private int dmg = 1;


    private static float mana = 3;

    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private Skill fourthSkill;
    private Skill fifthSkill;
    private Skill sixthSkill;
    private PlayableComponent pc;
    private SkillComponent skillComponent;


    public Hero() {
        super();
        PositionComponent psc = new PositionComponent(this);
        pc = new PlayableComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupSkillComponent();

        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);


        setupInventoryComponent();
        setupHealthComponent();
        setupXPComponent();
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);

        int dmg = 1;
        firstSkill =
            new Skill(new MeleeSkill(
                "knight/melee/",
                new Damage(dmg, DamageType.PHYSICAL, this),
                new Point(1, 1),
                SkillTools::getHeroPosition),
                1);


        secondSkill = new Skill(new BoomerangSkill(SkillTools::getCursorPositionAsPoint,new Damage(1, DamageType.PHYSICAL, this)), 2);
        skillComponent.addSkill(secondSkill);

        thirdSkill = new Skill(new LaserSkill(SkillTools::getCursorPositionAsPoint,new Damage(1, DamageType.FIRE, this)), 2);
        skillComponent.addSkill(thirdSkill);

        fourthSkill = new Skill(new SpeedSkill(4), 5);
        skillComponent.addSkill(fourthSkill);

        fifthSkill = new Skill(new HealthSkill(4), 2);
        skillComponent.addSkill(fifthSkill);

        sixthSkill = new Skill(new FireballSkill(SkillTools::getCursorPositionAsPoint,new Damage(1, DamageType.FIRE, this)),1);
        skillComponent.addSkill(sixthSkill);

        System.out.println("Das Mana des Heros betraegt " + mana);

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
        XPComponent heroXP = new XPComponent(this,this);
        heroXP.setCurrentLevel(0);
        heroXP.setCurrentXP(0);
    }

    public static void addMana(float manaPerFrame){
        if (mana < 20){
            mana += manaPerFrame;
        }
        else {
            System.out.println("Dein Mana ist Voll!");
        }
    }
    public static void reduceMana(float manaCost){
        mana -= manaCost;
        System.out.println(mana);
    }

    @Override
    public void onLevelUp(long nexLevel) {
        Game.lvUP(nexLevel);

        //Gives the hero a new skill when he reaches a certain level
        if (nexLevel == 1){
            pc.setSkillSlot4(fourthSkill);
            pc.setSkillSlot3(thirdSkill);
        }
        if (nexLevel == 2){
            pc.setSkillSlot6(sixthSkill);

        }
        if (nexLevel == 3){
            pc.setSkillSlot5(fifthSkill);
        }
    }
    public static float getMana() {
        return mana;
    }


}
