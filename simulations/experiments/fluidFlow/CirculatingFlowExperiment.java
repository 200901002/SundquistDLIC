package simulations.experiments.fluidFlow;

import simulations.Constants;
import simulations.experiments.BaseExperiment;
import simulations.objects.BaseObject;
import simulations.objects.ConstantFields;
import simulations.objects.EMCollection;
import simulations.objects.LineCurrent;
import simulations.objects.LineMagneticMonopoles;
import core.math.Vec;
import core.math.Vec3;


 public class CirculatingFlowExperiment extends BaseExperiment {

  private double  z1, x1, vz1, vx1,  z2, x2, vz2, vx2,  z3, x3, vz3, vx3, z4, x4, vz4, vx4;
  private double i1, i2, i3, i4;
  private double Bx, Bz;
  private double xm, zm, lambda;
  public double t;

  public CirculatingFlowExperiment(double x1, double z1, double i1, double x2, double z2, double i2, double x3, double z3, double i3, double x4, double z4, double i4, double xm, double zm, double lambda, double Bx, double Bz){
	/* this experiment is a fluid flow experiment using magnetic EMobjects, so we set FieldType accordingly */
	this.FieldType = Constants.FIELD_BFIELD;
	/* this experiment is a fluid flow experiment using magnetic EMobjects, so we set FieldMotionType accordingly */
	this.FieldMotionType = Constants.FIELD_MOTION_VBFIELD;
	
    this.xm = xm;
    this.zm = zm;
    this.lambda = lambda;
      
    this.Bx = Bx;
    this.Bz = Bz;
    
    this.x1 = x1;
    this.z1 = z1;
    this.vx1 = 0.;
    this.vz1 = 0.;
    this.i1 = i1;
    
    this.x2 = x2;
    this.z2 = z2;
    this.vx2 = 0.;
    this.vz2 = 0.;
    this.i2 = i2;
    
    this.x3 = x3;
    this.z3 = z3;
    this.vx3 = 0.;
    this.vz3 = 0.;
    this.i3 = i3;

    this.x4 = x4;
    this.z4 = z4;
    this.vx4 = 0.;
    this.vz4 = 0.;
    this.i4 = i4;
    
    t=0.;
    
    ConstructEMSource();
  }

  private LineCurrent line1;
  private LineCurrent line2;
  private LineCurrent line3;
  private LineCurrent line4;
  private ConstantFields BConst;
  private LineMagneticMonopoles Bmonopole;
  public EMCollection collection;

  public void ConstructEMSource(){
    double radiusline = 15.;
    line1 = new LineCurrent(i1, new Vec3(x1,0,z1), radiusline);
    line2 = new LineCurrent(i2, new Vec3(x2,0,z2), radiusline);
    line3 = new LineCurrent(i3, new Vec3(x3,0,z3), radiusline);
    line4 = new LineCurrent(i4, new Vec3(x4,0,z4), radiusline);
    BConst = new ConstantFields(new Vec3(0.,0.,0.), new Vec3(Bx,0.,Bz));
    Bmonopole = new LineMagneticMonopoles(lambda, new Vec3(xm,0.,zm), radiusline);

    //fields = new ConstantFields(new Vec3(0,0,E), new Vec3(0,0,0));
    System.out.println("line1 " + line1 );
    System.out.println("line2 " + line2 );
    System.out.println("line3 " + line3 );
    System.out.println("line4 " + line4 );
    
    collection = new EMCollection();
    collection.Add(line1);
    collection.Add(line2);
    collection.Add(line3);
    collection.Add(line4);
    collection.Add(BConst);
    collection.Add(Bmonopole);
  }

  /**  Returns: an EMSource that represents the current experimental state.  */
  public BaseObject getEMSource(){
    return collection;
  }
  
  public void Evolve(double dt){
    System.out.println("evolve  t " + t + " x1 " + x1 + "  vx1 " + vx1 );
    t = t + dt;
    //System.out.println(" Evolve2:  p0 " + p.x[0] + " p1 " + p.x[1] + " p2 " + p.x[2] + " p3 " + p.x[3]);
    line1.i = i1;
    line1.p = new Vec3(x1, 0, z1);
    line2.i = i2;
    line2.p = new Vec3(x2, 0, z2);
    line3.i = i3;
    line3.p = new Vec3(x3, 0, z3);
    line4.i = i4;
    line4.p = new Vec3(x4, 0, z4); 
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
