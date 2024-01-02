package simulations.experiments.electrostatics;

import simulations.Constants;
import simulations.objects.BaseObject;
import simulations.objects.EMCollection;
import simulations.objects.MovingPointCharge;
import simulations.objects.PointCharge;
import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.experiments.BaseExperiment;

/** Electro-quasi-statics exeriment with one moving charge and one stationary charge.
 * 
 * @author Michael Danziger
 * @version 1.0
 */
public class TwoChargesExperiment extends BaseExperiment{
    /**  The charge of the first charge, which moves */
    private double q;
    /**  The position of charge one along the z axis */
    private double z;
    /**  The speed of charge one along the z axis */
    private double v;
    /**  The charge of the second charge */
    private double q1;
    /**  The position of the second charge along the z axis */
    private double z1;
    /**  The time t */
    private double t;
    
    /** Constructs the two charges with their initial positions 
     * @param q the charge of the first charge 
     * @param m the mass of the first charge 
     * @param z0 the position of the first charge along the z axis
     * @param v0 the speed of the first charge along the z axis
     * @param q1 the charge of the second stationary charge 
     * @param z1 the position of the second charge */ 
    public TwoChargesExperiment(double q, double m, double z0, double v0, double q1, double z1) {
      /* this experiment is electro-quasi-statics, so we set FieldType accordingly */
      this.FieldType = Constants.FIELD_EFIELD;
      /* this experiment is electro-quasi-statics, so we set FieldMotionType accordingly */
      this.FieldMotionType = Constants.FIELD_MOTION_EFIELD;
      this.q = q;
      this.z = z0;
      this.v = v0;
      this.q1 = q1;
      this.z1 = z1;
      t = 0.0;	    
      ConstructEMSource();
    }

    /** The first charge, which moves */
    private MovingPointCharge charge;
    /** The second charge, which does not move */
    private PointCharge charge1;
    /** The sum of these two charges */
    private EMCollection collection;
    /** Construct the EMColletion object which is the sum of the two charges */
    public void ConstructEMSource(){
      charge = new MovingPointCharge(q, new Vec3(0,0,z), new Vec3(0,0,v));
      charge1 = new PointCharge(q1, new Vec3(0,0,z1));
      collection = new EMCollection();
      collection.Add(charge);
      collection.Add(charge1);
      System.out.println("charge " + charge );
      System.out.println("charge1 " + charge1 );
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
        v.x[0] = p.x[1];
        v.x[1] = q*q1/((Math.pow(Math.abs(p.x[0]-z1),2.)));  
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
      p.x[0] = z;
      p.x[1] = v;
      /* Evolve the system by the time step dt */ 
      integrator.Evolve(equations, p, t, dt);
      /* Evolve advances p but does NOT advance t, so we advance it in the step below */
      t = t + dt;
      /* "p" now contains the system coordinates at the new time.  */
      /* Get the new first derivatives wrt t at their new values. */
      Vec dpdt = new Vec(2);
      equations.get(p, t, dpdt);
      /* update the state of the system with the new values */
      z = p.x[0]; 
      v = p.x[1];  
      charge.p = new Vec3(0, 0, z);
      charge.v = new Vec3(0, 0, v);
    }
    
    /** Evolves the experiment by a time step "dt" using an RK4 integrator by taking numberSmallSteps between
     * t and t + dt, for accuracy. */
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
