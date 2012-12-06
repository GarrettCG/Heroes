package com.github.odin8472.Plugina.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ExploData{
	public ExploData(Location where,Player p,boolean doesBlockDamage,boolean doesSelfDamage,boolean doesAnimation){
		blockDamage=doesBlockDamage;
		selfDamage=doesSelfDamage;
		animation=doesAnimation;
		expPoint=where;
	    player=p;
	}
	public Location getExp(){
		return expPoint;
	}
	public void setExp(Location loc){
		expPoint=loc;
	}

	public void setPlayer(Player p){
		player=p;
	}
	public Player getPlayer(){
		return player;
	}
	
	
	public void setDoesBlockDamage(boolean b){
		blockDamage=b;
	}
	public boolean getDoesBlockDamage(){
		return blockDamage;
	}
	public void setDoesSelfDamage(boolean b){
		selfDamage=b;
	}
	public boolean getDoesSelfDamage(){
		return selfDamage;
	}
	public boolean getDoesAnimation() {
		return animation;
	}
	public void setDoesAnimation(boolean a) {
		animation=a;
	}
	private boolean blockDamage;
	private boolean selfDamage;
	private boolean animation;
	private Location expPoint;
	private Player player;

}