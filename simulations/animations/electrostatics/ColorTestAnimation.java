/**
 * 
 */
package simulations.animations.electrostatics;

import simulations.experiments.electrostatics.ColorTestExperiment;
import core.rendering.Renderer;

/** An animation designed to illustrate the color coding possible.   
 * We also test ways to color by region and also set flow velocity by region
 * (as opposed to setting color and flow by the magnitude of the field).  
 * 
 * @author John Belcher
 * @version 1.0
 */
public class ColorTestAnimation {
	  /** The width of the image generated.  */
	  static int width = 400;
	  /** The height of the image generated.  */
	  static int height = 50;
	  /** The stream integration length.  */
	  static int streamlen = 50;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\Development\\Projects\\SundquistDLIC_Master\\DLICdoc\\DLICs\\colortest\\ct";
	  /** The number of frames to be genererated. */
	  static int frames = 1;
	  /** The time step for the evolution of the system.  */
	  static double dt = .25;

	  public static void main(String[] args){ 
		    double ACT = -10./400.;
		    double BCT = 0.;
		    
		    ColorTestExperiment experiment = new ColorTestExperiment(ACT,BCT);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(0);
		    renderer.SetFrames(frames);
		    renderer.SetColorStrength(5.);
		    renderer.SetColorMode(2);
		    renderer.SetFallOff(1.);
		    renderer.SetColorHue(0.1);
		    
		    renderer.StartRender();
	  }
}
