package io.github.liuchibing.teleportinggate;

import cn.nukkit.plugin.*;
import cn.nukkit.event.*;
import cn.nukkit.event.player.*;
import cn.nukkit.level.*;
import cn.nukkit.utils.*;

import java.util.*;

public class TeleportingGate extends PluginBase implements Listener
{
	private final String LOCATIONSCONFIG = "gateLocations";
	private List<Map> gateLocations;
	
	@Override
	public void onLoad() {
        getLogger().info("Hello Nukkit,I've loaded!");
    }
	
	@Override
    public void onEnable() {
		saveDefaultConfig();
        loadConfig();
        
    }

	@Override
    public void onDisable() {
        getLogger().info("Hello Nukkit,I've disabled!");
    }

	public void loadConfig() {
		reloadConfig();
		gateLocations = getConfig().getMapList(LOCATIONSCONFIG);
		if(gateLocations.isEmpty()) {
			getLogger().info("gateLocations is empty. Generating Config file.");
			gateLocations.add(new HashMap<Integer, Integer>());
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
	}
}
