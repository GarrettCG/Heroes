package com.github.odin8472.Plugina.blocks;

import java.util.LinkedList;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;

public class HoverBlock implements BlockingMove{
	public HoverBlock(Player p){
		player=p;
		isdone=false;
	}
	public void execute() {
		if (player.isBlocking()){
			if(!(Plugina.playerMap.get(player).getCurrentMana()<1)){
				System.out.println("About to lose one mana, current mana:"+Plugina.playerMap.get(player).getCurrentMana());
				Plugina.playerMap.get(player).loseMana(1, player);

				Location l=player.getLocation();
				//int y=getHighestPoint(l);
				//if(l.getBlockY()>y+4){
					//player.setFlying(false);
					//player.setAllowFlight(false);
				//}
				//else
				//{
					
					player.setAllowFlight(true);
					player.setFlying(true);
				//}
				
				//do lots more calculations here for the hero falling to the block below it and hovering
				//Vector v=player.getVelocity();
				//System.out.println("vector v(y component) is:"+v.getY());
				//if(v.getY()==0&&Plugina.playerMap.get(player).infallingstate==false){
					//System.out.println("v.setY(.1)");
					//v.setY(.1);
					//Plugina.playerMap.get(player).infallingstate=true;
				//}
			}
			else{  
				isdone=true;
				Plugina.playerMap.get(player).isBlockingArray[3]=false;//set the true value of isblocking 
				player.setFlying(false);
				player.setAllowFlight(false);
				return;
			}
				
		}
		else{
			isdone=true;
			Plugina.playerMap.get(player).isBlockingArray[3]=false;//set the true value of isblocking 
			player.setFlying(false);
			player.setAllowFlight(false);
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
	private int getHighestPoint(Location location){
		Location loc=location.clone();
		for (int y=loc.getBlockY();y>1;y--){
			loc.setY(y);
			if(loc.getBlock().getTypeId()!=0 ){
				System.out.println("y="+y);
				return y;
			}
		}
		System.out.println("returning 1, default, probably shouldn't ever happen unless you fight at bedrock");
		return 1;
	}
	private boolean isdone;
	private Player player;
}
