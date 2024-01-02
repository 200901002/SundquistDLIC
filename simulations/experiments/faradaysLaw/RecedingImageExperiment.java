package simulations.experiments.faradaysLaw;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;
import simulations.Constants;
/** Receeding Image experiment calculates the magnetic fields when a monopole appears at <i> t = 0 </i>
 * above a conducting plane.  The field of the monopole subsequently diffuses into the region
 * below the conducting plane, using the method of Saslow and Maxwell.
 * This object computes the evolution of the system and also generates
 * an EMSource that computes the E&M fields of the system. 
 * @author John Belcher 
 * @version 1.0
 */
public class RecedingImageExperiment extends BaseExperiment {
  /** The height of the monople above z = 0. */
  public double H;
   /** The time.  */
  public double t;
  /** The overall time offset before the image charges start moving. */
  public double offset;
  /** The magnetic charge. */
  public double q0;
  /** The speed of the receeding monopoles once they start receeding. */
  public double v0;
  /** Constructs an instance of the experiment using the given parameters.
   *   @param H The height of the monopole above z = 0.
   *   @param q0 The monopole charge.
   *   @param offset The frame offset before image charges begin moving.
   *   @param v0 The speed of the receding charge once it starts receding. */
  public RecedingImageExperiment(double H, double q0, double v0, double offset){
    /* this experiment is magneto-quasi-statics, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
    /* this experiment is magneto-quasi-statics, so we set FieldMotionType accordingly */
    this.FieldMotionType = Constants.FIELD_MOTION_BFIELD;
    this.offset = offset;  
    this.q0 = q0;
    this.H = H;
    this.v0 = v0;
    ConstructEMSource();
  }
 
  /** The real magnetic monopole. */
  public MagneticMonopole mono;
  /** The monopole whose field vanishes for z < 0. */
  public MagneticMonopole monoplus;
  /** The monopole whose field vanishes for z > 0. */
  public MagneticMonopole monominus;
  /** The collection of the three monopoles. */
  private EMCollection collection;
 
  /** Constructs the EMCollection representing the experiment consisting of the three monopoles, 
   * the real monopole and the two receeding image monopoles */
  public void ConstructEMSource(){
    mono = new MagneticMonopole(q0, new Vec3(0., 0., H),new Vec3(0., 0., 0.), 0.,0, 0.);
    monoplus = new MagneticMonopole(q0, new Vec3(0., 0., -H),new Vec3(0., 0., 0.), 0.,-1, 0.);
    monominus = new MagneticMonopole(-q0, new Vec3(0., 0., H),new Vec3(0., 0.,0.), 0.,1, 0.);
    collection = new EMCollection();
    collection.Add(mono);
    collection.Add(monoplus);
    collection.Add(monominus);
  }
  
  
  /** An EMSource that represents the current experimental state that
   *  can be used to compute the current E&M fields.
   *  The real monopole is at rest at (0,0,H), the two image dipoles are initially
   *  at rest at (0,0,+H) (imageminus)  and (0,0,-H) (imageplus) and then start moving up (imageminus) and down 
   *  (imageminus) the z-axis at frames > offset. */
  public BaseObject getEMSource() {
    return collection;
  }
  
  /** Evolves the experiment by a time step "dt" using an RK4 integrator   */
  public void Evolve(double dt){
	  /* we simply call the Evolve(double dt,double maxStep) method */
    Evolve(dt, dt);
  }

  /* Define the integraton scheme used to numerically integrate the evolution equations in time. */
  private RungeKuttaIntegration integrator = new RungeKuttaIntegration();
  /* Define the evolution equations used by the above RK4 integrator */
  public Motion equations = new Motion();  

  /** Evolve the experiment by a time step "dt" using an integrator.
   *   The maximum integrator step size allowed is "maxStep".
   *   The state of the EMSource representing the system is updated to
   *   reflect the change. 
   *   @param maxStep The maximum step allowed. 
   *   @param dt The time step. */  
  public void Evolve(double dt, double maxStep){
    /* Collect the current system coordinates into a vector of dependent variables for integration.*/
    Vec p = new Vec(2);
    Vec dpdt = new Vec(2);
    p.x[0] = monoplus.p.z;
    p.x[1] = monominus.p.z;
    /* Evolve the system by the dimensionless time step */ 
    integrator.SetStep(maxStep);
    integrator.Evolve(equations, p, t, dt);
    /* Evolve advances p but does NOT advance t, so we advance t in the step below */
    t = t + dt;
    /* "p" now contains the system coordinates at the new time.  */
    /* Get the new first derivatives wrt t at their new values. */
    equations.get(p, t, dpdt);
    System.out.println("From experiment: time  " + t + " z-position monoplus "+ p.x[0] +  " z-position monominus "+ p.x[1] );
    /* Update the state of the image monopoles to reflect the new state.  Note that the monopole position
     * does not change with time, so no updating is necessary. */
    monoplus.p.z = p.x[0];
    monoplus.v.z = dpdt.x[0];
    monominus.p.z = p.x[1];
    monominus.v.z = dpdt.x[1];
  }
  
  
  public class Motion extends VecTimeField {
   /** Given the vector representing the dependent variables of the experiment and the time, returns
   *   the time derivatives of those variables.  
   *   @param p The vector of parameter values which describe the experiment at any time t.  In this case,  
   *   p.x[0] is the vertical position of monoplus and p.x[1] is the vertical position of monominus. 
   *   @param t The time t.
   *   @param v Time derivatives of the vector of parameter values (calculated).  Note that in this case the
   *   derivatives do not depend on the positions p at all, but just on the time t.  
   *   @return The time derivatives of the vector of parameter values. */
    public Vec get(Vec p, double t, Vec v){
      if( t > offset)  v.x[0] = -v0;
      else v.x[0] = 0.;
      if( t > offset)  v.x[1] = +v0;
      else v.x[1] = 0.;
      return v;
    }
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
