package simulations.experiments.fluidFlow;

import core.math.Vec;
import core.math.Vec3;
import simulations.Constants;
import simulations.experiments.BaseExperiment;
import simulations.objects.BaseObject;
import simulations.objects.EMCollection;
import simulations.objects.DataInputObject;



 public class DataInputExperiment extends BaseExperiment {

  public double t;
  public int width;
  public int height;

  public DataInputExperiment(int width, int height){
	/* this experiment is a fluid flow experiment using data input, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
	/* this experiment is a fluid flow experiment using magnetic EMobjects, so we set FieldMotionType accordingly */
	this.FieldMotionType = Constants.FIELD_MOTION_VBFIELD;
	this.height = height;
	this.width = width;
    t=0.;
    
    ConstructEMSource();
  }
  
  private DataInputObject dataInput;
  public EMCollection collection;
  public void ConstructEMSource(){
    dataInput = new DataInputObject(width,height);
    System.out.println(dataInput.toString());
    collection = new EMCollection();
    collection.Add(dataInput);
  }

  /**  Returns: an EMSource that represents the current experimental state.  */
  public BaseObject getEMSource(){
    return collection;
  }
  
  public void Evolve(double dt){
    t = t + dt;
    /* update the time in the data input object so it knows to read in a new array. */
    dataInput.t = t;
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
