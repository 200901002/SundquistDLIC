package simulations.objects;

import core.math.Vec3;
import core.math.SpecialFunctions;

/** A ring carrying current. 
 *
 * CurrentRing is an EMSource object that computes the electric and magnetic
 * field of a moving circular ring of current. The adjustable parameters are
 * the position, orientation, radius, and velocity of the ring, the current
 * and its time rate of change.
 * 
 * To compute the fields, elliptic integrals must be performed, 
 * and so using a CurrentRing will slow down computations.
 * <p>
 * Documentation for the expressions used herein in calculating the magnetic field
 * can be found in the
 * <a href="C:\Development\Projects\SundquistDLIC\DLICdoc\TEAL_Physics_Math.pdf"> 
 * TEAL_Physics_Math document </a> (see Section 3.2.2).  This link will work if you have installed
 * the <i>SundquistDLIC</i> code base and documentation as instructed. 
 * @author Andreas Sundquist
 * @version 1.0
 */
public class CurrentRing extends BaseObject {
  /** current in ring (positive current is right handed with respect to the 
   * axis of the ring) */
  public double I;
  /** time rate of change of current */
  public double dIdt;
  /** R = radius of the ring */
  public double R;
  /** the observer"s location where we are going to calculate the fields  */
  public Vec3 p;
  /** center of the ring */
  public Vec3 c;
  /** unit vector along the direction of the rings axis, defines the direction of positive current */
  public Vec3 d;
  /** the velocity of the ring */
  public Vec3 v;
  /** the angular velocity vector of the ring, omega cross r from center gives the angular speed   */
  public Vec3 omega;

  /** Constructs a new Current ring.  The velocity and the angular velocity and
  // *   the rate of current change are initialized to zero. 
   * @param p the position of the center of the ring
   * @param d the axis of the ring
   * @param R the radius of the ring
   * @param I the current in the ring */
  public CurrentRing(Vec3 p, Vec3 d, double R, double I){
    this.p = p;
    this.d = d.unit();
    this.v = Vec3.Zero;
    this.R = R;
    this.I = I;
    this.dIdt = 0.0;
    this.omega = Vec3.Zero;
  }
  /** Constructs a new Current ring.  The angular velocity
   * and the rate of current change are initialized to zero. 
   * @param p the position of the center of the ring
   * @param d the axis of the ring
   * @param R the radius of the ring
   * @param I the current in the ring *
   * @param v the velocity of the ring */ 
   public CurrentRing(Vec3 p, Vec3 d, double R, double I, Vec3 v){
    this.p = p;
    this.d = d.unit();
    this.v = v;
    this.R = R;
    this.I = I;
    this.dIdt = 0.0;
    this.omega = Vec3.Zero;
  }
   /** Constructs a new Current ring.  The angular velocity is initialized to zero, as is the speed,
    * but we allow a time rate of change dIdt. 
    * @param p the position of the center of the ring
    * @param d the axis of the ring
    * @param R the radius of the ring
    * @param I the current in the ring *
    * @param dIdt the time rate of change of current in the ring */ 
    public CurrentRing(Vec3 p, Vec3 d, double R, double I, double dIdt){
     this.p = p;
     this.d = d.unit();
     this.R = R;
     this.I = I;
     this.dIdt = dIdt;
     this.omega = Vec3.Zero;
     this.v = Vec3.Zero;
   }
   /** Constructs a new Current ring.  The rate of current change
    * is initialized to zero. 
    * @param p the position of the center of the ring
    * @param d the axis of the ring
    * @param R the radius of the ring
    * @param I the current in the ring *
    * @param v the velocity of the ring 
    * @param omega the angular velocity of the ring */
  public CurrentRing(Vec3 p, Vec3 d, double R, double I, Vec3 v, Vec3 omega){
    this.p = p;
    this.d = d.unit();
    this.v = v;
    this.R = R;
    this.I = I;
    this.dIdt = 0.0;
    this.omega = omega;
  }
  /** A very simple evolution of the CurrnetRing by amount of time "dt". For simplicity, it
   *   assumes zero second-order rate of changes and therefore uses simple Euler integration. 
   *   This method should be overridden or replaced in more complicated situations.  
   *   @param dt the time step */ 
  public void Evolve(double dt){
    p.AddScaled(v,dt);
    I += dt*dIdt;
  }
  
  /** the last calculated location, stored to avoid unnecessary calculations */
  private Vec3 LastX = Vec3.Invalid;
  /** the last calculated B field value, stored to avoid unnecessary calculations */
  private Vec3 LastB = Vec3.Zero;
  /** the last calculated E field value, stored to avoid unnecessary calculations */
  private Vec3 LastE = Vec3.Zero;
  
