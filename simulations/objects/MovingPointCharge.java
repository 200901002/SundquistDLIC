package simulations.objects;

import simulations.Constants;
import core.math.Vec3;
/** Calculates the fields of a moving point charge (extends PointCharge) */

public class MovingPointCharge extends PointCharge {
/** Velocity of the charge */
  public Vec3 v; 
/** acceleration of the charge.  Not used at present, in the future it may be used if we change this to give
 * a relativistically correct expression for E and B ,because those expressions depend on the acceleration */
  public Vec3 a;
/** the radial dependence of the Pauli repulsive force */
  public double power;
/** Constructor for the moving point charge.  Sets the acceleration and velocity to zero.  
 * @param q the charge 
 * @param p the position of the charge
 * @param radius the Pauli radius of the charge
 */
  public MovingPointCharge(double q, Vec3 p, double radius){
    super(q, p, radius);
    this.v = new Vec3();
    this.a = new Vec3();
    this.power = 12.;
  }
  
  /** Constructor for the moving point charge. Sets the velocity and acceleration to zero.
   * 
   * @param q the charge
   * @param p the position of the charge
   * @param radius the Pauli radius of the charge
   * @param power the radial dependence of the Pauli repulsion
   */
  public MovingPointCharge(double q, Vec3 p, double radius, double power){
    super(q, p, radius);
    this.v = new Vec3();
    this.a = new Vec3();
    this.power = power;
  }
 
  /** Constructor for the moving point charge. Sets the acceleration to zero.
   * 
   * @param q the charge
   * @param p the position of the charge
   * @param radius the Pauli radius of the charge
   * @param v the velocity of the charge
   */
 public MovingPointCharge(double q, Vec3 p, double radius, Vec3 v){
    super(q, p, radius);
    this.v = v;
    this.a = new Vec3();
    this.power = 12.;
  }
 /** Constructor for the moving point charge. Sets the acceleration to zero.
  * 
  * @param q the charge 
  * @param p the position of the charge
  * @param radius the Pauli radius
  * @param power the radial dependence of the Pauli repulsion
  * @param v the velocity of the charge
  */
  public MovingPointCharge(double q, Vec3 p, double radius, double power, Vec3 v){
    super(q, p, radius);
    this.v = v;
    this.a = new Vec3();
    this.power = power;
  }
  
  /** Constructor for the moving point charge. 
   * 
   * @param q the charge
   * @param p the position
   * @param radius the radius
   * @param power the radial dependence of the Pauli repulsion
   * @param v the velocity of the charge
   * @param a the acceleration of the charge
   */
  public MovingPointCharge(double q, Vec3 p, double radius, double power, Vec3 v, Vec3 a){
    super(q, p, radius);
    this.v = v;
    this.a = a;
    this.power = power;
  }
  
  /** Constructor for the moving point charge. Sets the acceleration and velocity to zero.
   * @param q the charge
   * @param p the acceleration 
   */
  public MovingPointCharge(double q, Vec3 p){
      super(q, p);
      this.v = new Vec3();
      this.a = new Vec3();
  }
  /** Constructor for the moving point charge. Sets the acceleration to zero.
   * 
   * @param q the charge
   * @param p the position
   * @param v the velocity
   */
  public MovingPointCharge(double q, Vec3 p, Vec3 v){
      super(q,p);
      this.v = v;
      this.a = new Vec3();
  }
  /** Constructor for the moving point charge. 
   * 
   * @param q the charge
   * @param p the position
   * @param v the velocity
   * @param a the acceleration
   */
  public MovingPointCharge(double q, Vec3 p, Vec3 v, Vec3 a){
      super(q,p);
      this.v = v;
      this.a = a;
  }
 /** Evolves the position and velocity of the charge.  Very naive, should be overridden 
  * @param dt the time step for the evolution
  */
  public void Evolve(double dt){
    /* Fix: Implement a better integration method */
    p.AddScaled(v,dt);
    v.AddScaled(a,dt);
  }
 /** Sets the acceleration 
  * 
  * @param a the acceleration
  */
  public void Acceleration(Vec3 a){
    this.a = a;
  }
  
  /** the non-relativistic electric field of a moving point charge 
   * @param x the position of the observer
   * @param E the electric field at the position of the observer due to the charge at p (calculated)
   * @return E the electric field at the position of the observer due to the charge at p (calculated)
   */
  public Vec3 Efield(Vec3 x, Vec3 E){
    E.Set(x).Sub(p);
    if (!E.isZero() && (Math.abs(this.q)!=0.)){
        E.Scale(q*Constants.Efactor/E.len3());
        return E;
    }
    else
      return E.SetZero();
  }
  /** the non-relativistic magnetic field of a moving point charge (v x E )  
  * @param x the position of the observer
  * @param B the magnetic field at the observer's position if the charge is at p and its velocity is v (calculated) 
  * @return B the magnetic field at the observer's position if the charge is at p and its velocity is v 
  * (calculated) */  
  public Vec3 Bfield(Vec3 x, Vec3 B){
    return Efield(x, B).Cross(v).Scale(-1.0/Constants.c2);
  }
 
  /** The Pauli field of a moving point charge.  The Pauli field is returned as a 
   * multiple of the electric field of the particle.  
   * @param x the position of the observer
   * @param P the Pauli repulsive field (calculated) 
   * @return P the Pauli repulsive field (calculated)
   */  
  public Vec3 Pfield(Vec3 x, Vec3 P){
    P.Set(x).Sub(p);
    if (!P.isZero() && (Math.abs(this.q)!=0.))
    	/* the Pauli field is returned as a multiple of the electric field of the particle */
      return P.Scale(Math.pow(2.*this.radius/P.len(),power-2.)*Math.abs(q)*Constants.Efactor/P.len3());    
    else
      return P.SetZero();
  }
 /** prints a string with the properties of the moving point charge */
     public String toString() {
	    return "MovingPointCharge:  charge " + this.q + " radius "+ this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z +
	    ") velocity (" + this.v.x + ", " + this.v.y + ", " + this.v.z +  ")";
    }

}