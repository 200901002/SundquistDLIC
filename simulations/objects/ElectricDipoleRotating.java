package simulations.objects;

import simulations.Constants;
import core.math.Vec3;
/** This is a rotating electric dipole.  It rotates about the z axis, lies in the xy plane
 * Important note:  we multiply the overall electric field of this dipole by one factor
 * of the radius and scale it by 1/100.  If you want this dipole to interact with other electromagnetic
 * objects you must compensate for this.  
 * @author Andreas Sunquist, John Belcher
 * @version 1.0 
 */
public class ElectricDipoleRotating extends BaseObject {
  /** The position of the dipole. */
  public Vec3 x;
  /** The time. */
  public double t; 
  /** The magnitude of the dipole moment of the rotating dipole */	
  public double p0;
  /** The angular frequency of rotation of the rotating dipole */	
  public double omega;
  /** The angle that the dipole moment mades to the z-axis */	
  public double theta;
  /** Create an electric dipole */
  public ElectricDipoleRotating(Vec3 x, double p0, double omega, double theta)
  {
	  this.x = x;
	  this.p0 = p0;
      this.omega = omega;
      this.theta = theta;
      this.t = 0;
  }

  /** Get the time.  This allows us the find the current time for this dipole. */  
  public double getT(){
    return this.t;
  }

  /** Returns the dipole moment at a time retarded by dt.
   * We use the method getT to find out the current time of the dipole */   
  public Vec3 getP(double dt) {
      double tretarded = getT() - dt;
      Vec3 Pcom;
      Pcom =  Vec3.Xhat.scale(p0*Math.sin(theta)*Math.cos(omega*tretarded));
      Pcom.Add(Vec3.Yhat.scale(p0*Math.sin(theta)*Math.sin(omega*tretarded)));
      Pcom.Add(Vec3.Zhat.scale(p0*Math.cos(theta)));
      //System.out.println("dipole from ElectricDipoleRotating.getP   x " + Pcom.x + " y " +Pcom.y  + " z " + Pcom.z);
      return Pcom;
    }
  
  /** Returns the first time derivative of the dipole moment at a 
   *   time retarded by dt 
   *   We use the method getT to find out the current time of the dipole */    
  public Vec3 getDP(double dt) {
	double tretarded = getT() - dt;
	  Vec3 Pcom;
      Pcom =  Vec3.Xhat.scale(-omega*p0*Math.sin(theta)*Math.sin(omega*tretarded));
      Pcom.Add(Vec3.Yhat.scale(omega*p0*Math.sin(theta)*Math.cos(omega*tretarded)));
      return Pcom;
  }
  
  /** Returns the second time derivative of the dipole moment at a
   *   time retarded by dt.  We use the method getT to find out 
   *   the current time of the dipole */     
  public Vec3 getDDP(double dt) {
  double tretarded = getT() - dt;
  Vec3 Pcom;
  Pcom =  Vec3.Xhat.scale(-omega*omega*p0*Math.sin(theta)*Math.cos(omega*tretarded));
  Pcom.Add(Vec3.Yhat.scale(-omega*omega*p0*Math.sin(theta)*Math.sin(omega*tretarded)));
  return Pcom;
  }
  
  /** Evolve the dipole time  */
  public void Evolve(double dt){
	t += dt;
  }
  
  /** Compute the electric field at position x and time t */
  public Vec3 Efield(Vec3 x, Vec3 E)
  {
      /*  calculate the vector from the dipole to the position to the observation
       * point  */
    Vec3 r = x.sub(this.x);
    double rmag = r.len();
    if (rmag==0.0)
      return E.SetZero();
    else {
      r.Scale(1.0/rmag);
      /*  calculate the speed of light travel time dtretarded from the position of the dipole
       * to the observation point */
      double dtretarded = rmag/Constants.c;
      /*  get the dipole moment vector at the retarded time t - dtretarded */
      Vec3 p = getP(dtretarded);
      //System.out.println("postion ElectricDipoleRotating " + r.x + " dipole " + p.x);
      /*  calculate E1, the quasi-static term in the total electric field */
      Vec3 E1 = r.scale(3.0*p.dot(r)).Sub(p).Scale(1.0/(rmag*rmag*rmag));
      /*  get the time derivative of the dipole moment vector at the retarded time t - dtretarded */
      p = getDP(dtretarded);
      /*  calculate E2, the intermediate term in the total electric field */
      Vec3 E2 = r.scale(3.0*p.dot(r)).Sub(p).Scale(1.0/(Constants.c*rmag*rmag));
      /*  get the second time derivative of the dipole moment vector at the retarded time t - dtretarded */
      p = getDDP(dtretarded);
      /*  calculate E3, the radiation term in the total electric field */
      Vec3 E3 = p.Cross(r).Cross(r).Scale(1.0/(Constants.c*Constants.c*rmag));
      /*  get the total electric field */
      E.Set(E1).Add(E2).Add(E3).Scale(Constants.Efactor);
      /* multiply by r so that the radiation term stays constant at large distances
       * and scale by a factor of 1/100  */
      E.Scale(rmag/100.);

      return E;
    }
  }
  /** Compute the magnetic field at position x and time t */
  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    Vec3 r = x.sub(this.x);
    double rmag = r.len();
    if (rmag==0.0)
      return B.SetZero();
    else {
      r.Scale(1.0/rmag);
      double dtretarded = rmag/Constants.c;
      
      
      Vec3 p = getDP(dtretarded);
      Vec3 B1 = p.cross(r).Scale(1.0/(Constants.c*Constants.c*rmag*rmag));  
      
      p = getDDP(dtretarded);
      Vec3 B2 = p.cross(r).Scale(1.0/(Constants.c*Constants.c*Constants.c*rmag));
      
      B1.Add(B2).Scale(Constants.Efactor);
      B.Add(B1);
       // scale B by r so that radiation field does not fall off at infinity
      B.Scale(rmag/100.);
      B.Scale(0.);
      return B;
    }
  }

}