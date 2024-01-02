
package simulations.objects;

import core.math.Vec3;
import simulations.objects.ElectricDipole;

/**
 * An extension of the ElectricDipole class that replaces
 * the constant electric dipole moment with one that is oscillating. We assume
 * that the dipole is fixed in direction along the z-axis, with its magnitude 
 * changing as defined below.  
 * @author Andreas Sundquist
 * @version 1.0
 */
public class ElectricOscillatingDipole extends ElectricDipole {
	/** The constant part of the dipole moment of the oscillating dipole */	
	  public double p0;
	  /** The sinusoidal part of the dipole moment of the oscillating dipole */	
	  public double p1;
	  /** The angular frequency of the oscillating dipole */	
	  public double omega;
	  /** The phase shift in the sinusoidal time depedence of the oscillating dipole */	
	  public double phase;
	  
	/** Constructs an ElectricOscillatingDipole centered at "x" at t = 0. The
	  *   parameter "p" is ignored, because we are creating a dipole which is always
	  *   along the z axis */   
	    public ElectricOscillatingDipole(Vec3 x, Vec3 p,double p0, double p1, double omega, double phase){
	      super(x, p);
	      this.p0 = p0;
	      this.p1 = p1;
	      this.omega = omega;
	      this.phase = phase;
	    }
	    /** Returns the dipole moment at a time retarded by dt.
	     * We use the method getT to find out the current time of the dipole */   
	    public Vec3 getP(double dt) {
	      double tretarded = getT() - dt;
	      return Vec3.Zhat.scale(p0 + p1*Math.sin(omega*tretarded-phase));
	    }
	    /** Returns the first time derivative of the dipole moment at a 
	     *   time retarded by dt 
	     *   We use the method getT to find out the current time of the dipole */    
	    public Vec3 getDP(double dt) {
		double tretarded = getT() - dt;
	    return Vec3.Zhat.scale(p1*omega*Math.cos(omega*tretarded-phase));
	    }
	    /** Returns the second time derivative of the dipole moment at a
	     *   time retarded by dt.  We use the method getT to find out 
	     *   the current time of the dipole */     
	    public Vec3 getDDP(double dt) {
	    double tretarded = getT() - dt;
	      return Vec3.Zhat.scale(-p1*omega*omega*Math.sin(omega*tretarded-phase));
	    }
	    
	  }
	  
