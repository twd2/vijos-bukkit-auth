package org.vijos.auth.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import org.vijos.auth.VijosLogin;
import org.vijos.auth.data.Sessions;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (player == null || Sessions.i().get(player).isLoggedIn()) return;
			
		//Login command
		if (event.getMessage().toLowerCase().indexOf("/login") == 0)
			return;
		
		//No chat
		VijosLogin.i().sendLoginMessage(player);
		event.setMessage("/");
		event.setCancelled(true);     
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerDropItem(final PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (player == null || Sessions.i().get(player).isLoggedIn()) return;
		
		VijosLogin.i().sendDropItemMessage(player);
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player == null || Sessions.i().get(player).isLoggedIn()) return;
		
		VijosLogin.i().sendInteractMessage(player);
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {			
		//Player player = event.getPlayer();
		//if (player == null || Sessions.i().get(player).loggedIn) return;
		
		//VijosLogin.i().sendMoveMessage(player);
		//event.setTo(event.getFrom());
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (player == null || Sessions.i().get(player).isLoggedIn()) return;
		
		VijosLogin.i().sendPickupItemMessage(player);
		event.getItem().setPickupDelay(30);
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerDamageEvent (EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if (entity == null) return;
		if (entity instanceof Player && !Sessions.i().get((Player)entity).isLoggedIn())
			event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerRespawnEvent (PlayerRespawnEvent event) {
		Entity entity = event.getPlayer();
		if (entity == null) return;
		
		final Player player = (Player) entity;
		if (Sessions.i().get((Player)entity).isLoggedIn())
			return;
		
		VijosLogin.i().getServer().getScheduler().runTaskLaterAsynchronously(VijosLogin.i(), new Runnable() {
			public void run() { player.teleport(player.getWorld().getSpawnLocation()); }
		}, 10L);
	}
}