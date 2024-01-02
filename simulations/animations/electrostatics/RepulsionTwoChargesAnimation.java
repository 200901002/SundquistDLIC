/**
 * 
 */
package simulations.animations.electrostatics;

import simulations.experiments.electrostatics.TwoChargesExperiment;
import core.rendering.Renderer;
import simulations.Constants;

/** An animation of two charges repelling.  One charge is at rest and the other moves.  
 * This class has a main method and is used to generate
 * the sequence of images for the experiment.  It also defines the number of frames, the time step between
 * each frame, the colorizing of the images, the folder where the image files will be stored, and so on.  
 * 
 * @author John Belcher
 * @version 1.0
 */
public class RepulsionTwoChargesAnimation {
	  /** The width of the image generated.  */
	  static int width = 240;
	  /** The height of the image generated.  */
	  static int height = 320;
	  /** The stream integration length.  */
	  static int streamlen = 80;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\charges\\ch";
	  /** The number of frames to be genererated. */
	  static int frames = 100;
	  /** The time step for the evolution of the system.  */
	  static double dt = .25;

	  public static void main(String[] args){ 
		    double displacement = -100.;
		    double movingChargePos = 100.;
		    double movingChargeCharge = 25.;
		    double staticChargePos = 0.0;
		    double staticChargeCharge = 400.0;    
		    movingChargePos = movingChargePos + displacement;
		    staticChargePos = staticChargePos + displacement;
		    
		    TwoChargesExperiment experiment = new TwoChargesExperiment(movingChargeCharge,1.0,movingChargePos,-0.001,staticChargeCharge,staticChargePos);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(1);
		    renderer.SetColorMode(1);
		    renderer.SetColorHue(Constants.COLOR_EFIELD);
		    renderer.SetFrames(frames);
		   // renderer.SetStartFrame(50);
		   // renderer.SetEndFrame(50);
		    renderer.StartRender();
	  }
}
