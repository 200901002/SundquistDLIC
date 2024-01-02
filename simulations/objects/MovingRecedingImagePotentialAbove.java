package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

/** 
 * MovingRecedingImagePotentialAbove.  This BaseObject calculates the non-relativistic magnetic fields above a thin
 * conducting plane due to a moving magnetic monopole a distance H above the plane, following the formula
 * for the potential there given in equation (65) of Saslow Am J Phys 60 (8) 693 (1992) or equation (8)
 * of Liu and Belcher 2007.  Given the potential
 * we compute the fields using numerical differentiation  */
public class MovingRecedingImagePotentialAbove extends BaseObject {
  /** H = height of the monople above z = 0 */
  public double H;
  /** the time  */
  public double t;
  /** offset is an overall time offset before the image charges start moving */
  public double offset;
  /** zplane is the location in z of the conducting thin sheet */
  public double zplane;
  /** q0 is the magnetic charge */
  public double q0;
  /** v0 is the vertical speed of the receeding monopoles once they start receding*/
  public double v0;
  /** v is the horizontal speed of the monopole */
  public double v;
  
  /** Constructs an instance of the object using the given parameters.
   *   @param H the height of the monopole above z = 0
   *   @param offset the frame offset before image charges begin moving
   *   @param q0 the magnetic charge
   *   @param v the horizontal speed of the monopole
   *   @param v0 the speed of the receeding image 
   *   @param zplane the height of the conducting plane above z = 0 */
 
  public MovingRecedingImagePotentialAbove(double H, double q0, double v0, double v,  double zplane, double offset){
	  this.offset = offset;  
	    this.q0 = q0;
	    this.H = H;
	    this.v0 = v0;
	    this.v = v;
	    this.zplane = zplane;
  }
  /** the magnetic field of a non-relativistic moving magnetic monopole in the upper half plane
   * @param x the position of the observer
   * @param B the magnetic field at the position of the observer
   * @return B the magnetic field 
   */
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    /* calculates B by numberically taking gradient of the potential given by Saslow eq 65 */
	Vec3 rad = new Vec3(0.,0.,0.);
	Vec3 radplusx = new Vec3(0.,0.,0.);
	Vec3 radplusy = new Vec3(0.,0.,0.);
	Vec3 radplusz = new Vec3(0.,0.,0.);
	Vec3 Bzero = new Vec3(0.,0.,0.);
	double potentialplus=0., potential = 0.;
	double episilon = 0.01;
	int printout = 0;
    rad.Set(x); 
    radplusx.Set(x);
    radplusy.Set(x);
    radplusz.Set(x);
    radplusx.x = radplusx.x+episilon;
    radplusy.x = radplusy.x+episilon;
    radplusz.z = radplusz.z+episilon;
    // calculate vector field by numerical differentiation of the potential
    B.x = -(Potential(radplusx, potentialplus)-Potential(rad, potential))/episilon;
    B.y = -(Potential(radplusy, potentialplus)-Potential(rad, potential))/episilon;;
    B.z = -(Potential(radplusz, potentialplus)-Potential(rad, potential))/episilon;
  
	    if( printout == 1){
	    System.out.println("Bfactor" + Constants.Bfactor);
	    System.out.println("radius  rx" + rad.x + "  ry " + rad.y + "   rz " + rad.z);
	    }
	   if (x.z >= 0.) return B;
	   else return Bzero;
}
  
  
  /** the electric field of a moving monopole, which currently we set to zero
   * @param x the position of the observer
   * @param E the electric field at the observer's position if the monopope is at p (calculated) 
   * @return zero  */  
  public Vec3 Efield(Vec3 x, Vec3 E) {
	  Vec3 vzero = new Vec3(0.,0.,0.);
     return Bfield(x, E).Cross(vzero);
	}
  
  /** the potential of a moving monopole in the upper halfplane
   * @param x the position of the observer
   * @param potential the potential at the observer's position if the monopope is at (0,0,H) moving at
   * velocity (v,0,0)  */  
  public double Potential(Vec3 x, double potential) {
     double fact1=0., fact2=0., fact3=0., fact4=0., fact5=0.;
     fact1 = (x.z+H)*(x.z+H)*v*v - 2.*v*v0*(x.z+H)*x.x  + v0*v0*x.x*x.x  + x.y*x.y*(v*v+v0*v0);
     fact2 = v0*( v0*x.x-v*(x.z+H) );
     fact3 = +v*x.y*x.y - (x.z+H)*( v0*x.x-v*(x.z+H) );
     fact4 = Math.sqrt(v0*v0+v*v);
     fact5 = Math.sqrt((x.z+H)*(x.z+H)+x.x*x.x+x.y*x.y);
     potential = Constants.Bfactor*q0*(v/fact1)*(fact2/fact4+fact3/fact5);
	 return potential;
	}
  
  /** writes properties of the point charge to a string */  

       public String toString() {
	    return "Field Upper Half Plane";
    }

}