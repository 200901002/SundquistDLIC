package simulations.objects;

import core.math.Vec3;
/** 
 */
public class ElectromagneticPlaneWave extends BaseObject {
  /** The phase */
  public double phase = 0.;
  /** The amplitude of the electric field */
  public double E0;
  /** The propagation vector k */
  public Vec3 k;
  /** the angular frequence omega */
  public double omega;
  /** the polarization vector of the electric field */
  public Vec3 Pol;
  /** The time. */
  public double t; 
  /** Create an electromagnetic plane wave  */ 
  public ElectromagneticPlaneWave(Vec3 k, double omega, double E0, Vec3 Pol, double phase){
    this.k = k;
    this.t = 0.;
    this.E0 = E0;
    this.phase = phase;
    this.Pol = Pol;
    this.omega = omega;
  }
 
  /** Get the time.  This allows us the find the current time for this plane wave. */  
  public double getT(){
    return this.t;
  }

  /** Compute the electric field at position x and time t */
  public Vec3 Efield(Vec3 x, Vec3 E)
  {
      /*  calculate the vector from the dipole to the position to the observation
       * point  */
    double kx = x.dot(k);
    Vec3 Ecal = Pol.scale(Math.cos(kx-omega*t-phase));
      E.Set(Ecal);
      return E;
    }

  /** Compute the magnetic field at position x and time t */
  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
	  double kx = x.dot(k);
	  Vec3 Bcal = k.Cross(Pol);
	  Bcal.scale(Math.cos(kx-omega*t-phase));
	  B.Set(Bcal);
      return B;
    }
}
