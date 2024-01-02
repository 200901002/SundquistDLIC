package simulations.experiments.radiation;
import core.math.Vec;
import core.math.Vec3;
import simulations.Constants;
import simulations.objects.*;
import simulations.experiments.BaseExperiment;

public class ElectricAntennaExperiment extends BaseExperiment {
	  
/** 
 *   Fields of an electric antenna with a finite length.
 *   
 *   @author Norman Deby
 *   @version 1.0
 */
  /** The product of the length of the antenna and the wavenumber divided by 2.
   * This is also the product of pi times the length of the antenna divided by the wavelength.
   * For a "resonant antenna" the wavelength is twice the length of the antenna and ak is pi/2.   */	
  private double ak;
  /** The wavenumber of the radiation (the wavelength is 2 pi/k).  */
  private double k;
  /** The angular frequency of the radiation (the period is 2 pi/w).    */
  private double w;
  /** The time.*/
  public double t;
  
  /**
   * constructor for linear antenna experiment
   * 
   * @param k The wavenumber of the radiation, that is, 2 pi / wavelength.  
   * @param ak The product of pi times the length of the antenna divided by the wavelength of the readiation.
   * @param w The angular frequencey of the radiation.
  */
  
  public ElectricAntennaExperiment(double k, double ak, double w)
  /* Constructs an instance of the linear antenna */
	  {
		/* this experiment is electro-quasi-statics, so we set FieldType accordingly */
		this.FieldType = Constants.FIELD_EFIELD;
		/* this experiment is electro-quasi-statics, so we set FieldMotionType accordingly */
		this.FieldMotionType = Constants.FIELD_MOTION_EFIELD; 
	    this.k = k;
	    this.ak = ak;
	    this.w = w;
	    t = 0.0;
	    
	    ConstructEMSource();
	  }
	  

	  private ElectricAntenna quarterWaveAntenna;
	  
	  public void ConstructEMSource()
	  /* Creates the EMSource that represents the experiment and can be used to
	   *   compute the E&M fields */
	  {
	    quarterWaveAntenna = new ElectricAntenna(new Vec3(0,0,0),k,ak,w,t);
	  }
	  
	  public BaseObject getEMSource()
	  /* Returns: an EMSource that represents the current experimental state.
	   *   It can be used to compute the E&M fields.
	   * The linear antenna is centered at the origin, and its direction is along the
	   *   vertical z-axis. */
	  {
	    return quarterWaveAntenna;
	  }
	  
	  public void Evolve(double dt)
	  /* Evolves the system by a time step 'dt'. */
	  {
	    t += dt;
	    quarterWaveAntenna.ta = t;
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
