package com.github.odin8472.Plugina.attacks;

import com.github.odin8472.Plugina.utilities.StrikeDetails;


public class KnockBack implements SwordMove{
	public KnockBack(StrikeDetails strike,int p){
		info=strike;
		power=p;
		isdone=false;
	}
	@Override
	public void execute() {
			//Plugina.playerMap.get(info.getPlayer()).setKB(true);
			info.getPlayer().setVelocity(info.getPlayer().getLocation().getDirection().multiply(-power));
			isdone=true;
			return;
		}

	@Override
	public boolean isDone() {
		return isdone;
	}
	private int power;
	private boolean isdone;
	private StrikeDetails info;

}
