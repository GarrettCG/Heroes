package com.github.odin8472.Plugina.attacks;

import com.github.odin8472.Plugina.utilities.StrikeDetails;


public class SimpleLightningStrike implements SwordMove{
	public SimpleLightningStrike(StrikeDetails strike){
		info=strike;
		isdone=false;
	}
	@Override
	public void execute() {
		info.getWorld().strikeLightning(info.getEnd());
		isdone=true;
	}



	@Override
	public boolean isDone() {
		return isdone;
	}
	private boolean isdone;
	private StrikeDetails info;
}