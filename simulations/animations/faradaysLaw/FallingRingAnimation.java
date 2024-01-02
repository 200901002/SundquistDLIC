/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.FallingRingExperiment;
import core.rendering.Renderer;
import simulations.Constants;

/** An animation of a falling ring.  This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * 
 * @author Andreas Sundquist
 * @version 1.0
 * 
 */
public class FallingRingAnimation {
	  /** The width of the image generated.  */
	  static int width = 320;
	  /** The height of the image generated.  */
	  static int height = 240;
	  /** The stream integration length.  */
	  static int streamlen = 80;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\fallingring\\fr";
	  /** The number of frames to be genererated. */
	  static int frames = 130;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.042;

	  public static void main(String[] args){  
		    /* Set the radius of the falling ring to 100 pixels with a 640 pixel width, and scaled linearly
		     * with the width if the width is not 640. */
		    double R = 100.;
		    R = R*(1.*width)/640.;
		    FallingRingExperiment experiment = new FallingRingExperiment(R, 2., 0.0, 30.0,0.);
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
		    renderer.SetColorHue(Constants.COLOR_BFIELD);
		    renderer.SetColorSaturation(.5);
		    //renderer.SetStartFrame(50);
		    //renderer.SetEndFrame(50);
		    renderer.StartRender();
		    
	  }
}
