package simulations.objects.antennaMath;
/*  
 * polint.java
 */


public class polint {

    public static double polint( double[] xa, double[] ya, int n, double x, double y, double[] dy, int offset, int[] note) {
        // Given arrays xa and ya and a value x, this function returns
        //       a value y and an error estimate dy.
        // If P(x) is the polynomial of degree n-1 such that
        // P(xa[i+offset])= ya[i+offset] for i=0 to n-1, then y = P(x).
        // modifications to xa,ya,note,dy are returned.
        // note and dy are dimension 1.
        // Interpolation is performed on tabulated values from xa/ya[offset-1] to xa/ya[offset-1+n]
        int i, m, ns, nmax;
        double dif, dift, ho, hp, den, w;
        double[] ct = new double[n];
        double[] dt = new double[n];
        nmax=10;      //largest anticipated value of n
        ns=1;
        dif = Math.abs( x - xa[offset] );
        
        for (i=1; i <= n ; i++) {
            dift = Math.abs(x - xa[i-1+offset]);
            if (dift < dif) {
                ns=i;
                dif=dift;
            }
            ct[i-1]=ya[i-1+offset];
            dt[i-1]=ya[i-1+offset];
        }
        y=ya[ns+offset-1];
        ns=ns-1;
        for (m=1; m <= (n-1); m++) {
            for (i=1; i <= (n-m); i++) {
                ho=xa[i-1+offset]-x;
                hp=xa[i-1+m+offset]-x;
                w=ct[i]-dt[i-1];
                den=ho-hp;
                if(den == 0.) {
                    note[0] = 1;    //qRomberg Interpolation failed
                    y = 0.;
                    return y;
                }
                den=w/den;
                dt[i-1]=hp*den;
                ct[i-1]=ho*den;
            }
            if (2*ns < n-m) {
                dy[0]=ct[ns];
            }
            else {
                dy[0]=dt[ns-1];
                ns=ns-1;
            }
            y=y+dy[0];
            //System.out.print("\n test1:  y="+y+"\n");
        } 
        //System.out.print("\n polint2:  y="+y+"\n");
        return y;
    }

}
