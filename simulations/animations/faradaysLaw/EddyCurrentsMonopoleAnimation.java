/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.EddyCurrentsMonopoleExperiment;
import core.rendering.Renderer;
import core.math.Vec3;

/** An animation of eddy currents in a conducting plane below a moving monopole, for various values of the
 * speed of the monopole, following Saslow.
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * This animation generates Figure 3 of Liu and Belcher 2007 and part of Figure 4 of the same paper.    
 * @author John Belcher
 * @version 1.0
 * 
 */
public class EddyCurrentsMonopoleAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 800;  // was 800
	  /** The stream integration length.  */
	  static int streamlen = 160;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\Development\\Projects\\SundquistDLIC_Master\\DLICs\\eddycurrents\\mm";
	  /** The number of frames to be genererated. */
	  static int frames = 201;
	  /** The time step for the evolution of the system.  */
	  static double dt = 300./170.;

	  public static void main(String[] args){  
		    double H = 200.;
		    double v = .001;  // must have this value non-zero and small
		    double v0 = 25.;
		    double q0 = 1.;
		    double offset = -0001.*dt;
		    EddyCurrentsMonopoleExperiment experiment = new EddyCurrentsMonopoleExperiment(H, q0, v0, v, offset);
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
		    renderer.SetColorHue(.035);  //(0.5961);
		    renderer.SetColorSaturation(.3);
		    renderer.SetColorValue(1.);
		    renderer.SetColorStrength(3.);
		    renderer.SetYdir(new Vec3(0.,1.,0.));  // this makes the plot the xy plane instead of the xz plane
		   // renderer.SetXdir(new Vec3(-1.,0.,0.)); // and reverses the x direction
		    renderer.SetStartFrame(0);
		    renderer.SetEndFrame(0);
		    renderer.StartRender();
		    
	  }
}
