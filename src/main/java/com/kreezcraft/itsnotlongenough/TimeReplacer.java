package com.kreezcraft.itsnotlongenough;

import java.util.Date;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameRules;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimeReplacer {
	public static int wait = 35;
	  
	  public int getTimeSec(String time)
	  {
	    int hour = Integer.parseInt(time.substring(0, 2));
	    int min = Integer.parseInt(time.substring(3, 5));
	    int sec = Integer.parseInt(time.substring(6, 8));
	    return hour * 60 * 60 + min * 60 + sec;
	  }
	  
	  public long convertTime()
	  {
	    String time = new Date().toString().substring(11, 19);
	    int timeInSec = (int)(getTimeSec(time) / 3.6D);
	    int mcTime = 0;
	    if (timeInSec - 6000 < 0) {
	      mcTime = timeInSec + 24000 - 6000;
	    } else {
	      mcTime = timeInSec - 6000;
	    }
	    return mcTime;
	  }
	  
	  public void clearWeather(WorldInfo worldinfo)
	  {
	    worldinfo.setCleanWeatherTime((300 + new Random().nextInt(600)) * 20);
	    worldinfo.setRainTime(0);
	    worldinfo.setThunderTime(0);
	    worldinfo.setRaining(false);
	    worldinfo.setThundering(false);
	  }
	  
	  @SubscribeEvent
	  public void onWorldTick(TickEvent.WorldTickEvent event)
	  {
	    float chance = event.world.rand.nextFloat();
	    GameRules gameRules = event.world.getGameRules();
	    if ((chance <= 0.005D) && (!event.world.isRemote))
	    {
	      int time = (int)Math.abs(event.world.getWorldTime() - convertTime());
	      if ((time > 1) && (time < wait)) {
	        return;
	      }
	      if ((gameRules.hasRule("doDaylightCycle")) && 
	        (gameRules.getBoolean("doDaylightCycle"))) {
	        gameRules.setOrCreateGameRule("doDaylightCycle", "false");
	      }
	      if (time > wait) {
	        event.world.setWorldTime(convertTime());
	      }
	    }
	  }
	  
	  @SubscribeEvent
	  public void onPlayerTick(TickEvent.PlayerTickEvent event)
	  {
	    if (event.player.isPlayerSleeping())
	    {
	      event.player.wakeUpPlayer(true, false, true);
	      event.player.sendMessage(new TextComponentString(ChatFormatting.RED + "Sleeping disabled: " + "timeoverhaul" + " Installed!"));
	      if (event.player.world.isRaining())
	      {
	        clearWeather(event.player.world.getWorldInfo());
	        event.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "Weather Cleared!"));
	      }
	      event.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "Bed Spawn Set!"));
	    }
	  }
}
