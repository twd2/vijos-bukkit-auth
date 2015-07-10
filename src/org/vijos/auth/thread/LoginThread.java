package org.vijos.auth.thread;

import org.bukkit.entity.Player;

import org.vijos.auth.VijosLogin;
import org.vijos.auth.data.PlayerSession;
import org.vijos.auth.data.Sessions;
import org.vijos.auth.data.Messages;
import org.vijos.auth.data.Settings;
import org.vijos.auth.lib.Hash;
import org.vijos.auth.lib.ConsoleLogger;
import org.vijos.auth.lib.API;

public class LoginThread extends Thread {
	
	public String username;
	public String password;
	public VijosLogin plugin;
	public Player player;
	
	public LoginThread(String username, String password, VijosLogin plugin, Player player, Boolean putLoging) {
		this.username = username;
		this.plugin = plugin;
		this.player = player;
		
		if (password != null)
			this.password = Hash.computeHash(Settings.HashMethod, password);
		else
			this.password = password;
		
		this.start();
	}
	
	public void run() {
		if (!this.player.isOnline())
			return;
		
		if (this.getLogin()) {
			PlayerSession session = Sessions.i().get(this.player);
			if (session.getLastLocation() != null)
				player.teleport(session.getLastLocation());
			session.setLoggedIn(true);
			session.setLoginState(System.currentTimeMillis());
			this.player.sendMessage(Messages.i().get("Login.Success"));
			ConsoleLogger.i().info(player.getName() + "[" + player.getAddress().getAddress().getHostAddress() + "] logged in.");
		} else {
			this.player.sendMessage(Messages.i().get("Login.Fail"));
		}
	}
	
	public boolean getLogin() {
		int line = API.i().getLogin(this.username, this.password);
		
		if (line == API.API_SUCCESS) {
			return true;
		}
		
		return false;
	}
}