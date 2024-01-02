package simulations.objects.antennaMath;
//package EMSimulations.Math;

import java.math.*;
//import EMSimulations.Field.*;

public class RombInt {
    
    public static double RombInt(Integrable func, double[] p, double a, double b, int nq, int[] note) {
        //General Romberg integration of func (with parameter array p) from a to b
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
        double da = (b-a)/(double)nq;
        double total = 0.0;
        for (i = 0; i <= (nq-1); i++) {
            double so = a + i*da;
            double sop = so + da;
            sum = qRomo.qRomo(func, p, so, sop, note );
            if (note[0] != 0) {
                nqc = i;
                total = total + sum;
                break;
            }
            total = total + sum;
        }
        return total;
    }
    
    
}