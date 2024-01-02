package simulations.objects.antennaMath;  
import java.math.*;

public class qRomo {
    
    public static double qRomo(Integrable func, double[] p, double a, double b, int[] note) {
        // Romberg integration of func (with parameters p) on OPEN interval a to b.
        // Returns sum. The midpoint routine triples the number of steps on each call,
        // and its error series contains only even powers of the number of steps.
        // The parameters have the same meaning as in qromb.
        double eps,sum;
        int jmax,jmaxp,k,km,j,i;
        double[] dsum= new double[1];
        sum=0.0;
        dsum[0] = 0.;
        note[0]=0;
        eps=1.e-6;
        jmax=14;
        jmaxp=jmax+1;
        k=5;
        km=k-1;
        
        double[] sq = new double[jmaxp];
        double[] hq = new double[jmaxp];
        for (j = 0; j< jmaxp; j++) {
            sq[j]=0.0;
            hq[j]=0.0;
        }
        hq[0] = 1.0;
        // System.out.print("\nqromo:  a=" +a+", b="+b+"\n");
        for (j = 1; j<= jmax; j++) {
            sq[j-1] = midpnt.midpnt(func, p, a, b, j, sq[j-1] );
            // System.out.print("\nqromo:  sq["+(j-1)+"]=" +sq[j-1]+"\n");
            if (j >= k) {
                i=j-km;
                sum = polint.polint(hq, sq, k, 0., sum, dsum, (i-1), note);
                // System.out.print("\nqromo:  sum=" +sum+"\n");
                if ( Math.abs(dsum[0]) < eps*Math.abs(sum))  return sum;
            }
            sq[j]=sq[j-1];
            hq[j]=hq[j-1]/9.0;
            //This is where the assumption of step tripling and an even error series is used.
        }
        note[0]=2;
        return sum;
    }
    
}
