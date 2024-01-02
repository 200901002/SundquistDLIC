/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.MovingReceedingImageExperiment;
import core.rendering.Renderer;


/** An animation of the magnetic fields in the xz plane when a magnetic monopole moves above the plane, 
 * for various values of the monople speed, following Saslow.
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * This animation generates Figure 5 of Liu and Belcher 2007.    This version of the program calculates
 * the magnetic fields using line dipoles and monopoles.  It is very similar to MovingRecedingImagePotentialAnimation
 * except that program calculates the fields using a magnetic potential, taking numerical derivatives. 
 * @author John Belcher
 * 
 * @author John Belcher
 * @version 1.0
 * 
 */
public class MovingRecedingImageAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 150;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\MovingMonopole\\mri";
	  /** The number of frames to be genererated. */
	  static int frames = 201;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.;

	  public static void main(String[] args){  
		    double H = 200.;
		    double v = 0.;
		    double v0 = 25.;
		    double q0 = 10000.;
		    double offset = -0001.*dt;
		    double zplane = 0.;
		    dt = 300./(1.*frames - 1.-30.);
		    MovingReceedingImageExperiment experiment = new MovingReceedingImageExperiment(H, q0, v0, v, zplane, offset );
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(0);
		    renderer.SetColorMode(2);
		    renderer.SetColorHue(0.5961);
		    renderer.SetColorStrength(0.000000001);
		    renderer.SetFallOff(1.);
		    renderer.SetStartFrame(100);
		    renderer.SetEndFrame(100);
		    renderer.StartRender();
		    
	  }
}
