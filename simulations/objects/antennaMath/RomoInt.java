package simulations.objects.antennaMath;

import java.math.*;
//import EMSimulations.Field.*;

public class RomoInt {
    
    public static double RomoInt(Integrable func, double[] p, double a, double b, int nq, int[] note) {
        //Romberg integration of func (with parameter array p) from a to b
        // This version has been specialized for calculating Erho.
        int i;
        double eps = 1.0E-6;
        double pp = 2.0*Math.PI;
        int nqc = 0;
        note[0]=0;
        double sum=0.;
        if (  (p[0] < eps*pp) && ( (Math.abs(p[1]) - p[2]) < eps*pp ) ) {
            double total = eps;    // antenna taboo region
            return total;
        }
        double da = b/(double)nq;
        a = da;                 // da must be < .1 ;   ( nq > 16)
        double total = 0.0;
        for (i = 0; i <= (nq-1); i++) {
            double ai = i*da;
            sum = qRomo.qRomo(func, p, ai, (ai+da), note );
            if (note[0] != 0) {
                nqc = i;
                total = total + sum;
                break;
            }
            total = total + sum;
            // System.out.print("\n Romo_Int:  total="+total+", nqc="+nqc+"\n");
        }
        return total;
    }
    
    
}