package com.github.odin8472.Plugina.attacks;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import com.github.odin8472.Plugina.Plugina;
import com.github.odin8472.Plugina.utilities.ExploData;
import com.github.odin8472.Plugina.utilities.StrikeDetails;


public class DonkeyPunch implements SwordMove{//as it stands this class will cause memory leaks because it will never stop moving, i should add a condition to make it disappear after a set amount of iterations
	//arrow.getWorld().playEffect(arrow.getLocation(),Effect.BOW_FIRE, 25);
	public DonkeyPunch(StrikeDetails strike){
		info=strike;
		isdone=false;
		System.out.println("end of DonkeyPunch constructor");
	}
	@Override
	public void execute() {
		Location l=(info.getTarget().getLocation().clone().subtract(info.getPlayer().getLocation()).multiply(2));
		info.getTarget().setVelocity(l.toVector());
		Plugina.explosionList.add(new ExploData(info.getTarget().getLocation(),info.getPlayer(),false,false,true));
		info.getWorld().createExplosion(info.getTarget().getLocation(), 1f);
		isdone=true;
		return;
	}
	@Override
	public boolean isDone() {
		return isdone;
	}
	private Vector velocity;

	private boolean isdone;
	private StrikeDetails info;
}
