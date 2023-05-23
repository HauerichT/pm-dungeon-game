package ecs.components.skill;

public abstract class PassivSkill implements ISkillFunction {
    private int mana;
    private float cooldown;
    private int lvlNeeded;

    public PassivSkill(int m, float c, int l) {
        this.mana = m;
        this.cooldown = c;
        this.lvlNeeded = l;
    }
}
