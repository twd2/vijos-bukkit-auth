package org.vijos.auth.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.vijos.auth.VijosLogin;
import org.vijos.auth.data.PlayerSession;
import org.vijos.auth.data.Settings;
import org.vijos.auth.data.Sessions;
import org.vijos.auth.thread.StatusThread;

public class LoginListener implements Listener {
	
	public LoginListener() {
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		if (Settings.i().getBoolean("Login.AtSpawn")) {
			Sessions.i().get(player).setLastLocation(player.getLocation());
			player.teleport(player.getWorld().getSpawnLocation());
		}
		
		new StatusThread(player.getName().toLowerCase(), VijosLogin.i(), player);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final PlayerSession session = Sessions.i().get(player);
		
		if (!session.isLoggedIn() && session.getLastLocation() != null)
			player.teleport(session.getLastLocation());

		Sessions.i().del(player);
	}
	
}