package simulations.objects;

import core.math.Vec3;

public class HelioField extends BaseObject {
  /** shortest distance to the bow shock */
  public double radius;
  /** the position of the sun */
  public Vec3 p;
  
/** constructor 
 *  @param radius The distance to the termination shock.
 *  @param p The position of the Sun.  */
  public HelioField(double radius, Vec3 p){
    this.radius = radius;
    this.p = p;
  }
 
  /** inverse distance squared field 
   * @param x the position of the observer
   * @param E the electric field at the position of the observer 
   * @return E the electric field at the position of the observer 
   */
  public Vec3 Efield(Vec3 x, Vec3 E) {
	E.Set(x).Sub(p);  
	if (!E.isZero() ) E.Scale(Math.pow(radius,2.)/E.len3());
	else E.SetZero();
	// println("p "+ p.x + "  " + p.y + "  "  + p.z);
	return E;
  }
  
  /** the magnetic field (zero) 
   * @param x the position of the observer
   * @param B the magnetic field at the observer's position if the charge is at p (calculated) 
   * @return zero for a stationary charge */  
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    return B.SetZero();
  }
 
  
  /** A local way to print a string */ 
	private static void println(String s){
  	System.out.println(s);
	}
  /** writes properties of the HelioField to a string */  
       public String toString() {
	    return "HelioField:  radius " + this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z + ")";
    }

}