package com.github.odin8472.Plugina.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BetterPlayerInteractEvent extends PlayerInteractEvent{

	public BetterPlayerInteractEvent(Player who, Action action, ItemStack item,
			Block clickedBlock, BlockFace clickedFace,Entity target) {
		super(who, action, item, clickedBlock, clickedFace);
		entity=target;
	}
	public Entity getTarget() {
		return entity;
	}
	public void setTarget(Entity entity) {
		this.entity = entity;
	}
	private Entity entity=null;

}
