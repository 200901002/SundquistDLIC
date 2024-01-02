package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

/** 
 * Point Charge.  This BaseObject calculates the non-relativistic electric and magnetic fields of 
 * a stationary point charge */
public class PointCharge extends BaseObject {
  /** the charge of the point charge */
  public double q;
  /** the mass of the point charge */
  public double m;
  /** the Pauli radius of the point charge (repulsion always dominates inside this radius).  
   * Radius is the "Pauli" radius, that is the radius at which a r**(-12) repulsion sets in 
   * regardless of whether we have electrostatic repulsion or attraction. */
  public double radius;
  /** the position of the point charge */
  public Vec3 p;
  
/** constructor for the point charge setting Pauli radius 
 *  @param q the charge
 *  @param p the position
 *  @param radius the Pauli radius
 *  */
  public PointCharge(double q, Vec3 p, double radius){
    this.q = q;
    this.p = p;
    this.radius = radius;
    this.m = 1.;
  }
  /** constructor for the point charge with radius set to 5.
   * @param q the charge 
   * @param p the position
   */
  public PointCharge(double q, Vec3 p) {
      this.q = q;
      this.p = p;
      this.radius = 5.0;
      this.m = 1.;
  }
  
  /** the electric field of a stationary point charge 
   * @param x the position of the observer
   * @param E the electric field at the position of the observer due to the charge at p (calculated)
   * @return E the electric field at the position of the observer due to the charge at p (calculated)
   */
  public Vec3 Efield(Vec3 x, Vec3 E) {
    /* first sets E to the position x minus the position of the point charge  */
    E.Set(x).Sub(p);  
    if (!E.isZero() && Math.abs(this.q)!=0.)
    /* then sets E to the coulomb electric field (non-relativistic)  */
      return E.Scale(q*Constants.Efactor/E.len3());
    else
      return E.SetZero();
  }
  
  /** the magnetic field of a stationary point charge (zero) 
   * @param x the position of the observer
   * @param B the magnetic field at the observer's position if the charge is at p (calculated) 
   * @return zero for a stationary charge */  
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    return B.SetZero();
  }
  
  /** writes properties of the point charge to a string */  
       public String toString() {
	    return "PointCharge:  charge " + this.q + " radius "+ this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z + ")";
    }

}