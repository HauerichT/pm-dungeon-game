package ecs.entities;


import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.IIdleAI;
import graphic.Animation;


public abstract class Monster extends Entity {

    private float horizontalSpeed;
    private float verticalSpeed;

    private int health;
    private float dmg;

    private final String pathToIdleLeft;
    private final String pathToIdleRight;
    private final String pathToRunLeft;
    private final String pathToRunRight;

    private final IIdleAI idleAI;

    public Monster(String idleLeft, String idleRight, String runLeft, String runRight, IIdleAI idleAI, float horizontalSpeed, float verticalSpeed, float dmg, int health) {
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

        setupVelocityComponent();
        setupAnimationComponent();
        setupPositionComponent();
        setupAIComponent();
        setupHealthComponent();
    }


    /* Setup Components for Monster */

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(idleAI);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
        new VelocityComponent(this, horizontalSpeed, verticalSpeed, moveLeft, moveRight);
    }

    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(this.health);
        hc.setCurrentHealthpoints(this.health);
    }


    /* Getter to get current information about Monster */
    public float getDmg() {
        return dmg;
    }

    public float getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public float getHealth() {
        return health;
    }


    /* Setter to update Monster */
    public void setHealth(int health) {
        this.health = health;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public void setHorizontalSpeed(float horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

}

