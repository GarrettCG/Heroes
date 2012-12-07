package com.github.odin8472.Plugina.attacks;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;

import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;
import com.github.odin8472.Plugina.utilities.Th3dtran;

public class ExplosionWave implements SwordMove{
	public ExplosionWave(StrikeDetails strike){
		info=strike;
		isdone=false;
		setLocationQueueForWaveStrike();
	}
	@Override
	public void execute() {
		if(locationQueue.isEmpty()){
			isdone=true;
			return;
		}else
		{
			 Plugina.explosionList.add(new ExploData(locationQueue.peek(),info.getPlayer(),true,true,true));
			 info.getWorld().createExplosion(locationQueue.remove(),(2+Plugina.playerMap.get(info.getPlayer()).getPower()));//Plugina.playerMap.get(info.getPlayer()).getPower()));//(float)Plugina.playerMap.get(info.getPlayer()).getPower());
		}
	}

	@Override
	public boolean isDone() {
		return isdone;
	}
	private boolean isdone;
	private void setLocationQueueForWaveStrike(){
		
		Th3dtran tranny=new Th3dtran();	
		for (double j=0;j<50*Math.PI;j=j+Math.PI/4){
			rotated=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(-5*Math.sin(j),0,j*4));
			Location finalResult=new Location(info.getWorld(),info.getStart().getX()+rotated.getX(),info.getStart().getY()+rotated.getY(),info.getStart().getZ()+rotated.getZ());
			locationQueue.add(finalResult);
		}

	}
	private StrikeDetails info;
	private Vector rotated;
	public Queue<Location> locationQueue=new LinkedList<Location>();
}
