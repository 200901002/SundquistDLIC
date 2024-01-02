/**
 * 
 */
package simulations.animations.fluidFlow;

import simulations.experiments.fluidFlow.HeliosphereFlowExperiment;
import core.rendering.Renderer;
import simulations.Constants;
import core.math.Vec;
import core.math.Vec3;

/** An animation of heliospheric flow for the Voyager Press conference Nov 30, 2007  
 * 
 * @author John Belcher
 * @version 1.0
 */
public class HeliosphereFlowAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 180;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\heliosphere\\hs";
	  /** The number of frames to be genererated. */
	  static int frames = 100;
	  /** The time step for the evolution of the system.  */
	  static double dt = .02;

	  public static void main(String[] args){ 
		    Vec RegionColor = new Vec(3);
		    Vec RegionFlow = new Vec(3);
		    RegionColor.x[0] = .1;  // hue for the solar wind region
		    RegionColor.x[1] = .05;  // hue for the heliosheath
		    RegionColor.x[2] = .5961;  // hue for the interstellar medium 
		    RegionFlow.x[0] = 300.;  // flow speed for the solar wind region
		    RegionFlow.x[1] = 100.;  // flow speed for the heliosheath
		    RegionFlow.x[2] = 50.;  // flow speed for the interstellar medium 
		    double radiusTerminationShock = 90.;
		    double radiusBowShock = 115.;
		    Vec3 Xsun = new Vec3(0.,0.,0);  // new Vec3(60.,0.,97.*2); for close up
		    HeliosphereFlowExperiment experiment = new HeliosphereFlowExperiment(radiusBowShock,radiusTerminationShock,Xsun);
		    Renderer renderer = new Renderer();
		    renderer.SetColorSaturation(.7);
		    renderer.SetRegionColor(RegionColor);
		    renderer.SetRegionFlow(RegionFlow);
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(2);
		    renderer.SetColorMode(4);
		    renderer.SetColorHue(Constants.COLOR_EFIELD);
		    renderer.SetFrames(frames);
		  //  renderer.SetStartFrame(1);
		  //  renderer.SetEndFrame(1);
		    renderer.StartRender();
	  }
}
