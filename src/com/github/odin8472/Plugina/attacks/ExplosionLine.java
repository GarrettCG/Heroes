package com.github.odin8472.Plugina.attacks;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.EntityEffect;
import org.bukkit.Location;

import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;
import com.github.odin8472.Plugina.utilities.Th3dtran;

public class ExplosionLine implements SwordMove{
	public ExplosionLine(StrikeDetails strike){
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
			System.out.println("about to add explodata(explosionLine to the masterlist");
			 Plugina.explosionList.add(new ExploData(locationQueue.peek(),info.getPlayer(),true,true,true));
			 info.getWorld().createExplosion(locationQueue.remove(),(2+Plugina.playerMap.get(info.getPlayer()).getPower()));//Plugina.playerMap.get(info.getPlayer()).getPower()));//(float)Plugina.playerMap.get(info.getPlayer()).getPower());
			 info.getPlayer().playEffect(EntityEffect.WOLF_SMOKE);
		}
	}

	@Override
	public boolean isDone() {
		return isdone;
	}
	private boolean isdone;
	private void setLocationQueueForWaveStrike(){
		
		Th3dtran tranny=new Th3dtran();	
		for (double j=0;j<40+20*Plugina.playerMap.get(info.getPlayer()).getPower();j++){
			rotated=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(0,0,j));
			Location finalResult=new Location(info.getWorld(),info.getStart().getX()+rotated.getX(),info.getStart().getY()+rotated.getY(),info.getStart().getZ()+rotated.getZ());
			locationQueue.add(finalResult);
		}

	}
	private StrikeDetails info;
	private Vector rotated;
	public Queue<Location> locationQueue=new LinkedList<Location>();
}
