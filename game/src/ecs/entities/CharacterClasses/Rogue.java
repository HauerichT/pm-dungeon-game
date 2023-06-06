package ecs.entities.CharacterClasses;

import ecs.components.PlayableComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

import java.util.logging.Logger;

public class Rogue extends Hero {

    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private PlayableComponent pc;
    private SkillComponent skillComponent;


    /**
     * Konstruktor
     */
    public Rogue(){
        super(15,
            6,
            0.25f,
            0.25f,
            "character/Rogue/runLeft",
            "character/Rogue/runRight",
            "character/Rogue/idleLeft",
            "character/Rogue/idleRight");
        setupSkillComponent();
        pc = new PlayableComponent(this);
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);

        firstSkill =
            new Skill(
                new MeleeSkill(
                    "knight/melee/",
                    new Damage(3, DamageType.PHYSICAL, this),
                    new Point(1, 1),
                    SkillTools::getHeroPosition),
                1);

        secondSkill =
            new Skill(
                new BoomerangSkill(
                    SkillTools::getCursorPositionAsPoint,
                    new Damage(2, DamageType.PHYSICAL, this)),
                1);
        skillComponent.addSkill(secondSkill);

        thirdSkill = new Skill(new SpeedSkill(4), 20);
        skillComponent.addSkill(thirdSkill);
    }
    @Override
    public void onLevelUp(long nexLevel) {

        Logger abilityLog = Logger.getLogger(Game.getHero().getClass().getName());
        Game.lvUP(nexLevel);

        // Gives the hero a new skill when he reaches a certain level
        if (nexLevel == 1) {
            pc.setSkillSlot4(thirdSkill);
            abilityLog.info(
                "\u001B[32m" + "Rogue learned Speed skill, press 1 to use it" + "\u001B[31m");

        }
    }
}
