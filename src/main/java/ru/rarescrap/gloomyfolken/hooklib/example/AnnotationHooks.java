package ru.rarescrap.gloomyfolken.hooklib.example;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import ru.rarescrap.gloomyfolken.hooklib.asm.Hook;
import ru.rarescrap.gloomyfolken.hooklib.asm.Hook.ReturnValue;
import ru.rarescrap.gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeHooks;
import ru.rarescrap.jee.PotionImpactEvent;

import java.util.Iterator;
import java.util.List;

public class AnnotationHooks {

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(EntityPotion entityPotion, MovingObjectPosition p_70184_1_) {
        if (!entityPotion.worldObj.isRemote)
        {
            List list = Items.potionitem.getEffects(entityPotion.getPotionDamage());

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = entityPotion.boundingBox.expand(4.0D, 2.0D, 4.0D);
                List list1 = entityPotion.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                /*========================================= JEE START =========================================*/
                PotionImpactEvent event = new PotionImpactEvent(entityPotion, axisalignedbb, list1);
                if (list1 != null && !list1.isEmpty() && MinecraftForge.EVENT_BUS.post(event))
                /*========================================= JEE END =========================================*/
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = entityPotion.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == p_70184_1_.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                    Potion.potionTypes[i].affectEntity(entityPotion.getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            entityPotion.worldObj.playAuxSFX(2002, (int)Math.round(entityPotion.posX), (int)Math.round(entityPotion.posY), (int)Math.round(entityPotion.posZ), entityPotion.getPotionDamage());
            entityPotion.setDead();
        }
    }
}
