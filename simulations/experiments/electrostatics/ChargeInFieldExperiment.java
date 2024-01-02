package simulations.experiments.electrostatics;

import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec3;
import simulations.Constants;
import simulations.objects.ConstantFields;
import simulations.objects.EMCollection;
import simulations.objects.BaseObject;
import simulations.objects.MovingPointCharge;
import simulations.experiments.BaseExperiment;
/**
 * Experiment to show the electric field motion for an electric charge 
 * moving in a constant background electric field.  Extends BaseExperiment.  We provide a method getHue
 * which allows us to color the field lines connected to the charge a different color than
 * the field lines connected to the charges which produce the constant field. */
 
public class ChargeInFieldExperiment extends BaseExperiment {
	/** electric field */
    private double E;
    /** charge of the particle */
    private double q;
    /** mass of the particle */
    private double m;
    /** z position of the particle */
    private double z;
    /** z velocity of the particle */
    private double v;
    /** time */
    private double t;

    /**
     * constructor for charge in field experiment
     * 
     * @param E background electric field
     * @param q charge
     * @param m mass of charge
     * @param z0 initial position of charge at t  = 0
     * @param v0 initial velocity of charge at t = 0
    */
  
    public ChargeInFieldExperiment(double E, double q, double m, double z0, double v0)
    {
      /* this experiment is electro-quasi-statics, so we set FieldType accordingly */
      this.FieldType = Constants.FIELD_EFIELD;
      /* this experiment is electro-quasi-statics, so we set FieldMotionType accordingly */
      this.FieldMotionType = Constants.FIELD_MOTION_EFIELD;
      this.E = E;
      this.q = q;
      this.m = m;
      this.z = z0;
      this.v = v0;
      t = 0.0; 
      ConstructEMSource();
    }

    /** the charge (initially moving upward) */  
    private MovingPointCharge charge;
    /** the electric field pointing downward */
    private ConstantFields fields;
    /** collection of EM objects consisting of the charge and the constant electric field */
    private EMCollection collection;
    
    /**constructs the EM source consisting of the charge and the constant field */
    public void ConstructEMSource()
    {
      charge = new MovingPointCharge(q, new Vec3(0,0,z), 5.,new Vec3(0,0,v));
      fields = new ConstantFields(new Vec3(0,0,E), new Vec3(0,0,0));
      collection = new EMCollection();
      collection.Add(charge);
      collection.Add(fields);
    }
    /** returns the collecton of charge and constant field as the EM source */
    public BaseObject getEMSource()
    {
      return collection;
    }
    
    /** 
     * the equation of motion for a charge in an electric field
     */
    public class Motion extends VecTimeField {
      public Vec get(Vec p, double t, Vec v)
      {
        v.x[0] = p.x[1];
        v.x[1] = (q/m)*E;
        return v;
      }
    }
    
    /** equation of motion of the particle*/
    private Motion equations = new Motion();
    /** integrator for the equation of motion of the particle*/
    private RungeKuttaIntegration integrator = new RungeKuttaIntegration();
  
    /** 
     * method to evolve the particle motion in time
     */
    public void Evolve(double dt)
    {
      Vec p = new Vec(2);
      p.x[0] = z;
      p.x[1] = v;
      // we do not integrate but use an analytic form for the motion 
      integrator.Evolve(equations, p, 0, dt);
      t = t+dt;
      double ts;
      if (t < 6.0) ts = t; else ts = t-6.0;
      v = -100*ts + 300;
      z= -.5*100.*ts*ts+300*ts-450 ; 
      charge.p = new Vec3(0, 0, z);
      charge.v = new Vec3(0, 0, v);
      println(charge.toString());
    }
    
    /** prints the string s */
    private static void println(String s)
    {
      System.out.println(s);
    }
  /**  Method to find the hue in a given region when we are coloring according to region (Color Mode 4).
   * @param TargetHue This is the target hue from the renderer.
   * @param r This is the vector postion of the point in the image.
   * @param RegionColor This is the varous hues for the regions.
   * @return The hue for the part of the image map at r.   
   * */
    public double getHue(double TargetHue, Vec3 r, Vec RegionColor){
    	double MyHue = 0;
    	double bs = Math.pow(q*Constants.Efactor/Math.abs(E),0.5);
    	Vec3 position = r.Sub(charge.p);
    	double xcomp = position.x;
    	double zcomp = position.z;
    	MyHue = RegionColor.x[1];
    	if(Math.abs(xcomp)>=2.*bs) MyHue = RegionColor.x[1];
    	else 
    	{
	         if (zcomp < bs*(2.-xcomp*xcomp/(bs*bs))/Math.sqrt(4.-xcomp*xcomp/(bs*bs))) MyHue = RegionColor.x[0];
    	}
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
