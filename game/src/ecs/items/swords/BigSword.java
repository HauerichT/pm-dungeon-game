package ecs.items.swords;

import dslToGame.AnimationBuilder;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import graphic.Animation;
import tools.Point;

public class BigSword extends ItemData {
    public BigSword(){
        WorldItemBuilder.buildWorldItem(new ItemData
            (ItemType.Basic
                ,AnimationBuilder.buildAnimation("weapon_anime_sword.png")
                ,AnimationBuilder.buildAnimation("sword/weapon_anime_sword.png"),
                "Big sword",
                "ezez"));

    }


}
