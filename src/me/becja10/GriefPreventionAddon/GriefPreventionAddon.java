package me.becja10.GriefPreventionAddon;

import java.util.logging.Logger;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.events.CombatStartedEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.dsh105.echopet.api.EchoPetAPI;

public class GriefPreventionAddon extends JavaPlugin implements Listener{

	public static GriefPreventionAddon instance;
	public final static Logger logger = Logger.getLogger("Minecraft");
	
	public static GriefPrevention griefPrevention = null;
	public static EchoPetAPI echoPet = null;

	
//	private String configPath;
//	private FileConfiguration config;
//	private FileConfiguration outConfig;
	
	//Config Settings

	
		
//	private void loadConfig(){
//		configPath = this.getDataFolder().getAbsolutePath() + File.separator + "config.yml";
//		config = YamlConfiguration.loadConfiguration(new File(configPath));
//		outConfig = new YamlConfiguration();
//		
//		saveConfig(outConfig, configPath);		
//	}
	
//	private void saveConfig(FileConfiguration config, String path)
//	{
//        try{config.save(path);}
//        catch(IOException exception){logger.info("Unable to write to the configuration file at \"" + path + "\"");}
//	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager manager = getServer().getPluginManager();

		logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been enabled!");
		instance = this;		
		
		manager.registerEvents(this, this);
		
		Plugin ep = manager.getPlugin("EchoPet"); 
		
		if(ep != null && ep.isEnabled())		
			echoPet = EchoPetAPI.getAPI();
		
		griefPrevention = (GriefPrevention) manager.getPlugin("GriefPrevention");
		
		if(griefPrevention == null || !griefPrevention.isEnabled()){
			logger.severe("Could not load GriefPrevention. Disabling plugin");
			manager.disablePlugin(this);
			return;
		}		
		
		//loadConfig();		
	}
		
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " Has Been Disabled!");
//		saveConfig(outConfig, configPath);
	}
	
	@EventHandler
	public void onCombatStart(CombatStartedEvent event){
		Player attacker = event.getAttacker();
		Player defender = event.getDefender();
		if(echoPet != null){
			if(echoPet.hasPet(attacker))
				echoPet.removePet(attacker, false, false);
			if(echoPet.hasPet(defender))
				echoPet.removePet(defender, false, false);
		}
	}	
}
