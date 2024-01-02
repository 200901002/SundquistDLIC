package simulations;

/** Electromagnetic constants and field and field motion constants.
*
* The class defines the integers for various field types and field motion
* types.  This class also defines the free-space permittivity and permeability
* somewhat arbitrarily so that the speed of light is 1000 units/second.
* It may be a good idea to rescale these to SI units at some point. */

public class Constants {
	
	/** FieldType for electrostatics experiments. */
	public static final int FIELD_EFIELD = 1;
	/** FieldType for magnetostatics experiments.*/	
	public static final int FIELD_BFIELD = 2;
	/** FieldMotionType for electrostatics experiments. */	
	public static final int FIELD_MOTION_EFIELD = 1;
	/** FieldMotionType for magnetostatics experiments. */		
	public static final int FIELD_MOTION_BFIELD = 2;
	/** FieldMotionType for fluid flow parallel to E field. */
	public static final int FIELD_MOTION_VEFIELD = 3;
	/** FieldMotionType for fluid flow parallel to B field. */
	public static final int FIELD_MOTION_VBFIELD = 4;
	/** FieldMotionType for fluid flow varying by spatial region. */
	public static final int FIELD_MOTION_VREFIELD = 5;
	/** FieldMotionType for fluid flow varying by spatial region. */
	public static final int FIELD_MOTION_VRBFIELD = 6;
	
	/** The "colorHue" value used as the default for electric fields */
	public static final double COLOR_EFIELD = 0.1;
	/** The "colorHue" value used as the default for magnetic fields */
	public static final double COLOR_BFIELD = 0.5961;
		
	/** episilon naught */
	public static double e0 = 0.001;
	/** mhu naught */
	public static double u0 = 0.001;
	/** the speed of light computed from above values */
	public static double c = 1.0/Math.sqrt(e0*u0);
	/** the speed of light squared computed from above values */
	public static double c2 = 1.0/(e0*u0);
	/** 1/(4 pi epsilon naught) in coulomb's law */
	public static double Efactor = 1.0/(4.0*Math.PI*Constants.e0);
	/** mhu naught over 4 pi in Biot Savart Law */
	public static double Bfactor = Constants.u0/(4.0*Math.PI);
  
}