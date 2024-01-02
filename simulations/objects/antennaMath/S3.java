package simulations.objects.antennaMath;
import java.lang.*;/*
 * S3.java
 * Created on July 11, 2002
 */

public class S3 implements Integrable {
    
    /** Creates new Func3 */
    public S3() {
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
        f =  g_one(d1,rk) + g_one(d2,rk) ;
        
        return rhok*f*Math.sin(ak - sk);
    }
    
    public double g_one(double R, double r) {
        return ( R*Math.sin(R-r) + Math.cos(R-r) )/(R*R*R);
    }
}