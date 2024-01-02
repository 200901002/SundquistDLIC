/**
 * 
 */
package simulations.animations.electrostatics;

import simulations.experiments.electrostatics.ChargeInFieldExperiment;
import core.math.Vec;
import core.rendering.Renderer;

/** An animation of a point charge moving in a constant electric field.  We color code the image so that the field
 * lines connected to the point charge are a different hue than the field lines connected to the charges that produce
 * the constant field.  This is an example of Color Mode 4.  
 * 
 * @author John Belcher
 * @version 1.0
 */
public class ChargeMovingAgainstConstantFieldAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 160;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\charges\\ch";
	  /** The number of frames to be genererated. */
	  static int frames = 100;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.03;
	  
	  public static void main(String[] args){ 
		    Vec RegionColor = new Vec(3);
		    RegionColor.x[0] = .1;  // hue for the field lines connected to the point charge
		    RegionColor.x[1] = .05;  // hue for the field lines connected to the charges generating the constant field
		    double z0 = -450;
		    double v0 = 300.;
		    ChargeInFieldExperiment experiment = new ChargeInFieldExperiment(-1., 100., 10000., z0, v0);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(1);
		    renderer.SetFrames(frames);
		    renderer.SetColorStrength(5.);
		    renderer.SetColorMode(4);
		    renderer.SetFallOff(1.);
		    renderer.SetStartFrame(90);
		    renderer.SetEndFrame(90);
		    renderer.SetRegionColor(RegionColor);
		    renderer.StartRender();
	  }
}
