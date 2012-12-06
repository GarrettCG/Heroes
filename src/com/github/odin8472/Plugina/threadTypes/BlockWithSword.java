package com.github.odin8472.Plugina.threadTypes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.odin8472.Plugina.blocks.BlockingMove;

public class BlockWithSword implements Runnable {
		private int id;
		//private Player player;
		private BlockingMove blockmove;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	   public BlockWithSword(BlockingMove bmove) {
	       blockmove=bmove;
	      // player=p;

	   }
	   public void run() {
		   if(blockmove.isDone()){
			   cancel();
			   return;
		   }
		   blockmove.execute();
	   }

	   private void cancel() {
		    Bukkit.getScheduler().cancelTask(id);
		}
}

