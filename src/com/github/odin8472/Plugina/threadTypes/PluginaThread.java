package com.github.odin8472.Plugina.threadTypes;

import org.bukkit.Bukkit;

import com.github.odin8472.Plugina.attacks.SwordMove;

public class PluginaThread implements Runnable {
		private int id;
		private SwordMove swordmove;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	   public PluginaThread(SwordMove smove){
	      swordmove=smove;
	   }
	   public void run() {
		   if (swordmove.isDone()){
			   cancel();
			   return;
		   }
		   else
		   {
			   swordmove.execute();
		   }
	   }
	   private void cancel() {
		    Bukkit.getScheduler().cancelTask(id);
		}
}

