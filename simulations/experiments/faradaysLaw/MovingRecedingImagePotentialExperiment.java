package simulations.experiments.faradaysLaw;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;
import simulations.Constants;
/** Receeding Image experiment, consisting of a monopole appearing at t =0
 * above a conducting plane, using the method of Saslow.
 * This object computes the evolution of the system and also generates
 * an EMSource that computes the E&M fields of the system. The evolution
 * is computed in terms of dimensionless variables and then converted to 
 * "real" units.  This version of the program calculates the magnetic fields 
 * using the magnetic potential function instead of 
 * monopoles and line dipoles (see equation (8) of Liu and Belcher 2007).  
 * @author John Belcher 
 * @version 1.0
 */
public class MovingRecedingImagePotentialExperiment extends BaseExperiment {
  /** H = height of the monople above z = 0 */
  public double H;
  /** the time  */
  public double t;
  /** offset is an overall time offset before the image charges start moving */
  public double offset;
  /** zplane is the location in z of the conducting thin sheet */
  public double zplane;
  /** q0 is the magnetic charge */
  public double q0;
  /** v0 is the vertical speed of the receeding monopoles once they start receeding*/
  public double v0;
  /** v is the horizontal speed of the monopole */
  public double v;
  /** Constructs an instance of the experiment using the given parameters.
   *   @param H the height of the monopole above z = 0
   *   @param offset the frame offset before image charges begin moving
   *   @param q0 the magnetic charge
   *   @param v the horizontal speed of the monopole
   *   @param v0 the speed of the receeding image 
   *   @param zplane the height of the conducting plane above z = 0 */
 
  
  public MovingRecedingImagePotentialExperiment(double H, double q0, double v0, double v,  double zplane, double offset){
    /* this experiment is magneto-quasi-statics, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
    /* this experiment is magneto-quasi-statics, so we set FieldMotionType accordingly */
    this.FieldMotionType = Constants.FIELD_MOTION_BFIELD;
    this.offset = offset;  
    this.q0 = 1.;
    this.H = H;
    this.v0 = v0;
    this.v = v;
    this.zplane = zplane;
    ConstructEMSource();
  }
 
  /** the real magnetic monopole */
  public MagneticMonopole mono;
  /** the potential whose field vanishes for z < 0 */
  public MovingRecedingImagePotentialAbove above;
  /** the potential whose field vanishes for z > 0 */
  public MovingRecedingImagePotentialBelow below;

  /** the collection of the monopole plus the two potential function */
  private EMCollection collection;
  public void ConstructEMSource(){
	
    mono = new MagneticMonopole(q0, new Vec3(0., 0., H),new Vec3(0., 0., 0.),zplane,0,0.);
    above = new MovingRecedingImagePotentialAbove(H, q0, v0, v,  zplane, offset);
    below = new MovingRecedingImagePotentialBelow(H, q0, v0, v,  zplane, offset);
    collection = new EMCollection();
    collection.Add(mono);
    collection.Add(above);
    collection.Add(below);
    
    //print out a number of values so we can check the field calculation
    Vec3 B = new Vec3(0.,0.,0.);
	Vec3 x = new Vec3(10.,0.,4.);
	double circ = 1.;
	int number = 1;
    double ratio = 0.;
    double ang1 = 0.;
    double ang2 = 0.;
    int printout = 1;
    if ( printout == 0 ) {
    for  (int i=1; i <= number; i++) {
    ang1 = 60.;  //(i-1)*360./number;  
    ang2 = ang1*Math.PI/180.;
    x.Set(circ*Math.cos(ang2),0.,circ*Math.sin(ang2));	
    above.Bfield(x, B);
    ratio = B.z/B.x;
    //mono.image = 0;
    System.out.println(" ang " + ang1 + "  x" + x.x +"   z " + x.z + " ratio " + ratio + "  bx" + B.x + "  bz  "+ B.z );
    }
    }

  }
  
  /** An EMSource that represents the current experimental state that
   *  can be used to compute the current E&M fields.
   *  The real monopole is at rest at (0,0,H), the two image dipoles are initially
   *  at rest at (0,0,+H) (imageminus)  and (0,0,-H) (imageplus) and then start moving up (imageminus) and up 
   *  (imagrminud) the z-axis at frames > offsett */
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
      /*  p.x[0] is the vertical position of monoplus */
      /*  p.x[1] is the vertical position of monominus*/
      /*  v is the derivative of these parameters */
      if( t >= offset)  v.x[0] = -v0;
      else v.x[0] = 0.;
      if( t >= offset)  v.x[1] = +v0;
      else v.x[1] = 0.;
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
	  double zplus, zminus;
	  double ang3 = 0.;
    integrator.SetStep(maxStep);
    /* Collect the current system coordinates into a vector of dependent variables for integration.*/
    Vec p = new Vec(3);
    //zplus = monoplus.p.z;
    //zminus = monominus.p.z;
   // p.x[0] = zplus;
    //p.x[1] = zminus;
    /* Evolve the system by the dimensionless time step */ 
   // integrator.Evolve(equations, p, t, dt);
    /* Evolve advances p but does NOT advance t, so we advance t in the step below */
    t = t + dt;
    v = v + dt;
    double mlength = 0.;
    double mlengthx = 0.;
    double mlengthz = 0.;
    double qimage = 0.;
    qimage = q0*v*v/(v*v+v0*v0);
    mlength = q0*v*v0/(v*v+v0*v0);
    mlengthx = mlength*(v0/Math.sqrt(v*v+v0*v0));
    mlengthz = -mlength*(v/Math.sqrt(v*v+v0*v0));
    System.out.println(mlength + " FGHJKL " + mlengthx + " " + mlengthz);
    below.v = v;
    above.v = v;
  //  monoplus.q=qimage;
  //  lineminus.mdipole = new Vec3(-mlengthx,0.,mlengthz);
  //  lineminus.tline = new Vec3(-v,0.,v0);
 //  monominus.q=-qimage;
    Vec dpdt = new Vec(2);
    //equations.get(p, t, dpdt);
    System.out.println("From experiment: time  " + t + " v " + v + " "+ qimage);
    /* Update the state of the falling ring object to reflect the new state.  Note that the "point dipole"
     * does not change with time, so no updating is necessary. */
    //monoplus.p = new Vec3(0, 0, p.x[0]);
    //monominus.p = new Vec3(0, 0, p.x[1]);
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
