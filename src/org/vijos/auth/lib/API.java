package org.vijos.auth.lib;

import java.io.IOException;
import java.net.URLEncoder;

import org.bukkit.entity.Player;

import org.vijos.auth.VijosLogin;
import org.vijos.auth.data.Settings;
import org.vijos.auth.data.Sessions;
import org.vijos.auth.data.Messages;
import org.vijos.auth.thread.LoginThread;

public class API {
	
	public static final int API_SUCCESS = 0;
	public static final int API_BANNED = 1;
	public static final int API_USERNOTEXISTS = 2;
	public static final int API_LOGINFAILED = 3;
	public static final int API_UNKNOWNERROR = 10;
	
	private static API instance;
	
	private String statusURI;
	private String loginURI;
	
	public static API i() {
		return API.instance;
	}
	
	public API() {
		API.instance = this;
		
		this.statusURI = Settings.i().getString("API.StatusURI");
		this.loginURI = Settings.i().getString("API.LoginURI");
	}
	
	public void login(Player player, String password) {
		if (Sessions.i().get(player).isLoggedIn()) {
			player.sendMessage(Messages.i().get("Login.In"));
			return;
		}
		
		String playerName = player.getName().toLowerCase();
		
		if (Sessions.i().get(player).isLoging()) {
			player.sendMessage(Messages.i().get("Login.Ing"));
			return;
		}
		
		new LoginThread(playerName, password, VijosLogin.i(), player, true);
		player.sendMessage(Messages.i().get("Login.Start"));
	}
	
	public int getStatus(String username, Player player) {
		int line = API_UNKNOWNERROR;
		
		try {
			String dataPost = "username=" + URLEncoder.encode(username, "UTF-8");
			
			line = Integer.parseInt(Sender.Post(this.statusURI, dataPost));
		} catch (IOException exception) {}
		
		return line;
	}

	public int getLogin(String username, String password) {
		int line = API_UNKNOWNERROR;
		
		try {
			String dataPost = "username=" + URLEncoder.encode(username, "UTF-8") + "&hash=" + password;
			
			line = Integer.parseInt(Sender.Post(this.loginURI, dataPost));
		} catch (IOException exception) {}
		
		return line;
	}
}
