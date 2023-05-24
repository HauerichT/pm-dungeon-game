package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.systems.ProjectileSystem;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * BoomerangSkill is a ranged combat possibility for the Hero
 */
public class BoomerangSkill extends DamageProjectileSkill {

    public BoomerangSkill(ITargetSelection targetSelection,Damage dmg) {
        super(
                "skills/boomerang/",
                0.4f,
                dmg,
                new Point(2, 2),
                targetSelection,
                3f);

    }

    /* Execute boomerang when its thrown and not coming back */
    @Override
    public void execute(Entity entity) {
        Entity projectile = new Entity();
        projectile.setIsBoomerang(true);

        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);

        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
                SkillTools.calculateLastPositionInRange(
                        epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
                new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);

        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);

        // checks if boomerang collides with hero
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Game.removeEntity(projectile);
                                            SkillTools.takeKickback(epc.getPosition(), b);
                                        });
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }

    /**
     * Execute function for when the boomerang has to be taken back to hero
     *
     * @param entity the original boomerang.
     * @param boomerangIsComingBack set this to true, else the boomerang will jump back and forth
     *     forever.
     */
    public void execute(Entity entity, boolean boomerangIsComingBack) {
        Entity projectile = new Entity();
        projectile.setIsBoomerang(true);
        ProjectileSystem.setBoomerangIsComingBack(boomerangIsComingBack);

        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        new PositionComponent(projectile, epc.getPosition());
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);
        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
                SkillTools.calculateLastPositionInRange(
                        epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);

        // checks if boomerang collides with entity (not hero)
        ICollide collide =
                (a, b, from) -> {
                    if (b != Game.getHero().get()) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Game.removeEntity(projectile);
                                            SkillTools.takeKickback(epc.getPosition(), b);
                                        });
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }
}
