package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Constants;
import tools.Point;

public class DamageMeleeSkill implements ISkillFunction {

    private final String pathToTexturesOfProjectile;
    private final Damage projectileDamage;
    private final Point projectileHitboxSize;
    private final ITargetSelection selectionFunction;
    private final float holdingTimeInSeconds = 0.5f;
    private float currentHoldingTimeInFrames;
    private float hitCooldownInFrames;
    private Entity ownedBy;
    private Point offSet;
    private PositionComponent projectilepc;

    public DamageMeleeSkill(
        String pathToTexturesOfProjectile,
        Damage projectileDamage,
        Point projectileHitboxSize,
        ITargetSelection selectionFunction) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
        this.projectileDamage = projectileDamage;
        this.projectileHitboxSize = projectileHitboxSize;
        this.selectionFunction = selectionFunction;
    }

    @Override
    public void execute(Entity entity) {
        this.currentHoldingTimeInFrames = (holdingTimeInSeconds * Constants.FRAME_RATE);
        this.hitCooldownInFrames = 0;
        this.ownedBy = entity;

        Entity projectile = new Entity();
        Point aimedOn = selectionFunction.selectTargetPoint();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        this.offSet = SkillTools.getMeleeSkillOffsetPositon(epc.getPosition(), aimedOn);
        this.projectilepc = new PositionComponent(projectile, new Point(epc.getPosition().x + offSet.x, epc.getPosition().y + offSet.y));

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);
        new MeleeComponent(projectile, this);
        ICollide collide =
            (a, b, from) -> {
                if (b != entity && hitCooldownInFrames == 0) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(projectileDamage);
                                SkillTools.recieveKnockback(epc.getPosition(), b);
                                this.hitCooldownInFrames = 15;
                            });
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }

    public void update(Entity entity) {
        if (currentHoldingTimeInFrames == 0) {
            Game.removeEntity(entity);
            return;
        }
        hitCooldownInFrames = Math.max(0, --hitCooldownInFrames);
        currentHoldingTimeInFrames = Math.max(0, --currentHoldingTimeInFrames);
        PositionComponent ownedBypc = (PositionComponent) ownedBy.getComponent(PositionComponent.class).orElseThrow();
        if (projectilepc == null) return;
        this.projectilepc.setPosition(new Point(ownedBypc.getPosition().x + offSet.x, ownedBypc.getPosition().y + offSet.y));
    }
}
