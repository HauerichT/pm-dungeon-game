package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import graphic.Animation;
import starter.Game;
import tools.Point;

import static com.badlogic.gdx.math.MathUtils.random;

public class Ghost extends Entity{
    private final float xSpeed = 0.1f;
    private final float ySpeed = 0.1f;
    private final String pathToIdleLeft = "character/ghost/idleLeft/left0.png";
    private final String pathToIdleRight = "character/ghost/idleRight/right0.png";
    private final String pathToIdleLeftinvisible = "character/ghost/empty/Empty.png";
    private final String pathToIdleRightinvisible = "character/ghost/empty/Empty.png";
    private final String pathToRunLeft = "character/ghost/idleLeft/left0.png";
    private final String pathToRunRight = "character/ghost/idleRight/right0.png";
    private Boolean hasAnimationComponent = true;
    private Point ghostPosition;
    private AIComponent ai;
    private AnimationComponent ac;
    private Boolean followWalk = false;

    public Ghost() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        ai = new AIComponent(this);
        ai.setIdleAI(new RadiusWalk(1.0f,0));
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        ac = new AnimationComponent(this, idleLeft, idleRight);

    }

    public void movement(){
        int r = random.nextInt(3);
        switch (r){
            case 0:
                System.out.println("1");
                if (hasAnimationComponent){
                    ai.setIdleAI(new FollowHeroWalk());
                    followWalk = true;
                }
                else {
                    ai.setIdleAI(new FollowHeroWalk());
                    this.hasAnimationComponent = true;
                    setupVelocityComponent();
                    setAnimationComponent();
                    followWalk = true;
                }
                break;
            case 1:
                System.out.println("2");
                if (hasAnimationComponent){
                    new PositionComponent(this);
                    ai.setIdleAI(new RadiusWalk(1.0f,0));
                    followWalk = false;
                }
                else {
                    hasAnimationComponent = true;
                    setupVelocityComponent();
                    setAnimationComponent();
                    new PositionComponent(this);
                    ai.setIdleAI(new RadiusWalk(1.0f,0));
                    followWalk = false;
                }
                break;
            case 2:
                System.out.println("3");
                hasAnimationComponent = false;
                setupVelocityComponent();
                setAnimationComponent();
                ai.setIdleAI(new RadiusWalk(1.0f,0));
                followWalk = false;
                break;
        }

    }


    private void setupVelocityComponent() {
        if (hasAnimationComponent) {
            Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
            Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
            new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
        }else {
            Animation moveRight = AnimationBuilder.buildAnimation(this.pathToIdleRightinvisible);
            Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeftinvisible);
            new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
        }
    }
    private void setAnimationComponent(){
        if (!hasAnimationComponent){
            Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRightinvisible);
            Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeftinvisible);
            ac = new AnimationComponent(this, idleLeft, idleRight);
        }else {
            Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
            Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
            ac = new AnimationComponent(this, idleLeft, idleRight);
        }
    }
    public Point getGhostPosition(){
        this.getComponent(PositionComponent.class).ifPresent(
            position -> {
                ghostPosition = ((PositionComponent) position).getPosition();
            }
        );
        return ghostPosition;
    }

    public AIComponent getAi() {
        return ai;
    }

    public Boolean getFollowWalk() {
        return followWalk;
    }
}
