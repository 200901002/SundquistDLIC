package simulations.objects.antennaMath;

public class ChebyshevIntegration {
  
     public static double qCheb(Integrable fct, double[] p, double a, double b, int ncmax, int[] nc) 
     {    
         // Chebyshev integration of (fct,p)  from a to b. 
          int i; 
          double eps = 1E-5;
          double[] cint = new double[ncmax];  // cint[0...ncmax-1]
          ChebIntFit(fct,p,a,b,cint,ncmax);    
          double ass = 0.0;
          for (i = 1; i<= ncmax; i++)
          {
          	ass = ass + Math.abs(cint[i-1]);
          }
          nc[0] = 1;
          double tail = Math.abs( cint[ncmax-1] )/ass;
          for (i = ncmax-1; i >= 1; i--)
          {
          	tail = tail + ( Math.abs(cint[i-1]) )/ass;
          		if (tail > eps)
          		{
          			nc[0] = i+1;
          			break;  //want to break out of loop
          		}
          }
          if ( (nc[0] != 1) && (nc[0] != ncmax) ) {
                // System.out.print("\n nc="+nc[0]+"; cint[nc-2]="+cint[nc[0]-2]+".\n");
                return chebFct(a,b,cint,nc[0],b);
          }
          else if (nc[0] == 1) {
            System.out.print("\n Integral value less than eps.\n"); 
                return 0.0;
          }
          else if (nc[0] == ncmax ) {
               System.out.print("\n No fit? or ncmax too small?\n");
               return 0.0;
          }
          return 0.0;
     }
     
     public static void ChebIntFit(Integrable fct, double[] p,double a,double b, double[] cint, int n)
     {
          //Based on Numerical Recipes (sec. 5.8):     chebft(a,b,c,n,fct)
          //Chebyshev fit: Given a function fct, lower and upper limits of the interval [a,b], 
          //and a maximum degree n, this routine computes the n coefficients c[k] such that 
          //func(x) ~= [Sum, k=1 to n, c[k]*T[k-1](y)] - c[1]/2, 
          //where y and x are related by (5.8.10). This routine is to be used with moderately  
          //large n (e.g., 30 or 50), the array of c's subsequently to be truncated
          //at the smaller value m such that c[m+1] and subsequent elements are negligible.
          int i, j, k;
          double [] f = new double[n];
          double [] c = new double[n];
          int nm = 100;     //Maximum expected value of n.
          double bma = (b-a)/2.0;
          double bpa = (b+a)/2.0;
          for (k=1; k<= n; k++)   //Evaluate function at n points - see (5.8.7).
          {
          	double y = Math.cos( Math.PI*(k-.5)/((double)n ) );
          	f[k-1] = fct.f_to_integrate(p,y*bma+bpa);
          }
          double fac = 2.0/( (double)n );
          for (j=1; j<= n; j++)
          {
          	double sum = 0.0;
          	for (k=1; k<= n; k++)
          	{
          		sum = sum + f[k-1]*Math.cos( Math.PI*(j-1)*((k-.5)/( (double)n)) );
          	}
          	c[j-1] = fac*sum;
          }
          // Based on Numerical Recipes (sec.5.9):     chint(a,b,c,cint,n)
          // Given a,b,c(1:n), as output from routine chebft 5:8, and given n, 
          // the desired degree of approximation (length of c to be used), 
          // this routine computes the array cint(1:n), the Chebyshev coefficients 
          // of the integral of the function whose coefficients on [a,b] are c. 
          // The constant of integration is set so that the integral vanishes at a.
          double con = (b-a)/4.0;           //Factor that normalizes to the interval b-a.
          double sum1 = 0.0;                // Accumulates the constant of integration.
          fac = 1.0;                        // Will equal +/- 1
          for (j=2; j <= n-1; j++)
          {
          	cint[j-1] = con*( c[j-2]-c[j] )/(j-1) ;    // Equation (5.9.1).
          	sum1 = sum1 + fac*cint[j-1];  
          	fac = -fac;
          }
          cint[n-1] = con*c[n-2]/(n-1);     // Special case of (5.9.1) for n.
          sum1 = sum1+fac*cint[n-1];
          cint[0] = 2.0*sum1;              // Set the constant of integration.
     }
          
    public static double chebFct(double a, double b, double[] c, int m, double x)
    {
        // Computes the value of a function at x on the interval [a,b] 
        // given its m Chebyshev coefficients, c[0,m-1].
        int j;
        // x must be between a and b
        if ( (x-a)*(x-b) > 1E-10 ) System.out.println("Variable not in allowed range.");
        double d=0.0;
        double dd=0.0;
        double y=(2.0*x-a-b)/(b-a);
        double y2=2.0*y;
        for (j=m; j>= 2; j--)
        {
              double sv=d;  
              d=y2*d-dd+c[j-1];   
              dd=sv;
         }
         return y*d-dd+c[0]/2.0;
    }  
}