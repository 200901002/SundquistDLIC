package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

/** 
 * This BaseObject calculates the non-relativistic electric and magnetic fields of 
 * a magnetic monopole at position p moving with velocity v. */
public class MagneticMonopole extends BaseObject {

  /** The magnetic charge of the monopole. */
  public double q;
  /** The mass of the monopole. */
  public double m;
  /** The Pauli radius of the monopole (repulsion always dominates inside this radius).  
   * Radius is the "Pauli" radius, that is the radius at which a r**(-12) repulsion sets in 
   * regardless of whether we have repulsion or attraction. */
  public double radius;
  /** The position of the monopole. */
  public Vec3 p;
  /** The velocity of the charge. */
  public Vec3 v; 
  /** The location of the the thin conducting sheet in z. */
  public double zplane;
  /** The image charge parameter for the monopole.  If image = 0 this is just a normal monople.  
   * If image = 1 then this is a monopole whose magnetic and electric fields are non-zero only 
   * for observers with z > 0; if image = -1 then this is a monopole whose magnetic and electric 
   * fields are non-zero only for observers with z < 0  */
  public int image; 
  
/** Constructor for the monopole.   
 *  @param q The charge.
 *  @param p The position.
 *  @param v The velocity.
 *  @param zplane The location of the conducting plane.
 *  @param image The image charge parameter for the monopole.
 *  @param radius The Pauli radius.
 *  */
  public MagneticMonopole(double q, Vec3 p, Vec3 v, double zplane, int image, double radius){
    this.q = q;
    this.p = p;
    this.v = v;
    this.radius = radius;
    this.image = image;
    this.m = 1.;
    this.zplane = zplane;
  }

  
  /** Computes the magnetic field of a non-relativistic magnetic monopole.
   * @param x The position of the observer.
   * @param B The magnetic field at the position of the observer due to the monopole at p (calculated).
   * @return B The magnetic field at the position of the observer due to the monopole at p (calculated.
   */
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    /* first set B to the position x minus the position of the point charge  */
    B.Set(x).Sub(p);  
    if (!B.isZero() && Math.abs(this.q)!=0.)
    /* then set B to the monopole magnetic field (non-relativistic)  */
      B.Scale(q*Constants.Bfactor/B.len3());
    else  B.SetZero();
    /* zero out the field in the appropriate regions depending on the value of image.  */
    if (image == 1 && x.z > zplane) B.SetZero();
    if (image == -1 && x.z <= zplane) B.SetZero();
    return B;
  }
  
  /** The electric field of a moving monopole.
   * @param x The position of the observer
   * @param E The electric field at the observer's position if the monopope is at p (calculated). 
   * @return E The elctric field. */  
  public Vec3 Efield(Vec3 x, Vec3 E) {
     return Bfield(x, E).Cross(v);
	}

  /** Puts properties of the point charge into a string. */  
       public String toString() {
	    return "Magnetic Monopole:  charge " + this.q + " radius "+ this.radius + " position (" + this.p.x + ", " + this.p.y + ", " + this.p.z + ";   velocity (" + this.v.x + ", "
            + this.v.y + ", " + this.v.z + ")";
    }

}