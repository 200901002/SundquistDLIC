package simulations.objects;

import core.math.Vec3;
import simulations.Constants;
import simulations.objects.antennaMath.*;
/** 
 * This BaseObject calculates the electric and magnetic fields of a linear antenna with the properties
 * specified.  
 * 
 *   @author Norman Deby
 *   @version 1.0 
 *   */

public class ElectricAntenna extends BaseObject {
/** The position of the antenna. */
  public Vec3 x;
/** The wavenumber of the radiation produced. */
  public double k; 
/** The product of the length of the antenna and the wavenumber divided by 2. */
  public double ak;
/** The angular frequency of the radiation produced. */
  public double w;
/** The time. */
  public double ta;

  /** constructor for the linear antenna
   *  @param x The position of the antenna.
   *  @param k The wavenumber of the radiation. 
   *  @param ak The product of the length of the antenna and the wavenumber divided by 2. 
   *  @param w The angular frequency of the radiation.  
   *  @param ta The time.*/
  public ElectricAntenna(Vec3 x,double k, double ak, double w, double ta)
  {
    this.x = x;
    this.k = k;
    this.ak = ak;
    this.w = w;
    this.ta = ta;
  }
  
  
  public void Evolve(double dt)
  {
    // this does nothing
    x.AddScaled(new Vec3(0.,0.,0.),dt);
  }

  /** The electric field of the linear antenna.  
   * @param x The position of the observer.
   * @param E The electric field at the position of the observer.
   * @return E The electric field at the position of the observer.
   */
  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    Vec3 r = x.sub(this.x);
    double rmag = r.len();
    if (rmag==0.0)
      return E.SetZero();
    else {
      double rho = Math.sqrt(r.x*r.x + r.y*r.y);
      double rhok = rho*k;
      double zk = r.z*k;
      double tw = ta*w;
      Erho Erho= new Erho(tw,rhok,zk,ak);
      Ez Ez = new Ez(tw,rhok,zk,ak);
      if (r.x >=0.) E.x = Erho.getErho();
      else E.x = -Erho.getErho();
      E.z = Ez.getEz();
      E.y=0.;
      return E;
    }
  }
  /** The magnetic field of the linear antenna.  
   * @param x The position of the observer.
   * @param B The magnetic field at the position of the observer.
   * @return B The magnetic field at the position of the observer.
   */
  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    Vec3 r = x.sub(this.x);
    double rmag = r.len();
    if (rmag==0.0)
      return B.SetZero();
    else {
   
      double rho = Math.sqrt(r.x*r.x + r.y*r.y);
      double rhok = rho*k;
      double zk = r.z*k;
      double tw = ta*w;
      Bphi Bphi = new Bphi(tw,rhok,zk,ak);
      B.x = 0.;
      if (r.x <= 0. ) B.y = Bphi.getBphi()/Constants.c;
      else B.y =  -Bphi.getBphi()/Constants.c;

      B.z=0.;
      return B;
    }
  }

}
