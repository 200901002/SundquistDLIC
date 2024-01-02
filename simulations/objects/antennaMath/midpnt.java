package simulations.objects.antennaMath;  
/*
 * midpnt.java  
 * Created on May 28, 2002, 12:34 PM
 */

public class midpnt {
    
    /** Creates new midpnt */
    public static double midpnt(Integrable func, double[] p, double a1, double a2, int n, double s) {
        //This routine computes the nth stage of refinement of an extended midpoint rule.
        //func (with parameters p) is to be integrated from a1 to a2.
        //When n=1, routine returns as s the crudest estimate. Subsequent calls with n=2,3,...
        //improve the accuracy by adding (2/3) x 3^(n-1) additional interior points.
        // s should not be modified between sequential calls.
        double ddel,del,sum,tnm,x,ss;
        int it,j;
        it=1;
        if (n == 1) {
            return   (a2-a1)* func.f_to_integrate(p, 0.5*(a1+a2) );
        }
        else {
            if (n >2) {
                for (j=1; j<= (n-2); j++) {
                    it=it*3;
                }
            }
        }
        //System.out.print("\n midpnt:  it=" + it + "\n");
       
        tnm = (double)(it);
        del=(a2-a1)/(3.*tnm);
        ddel=del+del;     //The added points alternate in spacing between del and ddel.
        x=a1+0.5*del;
        sum=0.;
        for (j=1 ; j <= it; j++) {
            sum = sum + func.f_to_integrate(p,x);
            x = x + ddel;
            sum = sum + func.f_to_integrate(p,x);
            x = x+del;
        }
        return (s+(a2-a1)*sum/tnm)/3.0;
        //The new sum is combined with the old integral to give a refined integral.
    }
}
