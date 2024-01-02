package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

/** 
 * TwoPlanes:  This BaseObject calculates the non-relativistic magnetic fields induced by
 * a monopole situated near two thin conducting planes intersecting at right angle.
 * Given the potential we compute the fields using numerical differentiation  */
public class TwoPlanes extends BaseObject {
  /** monople at (k,0,h) */
  public double k;
  public double h;
  /** offset is an overall time offset before the image charges start moving */
  public double offset;
  /** q0 is the magnetic charge */
  public double q0;
  /** v1 is the receding velocity associated with x=0 plane*/
  public double v1;
  /** v2 is the receding velocity associated with z=0 plane */
  public double v2;
  /** t is the time */
  public double t;
  
  /** Constructs an instance of the object using the given parameters.
   *   @param k the x component of the monopole
   *   @param h the y component of the monopole
   *   @param offset the frame offset before image charges begin moving
   *   @param q0 the magnetic charge
   *   @param v1 the receding velocity associated with x=0 plane
   *   @param v2 the receding velocity associated with z=0 plane */
 
  public TwoPlanes(double k, double h, double q0, double v1, double v2, double t, double offset){
	  this.offset = offset;  
	  this.q0 = q0;
	  this.k = k;
	  this.h = h;
	  this.v1 = v1;
	  this.v2 = v2;
	  this.t = t;
  }
  /** the magnetic field of a non-relativistic moving magnetic monopole in the upper half plane
   * @param x the position of the observer
   * @param B the magnetic field at the position of the observer
   * @return B the magnetic field 
   */
  public Vec3 Bfield(Vec3 x, Vec3 B) {
	  /* calculates B by numberically taking gradient of the potential*/
	  Vec3 rad = new Vec3(0.,0.,0.);
	  Vec3 radplusx = new Vec3(0.,0.,0.);
	  Vec3 radplusy = new Vec3(0.,0.,0.);
	  Vec3 radplusz = new Vec3(0.,0.,0.);
	  double potentialplus=0., potential = 0.;
	  double epsilon = 0.01;
	  double x1=0., x2=0., z1=0., z2=0., r1=0., r2=0.;
	  int printout = 1;
	  rad.Set(x); 
	  radplusx.Set(x);
	  radplusy.Set(x);
	  radplusz.Set(x);
	  radplusx.x = radplusx.x+epsilon;
	  radplusy.y = radplusy.y+epsilon;
	  radplusz.z = radplusz.z+epsilon;
    
	  // calculate vector field by numerical differentiation of the potential
	  if (x.x >= 0.) {
		  if ( x.z >= 0.){
			  B.x = -(Potential1(radplusx, potentialplus)-Potential1(rad, potential))/epsilon;
			  B.y = -(Potential1(radplusy, potentialplus)-Potential1(rad, potential))/epsilon;
			  B.z = -(Potential1(radplusz, potentialplus)-Potential1(rad, potential))/epsilon;
			  x1 = x.x+k+v1*t;
			  z1 = x.z-h;
			  x2 = x.x-k;
			  z2 = x.z+h+v2*t;
			  r1 = Math.pow(x1*x1+x.y*x.y+z1*z1, 1.5);
			  r2 = Math.pow(x2*x2+x.y*x.y+z2*z2, 1.5);
			  B.x = B.x + Constants.Bfactor*q0*(x1/r1+x2/r2);
			  B.y = B.y + Constants.Bfactor*q0*(x.y/r1+x.y/r2);
			  B.z = B.z + Constants.Bfactor*q0*(z1/r1+z2/r2);
		  } else {
			  B.x = -(Potential4(radplusx, potentialplus)-Potential4(rad, potential))/epsilon;
			  B.y = -(Potential4(radplusy, potentialplus)-Potential4(rad, potential))/epsilon;
			  B.z = -(Potential4(radplusz, potentialplus)-Potential4(rad, potential))/epsilon;
			  x1 = x.x+k+v1*t;
			  z1 = x.z-h;
			  x2 = x.x-k;
			  z2 = x.z-h-v2*t;
			  r1 = Math.pow(x1*x1+x.y*x.y+z1*z1, 1.5);
			  r2 = Math.pow(x2*x2+x.y*x.y+z2*z2, 1.5);
			  B.x = B.x + Constants.Bfactor*q0*(x1/r1-x2/r2);
			  B.y = B.y + Constants.Bfactor*q0*(x.y/r1-x.y/r2);
			  B.z = B.z + Constants.Bfactor*q0*(z1/r1-z2/r2);
		  }
	  } else if (x.z >= 0.){
		  B.x = -(Potential2(radplusx, potentialplus)-Potential2(rad, potential))/epsilon;
		  B.y = -(Potential2(radplusy, potentialplus)-Potential2(rad, potential))/epsilon;
		  B.z = -(Potential2(radplusz, potentialplus)-Potential2(rad, potential))/epsilon;
		  x1 = x.x-k-v1*t;
		  z1 = x.z-h;
		  x2 = x.x-k;
		  z2 = x.z+h+v2*t;
		  r1 = Math.pow(x1*x1+x.y*x.y+z1*z1, 1.5);
		  r2 = Math.pow(x2*x2+x.y*x.y+z2*z2, 1.5);
		  B.x = B.x + Constants.Bfactor*q0*(-x1/r1+x2/r2);
		  B.y = B.y + Constants.Bfactor*q0*(-x.y/r1+x.y/r2);
		  B.z = B.z + Constants.Bfactor*q0*(-z1/r1+z2/r2);
	  } else {
		  B.x = -(Potential3(radplusx, potentialplus)-Potential3(rad, potential))/epsilon;
		  B.y = -(Potential3(radplusy, potentialplus)-Potential3(rad, potential))/epsilon;
		  B.z = -(Potential3(radplusz, potentialplus)-Potential3(rad, potential))/epsilon;
		  x1 = x.x-k-v1*t;
		  z1 = x.z-h;
		  x2 = x.x-k;
		  z2 = x.z-h-v2*t;
		  r1 = Math.pow(x1*x1+x.y*x.y+z1*z1, 1.5);
		  r2 = Math.pow(x2*x2+x.y*x.y+z2*z2, 1.5);
		  B.x = B.x + Constants.Bfactor*q0*(-x1/r1-x2/r2);
		  B.y = B.y + Constants.Bfactor*q0*(-x.y/r1-x.y/r2);
		  B.z = B.z + Constants.Bfactor*q0*(-z1/r1-z2/r2);
	  }
    
    
	  if( printout == 0){
		  System.out.println("Constants.Bfactor" + Constants.Bfactor);
		  System.out.println("radius rx" + rad.x + "  ry " + rad.y + "   rz " + rad.z);
		  System.out.println("Bx=" + B.x + "  By=" + B.y + "  Bz=" + B.z);
	  }
    
    return B;
  }
  
  
  /** the electric field of a moving monopole, which currently we set to zero
   * @param x the position of the observer
   * @param E the electric field at the observer's position if the monopope is at p (calculated) 
   * @return zero  */  
  public Vec3 Efield(Vec3 x, Vec3 E) {
	  Vec3 vzero = new Vec3(0.,0.,0.);
	  return Bfield(x, E).Cross(vzero);
	}