  /** Computes both the electric and magnetic fields at the observer"s position. 
   *   This method is used by both Efield() and Bfield(). 
   *   @param x the position of the observer */
  private void CalculateFields(Vec3 x){
	/* don"t recalculate at the last position again if it is the same as before */
    if (x.equals(LastX)) return;
    /* store the current position to check at the next position */
    LastX = new Vec3(x);
    /* calculate the vector r from the position x to the center of the ring */
    Vec3 r = x.sub(p);
    Vec3 rtry =  x.sub(p);
    /* calculate the linear velocty plus r cross omega  */
    Vec3 vtotal = v.add(rtry.Cross(omega));
    /* we now set up a coordinate system centered on the ring which is cylindrical with the z-axis along
     * the axis of the ring, with the usual definitions of the rho unit and phi unit vectors */
    /* calculate that part of r which is perpendicular to the axis of the ring  */
    Vec3 rp = r.perpunit(d);
    Vec3 rhohat;
    /* if rp is zero (e.g. the observer is on the axis of the ring) set our x unit vector to zero */
    if (rp.isZero()) rhohat = Vec3.Zero;
    /* if rp is not zero set our x unit vector to the direction perpendicular to the axis of the ring in the 
     * direction of the observer */
    else rhohat = rp.unit();
    /* set our phihat unit direction to the direction perpendicular to the axis of the ring and rhohat */
    Vec3 phihat = d.cross(rhohat);    
    /* find the position of the observer along the axis of the ring */
    double rz = r.dot(d)/R;
    /* find the position of the observer along the cylindrical "rho" direction, normalized to the radius of 
     * the ring */
    double rrho = rp.len()/R;
    double Bz, Brho, Ephi;
    /*  On the ring of current"s axis we use simple formulas rather than eliptic integrals */
    if (rrho==0.0) { 
      Brho = 0.0;
      Bz = 2*I*Math.PI/(R*Math.pow(1 + rz*rz, -1.5));  
      Ephi = 0.0;   
      /* otherwise we do the full eliptic integral expressions, except if we are sitting right on the ring
       * itself.  In that case we set the fields to zero. */
    } else if ((rrho==1.0) && (rz==0.0)) { 
      Brho = 0.0;
      Bz = 0.0;
      Ephi = 0.0;
      /* we are not on the ring and not on the ring"s axis, now use the eliptic integral expressions */
    } else {
            double rz2 = rz*rz;
            double r12 = rz2 + (rrho + 1.)*(rrho + 1.);		
            double ks =  (rz2 + (rrho - 1.)*(rrho - 1.))/r12;
            double kc = Math.sqrt(ks);
            double k = Math.sqrt(1.-ks);
            double h = 1.+ks-(1.-ks)*rrho;
            double G0 =  SpecialFunctions.ellipticIntegral(kc,1.,-1.,1.,0.0003);
            double G1 = .5*SpecialFunctions.ellipticIntegral(kc,ks,-1.,1.,0.0003);
            double L1 = (G0 + h*G1)*k/Math.pow(rrho,1.5);
            double L2 = rz*G1*k*k*k/Math.pow(rrho,1.5);
            /* compute the E and B values leaving out the factor of mhu naught over 4 pi  */
            Brho = L2* I/R;	
            Bz = L1* I/R;
	        Ephi = -2.*dIdt*k*G0/Math.sqrt(rrho);
    }
    /* calculate the vector that is Bz times the unit ring normal plus Brho times the cylindrical rho axis */
    LastB = d.scale(Bz).addscaled(rhohat,Brho);
    /* calculate the vector that is Ephi times the unit vector in the phi direction, minus v cross B, the motional
     * electric field associated with the motion of the ring */
    LastE = LastB.cross(vtotal).addscaled(phihat,Ephi);  
 }
  /** Sets "E" to the value of the electric field at "x". "x" is not modified. Returns: resulting "E".  
   * This uses the CalculateFields method. 
   * @param x the position of the observer 
   * @param E the electric field at the observer (calculated) 
   * @return E the electric field at the observer */
  public Vec3 Efield(Vec3 x, Vec3 E){
    CalculateFields(x);
    return E.Set(LastE);
  }
  /** Sets "B" to the value of the magnetic field at "x". "x" is not modified. Returns: resulting "B" 
   * * This uses the CalculateFields method. 
   * @param x the position of the observer 
   * @param B the magnetic field at the observer (calculated) 
   * @return B the magnetic field at the observer */
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    CalculateFields(x);
    return B.Set(LastB);
   }
  
  }


