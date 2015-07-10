package org.vijos.auth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.vijos.auth.command.CommandMain;
import org.vijos.auth.data.Settings;
import org.vijos.auth.data.Messages;
import org.vijos.auth.data.Sessions;
import org.vijos.auth.lib.API;
import org.vijos.auth.lib.ConsoleLogger;
import org.vijos.auth.listener.BlockListener;
import org.vijos.auth.listener.PlayerListener;
import org.vijos.auth.listener.LoginListener;
import org.vijos.auth.lib.Hash;
import org.vijos.auth.lib.Sender;

public class VijosLogin extends JavaPlugin {
	
	private static VijosLogin instance;
	
	public static VijosLogin i() {
		return VijosLogin.instance;
	}
	
	public void onEnable() {
		VijosLogin.instance = this;
		
		new Settings();
		new API();
		new ConsoleLogger();
		new Hash();
		new Sender();
		new CommandMain();
		new Messages();
		new Sessions();
		
		PluginManager manager = getServer().getPluginManager();
		
		manager.registerEvents(new PlayerListener(), this);
		manager.registerEvents(new BlockListener(), this);
		manager.registerEvents(new LoginListener(), this);
	}
		
	public void sendMessage(Player player, String action) {
		if (Sessions.i().get(player).isLoging()) {
			player.sendMessage(Messages.i().get("Login.Ing"));
			return;
		}
		player.sendMessage(Messages.i().get(action));
	}
	
	public void sendLoginMessage(Player player) {
		sendMessage(player, "Login.Tip");
	}
	
	public void sendBlockBreakMessage(Player player) {
		sendMessage(player, "Login.Block.Break");
	}
	
	public void sendBlockPlaceMessage(Player player) {
		sendMessage(player, "Login.Block.Place");
	}
	
	public void sendDropItemMessage(Player player) {
		sendMessage(player, "Login.Item.Drop");
	}
	
	public void sendInteractMessage(Player player) {
		sendMessage(player, "Login.Interact");
	}
	
	public void sendMoveMessage(Player player) {
		sendMessage(player, "Login.Move");
	}
	
	public void sendPickupItemMessage(Player player) {
		sendMessage(player, "Login.Item.Pickup");
	}
	
	public void onDisable() {
		ConsoleLogger.i().info("Plugin disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return CommandMain.i().onCommand(sender, cmd, commandLabel, args);
	}
	
}