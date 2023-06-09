package ecs.entities;

import configuration.hud.GameOver;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import java.util.logging.Logger;
import starter.Game;

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
    private transient PlayableComponent pc;
    private transient XPComponent heroXP;
    private transient PositionComponent psc;
    private transient InventoryComponent inventory;
    private transient HealthComponent hc;

    /**
     * @param health of the Hero
     * @param mana of the Hero
     * @param xSpeed of the Hero
     * @param ySpeed of the Hero
     * @param runLeft animations of the Hero
     * @param runRight animations of the Hero
     * @param idleLeft Images of the Hero
     * @param idleRight Images of the Hero
     */
    public Hero(
            int health,
            float mana,
            float xSpeed,
            float ySpeed,
            String runLeft,
            String runRight,
            String idleLeft,
            String idleRight) {
        super();
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.health = health;
        this.mana = mana;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        setupPositionComponent();
        setupPlayableComponent();
        setupInventoryComponent();
        setupHealthComponent();
        setupXPComponent();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
    }

    /** Set up the inventory component */
    public void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 5);
    }

    /** Set up the position component */
    public void setupPositionComponent() {
        this.psc = new PositionComponent(this);
    }

    /** Set up the playable component */
    public void setupPlayableComponent() {
        this.pc = new PlayableComponent(this);
    }

    /** Set up the velocity component */
    public void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    /** Set up the animation component */
    public void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /** Set up the health component */
    public void setupHealthComponent() {
        Logger healtLog = Logger.getLogger(Game.getHero().getClass().getName());

        hc = new HealthComponent(this);
        hc.setOnDeath(entity -> Game.setGameOver(new GameOver<>()));

        int health = 20;
        hc.setMaximalHealthpoints(health);
        hc.setCurrentHealthpoints(health);

        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);

        healtLog.info("\u001B[33m" + "Health = " + health + "\u001B[31m");
    }

    /** Set up the hitbox component */
    public void setupHitBoxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> {
                    if (other.getClass() == Monster.class) {
                        setupVelocityComponent();
                    }
                },
                (you, other, direction) -> setupVelocityComponent());
    }

    /** Set up the xp component */
    public void setupXPComponent() {
        heroXP = new XPComponent(this, this);
        heroXP.setCurrentLevel(0);
        heroXP.setCurrentXP(0);
    }

    /**
     * Set up the playable component
     *
     * @param currentLevel the current level of the hero
     * @param currentXP the amount of xp the hero have
     */
    public void setupXPComponent(int currentLevel, long currentXP) {
        heroXP = new XPComponent(this, this);
        heroXP.setCurrentLevel(currentLevel);
        heroXP.setCurrentXP(currentXP);
    }

    /**
     * Adding Mana is called per Frame in Game.java
     *
     * @param manaPerFrame float value to add Mana to the Hero
     */
    public static void addMana(float manaPerFrame) {
        Logger manalog = Logger.getLogger(Game.getHero().getClass().getName());
        if (mana < 20) {
            mana += manaPerFrame;
        } else {
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

    /**
     * @return the current mana of the Hero
     */
    public static float getMana() {
        return mana;
    }
}
