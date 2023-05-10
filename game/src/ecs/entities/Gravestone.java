package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.collision.ICollide;
import ecs.entities.items.HealPotion;
import ecs.entities.items.StrengthPotion;
import ecs.entities.items.Sword;
import ecs.entities.monster.Skeleton;
import ecs.entities.monster.Tot;
import ecs.entities.monster.Zombie;
import graphic.Animation;
import starter.Game;
import tools.Point;

import static com.badlogic.gdx.math.MathUtils.random;

public class Gravestone extends Entity{
    private final String gravestone = "character/grabstein/grave.png";
    private Entity ghost;
    private Entity hero;
    private Entity collide;

     private Point graveStonePosition;
    private IIdleAI FollowHeroWalk = new FollowHeroWalk();

    public Gravestone(Entity ghost,Entity hero){
        super();
        new PositionComponent(this);
        setupHitboxComponent();
        setupAnimationComponent(0);
        this.ghost=ghost;
        this.hero=hero;
    }
    private void setupAnimationComponent(int a) {
        Animation graveStone = AnimationBuilder.buildAnimation(this.gravestone);
        new AnimationComponent(this, graveStone);
        if (a == 1){
        this.getComponent(HitboxComponent.class).ifPresent(
            collide -> {
                this.collide = ((HitboxComponent) collide).getCollide();
                if (((Ghost) ghost).getFollowWalk() && this.collide.equals(hero)){
                    int rnd = random.nextInt(2);
                    if (rnd == 0) {
                        spielerBelohnen();
                        Game.removeEntity(this);
                    } else {
                        spielerBestrafen();
                        Game.removeEntity(this);
                    }
                }
            }
        );
        }
    }
        private void spielerBelohnen(){
        int randomItem = random.nextInt(3);
        switch (randomItem) {
            case 0 -> new HealPotion();
            case 1 -> new StrengthPotion();
            case 2 -> new Sword();
        }
    }
    private void spielerBestrafen(){
            int randomMonster = random.nextInt(3);
            switch (randomMonster) {
                case 0 -> new Skeleton();
                case 1 -> new Zombie();
                case 2 -> new Tot();
            }
        }
    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> setupAnimationComponent(1),
            (you, other, direction) -> setupAnimationComponent(0));

    }
    private Point gravestonePosition(){
         this.getComponent(PositionComponent.class).ifPresent(
            position -> {
                graveStonePosition = ((PositionComponent) position).getPosition();
            }
        );
         return graveStonePosition;
    }
}
