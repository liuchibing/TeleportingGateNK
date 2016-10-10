package io.github.liuchibing.teleportinggate;

import cn.nukkit.*;
import cn.nukkit.plugin.*;
import cn.nukkit.event.*;
import cn.nukkit.event.player.*;
import cn.nukkit.level.*;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.*;
import cn.nukkit.command.*;

import java.util.*;

public class TeleportingGate extends PluginBase implements Listener
{
	private final String LOCATIONSCONFIG = "gateLocations";
	private Map<String, Map> gateLocations;
	private Config config;
	private ArrayList<Gate> gates;
	
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
		gateLocations = (Map<String, Map>)config.get(LOCATIONSCONFIG);
		gates = new ArrayList<Gate>();
		for(String item : gateLocations.keySet()) {
			unserializeGate(gateLocations.get(item));
		}
	}
	
	public void unserializeGate(Map item) {
		Gate g = new Gate();
		g.fromX = Integer.parseInt((String)item.get("fromX"));
		g.fromY = Integer.parseInt((String)item.get("fromY"));
		g.fromZ = Integer.parseInt((String)item.get("fromZ"));
		g.toX = Integer.parseInt((String)item.get("toX"));
		g.toY = Integer.parseInt((String)item.get("toY"));
		g.toZ = Integer.parseInt((String)item.get("toZ"));
		g.name = (String)item.get("name");
		gates.add(g);
	}
	
	public void serializeGate(Gate gate) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("fromX", String.valueOf(gate.fromX));
		m.put("fromY", String.valueOf(gate.fromY));
		m.put("fromZ", String.valueOf(gate.fromZ));
		m.put("toX", String.valueOf(gate.toX));
		m.put("toY", String.valueOf(gate.toY));
		m.put("toZ", String.valueOf(gate.toZ));
		m.put("name", gate.name);
		gateLocations.put(gate.name, m);
		config.save();
		getLogger().info("config saved: "+ m.get("name"));
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		getLogger().info("event catched!");
		getLogger().info(event.getFrom().toString());
		getLogger().info(event.getTo().toString());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			for(Gate item: gates) {
				getLogger().info(item.name + " from: " + item.fromX + "," + item.fromY + "," + item.fromZ);
			}
		}
		
		return true;
	}
}
