/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.EddyCurrentsDipoleExperiment;
import core.rendering.Renderer;
import core.math.Vec3;

/**  An animation of eddy currents in a conducting plane below a moving dipole, for various values of the
 * speed of the monopole, following Saslow.
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * This animation generates part of Figure 6 of Liu and Belcher 2007.    
 * @author John Belcher
 * @version 1.0
 * 
 */
public class EddyCurrentsDipoleAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 640;
	  /** The stream integration length.  */
	  static int streamlen = 130;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICS\\eddycurrents\\movingDipole\\md";
	  /** The number of frames to be genererated. */
	  static int frames = 201;
	  /** The time step for the evolution of the system.  */
	  static double dt = 300./170.;  

	  public static void main(String[] args){  
		    double H = 100.;
		    double v = 0.001;
		    double v0 = 25./2.;
		    double q0 = 200.;
		    double offset = -0001.*dt;
		    double r = 1.;
		    double angle = Math.PI/2.;
		    EddyCurrentsDipoleExperiment experiment = new EddyCurrentsDipoleExperiment(H, q0, v0, v, r, angle, offset );
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
		    renderer.SetStartFrame(56);
		    renderer.SetEndFrame(56);
		    renderer.StartRender();
		    
	  }
}
