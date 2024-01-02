package simulations.objects.antennaMath;

import java.math.*;
//import EMSimulations.Field.*;  

public class Ez {
	double tw;
	double rhok;
	double zk;
	double ak;
	
	public Ez(double tw, double rhok, double zk, double ak)
	{
		this.tw=tw;
		this.rhok=rhok;
		this.zk=zk;
		this.ak=ak;
	}
	
	public double getEz()
	{
		return Ezee(tw,rhok,zk,ak);
	}
 
     public double Ezee(double tw, double rhok, double zk, double ak) 
     {
     	double dk = Math.sqrt(rhok*rhok + zk*zk);
     	double D1 = Math.sqrt(rhok*rhok + (zk+ak)*(zk+ak) );
     	double D2 = Math.sqrt(rhok*rhok + (zk-ak)*(zk-ak) );
     	double Q1 = Math.cos(D1-dk)/D1 + Math.cos(D2-dk)/D2 - 2.0*Math.cos(ak)/dk;
     	double Q2 = Math.sin(D1-dk)/D1 + Math.sin(D2-dk)/D2;
     	double tr = tw - dk;
     	return  Q1*Math.cos(tr) + Q2*Math.sin(tr);
     }

}  