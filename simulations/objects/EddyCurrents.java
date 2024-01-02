package simulations.objects;

import core.math.Vec3;

/** 
 * This BaseObject calculates the eddy currents due to a 
 * a magnetic monopole moving moving with velocity v 
 *   */
public class EddyCurrents extends BaseObject {
  /** H = height of the monople above z = 0 */
  public double H;
   /** the time  */
  public double t;
  /** offset is an overall time offset before the image charges start moving */
  public double offset;
  /** q0 is the magnetic charge */
  public double zplane;
  /** zplane is the location in z of the conducting thin sheet */
  public double q0;
  /** v0 is the vertical speed of the receeding monopoles once they start receeding*/
  public double v0;
  /** v is the horizontal speed of the monopole */
  public double v;
  /** xorigin is the x coordinate of the origin */
  public double xorigin;
  /** yorigin is the y coordinate of the origin */
  public double yorigin;
  
/** constructor for the eddy currents
 *  @param H The height of the monopole above the plane.
 *  @param q0 The monopole charge.
 *  @param v0 The vertical speed of the receding monopoles.
 *  @param v The horizontal speed of the monopole.  
 *  @param offset The time before the receding monopoles start receding.  
 *  @param xorigin The x location of the monopole.
 *  @param yorigin The y location of the monopole.  
 *  */
  public EddyCurrents(double H, double q0, double v0, double v,  
		  double offset, double xorigin, double yorigin){
	    this.offset = offset;  
	    this.q0 = q0;
	    this.H = H;
	    this.v0 = v0;
	    this.v = v;
	    this.xorigin = xorigin;
	    this.yorigin = yorigin;
  }

  /** The eddy current of a moving monopole.  We put this in the "B field" so we can plot the eddy currents using 
   * our standard procedures in SundquistDLIC, but of course this is NOT a B field.
   * @param X The position of the observer in the xy plane at z = 0.
   * @param B The eddy current in the xy plane at z = 0 due to the moving monopole.
   * @return B The eddy current in the xy plane at z = 0 due to the moving monopole.
   */
  public Vec3 Bfield(Vec3 X, Vec3 B) {
	  /* calculate the eddy current, see equation (13) of Liu and Belcher (2007). */
	  double x = 0.;
	  double y = 0.;
	  double a = 0.;
	  double b = 0.;
	  double c = 0.;
	  double vsq = 0.;
	  double r = 0.;
	  double d = 0.;
	  x = X.x - xorigin;
	  y = X.y - yorigin;
	  a = v0*x - v*H;
	  b = v*y*y - a*H;
	  vsq = v*v + v0*v0;
	  r = Math.sqrt(x*x + y*y + H*H);
	  c = vsq*y*y+a*a;
	  d = v0*a/Math.sqrt(vsq)+b/r;
	  B.y = q0*v*(4*v0*a*d/(c*c)+2*(-v0*v0/Math.sqrt(vsq)+v0*H/r+b*x/(r*r*r))/c);
	  B.x = -q0*v*(4*vsq*y*d/(c*c)+2*(-2*v*y/r+b*y/(r*r*r))/c);
	  return B;
  }
  
  /** This "E" field has no meaning for this object, as we are
   * only using this object to calcuate the eddy current in the xy plane and put it in Bfield.  
   * @param x the position of the observer
   * @param E the electric field at the observer's position if the monopole is at p (calculated) 
   * @return zero for a stationary charge */  
  public Vec3 Efield(Vec3 x, Vec3 E) {
	  Vec3 vzero = new Vec3(0.,0.,0.);
	  return Bfield(x, E).Cross(vzero);
  }
  
  /** writes properties of the point charge to a string */  
  public String toString() {
	  return "Eddy Currents:  xorigin " + this.xorigin + " yorigin "+ this.yorigin +  ")";
  }

}