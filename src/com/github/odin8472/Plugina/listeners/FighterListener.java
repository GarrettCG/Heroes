package com.github.odin8472.Plugina.listeners;



import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Fighter;
import com.github.odin8472.Plugina.heroes.Hero;


public class FighterListener implements Listener{//fighter doesn't need this class, vampire will though, i'm testing it on fighter though since he's all i got right now
	private final Plugina plugin;
	public FighterListener(Plugina plugin) {
	    // Register the listener
		this.plugin = plugin;
	    //plugin.getServer().getPluginManager().registerEvents(this, plugin);
	    //HandlerList.unregisterAll(this);
	}

	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
		Location startpoint=event.getFrom().clone();
		if (Plugina.playerMap.get(event.getPlayer()).getHeroName().equals("Fighter")){
			Hero fighter=(Fighter)Plugina.playerMap.get(event.getPlayer());
			//System.out.println("2");
			if(Plugina.playerMap.get(event.getPlayer()).isBlockingArray[3]==true){
				Location v=event.getFrom().subtract(event.getTo());
				//v.setY(v.getY());
				
				if(!fighter.isLocationAware){//not location aware
					fighter.xcoord=event.getFrom().getX();
					fighter.zcoord=event.getFrom().getZ();
					fighter.isLocationAware=true;//anytime the blockingarray isn't true i need to set it to false
				}
			}
		}
	}

}

