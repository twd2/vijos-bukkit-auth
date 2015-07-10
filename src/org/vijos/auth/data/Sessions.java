package org.vijos.auth.data;

import java.util.Hashtable;
import org.bukkit.entity.Player;


public class Sessions {
	
	private static Sessions instance;
	
	public Hashtable<String, PlayerSession> sessions;
	
	public static Sessions i() {
		return Sessions.instance;
	}
	
	public Sessions() {
		Sessions.instance = this;
		
		this.sessions = new Hashtable<String, PlayerSession>();
	}
	
	public PlayerSession get(Player player) {
		String playerName = player.getPlayer().getName().toLowerCase();
		
		if (!this.sessions.containsKey(playerName))
			this.sessions.put(playerName, new PlayerSession(player));
		return this.sessions.get(playerName);
	}
	
	public void del(Player player) {
		String playerName = player.getPlayer().getName().toLowerCase();
		
		if (!this.sessions.containsKey(playerName))
			return;
		this.sessions.remove(playerName);
	}
	
}
