package simulations.objects.antennaMath;
import java.lang.*;/*
 * S4.java
 */

public class S4 implements Integrable {
    
    /** Creates new Func4 */
    public S4() {
    }
    
    public double f_to_integrate(double[] p, double sk) {
        double eps,rhok,zk,ak,rk,zs,dd2,d1,d2,f,dd;
        eps = 1E-8;
        rhok = p[0];
        zk = p[1];
        ak = p[2];
        rk = Math.sqrt(rhok*rhok + zk*zk);
        zs = 2.0*zk*sk;
        dd2 = rk*rk + sk*sk;
        
        d1 = Math.sqrt(dd2 + zs);
        d2 = Math.sqrt(dd2 - zs);
        f = g_two(d1,rk) + g_two(d2,rk);
        
        return rhok*f*Math.sin(ak - sk);
    }
    
    public double g_two(double R, double r) {
        return ( R*Math.cos(R-r) - Math.sin(R-r) )/(R*R*R);
    }
}