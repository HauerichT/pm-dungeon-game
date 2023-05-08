package ecs.components.skill;

import ecs.components.Component;
import ecs.entities.Entity;

public class MeleeComponent extends Component {
    private final Entity entity;
    private final MeleeSkill skill;

    public MeleeComponent(Entity entity, MeleeSkill skill) {
        super(entity);
        this.entity = entity;
        this.skill = skill;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public MeleeSkill getMeleeSkill() {
        return skill;
    }
}
