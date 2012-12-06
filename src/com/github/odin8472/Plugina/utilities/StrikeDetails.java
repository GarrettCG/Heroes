package com.github.odin8472.Plugina.utilities;


import java.util.LinkedList;
import java.util.Queue;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class StrikeDetails{
	public StrikeDetails(Location you, Location target,World w,Player p){
	    world=w;
		startPoint=you;
	    endPoint = target;
	    pitch=p.getLocation().getPitch();
	    yaw=p.getLocation().getYaw();
	    player=p;
	}
	public void setPitch(double p){
		pitch=p;
	}
	public void setYaw(double y){
		yaw=y;
	}
	public double getPitch(){
		return pitch;
	}
	public double getYaw(){
		return yaw;
	}
	public Location getStart(){
		return startPoint;
	}
	public Location getEnd(){
		return endPoint;
	}
	public void setStart(Location loc){
		startPoint=loc;
	}
	public void setEnd(Location loc){
		endPoint=loc;
	}
	public void setWorld(World w){
		world=w;
	}
	public World getWorld(){
		return world;
	}
	public void setPlayer(Player p){
		player=p;
	}
	public Player getPlayer(){
		return player;
	}
	public Entity getTarget() {
		return target;
	}
	public void setTarget(Entity target) {
		this.target = target;
	}
	private Entity target=null;
	private double yaw;
	private double pitch;
	private World world;
	private Location startPoint;
	private Location endPoint;
	private Player player;
}
