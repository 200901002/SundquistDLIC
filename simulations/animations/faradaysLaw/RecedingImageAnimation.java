/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.RecedingImageExperiment;
import core.rendering.Renderer;

/** An animation of the magnetic fields of 
 * a non-moving magnetic monopole appearing above a conducting plane, following Saslow.
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on. This 
 * animation generates Figure 2 of Liu and Belcher 2007.   
 * 
 * @author John Belcher
 * @version 1.0
 * 
 */
public class RecedingImageAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 160;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\ReceedingImage\\mi";
	  /** The number of frames to be genererated. */
	  static int frames = 300;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.075;

	  public static void main(String[] args){  

		    double H = 200.;  // the corresponding max script has the monopole sitting at 400, so we adjust the scale there when we import the DLIC
		    double v0 = 25./2.; // because the max script is double length, we reduce the vo to half the value used in the max script
		    double q0 = 10000.;
		    double offset = 0.075*30.;  
		    RecedingImageExperiment experiment = new RecedingImageExperiment(H, q0, v0, offset );
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(1);
		    renderer.SetColorMode(2);
		    renderer.SetColorHue(0.5961);
		    renderer.SetColorStrength(0.00001);
		    renderer.SetFallOff(1.);
		   // renderer.SetStartFrame(250);
		   // renderer.SetEndFrame(250);
		    renderer.StartRender();
		    
	  }
}
