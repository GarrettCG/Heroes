package com.github.odin8472.Plugina.attacks;

import org.bukkit.Effect;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.odin8472.Plugina.Plugina;

public class TransformBat implements SwordMove{//if you manipulate a player or location or world you need to check to see if the player is still there
	public TransformBat(Player play,Plugina plug){
		plugin=plug;
		player=play;
		isdone=false;
		counter=0;
	}
	@Override
	public void execute() {
		if(!Plugina.playerMap.containsKey(player)){
			isdone=true;
			return;
		}
		//set him to fly
		//take away his inventory
		if(counter==1){
			System.out.println("allowing flight");
			Plugina.playerMap.get(player).forceflight=true;
			player.setAllowFlight(true);
			player.setFlying(true);
			Plugina.playerMap.get(player).setUnablePickup(true);
			PotionEffect potion=new PotionEffect(PotionEffectType.NIGHT_VISION,60*20,1);
			PotionEffect potion2=new PotionEffect(PotionEffectType.INVISIBILITY,60*20,1);
			player.addPotionEffect(potion);
			player.addPotionEffect(potion2);
			Plugina.playerMap.get(player).tempinventory=player.getInventory().getContents();
			//System.out.println("INVENTORY:"+Plugina.playerMap.get(player).tempinventory.getContents());
			player.getInventory().clear();
			bat=player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
			player.setPassenger(bat);
			player.playEffect(player.getEyeLocation(), Effect.SMOKE, 5);
			//BatMove batm = new BatMove(player);
			//BlockWithSword batmTask = new BlockWithSword(batm);
			//batmTask.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, batmTask,0,20));

		}else{
			System.out.println("banning flight");
			
			for(ItemStack i :Plugina.playerMap.get(player).tempinventory){
				if(i!=null){
					player.getInventory().addItem(i);
				}
			}
			Plugina.playerMap.get(player).setUnablePickup(false);
			Plugina.playerMap.get(player).forceflight=false;
			player.setFlying(false);
			player.setAllowFlight(false);
			player.playEffect(player.getEyeLocation(), Effect.SMOKE, 5);
			bat.remove();
		}
	}

	@Override
	public boolean isDone() {//i use this cryptic method to make the method call twice and only twice
		counter++;
		if(counter>2){
			isdone=true;
			return true;
		}
		return false;
	}
	private Entity bat;
	private Plugina plugin;
	private int counter;
	private boolean isdone;
	private Player player;
}
