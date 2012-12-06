package com.github.odin8472.Plugina.blocks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.attacks.TransformBat;
import com.github.odin8472.Plugina.heroes.Hero;
import com.github.odin8472.Plugina.threadTypes.PluginaThread;

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