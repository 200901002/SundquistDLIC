package simulations.experiments.fluidFlow;

import simulations.Constants;
import simulations.objects.BaseObject;
import simulations.objects.EMCollection;
import simulations.objects.ISMfield;
import simulations.objects.HelioField;
import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.experiments.BaseExperiment;

/** Heliosphere flow experiment.
 * 
 * @author John Belcher
 * @version 1.0
 */
public class HeliosphereFlowExperiment extends BaseExperiment{
    /**  The radius of the Bow Shock */
    private double radiusBowShock;
    /**  The radius of the termination shock. */
    private double radiusTerminationShock;
    /**  The position of the Sun.  */
    private Vec3 Xsun;
    /**  The time t */
    private double t;
    
    /** Constructs the heliosphere using two parameters
     * @param radiusBowShock Radius of the bow shock.
     * @param radiusTerminationShock Radius of the termination shock.   
     * @param Xsun The position of the sun.  */
    public HeliosphereFlowExperiment(double radiusBowShock, double radiusTerminationShock, Vec3 Xsun) {
     /* this experiment is a fluid flow experiment using electric EMobjects, so we set FieldType accordingly */
      this.FieldType = Constants.FIELD_EFIELD;
      /* this experiment is fluid flow with the flow speed determined by region, so set FieldMotionType accordingly */
  	  this.FieldMotionType = Constants.FIELD_MOTION_VREFIELD;
      this.radiusBowShock = radiusBowShock;
      this.radiusTerminationShock = radiusTerminationShock;
      this.Xsun = Xsun;
      t = 0.0;	    
      ConstructEMSource();
    }

    private ISMfield ifield;
    private HelioField hfield;
    /** The sum of these two flow fields. */
    private EMCollection collection;
    /** Construct the EMColletion object which is the sum of the two charges */
    public void ConstructEMSource(){
      ifield = new ISMfield(radiusTerminationShock, Xsun);
      hfield = new HelioField(radiusBowShock,Xsun);
      collection = new EMCollection();
      collection.Add(ifield);
      collection.Add(hfield);
    }
  
    /** Returns the BaseObject that is the collection of the two point charges */
    public BaseObject getEMSource(){
      return collection;
    }
 
    /** The equation of motion governing the evolution of the system */
    private class Motion extends VecTimeField {
    /** Given the state of the system p at time t, computes its first time derivatives and puts 
     * them in v, and returns v.  The vector p is the position (p.x[0]) and speed (p.x[1]) of the charge 
     * moving along the z-axis , v.x[0] = p.x[1], and  v.x[1] is the acceleration, and is computed
     * from the coulomb force between the two charges.
     * @param p the position (first location p.x[0]) and the speed (second location p.x[1]) of 
     * the moving charge.
     * @param t the time
     * @param v the derivative of the p vector--so v.x[0] is the speed and v.x[1] is the acceleration, as
     * calculated using Coubomb's law for the force betweeen the charges.   */ 
      public Vec get(Vec p, double t, Vec v){
    	  /* p is the position and speed of the moving charge along the z-axis */
    	  /* v is the derivative of these quantities */
        v.x[0] = 0.;  //p.x[1];
        v.x[1] = 0;  //q*q1/((Math.pow(Math.abs(p.x[0]-z1),2.)));  
        return v;
      }
    }
    
    /** The equation of motion for the system (just Coulomb repulsion) */
    private Motion equations = new Motion();
    /** The integrator used to evolve the system */
    private RungeKuttaIntegration integrator = new RungeKuttaIntegration();
  /** Evolves the experiment a time step dt */
    public void Evolve(double dt, double maxStep){
    integrator.SetStep(maxStep);
    /* Collect the current system coordinates into a vector of dependent variables for integration.*/
      Vec p = new Vec(2);
      p.x[0] = 0;
      p.x[1] = 0;
      /* Evolve the system by the time step dt */ 
      integrator.Evolve(equations, p, t, dt);
      /* Evolve advances p but does NOT advance t, so we advance it in the step below */
      t = t + dt;
      /* "p" now contains the system coordinates at the new time.  */
      /* Get the new first derivatives wrt t at their new values. */
      Vec dpdt = new Vec(2);
      equations.get(p, t, dpdt);
      /* update the state of the system with the new values */
     // z = p.x[0]; 
     // v = p.x[1];  
     // charge.p = new Vec3(0, 0, z);
     // charge.v = new Vec3(0, 0, v);
    }
   
    public double getHue(double TargetHue, Vec3 r, Vec RegionColor){
	    double MyHue = 0;;
	    double radius; double limit; double bs2;
	    bs2 = radiusBowShock*radiusBowShock;
	    radius = Math.sqrt(r.x*r.x+r.z*r.z);
	    limit = (2.-r.z*r.z/bs2)/Math.sqrt(4.-r.z*r.z/bs2);
	    MyHue = RegionColor.x[2];
		if (radius < radiusTerminationShock) MyHue = RegionColor.x[0];
		if (radius >= radiusTerminationShock && r.x >= -limit*radiusBowShock )   MyHue = RegionColor.x[1];
		if (radius >= radiusTerminationShock && r.x < -limit*radiusBowShock ) MyHue =  RegionColor.x[2];
	    return MyHue;}

   public double getFlowSpeed(Vec3 r, Vec RegionFlow) {
	    double MyFlowSpeed = 0;;
	    double radius; double limit; double bs2;
	    bs2 = radiusBowShock*radiusBowShock;
	    radius = r.len();
	    double xcomp, zcomp;
	    xcomp = (r.x-Xsun.x);
	    zcomp = (r.z-Xsun.z);
	    limit = (2.-zcomp*zcomp/bs2)/Math.sqrt(4.-zcomp*zcomp/bs2);
	    MyFlowSpeed = RegionFlow.x[2];
	    if (radius < radiusTerminationShock) MyFlowSpeed = RegionFlow.x[0];
	    if (radius >= radiusTerminationShock && xcomp >= -limit*radiusBowShock )   MyFlowSpeed = RegionFlow.x[1];
	    if (radius >= radiusTerminationShock && xcomp < -limit*radiusBowShock ) MyFlowSpeed =  RegionFlow.x[2];
   		return MyFlowSpeed;	}
   
    /** Evolves the experiment by a time step "dt" using an RK4 integrator by taking numberSmallSteps between
     * t and t + dt, for accuracy. */
    public void Evolve(double dt){
  	  /* we simply call the Evolve(double dt,double maxStep) method with maxstep = dt/numberSmallSteps. */
	      Evolve(dt, dt/numberSmallSteps);
	    }
    
    /** A local way to print a string */ 
  	private static void println(String s){
    	System.out.println(s);
  	}
}
