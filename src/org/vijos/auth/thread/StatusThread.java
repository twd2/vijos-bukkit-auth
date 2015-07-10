package org.vijos.auth.thread;

import org.bukkit.entity.Player;

import org.vijos.auth.VijosLogin;
import org.vijos.auth.data.Messages;
import org.vijos.auth.lib.API;

public class StatusThread extends LoginThread {
	
	public StatusThread(String username, VijosLogin plugin, Player player) {
		super(username, null, plugin, player, false);
	}
	
	public void run() {
		if (!this.player.isOnline())
			return;
		
		if (!this.player.hasPermission("vijoslogin.login") && player.isOnline()) {
			player.kickPlayer(Messages.i().get("Login.Out"));
		}
		
		if (this.getStatus()) {
			VijosLogin.i().sendLoginMessage(this.player);
		}
	}
	
	public boolean getStatus() {
		int line = API.i().getStatus(this.username, this.player);
		
		if (line == API.API_SUCCESS)
			return true;
		
		if (line == API.API_BANNED) {
			this.player.kickPlayer(Messages.i().get("Banned"));
			return false;
		}
		
		this.player.kickPlayer(Messages.i().get("UserNotExists"));
		return false;
	}
}