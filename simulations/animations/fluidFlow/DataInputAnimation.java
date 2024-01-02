/**
 * 
 */
package simulations.animations.fluidFlow;


import simulations.experiments.fluidFlow.DataInputExperiment;
import core.rendering.Renderer;
import simulations.Constants;

/** An animation illustrating the program structure if the user wants to input a data array of
 * velocity values to display as a DLIC.  This would be the case for example if the user wants to 
 * display the output from a numerical program that computes fluid flow in the DLIC format.    
 * 
 * @author John Belcher
 * @version 1.0
 */
public class DataInputAnimation {
	  /** The width of the image generated.  */
	  static int width = 320;
	  /** The height of the image generated.  */
	  static int height = 240;
	  /** The stream integration length.  */
	  static int streamlen = 80;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\fluidFlows\\c";
	  /** The number of frames to be genererated. */
	  static int frames = 100;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.005;

	  public static void main(String[] args){ 
		  
		  DataInputExperiment experiment = 
		    	new DataInputExperiment(width,height);
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
		    renderer.SetColorHue(Constants.COLOR_EFIELD);
		    renderer.SetFluidFlowSpeed(10.);
		    renderer.SetFnorm(1.);
		    renderer.SetFpower(0.);
		    renderer.StartRender();
	  }
}
