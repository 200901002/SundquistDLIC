/**
 * 
 */
package simulations.animations.radiation;

import simulations.Constants;
import simulations.experiments.radiation.OscillatingDipoleExperiment;
import core.rendering.Renderer;
import core.math.Vec3;

/**
 * 
 */
public class SinusoidalDipoleRadiationAnimation {
	  static double resScale = 1.;  
	  static int width = (int)(320*resScale);
	  static int height = (int)(240*resScale);
	  static int streamlen = (int)(80*resScale);
	  static String fname = "C:\\DLICs\\dip\\dp";
	  static int frames = 180;
	  static int period = 150;
	  static double CyclesShown = 1.5;
	  static double dt = 1.0*width/(2.0*CyclesShown*Constants.c*period);
	  static double T = width/(CyclesShown*2.0*Constants.c);
	  static double scale = 1.;


	  public static void main(String[] args){  
		    OscillatingDipoleExperiment experiment = 
		      new OscillatingDipoleExperiment(0., 1.0, 2.*Math.PI/T, Math.PI/2.);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(0);
		    renderer.SetColorMode(1);
		    renderer.SetColorStrength(0.002);
		    renderer.SetColorHue(Constants.COLOR_EFIELD);
		    renderer.SetFallOff(2.);
		    renderer.SetStartFrame(1);
			renderer.SetEndFrame(1);
		    renderer.StartRender();
	  }
}
