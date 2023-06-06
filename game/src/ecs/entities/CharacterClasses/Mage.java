package ecs.entities.CharacterClasses;

import ecs.components.PlayableComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

import java.util.logging.Logger;

public class Mage extends Hero {
    private Skill firstSkill;
    private Skill secondSkill;
    private PlayableComponent pc;
    private SkillComponent skillComponent;

    /**
     * Konstruktor
     */
    public Mage(){
        super(15,
            10,
            0.2f,
            0.2f,
            "character/knight/runLeft",
            "character/knight/runRight",
            "character/knight/idleLeft",
            "character/knight/idleRight");

        setupSkillComponent();
        pc = new PlayableComponent(this);
        pc.setSkillSlot1(firstSkill);

    }
    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);

        firstSkill =
            new Skill(
                new FireballSkill(
                    SkillTools::getCursorPositionAsPoint,
                    new Damage(2,DamageType.MAGIC,this)),
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

    @Override
    public void onLevelUp(long nexLevel) {

            Logger abilityLog = Logger.getLogger(Game.getHero().getClass().getName());
            Game.lvUP(nexLevel);

            // Gives the hero a new skill when he reaches a certain level
            if (nexLevel == 1) {
                pc.setSkillSlot4(secondSkill);
                abilityLog.info(
                    "\u001B[32m" + "Mage learned Laser skill, press 1 to use it" + "\u001B[31m");
            }
    }
}
