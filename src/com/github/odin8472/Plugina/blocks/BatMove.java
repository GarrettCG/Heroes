package com.github.odin8472.Plugina.blocks;


import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;




public class BatMove implements BlockingMove{
	public BatMove(Player p){
		player=p;
		isdone=false;
		counter=0;
		bat=p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
		player.setPassenger(bat);
	}
	public void execute() {
		//Vector dif=player.getLocation().subtract(bat.getLocation()).toVector();
		//bat.setVelocity(dif.multiply(-1/2));

		counter++;
		if(counter>60){
			bat.remove();
			isdone=true;
		}
	}
	@Override
	public boolean isDone() {
		if(isdone)
			return true;
		else
			return false;
	}
	private boolean isdone;
	private Player player;
	private int counter;
	private Entity bat;
}


//TransformBat bt = new TransformBat(getPlayer());
//PluginaThread batTask = new PluginaThread(bt);
//batTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, batTask, 1,60*20));