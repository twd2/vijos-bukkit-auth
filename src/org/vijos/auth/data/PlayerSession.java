package org.vijos.auth.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.vijos.auth.thread.LoginThread;

public class PlayerSession {

	private boolean loggedIn = false;
	private Long loginState = 0L;
	private Location lastLocation;
	private boolean loging = false;
	private LoginThread loginThread;
	private Player player;
	
	public 	PlayerSession(Player player) {
		this.player = player;		
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Long getLoginState() {
		return loginState;
	}

	public void setLoginState(Long loginState) {
		this.loginState = loginState;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public boolean isLoging() {
		return loging;
	}

	public void setLoging(boolean loging) {
		this.loging = loging;
	}

	public LoginThread getLoginThread() {
		return loginThread;
	}

	public void setLoginThread(LoginThread loginThread) {
		this.loginThread = loginThread;
	}

	public Player getPlayer() {
		return player;
	}
	
}
