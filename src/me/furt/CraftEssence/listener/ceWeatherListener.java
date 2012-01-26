package me.furt.CraftEssence.listener;

import me.furt.CraftEssence.CraftEssence;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ceWeatherListener implements Listener {

	public ceWeatherListener(CraftEssence plugin) {
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLightningStrike(LightningStrikeEvent event) {
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onThunderChange(ThunderChangeEvent event) {
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onWeatherChange(WeatherChangeEvent event) {
	}
}
