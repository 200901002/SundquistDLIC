/**
 * 
 */
package simulations.animations.fluidFlow;

import simulations.experiments.fluidFlow.CirculatingFlowExperiment;
import core.rendering.Renderer;
import simulations.Constants;

/** An animation of fluid flow.  
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * 
 * @author John Belcher
 * @version 1.0
 */
public class CirculatingFlowAnimation {
	  /** The width of the image generated.  */
	  static int width = 320;
	  /** The height of the image generated.  */
	  static int height = 240;
	  /** The stream integration length.  */
	  static int streamlen = 60;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\fluidFlows\\c";
	  /** The number of frames to be genererated. */
	  static int frames = 130; //130;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.005;

	  public static void main(String[] args){ 
		  
		    CirculatingFlowExperiment experiment = 
		    	new CirculatingFlowExperiment(-50.,-25.,2.,50.,55.,-2.,20.,10.,3.,0.,0.,0.,0.,0.,0.,.0,.0);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(0);
		    renderer.SetColorMode(1);
		    renderer.SetColorStrength(.1);
		    renderer.SetFallOff(3.);
		    //renderer.SetEndFrame(1);
		    //renderer.SetStartFrame(1);
		    renderer.SetColorHue(Constants.COLOR_BFIELD);
		    renderer.SetFluidFlowSpeed(500.);
		    renderer.SetFnorm(.1);
		    renderer.SetFpower(.3);
		    renderer.StartRender();
	  }
}
