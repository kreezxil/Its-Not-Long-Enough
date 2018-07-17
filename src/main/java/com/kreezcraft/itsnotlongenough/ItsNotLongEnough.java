package com.kreezcraft.itsnotlongenough;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ItsNotLongEnough.MODID, name = ItsNotLongEnough.NAME, version = ItsNotLongEnough.VERSION)
public class ItsNotLongEnough
{
    public static final String MODID = "itsnotlongenough";
    public static final String NAME = "It's Not Long Enough";
    public static final String VERSION = "@version@";

    @SidedProxy(clientSide="com.kreezcraft.itsnotlongenough.ClientProxy", serverSide="com.kreezcraft.itsnotlongenough.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("timeoverhaul")
    public static ItsNotLongEnough instance;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
      proxy.registerRenderThings();
      MinecraftForge.EVENT_BUS.register(new TimeReplacer());
    }
}
