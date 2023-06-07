package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.components.xp.XPComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import starter.Game;
import tools.Point;

/** Used to create Monster. (Superclass) */
public abstract class Monster extends Entity {

    private float horizontalSpeed;
    private float verticalSpeed;

    private int health;
    private int dmg;
    private int exp;
    private final String pathToIdleLeft;
    private final String pathToIdleRight;
    private final String pathToRunLeft;
    private final String pathToRunRight;
    private final transient IIdleAI idleAI;
    private transient XPComponent monsterXP;
    private transient HealthComponent hc;
    private transient AIComponent ai;

    /**
     * Konstruktor
     *
     * @param idleLeft Images of the Monster
     * @param idleRight Images of the Monster
     * @param runLeft animations of the Monster
     * @param runRight animations of the Monster
     * @param IIdleAI Movementtrategie for the Monster
     * @param horizontalSpeed of the Monster
     * @param verticalSpeed of the Monster
     * @param dmg of the v
     * @param health of the Monster
     * @param exp which the Monster drops for the Hero
     */
    public Monster(
            String idleLeft,
            String idleRight,
            String runLeft,
            String runRight,
            IIdleAI idleAI,
            float horizontalSpeed,
            float verticalSpeed,
            int dmg,
            int health,
            int exp) {
        super();
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;

        this.idleAI = idleAI;

        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;

        this.dmg = dmg;
        this.health = health;
        this.exp = exp;

        setupVelocityComponent();
        setupAnimationComponent();
        setupPositionComponent();
        setupHealthComponent();
        setupXPComponent();
        setupAIComponent();
        setupHitboxComponent();
    }

    /** gives the Monster a new Position */
    public void setupPositionComponent() {
        new PositionComponent(this);
    }

    /** gives the Monster the ability to fight with a MeleeSkill */
    public void setupAIComponent() {
        ai = new AIComponent(this);
        // ai.setIdleAI(idleAI);

        ai.setFightAI(
                new MeleeAI(
                        0.8f,
                        new Skill(
                                new MeleeSkill(
                                        "knight/melee",
                                        new Damage(this.dmg, DamageType.PHYSICAL, null),
                                        new Point(1, 1),
                                        SkillTools::getHeroPosition),
                                3)));
    }

    /** gives the Monster animations */
    public void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /** gives the Monster ability to move */
    public void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
        new VelocityComponent(this, horizontalSpeed, verticalSpeed, moveLeft, moveRight);
    }

    /** gives the Monster Healthpoints */
    public void setupHealthComponent() {
        hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(this.health + Game.getLevelCounter() / 5);
        hc.setCurrentHealthpoints(this.health + Game.getLevelCounter() / 5);
    }

    /** gives the Monster exp which it drops when it dies */
    public void setupXPComponent() {
        monsterXP = new XPComponent(this);
        monsterXP.setLootXP(exp);
    }

    /** gives the Monster a HitBox */
    public void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    /**
     * @param horizontalSpeed sets the horizontal speed of the Monster
     */
    public void setHorizontalSpeed(float horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    /**
     * @param verticalSpeed sets the vertival speed of the Monster
     */
    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
}
