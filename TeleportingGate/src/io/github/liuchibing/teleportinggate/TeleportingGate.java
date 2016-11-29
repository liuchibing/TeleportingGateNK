package io.github.liuchibing.teleportinggate;

import cn.nukkit.plugin.*;
import cn.nukkit.event.*;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.*;
import cn.nukkit.command.*;
import cn.nukkit.math.*;

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
        loadConfig();//加载Config并将Gate读取为ArrayList
        getServer().getPluginManager().registerEvents(this, this);
    }

	@Override
    public void onDisable() {
        getLogger().info("Hello Nukkit,I've disabled!");
    }

	private void loadConfig() {
		reloadConfig();
		config = getConfig();
		gateLocations = (Map<String, Map>)config.get(LOCATIONSCONFIG);
		gates = new ArrayList<Gate>();
		for(String item : gateLocations.keySet()) {
			unserializeGate(gateLocations.get(item));
		}
	}
	
	private void unserializeGate(Map item) {
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


	private void serializeGate(Gate gate) { //将一个gate存到config中
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
		//getLogger().info("config saved: "+ m.get("name"));
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for(Gate item: gates) {
            if(((int) event.getTo().x)==item.fromX) {
                if(((int) event.getTo().y)==item.fromY) {
                    if(((int) event.getTo().z)==item.fromZ) {
                        event.getPlayer().teleport(new Vector3(item.fromX, item.fromY, item.fromZ));
                    }
                }
            }
        }
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean flag = true;
        boolean canEdit = sender.isOp();
		if(args.length == 0) {
            for (Gate item : gates) {
                getLogger().info(item.name + ": from: " + item.fromX + "," + item.fromY + "," + item.fromZ + " to: " + item.toX + "," + item.toY + "," + item.toZ);
            }
        }
        else if(args[0].equals("set")) { //format: /tpgate set name fromX fromY fromZ toX toY toZ
            if(!canEdit) {
                getLogger().error("权限不足：只有OP有权修改传送门。");
                return false;
            }
            try {
                Gate g = new Gate();
                g.name = args[1];
                g.fromX = Integer.parseInt(args[2]);
                g.fromY = Integer.parseInt(args[3]);
                g.fromZ = Integer.parseInt(args[4]);
                g.toX = Integer.parseInt(args[5]);
                g.toY = Integer.parseInt(args[6]);
                g.toZ = Integer.parseInt(args[7]);
                gates.add(g);
                serializeGate(g);
                getLogger().info("已创建传送门: " + args[1]);
            }
            catch(Exception e) {
                getLogger().error("命令格式错误！");
                flag = false;
            }
        }
        else if(args[0].equals("del")) { //format: /tpgate del name
            if(!canEdit) {
                getLogger().error("权限不足：只有OP有权修改传送门。");
                return false;
            }
            try {
                if(gateLocations.containsKey(args[1])) {
                    Gate tempGate = null;
                    for(Gate item: gates) {
                        if(item.name.equals(args[1])) {
                            tempGate = item;
                        }
                    }
                    if(tempGate != null) {
                        gates.remove(tempGate);
                    }
                    gateLocations.remove(args[1]);
                    config.save();
                    getLogger().info("已删除传送门:" + args[1]);
                }
                else {
                    getLogger().error("要删除的传送门不存在！");
                    flag = false;
                }
            }
            catch(Exception e) {
                getLogger().error("命令格式错误！");
                flag = false;
            }
        }
        else {
            getLogger().error("命令格式错误！");
            flag = false;
        }
		
		return flag;
	}
}
