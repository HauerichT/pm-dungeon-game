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
    private float xSpeed = 0.1f;
    private float ySpeed = 0.1f;
    private final String pathToIdleLeft = "character/ghost/idleLeft/left0.png";
    private final String pathToIdleRight = "character/ghost/idleRight/right0.png";
    private final String pathToIdleLeftinvisible = "character/ghost/empty/Empty.png";
    private final String pathToIdleRightinvisible = "character/ghost/empty/Empty.png";
    private final String pathToRunLeft = "character/ghost/idleLeft/left0.png";
    private final String pathToRunRight = "character/ghost/idleRight/right0.png";
    private Boolean hasAnimationComponent = true;
    private AIComponent ai;
    private AnimationComponent ac;
    private Boolean followWalk = false;

    public Ghost() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        ai = new AIComponent(this);
        ai.setIdleAI(new RadiusWalk(0.5f,0));
        setAnimationComponent();

    }

    public void movement(){
        int r = random.nextInt(3);
        switch (r){
            case 0:
                ghostFollowWalk();
                break;
            case 1:
                ghostRadiusWalk();
                break;
            case 2:
                ghostInvisible();
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

    public AIComponent getAi() {
        return ai;
    }

    public Boolean getFollowWalk() {
        return followWalk;
    }
    private void ghostFollowWalk(){
        if (hasAnimationComponent){
            ai.setIdleAI(new FollowHeroWalk());
            followWalk = true;
            xSpeed = 0.25f;
            ySpeed = 0.25f;
            setupVelocityComponent();
        }
        else {
            ai.setIdleAI(new FollowHeroWalk());
            hasAnimationComponent = true;
            xSpeed = 0.25f;
            ySpeed = 0.25f;
            setupVelocityComponent();
            setAnimationComponent();
            followWalk = true;
        }
    }
    private void ghostRadiusWalk(){
        if (hasAnimationComponent){
            new PositionComponent(this);
            xSpeed = 0.05f;
            ySpeed = 0.05f;
            setupVelocityComponent();
            followWalk = false;
            ai.setIdleAI(new RadiusWalk(1.0f,0));
        }
        else {
            hasAnimationComponent = true;
            xSpeed = 0.05f;
            ySpeed = 0.05f;
            setupVelocityComponent();
            setAnimationComponent();
            new PositionComponent(this);
            ai.setIdleAI(new RadiusWalk(1.0f,0));
            followWalk = false;
        }
    }
    private void ghostInvisible(){
        hasAnimationComponent = false;
        setupVelocityComponent();
        setAnimationComponent();
        ai.setIdleAI(new RadiusWalk(1.0f,0));
        followWalk = false;
    }
}
