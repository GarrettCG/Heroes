package com.github.odin8472.Plugina.listeners;



//import org.bukkit.Bukkit;


import org.bukkit.Location;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Hero;


/*
 * This is a sample event listener
 */
public class VampireListener implements Listener {
    private final Plugina plugin;
    public VampireListener(Plugina plugin) {
        // Register the listener
    	this.plugin = plugin;
        //plugin.getServer().getPluginManager().registerEvents(this, plugin);
        //HandlerList.unregisterAll(this);
    }
    public void registerPlayerMoveFunction(Player player){//should make a version that takes a player or a string
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public void unregister(Player player){//should make this a boolean
    	int counter=0;
    	for(Hero h:Plugina.playerMap.values()){
    		if(h.getHeroName().equals("Vampire")){
    			counter++;
    		}
    	}
    	if(counter>1){
    		System.out.println("more than one of these classes out there so i'm not gonna remove it yet");
    		return;
    	}
    	HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
    	Location startpoint=event.getFrom().clone();

   		if(Plugina.playerMap.get(event.getPlayer()).forceflight==true){
   			event.getPlayer().setFlying(true);
   		}
   		if (Plugina.playerMap.get(event.getPlayer()).getHeroName().equals("Vampire")){
   			Hero vampire=Plugina.playerMap.get(event.getPlayer());
   			//System.out.println("2");
   			if(Plugina.playerMap.get(event.getPlayer()).isBlockingArray[3]==true){
   				Location v=event.getFrom().subtract(event.getTo());
   				//v.setY(v.getY());
  				
   				if(!vampire.isLocationAware){//not location aware
   					System.out.println("bout to set ground, dunno why location is zero, startpoint:"+startpoint);
   					vampire.ground=getHighestPoint(startpoint)+1;
   					System.out.println("ground="+vampire.ground);
   					vampire.xcoord=event.getFrom().getX();
   					vampire.zcoord=event.getFrom().getZ();
   					vampire.isLocationAware=true;//anytime the blockingarray isn't true i need to set it to false
   				}else if((int)vampire.xcoord!=(int)startpoint.getX()||(int)vampire.zcoord!=(int)startpoint.getZ()){//it's aware but the coordinates have changed
   					System.out.println("coords needs to be updated");
   					vampire.ground=getHighestPoint(startpoint)+1;
   					vampire.xcoord=event.getFrom().getX();
   					vampire.zcoord=event.getFrom().getZ();
   				}
   				//now we just need to move to ground+4
   				double diff=startpoint.getY()-(vampire.ground+4);
   				double delta=diff/4;
   				if(diff/4>1)
   					delta=1;
   				if(diff/4<-1)
   					delta=-1;
   				System.out.println("ground, right before the fucked up number is:"+vampire.ground);
   				if(startpoint.getBlockY()>vampire.ground+4){
   					System.out.println("greater than ground which is:"+vampire.ground);
   					event.getPlayer().setVelocity(new Vector(-v.getX(),-delta,-v.getZ()));
   				}else if(startpoint.getBlockY()<vampire.ground+4){
   					System.out.println("less than ground which is:"+vampire.ground);
   					event.getPlayer().setVelocity(new Vector(-v.getX(),-delta,-v.getZ()));
   				}else{
   					event.getPlayer().setFallDistance(0);
   			//		System.out.println("equilibrium");
   					return;
   				}
   			}
   			else{
   				vampire.isLocationAware=false;
   			}
   		}
   	}
  
	private int getHighestPoint(Location location){
		Location loc=location.clone();
		for (int y=loc.getBlockY();y>1;y--){
			loc.setY(y);
			if(loc.getBlock().getTypeId()!=0 ){
				System.out.println("returning y="+y);
				return y;
			}
		}
		System.out.println("returning 1, default, probably shouldn't ever happen unless you fight at bedrock, location was"+location);
		return 1;
	}

}

 

