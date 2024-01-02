/**
 * 
 */
package simulations.animations.magnetostatics;

import simulations.experiments.magnetostatics.TeachSpinExperiment;
import core.rendering.Renderer;
import simulations.Constants;

/**
 * 
 * @author Andreas Sundquist
 * @version 1.0
 * 
 */
public class TeachSpinAnimation {
	  static int width = 320*2;
	  static int height = 240*2;
	  static int streamlen = 80;
	  static String fname = "C:\\DLICs\\magnetostatic\\TS";
	  static int frames = 130;
	  static double scale = 2.0;

	  public static void main(String[] args){ 
		  	double dt = 1./(100.*1.);
	        double omega = 2.*Math.PI;
		    TeachSpinExperiment experiment = new TeachSpinExperiment(75.,10.,-1.0, 50.,-30.,.002,omega);  
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(1);
		    renderer.SetColorMode(1);
		    renderer.SetStartFrame(25);
		    renderer.SetEndFrame(25);
		    renderer.SetColorHue(Constants.COLOR_BFIELD);
		    renderer.StartRender();
	  }
}
