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

	public class EflowXY extends BaseObject {

		  public double a1, b1, c1, d1, r, r1, r2, Ex1, Ex2, Ez1, Ez2;
		 
		  public EflowXY(double a1, double b1, double c1, double d1)
		  {
		    this.a1 = a1;
		    this.b1 = b1;
		    this.c1 = c1;
		    this.d1 = d1;
		  }

		    public Vec3 Efield(Vec3 x, Vec3 E)
		  {
		    r1 = Math.pow((x.z)*(x.z)+(x.x)*(x.x),0.5);
		    r2 = Math.pow((x.z-b1)*(x.z-b1)+(x.x-a1)*(x.x-a1),0.5);
		    if ( r1 >= c1 ) {
		        Ex1 = c1*c1*c1*x.x/(r1*r1*r1);
		        Ez1 = c1*c1*c1*x.z/(r1*r1*r1);
		    }
		    else {
		        Ex1 = x.x;
		        Ez1 = x.z;
		    }  
		     if ( r2 >= d1 ) {
		        Ex2 = -d1*d1*d1*(x.x-a1)/(r2*r2*r2);
		        Ez2 = -d1*d1*d1*(x.z-b1)/(r2*r2*r2);
		    }
		    else {
		        Ex2 = -x.x+a1;
		        Ez2 = -x.z+b1;
		    }  
		    //r=Math.pow((x.z/40)*(x.z/40)+(x.x/40)*(x.x/40),0.5);
		    //r1 = Math.log(Math.sin(x.x/30.));
		    //r1 = Math.pow(r1,3.);
		    //r2 = Math.log(Math.cos(x.z/30.));
		    //r2 = Math.pow(r2,3.);
		    //Vec3 fieldvector = new Vec3(300.*Math.sin(x.z*x.x/900.),0.,300.*Math.cos(x.x*x.z/900.));
		    //Vec3 fieldvector = new Vec3(300.*r1*Math.tan(x.z/30.),0.,300.*r2*Math.tan(x.x/30.));
		    Vec3 fieldvector = new Vec3(Ex1+Ex2,0.,Ez1+Ez2);
		    //Vec3 fieldvector = new Vec3(300.*x.x,0.,300.*x.z);
		    //Vec3 fieldvector = new Vec3(300.*(-Math.sin(x.z/33.)+Math.sin(x.x/33.)),0.,300.*(Math.sin(x.z/33.)+Math.sin(x.x/33.)));
		    //Vec3 fieldvector = new Vec3(300.*Math.cos(r),0.,300.*Math.sin(r));
		    //Vec3 fieldvector = new Vec3(300.*Math.cos(x.z/40)*x.z,0.,300.*Math.cos(x.x/40)*x.x);
		    // Vec3 fieldvector = new Vec3(a1*x.x+b1*x.z,0.,c1*x.x+d1*x.z);
		    // Vec3 fieldvector = new Vec3(300.*Math.sin(x.x/50)*Math.sin(x.x/50),0.,300.*Math.cos(x.z/50)*Math.cos(x.z/50));
		    //Vec3 fieldvector = new Vec3(300.*Math.pow(x.z,1),0.,30.*Math.pow(x.x,2));
		     return E.Set(fieldvector);
		  }
		 
		  public Vec3 Bfield(Vec3 x, Vec3 B)
		  {
		      return B.SetZero();
		  }


		  
		       public String toString() 
		    {
			    return "EflowXY: a1=  " + this.a1 + " b1= "+ this.b1 + "  c1= " + this.c1 + "  d1= " + this.d1;
		    }


}
