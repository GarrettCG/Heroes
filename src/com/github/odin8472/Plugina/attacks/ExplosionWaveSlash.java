package com.github.odin8472.Plugina.attacks;

import java.util.ArrayList;
import java.util.LinkedList;

import java.util.ListIterator;




import org.bukkit.Location;

import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;
import com.github.odin8472.Plugina.utilities.Th3dtran;

public class ExplosionWaveSlash implements SwordMove{
	ExplosionWaveSlash(StrikeDetails strike){
		info=strike;
		isdone=false;
		setLocationQueueForStrike();
	}
	@Override
	public void execute() {
		if(locationQueues.isEmpty()){//probably never gonna happen, i should write a different end condition
			isdone=true;
			return;
		}else
		{
			for(int numexplo=0;numexplo<2;numexplo++){//do it twice
			ListIterator<LinkedList<Location>> litr = locationQueues.listIterator();
			while(litr.hasNext()) {
				LinkedList<Location> element = litr.next();
				if(!element.isEmpty()){
					Plugina.explosionList.add(new ExploData(element.peek(),info.getPlayer(),false,false,true));
					info.getWorld().createExplosion(element.remove(),1+(int)(Plugina.playerMap.get(info.getPlayer()).getPower()/5));//Plugina.playerMap.get(info.getPlayer()).getPower()));//(float)Plugina.playerMap.get(info.getPlayer()).getPower());
				}else{
					litr.remove();
				}
			}
			}//kind of threw in a for loop
		}
	}

	@Override
	public boolean isDone() {
		return isdone;
	}
	private boolean isdone;
	private void setLocationQueueForStrike(){
		Th3dtran tranny=new Th3dtran();	
		Vector rotated=new Vector(info.getEnd().getX()-info.getStart().getX(),info.getEnd().getY()-(info.getStart().getY()+1),info.getEnd().getZ()-info.getStart().getZ()).normalize();
		Vector rotated2=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(-0.8,1,1));
		Vector rotated3=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(-0.6,0.75,1));
		Vector rotated4=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(-0.4,0.5,1));
		Vector rotated5=tranny.Rotate(info.getYaw(), info.getPitch(),new Vector(-0.2,0.25,1));
		rotated2.normalize();
		rotated3.normalize();
		rotated4.normalize();
		rotated5.normalize();
		Vector []normalVects=new Vector[5];
		normalVects[0]=rotated;
		normalVects[1]=rotated2;
		normalVects[2]=rotated3;
		normalVects[3]=rotated4;
		normalVects[4]=rotated5;
		for(int k=0;k<5;k++){
			locationQueues.add(new LinkedList<Location>());
			for(double i=2;i<21;i++){
				Vector temp=normalVects[k].clone().multiply(i);
				locationQueues.get(k).add(new Location(info.getWorld(),info.getStart().getX()+temp.getX(),info.getStart().getY()+temp.getY()+1,info.getStart().getZ()+temp.getZ()));
				if (info.getWorld().getBlockTypeIdAt((int) (info.getStart().getX()+temp.getX()),(int) (info.getStart().getY()+1+temp.getY()),(int) (info.getStart().getZ()+temp.getZ()))!=0){
					break;
				}
			}
		}
	}
	private StrikeDetails info;
	public ArrayList<LinkedList<Location>> locationQueues=new ArrayList<LinkedList<Location>>();
}