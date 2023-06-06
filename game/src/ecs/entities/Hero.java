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
import java.util.logging.Logger;
import starter.Game;
import tools.Point;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero
 * with all its components and attributes .
 */
public abstract class Hero extends Entity implements ILevelUp {

    private int health;
    private static float mana = 3;
    private float xSpeed;
    private float ySpeed;
    String pathToRunRight;
    String pathToRunLeft;
    String pathToIdleRight;
    String pathToIdleLeft;

    /**
     * Konstruktor
     *
     * @param health of the Hero
     * @param mana of the Hero
     * @param xSpeed of the Hero
     * @param ySpeed of the Hero
     * @param runLeft animations of the hero
     * @param runRight animations of the hero
     * @param idleLeft Images of the hero
     * @param idleRight Images of the hero
     */
    public Hero(int health, float mana, float xSpeed, float ySpeed,String runLeft, String runRight, String idleLeft, String idleRight) {
        super();
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.health = health;
        this.mana = mana;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        PositionComponent psc = new PositionComponent(this);

        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();

        setupInventoryComponent();
        setupHealthComponent();
        setupXPComponent();
    }

    private void setupInventoryComponent() {
        InventoryComponent inventory = new InventoryComponent(this, 5);
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

    private void setupHealthComponent() {
        Logger healtLog = Logger.getLogger(Game.getHero().getClass().getName());

        HealthComponent hc = new HealthComponent(this);
        hc.setOnDeath(entity -> Game.toggleGameOver());

        int health = 20;
        hc.setMaximalHealthpoints(health);
        hc.setCurrentHealthpoints(health);

        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);

        healtLog.info("\u001B[33m" + "Health = " + health + "\u001B[31m");
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

    private void setupXPComponent() {
        XPComponent heroXP = new XPComponent(this, this);
        heroXP.setCurrentLevel(0);
        heroXP.setCurrentXP(0);
    }

        /**
         * Adding Mana is called per Frame in Game.java
         *
         * @param manaPerFrame float value to add Mana to the Hero
         */
        public static void addMana ( float manaPerFrame){
            Logger manalog = Logger.getLogger(Game.getHero().getClass().getName());
            if (mana < 20) {
                mana += manaPerFrame;
            } else{
                manalog.info("Dein Mana ist voll");
            }
        }


    /**
     * Reducing Mana after the Hero used a skill
     *
     * @param manaCost Mana cost when you use a skill.
     */
    public static void reduceMana(float manaCost) {
        Logger manalog2 = Logger.getLogger(Game.getHero().getClass().getName());

        mana -= manaCost;
        manalog2.info("\u001B[34m" + "Current Mana =" + mana + "\u001B[31m");
    }

    public static float getMana() {
        return mana;
    }
}

