package com.github.odin8472.Plugina.blocks;


import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Hero;


public class ManaRegeneration implements BlockingMove{
	public ManaRegeneration(Player p){
		player=p;
		isdone=false;
		
	}
	public void execute() {
		if(!Plugina.playerMap.containsKey(player)){
			isdone=true;
			return;
		}
		System.out.println("regeneration.execute");
		Hero h=Plugina.playerMap.get(player);
		if(h.getCurrentMana()>=h.getMana()){
			h.setCurrentMana(h.getMana());
			player.setExp(.99f);
			isdone=true;
			h.setAlreadyRecovering(false);
			return;
		}else{
			player.setExp((float)(h.getCurrentMana()/h.getMana()));
			h.setCurrentMana(h.getCurrentMana()+h.getManaregen());
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
}
