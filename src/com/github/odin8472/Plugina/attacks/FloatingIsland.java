package com.github.odin8472.Plugina.attacks;


import java.util.ArrayList;
import java.util.ListIterator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.StrikeDetails;


public class FloatingIsland implements SwordMove{//as it stands this class will cause memory leaks because it will never stop moving, i should add a condition to make it disappear after a set amount of iterations
	public FloatingIsland(StrikeDetails strike){
		info=strike;
		isdone=false;
		Byte blockData = 0x0;
		fblocks=new ArrayList<FallingBlock>();
		for(Vector v:Plugina.floatingGrid){
			pos=info.getEnd().clone();
			if(pos.add(v).getBlock().getType()!=Material.AIR){
				System.out.println("pos:"+pos);
				mat=pos.getBlock().getType();
				pos.getBlock().setType(Material.AIR);
				fblocks.add(info.getWorld().spawnFallingBlock(pos, mat, blockData));
			}
		}
		counter=0;
	}
	@Override
	public void execute() {//use iterator
		if (counter>200){
			isdone=true;
			return;			
		}
		ListIterator<FallingBlock> litr = fblocks.listIterator();
		while(litr.hasNext()) {
			FallingBlock element = litr.next();
			element.setVelocity(velocity);
		}
		counter++;
	}
	@Override
	public boolean isDone() {
		return isdone;
	}
	private int counter;
	private final Vector velocity=new Vector(0,.5f,0);
	private ArrayList<FallingBlock> fblocks;
	private Material mat;
	private Byte blockData = 0x0;
	private Location pos;
	private boolean isdone;
	private StrikeDetails info;
}
