package simulations.experiments.faradaysLaw;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;
import simulations.Constants;
/** Falling Ring experiment, consisting of a ring falling past a stationary magnet.
 * This object computes the evolution of the system and also generates
 * an EMSource that computes the E&M fields of the system. The evolution
 * is computed in terms of dimensionless variables and then converted to 
 * "real" units.
 * <p>
 * Documentation for the evolution equations describing this system can be found in the
 * <a href="C:\Development\Projects\SundquistDLIC\DLICdoc\TEAL_Physics_Math.pdf"> 
 * TEAL_Physics_Math document </a> (see Section 4.1).  This link will work if you have installed
 * the <i>SundquistDLIC</i> code base and documentation as instructed. 
 * @author Andreas Sundquist 
 * @version 1.0
 */
public class FallingRingExperiment extends BaseExperiment {
  /** R = radius of the ring */
  public double R;
   /** the distance scale factor, which is the radius of the ring */
  public double z0;
  /** the time scale factor, see TEAL_Physics_Math document  */
  public double t0;
  /** the current scale factor, see TEAL_Physics_Math document */
  public double I0;
   /** the dimensionless height of the ring above the magnet */
  public double z;
   /** the time in dimensionless units */
  public double t;
  /** the current in dimensionless units */
  public double I;
  /** the velocity in dimensionless speed units = z0/t0 */
  public double v;
  /** the time derivative of the current in dimensionless current units = I0/t0 */
  public double dIdt;
  /** parameter of the experiment as defined in the TEAL_Physics_Math reference  */
  public double alpha, beta, lambda;
  /** offset is an overall offset of the entire experiment along the z-axis */
  public double offset;
  /** M0 = dipole moment of the "point dipole" */
  public double M0;
  
  /** Constructs an instance of the experiment using the given parameters.
   *   @param R the radius of the ring in real units
   *   @param relH the initial height/R of the falling ring 
   *   @param alpha experimental parameter as defined in TEAL_Physics_Math document Section 4.1.3 
   *   @param beta experimental parameter as defined in TEAL_Physics_Math document Section 4.1.3 
   *   @param offset the distance that the magnet is offset from the origin */
  public FallingRingExperiment(double R, double relH, double alpha, double beta, double offset){
    /* this experiment is magneto-quasi-statics, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
    /* this experiment is magneto-quasi-statics, so we set FieldMotionType accordingly */
    this.FieldMotionType = Constants.FIELD_MOTION_BFIELD;
    this.alpha = alpha;
    this.beta = beta;
    this.lambda = 2.0;
    this.offset = offset;  
    this.M0 = 1.;
    this.R = R;
    this.t0 = 1.;
    this.z0 = R;
    this.I0 = 1.0/(beta*lambda*R*R); 
    this.z = relH;
    this.t = 0.0;
    this.I = 0.0;
    this.dIdt = 0.0;
    this.v = 0.0;
    /*  Take 100 steps for every t to t + dt to make sure we get the integration correctly.  */
    this.numberSmallSteps = 100;
    ConstructEMSource();
  }
 
  /** the current ring representing the ring */
  public CurrentRing ring;
  /** the current ring representing the point dipole */
  public CurrentRing dipole;
  /** the collection of the two rings */
  private EMCollection collection;
 
  /** Constructs the EMCollection representing the experiment consisting of the two rings of current
   * with the given initial conditions */
  public void ConstructEMSource(){
	Vec3 axis = new Vec3(0.,0.,1.);
	/* create the falling ring */
    ring = new CurrentRing(new Vec3(0., 0., z*R+offset), axis, R, I*I0, new Vec3(0., 0., v*R));
    /* define the radius of the "point dipole" to be 1/15 of the radius of the ring */
    double  Rdipole = R/15.;
    /* given the assumed radius of the "point dipole", calculate the current necessary for 
     * it to have the given magnetic dipole moment M0 */
    double  IdipoleCurrent = M0/(Math.PI*Rdipole*Rdipole);
    /* create the "point dipole" */
    dipole = new CurrentRing(new Vec3(0., 0., offset), axis, Rdipole, IdipoleCurrent);
    collection = new EMCollection();
    collection.Add(ring);
    collection.Add(dipole);
  }
  
