package simulations.objects;

import java.io.*;
import java.util.*;
import core.dflic.DFLIC;
import core.field.EMVec2Field;
import core.image.AccumImage;
import core.image.RGBImage;
import core.io.ImageIO;
import core.io.OutputWindow;
import core.math.Vec2;
import core.math.Vec2Transform;
import core.math.Vec3;
import simulations.experiments.radiation.OscillatingDipoleExperiment;
import simulations.objects.*;

public class LineCurrent extends BaseObject {

	  static protected double Bfactor = 1.0;
	  public double i;
	  public double radius;
	  public Vec3 p;
	 

	  public LineCurrent(double i, Vec3 p, double radius)
	  {
	    this.i = i;
	    this.p = p;
	    this.radius = radius;
	  }

	  public LineCurrent(double i, Vec3 p)
	  {
	      this.i = i;
	      this.p = p;
	      this.radius = 5.0;
	  }
	  
	  public Vec3 Efield(Vec3 x, Vec3 E)
	  {
	      return E.SetZero();
	  }

	  public Vec3 Bfield(Vec3 x, Vec3 B)
	  {
	    Vec3 radiusvector = new Vec3(0.,0.,0.);
	    radiusvector.Set(x).Sub(p);
	    if (!radiusvector.isZero() && Math.abs(this.i)!=0.)
	    {
	        Vec3 fieldvector = new Vec3(-Bfactor*i*radiusvector.z/radiusvector.len2(),0.,Bfactor*i*radiusvector.x/radiusvector.len2());
	        return B.Set(fieldvector);
	    }
	    else
	      return B.SetZero();
	  }
	  
	       public String toString() 
	    {
		    return "Line Current:  current " + this.i + " radius "+ this.radius + " position (" + this.p.x + ", "
	            + this.p.y + ", " + this.p.z + ")";
	    }


}
