package com.github.odin8472.Plugina.utilities;

import org.bukkit.util.Vector;

public class Skellitrix {
	public Skellitrix(){
		Matrix = new double [][] {{0.,0.,0.,0.},{0.,0.,0.,0.},{0.,0.,0.,0.},{0.,0.,0.,0.}};
	}
	
	public void MatrixIdentity()
	{ 
		  MatrixReset();  
		  Matrix[0][0]=Matrix[1][1]=
		  Matrix[2][2]=Matrix[3][3]=1;
	}

	public void MatrixReset()
	{ 
		for(int i=0;i<4;i++)
	  { 
			for(int j=0;j<4;j++)
		  {
				//System.out.println("value of j:"+j);
				Matrix[i][j]=0;
		  }
	  }
	}
	
	void MatrixCopy(Skellitrix NewM)
	{
		Skellitrix temp=new Skellitrix();
	    int i,j;
	    for(i=0;i<4;i++)
	   {
	    	for(j=0;j<4;j++)
	     {
	    		temp.Matrix[i][j]=(Matrix[i][0]*NewM.Matrix[0][j])+(Matrix[i][1]*NewM.Matrix[1][j])+
	            (Matrix[i][2]*NewM.Matrix[2][j])+(Matrix[i][3]*NewM.Matrix[3][j]);
	     }
	   }
	   for(i=0;i<4;i++)
	  {
		   Matrix[i][0]=temp.Matrix[i][0];
		   Matrix[i][1]=temp.Matrix[i][1];
		   Matrix[i][2]=temp.Matrix[i][2];
		   Matrix[i][3]=temp.Matrix[i][3];
	  }
	}
	public Vector MultiplyByVector(Vector coordinates){
		//multiplies Matrix (4x4) by a 4x1 matrix
		Vector newCoordinates=new Vector();
		newCoordinates.setX(Matrix[0][0]*coordinates.getX()+Matrix[0][1]*coordinates.getY()+Matrix[0][2]*coordinates.getZ());
		newCoordinates.setY(Matrix[1][0]*coordinates.getX()+Matrix[1][1]*coordinates.getY()+Matrix[1][2]*coordinates.getZ());
		newCoordinates.setZ(Matrix[2][0]*coordinates.getX()+Matrix[2][1]*coordinates.getY()+Matrix[2][2]*coordinates.getZ());
		return newCoordinates;
	}
	public Skellitrix MatrixMult(Skellitrix M1,Skellitrix M2)
	{ 
		Skellitrix temp=new Skellitrix();
	    int i,j;
	    for (i=0;i<4;i++)
	   {
	    	for(j=0;j<4;j++)
	      {
	    		temp.Matrix[i][j]= (M2.Matrix[i][0]*M1.Matrix[0][j])+(M2.Matrix[i][1]*M1.Matrix[1][j]) + (M2.Matrix[i][2]*M1.Matrix[2][j])+(M2.Matrix[i][3]*M1.Matrix[3][j]);
	      }
	   }
	  for(i=0;i<4;i++)
	   {
		  M1.Matrix[i][0]=temp.Matrix[i][0];
	      M1.Matrix[i][1]=temp.Matrix[i][1];
	      M1.Matrix[i][2]=temp.Matrix[i][2];
	      M1.Matrix[i][3]=temp.Matrix[i][3];
	   }
	  return M1;
	}
	
	public double[][] Matrix;  

}
