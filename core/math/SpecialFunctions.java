package core.math;

public class SpecialFunctions {
	  /**
     * Elliptic integrals: 
     *	This algorithm for the calculation of the complete elliptic
     *	integral (CEI) is presented in papers by Ronald Bulirsch,
     *	Numerical Calculation of Elliptic Integrals and 
     *	Elliptic Functions, Numerische Mathematik 7,
     *	78-90 (1965) and Ronald Bulirsch: Numerical Calculation 
     *	of Elliptic Integrals and Elliptic Functions III,
     *	Numerische Mathematik 13,305-315 (1969).  The definition
     *	of the complete elliptic integral is given in equation (1.1.1.1)
     *	of the 
     *  <a href="C:\Development\Projects\SundquistDLIC\DLICdoc\TEAL_Physics_Math.pdf"> 
     *  TEAL_Physics_Math document </a>.
     *  @param kcc see Section 1.1.1 of the TEAL_Physics_Math documentation for the definition of this parameter
     *  @param pp see Section 1.1.1 of the TEAL_Physics_Math documentation for the definition of this parameter
     *  @param aa see Section 1.1.1 of the TEAL_Physics_Math documentation for the definition of this parameter
     *  @param bb see Section 1.1.1 of the TEAL_Physics_Math documentation for the definition of this parameter
     *  @param accuracy  the desired accuracy
     *  @return the value of the complete elliptic integral for these parameter values
     */

    public static double ellipticIntegral(double kcc, double pp, double aa, double bb, double accuracy) {
        double ca, kc, p, a, b, e, m, f, q, g;
        ca = accuracy;
        kc = kcc;
        p = pp;
        a = aa;
        b = bb;
        if ( kc != 0.0 ) 
        {
        	kc = Math.abs(kc);
        	e = kc;
        	m = 1.0;
        	
        	if (p > 0.) 
        	{
        		p = Math.sqrt(p);
        		b = b/p;
        	} 
        	else 
        	{
        		f = Math.pow(kc,2.0);
        		q = 1.-f;
        		g = 1.-p;
        		f = f-p;
        		q = q*(b-a*p);
        		p = Math.sqrt(f/g);
        		a = (a-b)/g;
        		b = -q/(p*Math.pow(g,2.0)) + a*p;
        	}

        	f = a;
        	a = b/p + a;
        	g = e/p;
        	b = 2.0*(f*g + b);
        	p = p + g;
        	g = m;
        	m = m + kc;
        	
        	while (Math.abs(g - kc) > g*ca) 
        	{
        		kc = 2.0*Math.sqrt(e);
        		e = kc*m;
        		f = a;
        		a = b/p + a;
        		g = e/p;
        		b = 2.0*(f*g + b);
        		p = p + g;
        		g = m;
        		m = m + kc;
        	}
        	
        	return (Math.PI / 2.)*(a*m + b)/(m*(m + p));
        	
        }
        
        else 
        {
        	return 0.0;
        }
    }

}
