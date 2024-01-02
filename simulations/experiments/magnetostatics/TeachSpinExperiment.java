package simulations.experiments.magnetostatics;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.Constants;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;
/** Experiment consists of two ring currents, one above the other.  The top one represents the small
 * magnet in the teach spin experiment, and the bottom one represents the bottom current ring in the teach
 * spin experiment.  In this experiment we let the current in the bottom current ring vary sinusoidally with time, 
 *         I(t) = I0 sin (omega * t)
 * and calculate the corresponding motion of the magnet due to this changing current in a purely magnetostatic 
 * fashion (that is there is no currrent induced anywhere in this problem due to a changing magnetic flux through
 * any ring--e.g. there are no induced electric fields or currents of importance).  We also assume that this magnet
 * motion is quasi-static--that is it is very slow compared to the natural frequency of oscillation of 
 * the magnet on the spring from which it is suspended, so that the magnetic force on the 
 * dipole simply results in a displacement from equilibrium that is proportional to the force.  
 * @author Michael Danziger
 * @author John Belcher
 * @version 1.0
 * */
public class TeachSpinExperiment extends BaseExperiment {
/** the radius of the lower current ring in pixels */
  public double R;
/** the amplitude of the motion of the magnet  */
  public double deltaR;
/** the amplitude of the sinusoidal variation in the current in the lower current ring */
  public double I0;
  /** the amplitude of the sinusoidal variation in the current in the lower current ring */
  public double I1;
/** the constant current in the magnetic dipole */
  public double omega;
/** the initial position of the magnet on the z-axis */
  public double z0;
/** the fixed position of the lower current ring center along the z-axis */
  public double Z0;
/** the current position of the magnet along the z-axis */
  public double z;
  /** the current velocity of the magnet along the z-axis */
  public double v ;
/** the current current in the ring */
  public double I;
/** the current time rate of change of current in the ring */
  public double dIdt;
/** the time */
  public double t;
 
  /** constructor for the experiment 
   * @param R the radius of the lower current ring
   * @param deltaR the amplitude of the variation in the height of the magnet
   * @param I1 the constant current in the magnet
   * @param z0 the initial position of the magnet above the lower current ring 
   * @param Z0 the initial position of the lower current ring along the z-axis 
   * @param I0 the amplitude of the variation in the current in the lower current ring
   * @param omega the angular frequency of the time variation in current
   * */
  public TeachSpinExperiment(double R, double deltaR, double I1,double z0, double Z0, double I0, double omega){
    /* this experiment is magneto-quasi-statics, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
    /* this experiment is magneto-quasi-statics, so we set FieldMotionType accordingly */
    this.FieldMotionType = Constants.FIELD_MOTION_BFIELD; 
    this.R = R;
    this.deltaR = deltaR;
    this.z = z0;
    this.z0 = z0;
    this.I0 = I0;
    this.Z0 = Z0;
    this.omega = omega;
    this.t = 0.;
    this.I = 0.;
    this.dIdt = I0*this.omega;
    this.v = deltaR*omega;
    this.I1 = I1;
    
    ConstructEMSource();
  }
 
  /** the lower ring in the Teach Spin experiment */
  public CurrentRing ring;
  /** the dipole in the Teach Spin experiment */
  public CurrentRing dipole;
  /** the EMCollection consisting of the dipole and the current ring */
  private EMCollection collection;
  /** Constructs the EMSource representing the two current rings in the experiment with the given
   *   initial conditions */
  public void ConstructEMSource(){
    ring = new CurrentRing(new Vec3(0, 0, Z0), Vec3.Zhat, this.R, this.I, this.dIdt);
    double Rdipole;
    Rdipole = R/20.;
    /* instantiate the dipole magnet intially with zero speed */
    dipole = new CurrentRing(new Vec3(0, 0, z0+Z0), Vec3.Zhat, Rdipole, I1, new Vec3(0,0,v));
    collection = new EMCollection();
    collection.Add(ring);
    collection.Add(dipole);
  }
  
  public BaseObject getEMSource(){
    return collection;
  }
  
  /** Evolves the experiment.  This is easy because we have an analytic expression for
   * the position of the magnet as a function of time and the current in the ring as a function of time.
   *   The state of the EMSource representing the ring and magnet must be updated to
   *   reflect the change */
  public void Evolve(double dt, double maxStep){   
	/* Evolve the system by the time step dt */
	t = t +dt;
    z = Z0 + z0 + deltaR*Math.sin(omega*t);
    v = deltaR*omega*Math.cos(omega*t);
    I = I0*Math.sin(omega*t);
    dIdt = I0*omega*Math.cos(omega*t);
    /* Update the state of the EMSource objects to reflect the new state */
    dipole.p = new Vec3(0, 0, z);
    dipole.v = new Vec3(0, 0, v);
    ring.I = I;
    ring.dIdt = dIdt;
    System.out.println(" t " + t + "  z "  + z + " v " + v +  "  I " + I);
  }
    
  /** Evolves the experiment by a time step "dt" 
   */
  public void Evolve(double dt){
	  /* we simply call the Evolve(double dt,double maxStep) method with maxstep = dt */
    Evolve(dt, dt);
  }
  /**  Method to find the hue in a given region when we are coloring according to region (Color Mode 4).
   * @param TargetHue This is the target hue from the renderer.
   * @param r This is the vector postion of the point in the image.
   * @param RegionColor This is the varous hues for the regions.
   * @return The hue for the part of the image map at r.   
   * */
    public double getHue(double TargetHue, Vec3 r, Vec RegionColor){
    	double MyHue = 0;
	    return MyHue;}
    
    /**  Method to find the flow speed in a given region when we are determining that speed according to region.
     * This method is used when we have set experiment.FieldMotionType to one of either Constants.FIELD_MOTION_VREFIELD 
     * or Constants.FIELD_MOTION_VRBFIELD.
     * @param r This is the vector postion of the point in the image.
     * @param RegionFlow This is the flow speeds for the regions.
     * @return The flow speed for the part of the image map at r.   
     * */
    public double getFlowSpeed(Vec3 r, Vec RegionFlow) {
	    double MyFlowSpeed = 0;;
 		return MyFlowSpeed;	}
}
