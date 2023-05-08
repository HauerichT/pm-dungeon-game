package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.SkillTools;
import ecs.items.IOnCollect;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;
import graphic.Animation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Item extends Entity {

    public Item(String pathToTexture,String name,String description){
        super();

        ItemData item = new ItemData(
            ItemType.Active,
            new Animation(List.of(pathToTexture),1,true),
            new Animation(List.of(pathToTexture),1,true),
            name,
            description
        );
        WorldItemBuilder.buildWorldItem(item);

    }


}
