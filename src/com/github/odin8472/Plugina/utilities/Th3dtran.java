package com.github.odin8472.Plugina.utilities;


import org.bukkit.util.Vector;


public class Th3dtran {
	public Th3dtran(){
		Init();
	}
	public void Init(){
		rmatrix=new Skellitrix();
		Rmat=new Skellitrix();
		look=new Vector(0,0,0);
	}
	//void Translate(float,float,float);
	public Vector Rotate(double yaw,double pitch,Vector attacktarget){
		yaw=-1*yaw*Math.PI/180;
		pitch=-1*pitch*Math.PI/180;
		rmatrix.MatrixIdentity();
		Rmat.MatrixIdentity();
		Rmat.Matrix[0][0]=Math.cos(yaw);Rmat.Matrix[0][2]=(Math.sin(yaw));
		Rmat.Matrix[2][0]=-(Math.sin(yaw));Rmat.Matrix[2][2]=Math.cos(yaw);
		Skellitrix rotationMatrix=Rmat;
		Vector intermediateVector=rotationMatrix.MultiplyByVector(attacktarget);
		
		look.setX(Math.sin(yaw));
		look.setZ(Math.cos(yaw));
		Vector g=new Vector((-1*look.getZ()),0,look.getX());//maybe i should rotate around the axis perpendicular to looking vector//change back to intermediateVector if i'm wrong
		g.normalize();
		rmatrix.Matrix[0][0]=(Math.cos(pitch)+g.getX()*g.getX()*(1-Math.cos(pitch)));rmatrix.Matrix[0][1]=(g.getX()*g.getY()*(1-Math.cos(pitch)))-g.getZ()*Math.sin(pitch);rmatrix.Matrix[0][2]=g.getX()*g.getZ()*(1-Math.cos(pitch))+g.getY()*Math.sin(pitch);
		rmatrix.Matrix[1][0]=g.getY()*g.getX()*(1-Math.cos(pitch))+g.getZ()*Math.sin(pitch);rmatrix.Matrix[1][1]=Math.cos(pitch)+(g.getY()*g.getY())*(1-Math.cos(pitch));rmatrix.Matrix[1][2]=g.getY()*g.getZ()*(1-Math.cos(pitch))-g.getX()*Math.sin(pitch);
		rmatrix.Matrix[2][0]=g.getZ()*g.getX()*(1-Math.cos(pitch))-g.getY()*Math.sin(pitch);rmatrix.Matrix[2][1]=g.getZ()*g.getY()*(1-Math.cos(pitch))+g.getX()*Math.sin(pitch);rmatrix.Matrix[2][2]=Math.cos(pitch)+g.getZ()*g.getZ()*(1-Math.cos(pitch));
		Vector finresult=rmatrix.MultiplyByVector(intermediateVector);//p is attacktarget...i had to make it confusing
		return finresult;
	}
	public Skellitrix matrix,Rmat,rmatrix;
	private Vector look;
}
