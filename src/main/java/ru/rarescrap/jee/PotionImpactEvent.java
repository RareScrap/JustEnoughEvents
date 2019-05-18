package ru.rarescrap.jee;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.EntityEvent;

import java.util.List;

@Cancelable
public class PotionImpactEvent extends EntityEvent {
    public AxisAlignedBB area;
    public List affectedEntities;

    public PotionImpactEvent(EntityPotion entityPotion, AxisAlignedBB area, List affectedEntities) {
        super(entityPotion);
        this.area= area;
        this.affectedEntities = affectedEntities;

    }

    public EntityPotion getEntityPotion() {
        return (EntityPotion) entity;
    }
}
