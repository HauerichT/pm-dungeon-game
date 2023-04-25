package ecs.entities;


import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.RadiusWalk;
import graphic.Animation;
import starter.Game;

public abstract class Monster extends Entity {

    private final float horizontalSpeed;
    private final float verticalSpeed;

    private float health;
    private float dmg;

    private final String pathToIdleLeft;
    private final String pathToIdleRight;
    private final String pathToRunLeft;
    private final String pathToRunRight;

    private final IIdleAI idleAI;

    public Monster(String idleLeft, String idleRight, String runLeft, String runRight, IIdleAI idleAI, float horizontalSpeed, float verticalSpeed, float dmg, float health) {
        super();

        new PositionComponent(this);

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
        setupAIComponent();
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

}
