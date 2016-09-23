package io.github.liuchibing.teleportinggate;

import cn.nukkit.*;
import cn.nukkit.plugin.*;
import cn.nukkit.event.*;
import cn.nukkit.event.player.*;
import cn.nukkit.level.*;
import cn.nukkit.utils.*;
import cn.nukkit.command.*;

import java.util.*;

public class TeleportingGate extends PluginBase implements Listener
{
	private final String LOCATIONSCONFIG = "gateLocations";
	private List<Map> gateLocations;
	private Config config;
	
	@Override
	public void onLoad() {
        getLogger().info("Hello Nukkit,I've loaded!");
    }
	
	@Override
    public void onEnable() {
		saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

	@Override
    public void onDisable() {
        getLogger().info("Hello Nukkit,I've disabled!");
    }

	public void loadConfig() {
		reloadConfig();
		config = getConfig();
		gateLocations = config.getMapList(LOCATIONSCONFIG);
		if(gateLocations.isEmpty()) {
			getLogger().info("gateLocations is empty. Generating Config file.");
			gateLocations.add(new HashMap<Location, Location>());
			saveConfig();
		}
		else {
			getLogger().info("gateLocation loaded!");
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		getLogger().info("event catched!");
		getLogger().info(event.getFrom().toString());
		getLogger().info(event.getTo().toString());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof ConsoleCommandSender) {
			
		}
		else if(sender instanceof Player) {
			
		}
		else {
			return false;
		}
		return true;
	}
}
