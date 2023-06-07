package ecs.entities.CharacterClasses;

import ecs.components.PlayableComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

import java.util.logging.Logger;

public class Warrior extends Hero {
    private Skill firstSkill;
    private Skill secondSkill;
    private PlayableComponent pc;
    private SkillComponent skillComponent;

    /**
     * Konstruktor
     * Creates a new Warrior which has one Skill at the beginning
     * The next Skill will be added at Level 1
     */
    public Warrior(){
        super(30,
            10,
            0.15f,
            0.15f,
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
                new MeleeSkill(
                    "knight/melee/",
                    new Damage(3, DamageType.PHYSICAL, this),
                    new Point(1, 1),
                    SkillTools::getHeroPosition),
                1);

        secondSkill = new Skill(new HealthSkill(5), 10);
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
                "\u001B[32m" + "Warrior learned Health Skill, press 1 to use it" + "\u001B[31m");
        }
    }
}
