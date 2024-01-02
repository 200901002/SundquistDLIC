package simulations.experiments.faradaysLaw;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;
import simulations.Constants;
/** Receeding Image experiment, consisting of a monopole appearing at t =0
 * in the first quadrant of two perpendicular thin conducting planes.
 * This object computes the evolution of the system and also generates
 * an EMSource that computes the E&M fields of the system. The evolution
 * is computed in terms of dimensionless variables and then converted to 
 * "real" units.
 * <p>
 * @author John Belcher 
 * @author Yao Liu 
 * @version 1.0
 */
public class TwoPlanesExperiment extends BaseExperiment {
  /** Location of the monopole (k,0,h)*/
  public double k;
  public double h;
  /** the time  */
  public double t;
  /** offset is an overall time offset before the image charges start moving */
  public double offset;
  /** q0 is the magnetic charge */
  public double q0;
  /** v1 is the receeding velocity associated with x=0 plane*/
  public double v1;
  /** v1 is the receeding velocity associated with z=0 plane */
  public double v2;
  /** Constructs an instance of the experiment using the given parameters.
   *   @param k the horizontal displacement of the monopole from the vertical plane
   *   @param h the vertical displacement of the monopole from the horizontal plane
   *   @param offset the frame offset before image charges begin moving
   *   @param q0 the magnetic charge
   *   @param v1 the receding image speed for the vertical plane
   *   @param v2 the receding image speed for the horizontal plane 
   *   @param t the time  */
  
  public TwoPlanesExperiment(double k, double h, double q0, double v1, double v2, double t, double offset){
	  /* this experiment is magneto-quasi-statics, so we set FieldType accordingly */
	  this.FieldType = Constants.FIELD_BFIELD;
	  /* this experiment is magneto-quasi-statics, so we set FieldMotionType accordingly */
	  this.FieldMotionType = Constants.FIELD_MOTION_BFIELD;
	  this.offset = offset;  
	  this.q0 = q0;
	  this.k = k;
	  this.h = h;
	  this.v1 = v1;
	  this.v2 = v2;
	  this.t=t;
	  ConstructEMSource();
  }
 
  /** the real magnetic monopole */
  public MagneticMonopole mono;
  public TwoPlanes twoPlanes;
  private EMCollection collection;
 
  /** Constructs the EMCollection representing the experiment  */
  public void ConstructEMSource(){
	 mono = new MagneticMonopole(q0, new Vec3(k, 0., h), new Vec3(0., 0., 0.), 0., 0, 0.);
	 twoPlanes = new TwoPlanes(k, h, q0, v1, v2, t, offset);
	 collection = new EMCollection();
	 collection.Add(mono);
	 collection.Add(twoPlanes);
    }
  
  /** An EMSource that represents the current experimental state that
   *  can be used to compute the current E&M fields. */
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
    	/* twoPlanes does its own evolution */
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
    /* Evolve advances p but does NOT advance t, so we advance t in the step below */
    t = t + dt;
    twoPlanes.t = t;
    System.out.println("From experiment: t: " + t + "   v1: " + v1 + "   v2:" + v2);
  }
  
  /** Evolves the experiment by a time step "dt" using an RK4 integrator by taking numberSmallSteps between
   * t and t + dt, for accuracy.
   */
  public void Evolve(double dt){
	  /* we simply call the Evolve(double dt,double maxStep) method */
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