  /** An EMSource that represents the current experimental state that
   *  can be used to compute the current E&M fields.
   *  The dipole is centered at the origin with its axis pointing
   *  in the z-direction.  The current ring falls down the z-axis. 
   *  @return EMCollection object that consists of the two rings of current */
  public BaseObject getEMSource() {
    return collection;
  }
  
  /** Specifies the time derivative of the parmeters which describe the
   *   experiment at any time t.  The parameters are the  
   *   (ring height, ring current, ring velocity) all measured in 
   *   dimensionless units.  The vector "p" specifies the values of these parameters,
   *   and the vector "v" specifies the time derivative of the values of these parameters. */ 
  public class Motion extends VecTimeField {
   /** Given the vector representing the dependent variables of the experiment and the time, returns
   *   the time derivatives of those variables.  See equations (4.1.3.9) - (4.1.3.11) in the
   *   <a href="C:\Development\Projects\SundquistDLIC\DLICdoc\TEAL_Physics_Math.pdf"> 
   *   TEAL_Physics_Math document </a>.  
   *   @param p the vector of parameter values which describe the experiment at any time t
   *   @param t the time t
   *   @param v time derivatives of the vector of parameter values (calculated)
   *   @return the time derivatives of the vector of parameter values */
    public Vec get(Vec p, double t, Vec v){
      /*  p.x[0] is the vertical distance between the magnet and ring, normalized */
      /*  p.x[1] is the current in the ring, normalized */
      /*  p.x[2] is the speed of the magnet, normlized */
      /*  v is the derivative of these parameters */
      double F = 1.5*p.x[0]/Math.pow(1.0 + p.x[0]*p.x[0], 2.5);
      v.x[0] = p.x[2];
      v.x[1] = -alpha*p.x[1] + beta*F*p.x[2];
      v.x[2] = -1.0 - F*p.x[1];    
      return v;
    }
  }
  
  /** Define the evolution equations used by the RK4 integrator */
  public Motion equations = new Motion();
  
  /** define the integraton scheme used to numerically integrate the evolution equations in time */
  private RungeKuttaIntegration integrator = new RungeKuttaIntegration();
  
  /** Evolve the experiment by a time step "dt" using an integrator.
   *   The maximum integrator step size allowed is "maxStep".
   *   The state of the EMSource representing the system is updated to
   *   reflect the change. 
   *   @param maxStep the maximum step allowed 
   *   @param dt the time step */  
  public void Evolve(double dt, double maxStep){
    integrator.SetStep(maxStep);
    /* Collect the current system coordinates into a vector of dependent variables for integration.*/
    Vec p = new Vec(3);
    p.x[0] = z;
    p.x[1] = I;
    p.x[2] = v;
    /* Evolve the system by the dimensionless time step */ 
    integrator.Evolve(equations, p, t, dt/t0);
    /* Evolve advances p but does NOT advance t, so we advance t in the step below */
    t = t + dt/t0;
    /* "p" now contains the system coordinates at the new time.  */
    /* Get the new first derivatives wrt t at their new values. */
    Vec dpdt = new Vec(3);
    equations.get(p, t, dpdt);
    z = p.x[0];
    I = p.x[1];
    v = p.x[2];
    dIdt = dpdt.x[1];
    System.out.println("From experiment: time  " + t + " z-position "+ z  );
    /* Update the state of the falling ring object to reflect the new state.  Note that the "point dipole"
     * does not change with time, so no updating is necessary. */
    ring.p = new Vec3(0, 0, z*R+offset);
    ring.v = new Vec3(0, 0, v*R/t0);
    ring.I = I*I0;
    ring.dIdt = dIdt*I0/t0;
  }
  
  /** Evolves the experiment by a time step "dt" using an RK4 integrator by taking numberSmallSteps between
   * t and t + dt, for accuracy.
   */
  public void Evolve(double dt){
	  /* we simply call the Evolve(double dt,double maxStep) method with maxstep = dt/numberSmallSteps. */
    Evolve(dt, dt/numberSmallSteps);
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
