package simulations.objects;

import core.math.Vec3;

public class ISMfield extends BaseObject {
  /** the radius of the heliosphere */
  public double radius;
  /** the position of the sun */
  public Vec3 p;
  
/** constructor 
 *  @param radius The distance to the termination shock.
 *  @param p The position of the Sun.  */
  public ISMfield(double radius, Vec3 p){
    this.radius = radius;
    this.p = p;
  }
 
  /** constant electric field except for a sphere centered at p where the field is zero
   * @param x the position of the observer
   * @param E the electric field at the position of the observer 
   * @return E the electric field at the position of the observer 
   */
  public Vec3 Efield(Vec3 x, Vec3 E) {
    Vec3 constantE = new Vec3(1.,0.,0.);
    Vec3 dummy = new Vec3(0.,0.,0.);
    dummy.Set(x).Sub(p);
    if (dummy.len() < radius) return E.SetZero();
    else  return E.Set(constantE); 
  }
  
  /** the magnetic field (zero) 
   * @param x the position of the observer
   * @param B the magnetic field at the observer's position if the charge is at p (calculated) 
   * @return zero for a stationary charge */  
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    return B.SetZero();
  }
  
  /** writes properties of the HelioField to a string */  
       public String toString() {
	    return "HelioField:  radius " + this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z + ")";
    }

}