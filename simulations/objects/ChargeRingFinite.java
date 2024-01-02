package simulations.objects;


import java.lang.*;

import core.math.Vec3;



public class ChargeRingFinite extends BaseObject {
  
  /*
   *   Computes the fields of a ring of charge with finite number of charges
   *
   */


  public double R, q;
  public int Ncharges;
  /* p = center of the ring
   * d = direction of the ring's axis, normalized*/
  public Vec3 p, d;
  
  public ChargeRingFinite(Vec3 p, Vec3 d, double R, int Ncharges, double q)
  /* Constructs a new Current ring with the specified center (p), axis (d),
   *   radius (R), and number of charges Ncharges, each carrying charge q.  */
  {
    this.p = p;
    this.d = d.unit();   
    this.R = R;
    this.q = q;
    this.Ncharges = Ncharges;
  }
  
  public void Evolve(double dt)
  /* Evolves the CurrnetRing by amount of time 'dt'. For simplicity, it
   *   assumes zero second-order rate of changes and therefore uses simple
   *   Euler integration. */
  {

  }
  

  public Vec3 Efield(Vec3 x, Vec3 E)
  /* Sets 'E' to the value of the electric field at 'x'. 'x' is not modified.
   * Returns: resulting 'E' */
  {
    Vec3 xn = new Vec3(0,0,0);
    Vec3 Estore = new Vec3(0,0,0);
    Vec3 En= new Vec3(0,0,0);
    Estore.SetZero();
    // below assumes that axis of ring is along z- axis.
    for (int i = 0; i < Ncharges; ++i) {
        double angle = 2*Math.PI*i/Ncharges;
        xn.Set(R* Math.cos(angle),R * Math.sin(angle),0.);
        xn.Add(p);
        En.Set(x).Sub(xn);
        if (!En.isZero()) En.Scale(q/En.len3());
        else En.SetZero();
        Estore.Add(En);
    }
    return E.Set(Estore);
  }

  public Vec3 Bfield(Vec3 x, Vec3 B)
  /* Sets 'B' to the value of the magnetic field at 'x'. 'x' is not modified.
   * Returns: resulting 'B' */
   {
    return B.Set(Vec3.Zero);
   }
}  
 
  

  
