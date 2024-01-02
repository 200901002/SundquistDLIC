package simulations.experiments.electrostatics;

import simulations.Constants;
import simulations.objects.BaseObject;
import simulations.objects.EMCollection;
import simulations.objects.ColorTestField;
import simulations.objects.PointCharge;
import core.field.VecTimeField;
import core.math.RungeKuttaIntegration;
import core.math.Vec;
import core.math.Vec2;
import core.math.Vec3;
import simulations.experiments.BaseExperiment;

/** Color Test exeriment with a simple field
 * 
 * @author John Belcher
 * @version 1.0
 */
public class ColorTestExperiment extends BaseExperiment{
    /**  The value at the origin of the color test field */
    private double ACT;
    /**  The slope of the color test field */
    private double BCT;
    /**  The time t */
    private double t;
    
    /** Constructs the color test field  
     * @param ACT the value of the z component of the field at the origin
     * @param BCT the slope of the z component of the field */ 
    public ColorTestExperiment(double ACT, double BCT) {
      /* this experiment is electro-quasi-statics, so we set FieldType accordingly */
      this.FieldType = Constants.FIELD_EFIELD;
      /* this experiment is electro-quasi-statics, so we set FieldMotionType accordingly */
      this.FieldMotionType = Constants.FIELD_MOTION_EFIELD;
      this.ACT = ACT;
      this.BCT = BCT;
      t = 0.0;	    
      ConstructEMSource();
    }

    /** The first charge, which moves */
    private ColorTestField ctf;
    private EMCollection collection;
    /** Construct the EMColletion object  */
    public void ConstructEMSource(){
      ctf = new ColorTestField(ACT,BCT);
      collection = new EMCollection();
      collection.Add(ctf);
    }
  
    /** Returns the BaseObject that is colortest field alone */
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
        //v.x[0] = p.x[1];
        // v.x[1] = q*q1/((Math.pow(Math.abs(p.x[0]-z1),2.)));  
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
     // p.x[0] = z;
     // p.x[1] = v;
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
    
public double getHue(double TargetHue, Vec2 xpos, Vec RegionColor, Vec RegionParameter ){
     return TargetHue;}

public double getFlowSpeed(Vec3 r, Vec RegionFlow, Vec RegionParameter) {
	return 0.;	}

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
