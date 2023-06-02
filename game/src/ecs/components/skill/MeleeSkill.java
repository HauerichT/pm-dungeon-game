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

/** MeleeSkill is a Skill type which allows the entities to have a close fight. */
public class MeleeSkill implements ISkillFunction {

    private final String pathToTexture;
    private final Damage dmg;
    private final Point hitBoxSize;
    private final ITargetSelection selectionFunction;
    private float currentHoldingTimeInFrames;
    private float hitCooldownInFrames;
    private Entity ownedBy;
    private Point offset;
    private PositionComponent ps;

    public MeleeSkill(
            String pathToTexture,
            Damage dmg,
            Point hitBoxSize,
            ITargetSelection selectionFunction) {
        this.pathToTexture = pathToTexture;
        this.dmg = dmg;
        this.hitBoxSize = hitBoxSize;
        this.selectionFunction = selectionFunction;
    }

    @Override
    public void execute(Entity entity) {
        float holdingTimeInSeconds = 0.5f;
        this.currentHoldingTimeInFrames = (holdingTimeInSeconds * Constants.FRAME_RATE);
        this.hitCooldownInFrames = 0;
        this.ownedBy = entity;

        Entity en = new Entity();
        Point aimedOn = selectionFunction.selectTargetPoint();

        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        this.offset = SkillTools.getMeleeSkillOffsetPositon(epc.getPosition(), aimedOn);
        this.ps =
                new PositionComponent(
                        en,
                        new Point(epc.getPosition().x + offset.x, epc.getPosition().y + offset.y));
        Animation animation = AnimationBuilder.buildAnimation(pathToTexture);
        new AnimationComponent(en, animation);
        new MeleeComponent(en, this);

        ICollide collide =
                (a, b, from) -> {
                    if (b != entity && hitCooldownInFrames == 0) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(dmg);
                                            SkillTools.takeKickback(epc.getPosition(), b);
                                            this.hitCooldownInFrames = 15;
                                        });
                    }
                };

        new HitboxComponent(en, new Point(0.25f, 0.25f), hitBoxSize, collide, null);
    }

    public void update(Entity entity) {
        if (currentHoldingTimeInFrames == 0) {
            Game.removeEntity(entity);
            return;
        }
        hitCooldownInFrames = Math.max(0, --hitCooldownInFrames);
        currentHoldingTimeInFrames = Math.max(0, --currentHoldingTimeInFrames);
        PositionComponent ownedByPs =
                (PositionComponent) ownedBy.getComponent(PositionComponent.class).orElseThrow();
        if (ps == null) return;
        this.ps.setPosition(
                new Point(
                        ownedByPs.getPosition().x + offset.x,
                        ownedByPs.getPosition().y + offset.y));
    }
}
