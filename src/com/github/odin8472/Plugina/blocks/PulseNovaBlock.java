package com.github.odin8472.Plugina.blocks;

import java.util.LinkedList;
import java.util.ListIterator;


import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Hero;
import com.github.odin8472.Plugina.utilities.ExploData;


public class PulseNovaBlock implements BlockingMove{
	public PulseNovaBlock(Player p){
		player=p;
		isdone=false;
		
	}
	public void execute() {
		if (player.isBlocking()){
			if(!(Plugina.playerMap.get(player).getCurrentMana()<1)){
				System.out.println("About to lose one mana, current mana:"+Plugina.playerMap.get(player).getCurrentMana());
				Plugina.playerMap.get(player).loseMana(1, player);
				Location l=player.getLocation();
				
				Hero fighter=Plugina.playerMap.get(player);
				Location startpoint=player.getLocation();
				if(!Plugina.playerMap.get(player).isLocationAware){
					System.out.println("wasn't location aware");
					fighter.xcoord=startpoint.getX();
					fighter.xcoord=startpoint.getY();
					fighter.zcoord=startpoint.getZ();
					setLocationQueueForPulseNova();
					Plugina.playerMap.get(player).isLocationAware=true;
				}else if((int)fighter.xcoord!=(int)startpoint.getX()||(int)fighter.zcoord!=(int)startpoint.getZ()||(int)fighter.xcoord!=(int)startpoint.getX()){//it's aware but the coordinates have changed
		
					System.out.println("data is stale");
					fighter.xcoord=startpoint.getX();
					fighter.xcoord=startpoint.getY();
					fighter.zcoord=startpoint.getZ();
					setLocationQueueForPulseNova();
					Plugina.playerMap.get(player).isLocationAware=true;
				}
				//if(!Plugina.playerMap.get(player).isLocationAware)
				if(locationQueue==null)
					return;
					//System.out.println("dunno why, but locationqueue is null");
				ListIterator<Location> litr = locationQueue.listIterator();
				while(litr.hasNext()) {
					Location element = litr.next();
					if(element!=null){
						Plugina.explosionList.add(new ExploData(element,player,false,false,false));
						player.getWorld().playEffect(element, Effect.POTION_BREAK, 4, 50);
						player.getWorld().createExplosion(element,(float)1+(int)(Plugina.playerMap.get(player).getPower()/2));
					}
				}
			}
			else{  
				isdone=true;
				Plugina.playerMap.get(player).isBlockingArray[3]=false;//set the true value of isblocking 
				return;
			}
				
		}
		else{
			isdone=true;
			Plugina.playerMap.get(player).isBlockingArray[3]=false;//set the true value of isblocking 
			return;
		}
	}

	@Override
	public boolean isDone() {
		if(isdone)
			return true;
		else
			return false;
	}
	private Location getAppropriateYlevel(Location location){//returns null location if it doesn't find an appropriate block
		Location loc=location.clone();
		loc.setY(loc.getY()-2);
		if(loc.getBlock().getTypeId()==0){
			return loc;
		}else{
			loc.setY(loc.getY()+1);
			if(loc.getBlock().getTypeId()==0){
				return loc;
			}else{
				loc.setY(loc.getY()+1);
				if(loc.getBlock().getTypeId()==0){
					return loc;
				}else{
					loc.setY(loc.getY()+1);
					if(loc.getBlock().getTypeId()==0){
						return loc;
					}else{
						loc.setY(loc.getY()+1);
						if(loc.getBlock().getTypeId()==0){
							return loc;
						}
						else
							return null;
					}
				}
			}
		}
	}
	private void setLocationQueueForPulseNova(){
		locationQueue=new LinkedList<Location>();
		Location result;
		Location l=player.getLocation();
		Location locate1=new Location(player.getWorld(),l.getX()+2,l.getY(),l.getZ());
		result=getAppropriateYlevel(locate1);
		if(result==null){
			locate1=null;
		}else{
			locate1=result;
		}
		Location locate2=new Location(player.getWorld(),l.getX()-2,l.getY(),l.getZ());
		result=getAppropriateYlevel(locate2);
		if(result==null){
			locate2=null;
		}else{
			locate2=result;
		}
		Location locate3=new Location(player.getWorld(),l.getX(),l.getY(),l.getZ()+2);
		result=getAppropriateYlevel(locate3);
		if(result==null){
			locate3=null;
		}else{
			locate3=result;
		}
		Location locate4=new Location(player.getWorld(),l.getX(),l.getY(),l.getZ()-2);
		result=getAppropriateYlevel(locate4);
		if(result==null){
			locate4=null;
		}else{
			locate4=result;
		}
		Location locate5=new Location(player.getWorld(),l.getX()+2,l.getY(),l.getZ()+2);
		result=getAppropriateYlevel(locate5);
		if(result==null){
			locate5=null;
		}else{
			locate5=result;
		}
		Location locate6=new Location(player.getWorld(),l.getX()+2,l.getY(),l.getZ()-2);
		result=getAppropriateYlevel(locate6);
		if(result==null){
			locate6=null;
		}else{
			locate6=result;
		}
		Location locate7=new Location(player.getWorld(),l.getX()-2,l.getY(),l.getZ()+2);
		result=getAppropriateYlevel(locate7);
		if(result==null){
			locate7=null;
		}else{
			locate7=result;
		}
		Location locate8=new Location(player.getWorld(),l.getX()-2,l.getY(),l.getZ()-2);
		result=getAppropriateYlevel(locate8);
		if(result==null){
			locate8=null;
		}else{
			locate8=result;
		}
		locationQueue.add(locate1);
		locationQueue.add(locate2);
		locationQueue.add(locate3);
		locationQueue.add(locate4);
		locationQueue.add(locate5);
		locationQueue.add(locate6);
		locationQueue.add(locate7);
		locationQueue.add(locate8);
		if(locate1==null&&locate2==null&&locate3==null&&locate4==null&&locate5==null&&locate6==null&&locate7==null&&locate8==null)
			System.out.println("they're all null!");
	}

	public LinkedList<Location> locationQueue;
	private boolean isdone;
	private Player player;
}
