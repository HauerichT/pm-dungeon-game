package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;

public class SpikeTrap extends Trap {
    private final String inactive = "trap/spiketrap/inactive";
    private final String active = "trap/spiketrap/active";

    private final int dmg = 2;

    public SpikeTrap() {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();

    }

    void setupAnimationComponent(int a) {
        Animation inactive = AnimationBuilder.buildAnimation(this.inactive);
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (a == 0) {
            new AnimationComponent(this, inactive);
        } else {
            new AnimationComponent(this, active);
        }

    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> new Damage(dmg, DamageType.PHYSICAL, this),
            (you, other, direction) -> {
                new Damage(dmg, DamageType.PHYSICAL, this);
            }

        );

    }

}
