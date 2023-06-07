package ecs.entities.CharacterClasses;

import ecs.components.PlayableComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Hero;
import java.util.logging.Logger;
import starter.Game;

/** used to create a Mage */
public class Mage extends Hero {
    private transient Skill firstSkill;
    private transient Skill secondSkill;
    private transient PlayableComponent pc;
    private transient SkillComponent skillComponent;

    /**
     * Konstruktor Creates a new Mage which has one Skill at the beginning The next Skill will be
     * added at Level 1
     */
    public Mage() {
        super(
                15,
                10,
                0.2f,
                0.2f,
                "character/Mage/runLeft",
                "character/Mage/runRight",
                "character/Mage/idleLeft",
                "character/Mage/idleRight");

        setupSkillComponent();
        pc = new PlayableComponent(this);
        pc.setSkillSlot4(firstSkill);
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);

        firstSkill =
                new Skill(
                        new FireballSkill(
                                SkillTools::getCursorPositionAsPoint,
                                new Damage(2, DamageType.MAGIC, this)),
                        1);
        skillComponent.addSkill(firstSkill);

        secondSkill =
                new Skill(
                        new LaserSkill(
                                SkillTools::getCursorPositionAsPoint,
                                new Damage(3, DamageType.MAGIC, this)),
                        10);
        skillComponent.addSkill(secondSkill);
    }

    /**
     * gives the Mage the second Skill
     *
     * @param nexLevel is the new level of the entity
     */
    @Override
    public void onLevelUp(long nexLevel) {

        Logger abilityLog = Logger.getLogger(Game.getHero().getClass().getName());
        Game.lvUP(nexLevel);

        // Gives the hero a new skill when he reaches a certain level
        if (nexLevel == 1) {
            pc.setSkillSlot5(secondSkill);
            abilityLog.info(
                    "\u001B[32m" + "Mage learned Laser skill, press 2 to use it" + "\u001B[31m");
        }
    }
}
