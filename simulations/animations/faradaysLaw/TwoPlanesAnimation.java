/**
 * 
 */
package simulations.animations.faradaysLaw;

import simulations.experiments.faradaysLaw.TwoPlanesExperiment;

import core.rendering.Renderer;


/** Calculate the magnetic field due to a monopole appearng at t = 0 in the first quandrant of the intersection of two
 * thin conducting planes, following Liu and Belcher 2007 Appendix A.
 * @author Yao Liu
 * @version 1.0
 * 
 */
public class TwoPlanesAnimation {
	  /** The width of the image generated.  */
	  static int width = 640;
	  /** The height of the image generated.  */
	  static int height = 480;
	  /** The stream integration length.  */
	  static int streamlen = 160;
	  /** The file name for the files to be generated, with path.  */
	  static String fname = "C:\\DLICs\\twoplanes\\tp";
	  /** The number of frames to be genererated. */
	  static int frames = 200;
	  /** The time step for the evolution of the system.  */
	  static double dt = 0.033;

	  public static void main(String[] args){  
		  double k = 200.;
		  double h = 100.;
		  double v1 = 40.;
		  double v2 = 40.;
		  double t = 0.;
		  double q0 = 1000.;
		  double offset = -0001.*dt;
		  TwoPlanesExperiment experiment = new TwoPlanesExperiment(k, h, q0, v1, v2, t, offset );
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
		  renderer.SetColorStrength(0.0000035);
		  renderer.SetFallOff(0.5);
		  //renderer.SetXdir(new Vec3(0.,1.,0.));
		 // renderer.SetStartFrame(2);
		 // renderer.SetEndFrame(2);
		  renderer.StartRender();
		    
	  }
}
