/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.MovingRecedingImagePotentialExperiment;
import core.rendering.Renderer;

/** An animation of a magnetic monopole moving above a conducting plane following Saslow
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * This version of the program calculates the magnetic fields using the magnetic potential function instead of 
 * monopoles and line dipoles (see equation (8) of Liu and Belcher 2007).  It is very similar to MovingRecedingImageAnimation
 * except that program calculates the fields using line dipoles and image monopoles.
 * 
 * @author John Belcher
 * @version 1.0
 * 
 */
public class MovingRecedingImagePotentialAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 160;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\FaradaysLaw\\MovingRecedingImagePotential\\mrip";
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
		    MovingRecedingImagePotentialExperiment experiment = new MovingRecedingImagePotentialExperiment(H, q0, v0, v, zplane, offset );
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
		    renderer.SetColorHue(0.5961);
		    renderer.SetColorStrength(1000.);
		    //renderer.SetXdir(new Vec3(0.,1.,0.));
		    renderer.SetStartFrame(100);
		    renderer.SetEndFrame(100);
		    renderer.StartRender();
		    
	  }
}
