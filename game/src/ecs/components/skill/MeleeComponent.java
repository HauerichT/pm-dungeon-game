package ecs.components.skill;

import ecs.components.Component;
import ecs.entities.Entity;

public class MeleeComponent extends Component {
    private final Entity entity;
    private final DamageMeleeSkill skill;

    public MeleeComponent(Entity entity, DamageMeleeSkill skill) {
        super(entity);
        this.entity = entity;
        this.skill = skill;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public DamageMeleeSkill getMeleeSkill() {
        return skill;
    }
}
