package fire.content;

import fire.gen.MutableMechUnit;
import fire.world.meta.FireAttribute;
import mindustry.ai.UnitCommand;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.power.LightBlock;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BuildVisibility;

import java.util.Arrays;

import static mindustry.content.Blocks.*;
import static mindustry.content.UnitTypes.*;

public class FireOverride{

    private static UnitType[] unitUpgrade(UnitType from, UnitType to){
        final var a = Arrays.copyOf(((Reconstructor)tetrativeReconstructor).upgrades.get(0), 2);
        a[0] = from;
        a[1] = to;
        return a;
    }

    public static void load(){

        //region block environment

        sandWater.itemDrop = Items.sand;
        darksandWater.itemDrop = Items.sand;
        darksandTaintedWater.itemDrop = Items.sand;
        sporePine.attributes.set(FireAttribute.tree, 1.5f);
        snowPine.attributes.set(FireAttribute.tree, 1.5f);
        pine.attributes.set(FireAttribute.tree, 1.5f);
        whiteTreeDead.attributes.set(FireAttribute.tree, 1f);
        whiteTree.attributes.set(FireAttribute.tree, 1f);
        grass.attributes.set(FireAttribute.grass, 0.25f);

        //endregion
        //region block turret

        wave.liquidCapacity += 10f;
        ((LiquidTurret)wave).ammoTypes.put(FireLiquids.liquidNitrogen, new LiquidBulletType(FireLiquids.liquidNitrogen){{
            damage = 4.55f;
            knockback = 0.7f;
            drag = 0.001f;
        }});
        tsunami.liquidCapacity += 20f;
        ((LiquidTurret)tsunami).ammoTypes.put(FireLiquids.liquidNitrogen, new LiquidBulletType(FireLiquids.liquidNitrogen){{
            speed = 4f;
            damage = 6.25f;
            lifetime = 49f;
            knockback = 1.3f;
            ammoMultiplier = 0.4f;
            statusDuration = 240f;
            puddleSize = 8f;
            orbSize = 4f;
            drag = 0.001f;
        }});

        //endregion
        //region block production

        ((Drill)laserDrill).drillTime -= 10f;
        ((Drill)laserDrill).hardnessDrillMultiplier -= 5f;
        ((Drill)blastDrill).drillTime -= 25f;
        ((Drill)blastDrill).hardnessDrillMultiplier -= 5f;

        //endregion
        //region block distribution

        phaseConveyor.itemCapacity += 5;
        ((ItemBridge)phaseConveyor).transportTime -= 1f;
        ((MassDriver)massDriver).rotateSpeed += 5f;
        ((MassDriver)massDriver).bulletSpeed += 9.5f;

        //endregion
        //region block liquid

        ((Pump)mechanicalPump).pumpAmount += 0.2f / 60f;
        ((Pump)impulsePump).pumpAmount += 1.2f / 9f / 60f;
        phaseConduit.liquidCapacity += 14f;

        //endregion
        //region units

        ((UnitFactory)groundFactory).plans.add(
            new UnitFactory.UnitPlan(FireUnitTypes.guarding, 1500f, ItemStack.with(
                Items.lead, 20,
                Items.titanium, 25,
                Items.silicon, 30
            ))
        );
        ((UnitFactory)airFactory).plans.add(
            new UnitFactory.UnitPlan(alpha, 2400f, ItemStack.with(
                Items.copper, 30,
                Items.lead, 40,
                Items.silicon, 30
            )),
            new UnitFactory.UnitPlan(FireUnitTypes.firefly, 2400f, ItemStack.with(
                Items.lead, 20,
                Items.metaglass, 10,
                Items.coal, 10,
                Items.silicon, 15
            ))
        );
        ((Reconstructor)additiveReconstructor).upgrades.add(
            unitUpgrade(alpha, beta),
            unitUpgrade(FireUnitTypes.guarding, FireUnitTypes.resisting),
            unitUpgrade(FireUnitTypes.blade, FireUnitTypes.hatchet),
            unitUpgrade(FireUnitTypes.firefly, FireUnitTypes.candlelight)
        );
        ((Reconstructor)multiplicativeReconstructor).upgrades.add(
            unitUpgrade(beta, FireUnitTypes.omicron),
            unitUpgrade(FireUnitTypes.resisting, FireUnitTypes.garrison),
            unitUpgrade(FireUnitTypes.hatchet, FireUnitTypes.castle)
        );
        ((Reconstructor)exponentialReconstructor).upgrades.add(
            unitUpgrade(FireUnitTypes.omicron, FireUnitTypes.pioneer),
            unitUpgrade(FireUnitTypes.garrison, FireUnitTypes.shelter)
        );

        //endregion
        //region block effect

        illuminator.buildVisibility = BuildVisibility.shown;
        ((LightBlock)illuminator).brightness += 0.25f;
        ((LightBlock)illuminator).radius += 60f;

        //endregion
        //region unit

        dagger.constructor = () -> new MutableMechUnit(FireUnitTypes.blade);
        mace.constructor = () -> new MutableMechUnit(FireUnitTypes.hatchet);
        fortress.constructor = () -> new MutableMechUnit(FireUnitTypes.castle);

        alpha.coreUnitDock = true;
        beta.coreUnitDock = true;
        gamma.coreUnitDock = true;
        alpha.defaultCommand = UnitCommand.mineCommand;
        beta.defaultCommand = UnitCommand.mineCommand;

        flare.speed += 0.5f;
        flare.trailLength = 3;

        //endregion
        //region liquid

        Liquids.neoplasm.effect = FireStatusEffects.overgrown;
    }
}
