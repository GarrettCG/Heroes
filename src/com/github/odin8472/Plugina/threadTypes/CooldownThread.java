package com.github.odin8472.Plugina.threadTypes;

import java.util.ListIterator;



import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.Plugina;

public class CooldownThread implements Runnable {
		private int id;
		private Player play;
		private float maxCD;
		private float currentCD;
		private int itemID;//the id of the index in that swordmap
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	   public CooldownThread(Player p,float mCD,int id){//should only get called on items that actually exist in your inventory
	      play=p;
	      maxCD=mCD*20;//convert them to game ticks
	      currentCD=0;//convert them to game ticks
	      itemID=id;
	   }
	   public void run() {
		   currentCD+=1f;
		   if(currentCD>=maxCD)
			   currentCD=maxCD;
		   //next set the durability on all the swords in swordmap(itemID)
		   ListIterator<Integer> litr = Plugina.playerMap.get(play).swordMap.get(itemID).listIterator(); 

				
		   if(!Plugina.playerMap.get(play).swordMap.get(itemID).isEmpty()){//it should never be empty, i should have never entered this check
				while(litr.hasNext()) {
				   Integer element = litr.next(); //for (int k :(Plugina.playerMap.get(play).swordMap.get(itemID))){
				   if(play.getInventory().getItem(element)==null){
					   System.out.println("the item is null");
					   Plugina.playerMap.get(play).updateRemove(play);
				   }else{
				   play.getInventory().getItem(element).setDurability((short) ( play.getInventory().getItem(element).getType().getMaxDurability()-(play.getInventory().getItem(element).getType().getMaxDurability()*(currentCD/maxCD))));//Plugina.durabilityArray[itemID]
				   }
			   }
		   }//else{
			   //System.out.println("cancelling because there are no more items of that type to keep track of left in inventory");
			   //cancel();
			  // return;
		  // }
		   if(currentCD==maxCD){
			   Plugina.playerMap.get(play).swordsOffCD[itemID]=true;
			   cancel();
			   System.out.println("done with cooldown");
			   return;
		   }
	   }
	   private void cancel() {
		    Bukkit.getScheduler().cancelTask(id);
		}
}
