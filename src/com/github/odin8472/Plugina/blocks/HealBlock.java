package com.github.odin8472.Plugina.blocks;


import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.heroes.Hero;



public class HealBlock implements BlockingMove{
	public HealBlock(Player p){
		player=p;
		isdone=false;
		
	}
	public void execute() {
		if (player.isBlocking()){
			if(!(Plugina.playerMap.get(player).getCurrentMana()<1)){
				System.out.println("About to lose one mana, current mana:"+Plugina.playerMap.get(player).getCurrentMana());
				Plugina.playerMap.get(player).loseMana(1, player);
				Hero h=Plugina.playerMap.get(player);
				h.gainHealth(1+h.getPower(),player);
				System.out.println("current health is:"+Plugina.playerMap.get(player).getCurrentHealth());
			}
		}
		else{
			System.out.println("It's not blocking");
			isdone=true;
			Plugina.playerMap.get(player).isBlockingArray[1]=false;//set the true value of isblocking 
			return;
		}
	}

	@Override
	public boolean isDone() {
		return isdone;
	}
	
	private boolean isdone;
	private Player player;
}