  /** the potential in the first quadrant x>0, z>0 */
  public double Potential1(Vec3 x, double potential) {
     double x1=0., x2=0., z1=0., z2=0., r1=0., r2=0., fact=0., eps=0.;
     x1 = x.x+k+v1*t;
     z1 = x.z+h;
     x2 = x.x+k;
     z2 = x.z+h+v2*t;
     r1 = Math.sqrt(x1*x1+x.y*x.y+z1*z1);
     r2 = Math.sqrt(x2*x2+x.y*x.y+z2*z2);
     fact = v2*x1+v1*z1;
     eps = x.y*x.y/fact;
     potential = Constants.Bfactor*q0*(v1*(z1+eps*v1)/r1+v2*(x2+eps*v2)/r2)/(fact+eps*(v1*v1+v2*v2));
     return potential;
  }

  /** the potential in the second quadrant x<0, z>0 */
  public double Potential2(Vec3 x, double potential) {
	  x.Set(-x.x, x.y, x.z);
	  potential = -Potential1(x, potential);
	  return potential;
  }

  /** the potential in the third quadrant x<0, z<0 */
  public double Potential3(Vec3 x, double potential) {
	  x.Set(-x.x, x.y, -x.z);
	  potential = Potential1(x, potential);
	  return potential;
  }

  /** the potential in the fourth quadrant x>0, z<0 */
  public double Potential4(Vec3 x, double potential) {
	  x.Set(x.x, x.y, -x.z);
	  potential = -Potential1(x, potential);
	  return potential;
  }
  
  
  /** writes properties of the point charge to a string */  

  public String toString() {
	  return "Field Upper Half Plane";
    }

}