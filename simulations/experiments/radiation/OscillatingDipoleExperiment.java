package simulations.experiments.radiation;   

import core.math.Vec;
import core.math.Vec3;
import simulations.experiments.*;
import simulations.objects.*;
import simulations.Constants;

/** 
 *   Fields of an oscillating electric dipole.
 *
 *   This class computes the evolution of the experiment in which a 
 *   electric dipole is oscillating, creating waves that flow out from the
 *   dipole at the speed of light.  We include near zone, intermediate zone, and 
 *   far zone terms in the expressions for the electric field.
 *   @author Andreas Sundquist
 *   @version 1.0
 */
public class OscillatingDipoleExperiment extends BaseExperiment {

/** The constant part of the dipole moment of the oscillating dipole */	
  private double p0;
  /** The sinusoidal part of the dipole moment of the oscillating dipole */	
  private double p1;
  /** The angular frequency of the oscillating dipole */	
  private double omega;
  /** The phase shift in the sinusoidal time depedence of the oscillating dipole */	
  private double phase;
  
  /** Constructs an instance of the experiment where the dipole
   *   has a constant moment "p0" added to an oscillation of
   *   amplitude "p1" at a frequency "omega". */ 
  public OscillatingDipoleExperiment(double p0, double p1, double omega, double phase){
	/* this experiment is electro-quasi-statics, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_EFIELD;
	/* this experiment is electro-quasi-statics, so we set FieldMotionType accordingly */
	this.FieldMotionType = Constants.FIELD_MOTION_EFIELD;
    this.p0 = p0;
    this.p1 = p1;
    this.omega = omega;
    this.phase = phase;
    
    ConstructEMSource();
  }
 
  private ElectricOscillatingDipole dipole;
  /** Creates the EMSource that represents the experiment and can be used to
   *   compute the E&M fields */ 
  public void ConstructEMSource(){
    dipole = new ElectricOscillatingDipole(new Vec3(0,0,0), new Vec3(0,0,p0),p0,p1,omega,phase);
  }
  /** Returns: an EMSource that represents the current experimental state.
   *   It can be used to compute the E&M fields.
   * The dipole is centered at the origin, and its direction is along the
   *   z-axis. */ 
  public BaseObject getEMSource(){
    return dipole;
  }
  /** Evolves the experiment by a time step "dt". */ 
  public void Evolve(double dt){
    /* evolve the dipole by an amount dt */
    dipole.Evolve(dt);
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
