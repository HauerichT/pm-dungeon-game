package ecs.entities.monster;

import static com.badlogic.gdx.math.MathUtils.random;
import static ecs.entities.Chest.defaultInteractionRadius;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.skill.MeleeSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.components.xp.XPComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import graphic.Animation;
import java.util.List;
import java.util.stream.IntStream;
import starter.Game;
import tools.Point;

public class MonsterChest extends Entity {
    private transient XPComponent monsterXP;
    private transient HealthComponent hc;
    private transient AIComponent ai;

    private transient PositionComponent pos;

    public MonsterChest() {
        super();
        setupAnimationComponent();
        setupPositionComponent();
        setupInteractionComponent();
    }

    public void setupAnimationComponent() {
        Animation idleRight =
                AnimationBuilder.buildAnimation(
                        "objects/treasurechest/chest_full_open_anim_f0.png");
        Animation idleLeft =
                AnimationBuilder.buildAnimation(
                        "objects/treasurechest/chest_full_open_anim_f0.png");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /** gives the Monster a new Position */
    public void setupPositionComponent() {
        pos = new PositionComponent(this);
    }

    public void setupInteractionComponent() {
        new InteractionComponent(
                this, defaultInteractionRadius, false, this::setupMonsterComponents);
    }

    private void setupMonsterComponents(Entity entity) {
        setupVelocityComponent();
        setupHealthComponent();
        setupHitboxComponent();
        setupXPComponent();
        setupAIComponent();
    }

    private void setupVelocityComponent() {
        Animation moveRight =
                AnimationBuilder.buildAnimation(
                        "objects/treasurechest/chest_full_open_anim_f0.png");
        Animation moveLeft =
                AnimationBuilder.buildAnimation(
                        "objects/treasurechest/chest_full_open_anim_f0.png");
        new VelocityComponent(this, 0.2f, 0.2f, moveLeft, moveRight);
    }

    private void setupHealthComponent() {
        hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(5 + Game.getLevelCounter() / 5);
        hc.setCurrentHealthpoints(5 + Game.getLevelCounter() / 5);
        ItemDataGenerator itemDataGenerator = new ItemDataGenerator();

        List<ItemData> itemData =
                IntStream.range(0, random.nextInt(1, 3))
                        .mapToObj(i -> itemDataGenerator.generateItemData())
                        .toList();

        hc.setOnDeath(entity -> new Chest(itemData, pos.getPosition()));
    }

    /** gives the Monster exp which it drops when it dies */
    private void setupXPComponent() {
        monsterXP = new XPComponent(this);
        monsterXP.setLootXP(5);
    }

    /** gives the Monster a HitBox */
    private void setupHitboxComponent() {
        new HitboxComponent(this);
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
                                        new Damage(5, DamageType.PHYSICAL, null),
                                        new Point(1, 1),
                                        SkillTools::getHeroPosition),
                                3)));
    }
}
