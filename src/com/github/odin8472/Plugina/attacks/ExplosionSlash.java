package com.github.odin8472.Plugina.attacks;

import java.util.LinkedList;

import java.util.ListIterator;




import org.bukkit.Location;

import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Fighter;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;
import com.github.odin8472.Plugina.utilities.Th3dtran;

public class ExplosionSlash implements SwordMove{
	public ExplosionSlash(StrikeDetails strike){
		info=strike;
		isdone=false;
		counter=0;
		locationQueue=new LinkedList<Location>();
	}
	@Override
	public void execute() {
		if(counter>6){//probably never gonna happen, i should write a different end condition
			isdone=true;
			return;
		}else
		{
			setLocationQueueForStrike();
			ListIterator<Location> litr = locationQueue.listIterator();
			while(litr.hasNext()) {
				Location element = litr.next();
				if(element.getBlock().getTypeId()==0){//slash stops on block hit
					Plugina.explosionList.add(new ExploData(element,info.getPlayer(),false,false,true));
					info.getWorld().createExplosion(element,1+(int)(Plugina.playerMap.get(info.getPlayer()).getPower()/5));//Plugina.playerMap.get(info.getPlayer()).getPower()));//(float)Plugina.playerMap.get(info.getPlayer()).getPower());
					System.out.println("explosion at:"+element);
				}else{
					System.out.println("inside else statement, block was not air apparently");
					counter++;
					return;
				}
			}
			counter++;
		}
	}

	@Override
	public boolean isDone() {
		return isdone;
	}
	private boolean isdone;
	private void setLocationQueueForStrike(){
		Location start=info.getPlayer().getLocation();
		locationQueue=new LinkedList<Location>();
		Fighter f=(Fighter)Plugina.playerMap.get(info.getPlayer());
		Th3dtran tranny=new Th3dtran();	
		Vector rotatedstart=tranny.Rotate(info.getYaw(), info.getPitch(), f.explosionslashstart[counter]);
		System.out.println("rotatedstart="+rotatedstart);
		for(int i=1;i<20;i++){
			Vector rotatedend=tranny.Rotate(info.getYaw(), info.getPitch(), f.explosionslashend[counter].clone());
			System.out.println("rotatedend="+rotatedend);
			Vector intermed=rotatedend.clone().multiply(i).add(start.toVector()).add(rotatedstart);
			intermed.setY(intermed.getY()+2);
			System.out.println("intermed="+intermed);
			locationQueue.add(new Location(info.getWorld(),intermed.getX(),intermed.getY(),intermed.getZ()));
		}
		//System.out.println("getstart.toVector"+start.toVector());
	}
	private int counter;
	private StrikeDetails info;
	public LinkedList<Location> locationQueue;
	
}