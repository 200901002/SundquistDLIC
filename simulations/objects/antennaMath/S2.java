package simulations.objects.antennaMath;
import java.lang.*;/*
 * S2.java  
 * Created on May 29, 2002, 3:38 PM
 */

public class S2 implements Integrable {
    
    /** Creates new Func1 */
    public S2() {
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
        f = (  g_two(d1,rk) + g_two(d2,rk)  )/zs;
        
        return 2.0*sk*f*Math.sin(ak - sk);
    }
    
    public double g_two(double R, double r) {
        return ( R*Math.cos(R-r) - Math.sin(R-r) )/(R*R*R);
    }
}
