/**
 * 
 */
package simulations.animations.radiation;

import simulations.Constants;
import simulations.experiments.radiation.ElectricAntennaExperiment;
import core.rendering.Renderer;

/** An animation of the radiation from an electric antenna in which the wavelength of the radiation is twice 
 * the total length of the antenna, or four times the half length of the antenna.  The supporting mathematics
 * is given in Section 7.2 of the TEAL_Physics_Math.pdf documentation.  
 * 
 * @author Norman Derby
 * @version 1.0
 * 
 */
public class LinearAntennaAnimation {
	  static double resScale = 1.;  
	  static int width = (int)(320*resScale);
	  static int height = (int)(240*resScale);
	  static int streamlen = (int)(80*resScale);
	  static String fname = "C:\\DLICs\\antenna1\\QW";
	  static int frames = 30  ;
	  /** the period of the oscillation in frames */
	  static int period = 25;
	  static double dt =.5*width/(2.0*Constants.c*period);
	  static double scale = 1.;
	  public static void main(String[] args){  
		    double T = width/(2.0*Constants.c); 
		    double wavelength = Constants.c*T;  
		    ElectricAntennaExperiment experiment = 
		      new ElectricAntennaExperiment(2.*Math.PI/wavelength, Math.PI/2., 2.*Math.PI/T);
		    Renderer renderer = new Renderer();
		    renderer.SetFileName(fname);
		    renderer.SetEndFrame(frames);
		    renderer.SetFrames(frames);
		    renderer.SetWidth(width);
		    renderer.SetHeight(height);
		    renderer.SetStreamLen(streamlen);
		    renderer.SetTimeStep(dt);
		    renderer.SetExperiment(experiment);
		    renderer.SetSymmetry(3);
		    renderer.SetColorMode(1);
		    renderer.SetColorStrength(0.002);
		    renderer.SetColorHue(Constants.COLOR_EFIELD);
		    renderer.SetFallOff(2.);
		    renderer.SetStartFrame(2);
		    renderer.SetEndFrame(2);
		    renderer.SetScale(scale);
		    renderer.StartRender();
	  }
}
