package simulations.objects.antennaMath;

public class Erho{
    double tw;
    double rhok;
    double zk;
    double ak;
    
    public Erho(double tw, double rhok, double zk, double ak) {
        //Electric field of linear antenna, rho-component.
        //using Romberg OR Chebyshev integration.
        this.tw=tw;
        this.rhok=rhok;
        this.zk=zk;
        this.ak=ak;
    }
    
    public double getErho() {
        return Erho(tw,rhok,zk,ak);
    }
    
    public double Erho(double tw, double rhok, double zk, double ak) {
        
        int np,nmx,nq;
        double pp,tr,cnq,cnmx,a,da,b,sum1,sum2,dsum1,dsum2;
        int nc [] = {0};
        
        np = 3;
        nq = 20;
        double p [] = {rhok,zk,ak};
        int nqc [] = {0};
        pp = 2*Math.PI;
        cnq = 20.0*(2.0 + 4.0*ak/pp);	//# of Romberg integration steps
        cnmx = 16.0*(1.0 + ak/pp);          //max # of Chebyshev coefficients
        nq = (int)cnq;
        nmx = (int)cnmx;
        b = ak;
            Func1 iFunc1 = new Func1();
            Func2 iFunc2 = new Func2();

        //Contribution to P1
        if ( limOval(rhok, zk, ak) <= 1. )      //Use Romberg
        {
            da = b/cnq;
            a = da;
            sum1 = RomoInt.RomoInt(iFunc1,p,a,b,nq,nqc) ;
            if  (nqc[0]  !=  0) {
                return 1.37E-10;
            }
            sum1 = sum1 + dsum1(p,da);
        }
        else                                     //Use Chebyshev
        {
            a = 0.0;
            sum1 = ChebyshevIntegration.qCheb(iFunc1,p,a,b,nmx,nc);
            //nc=results[2];
            if (nc[0] == nmx) {
                return 1.37E-11;
            }
        }
        
        //Contribution to P2
        if ( limOval(rhok, zk, ak) <= 1. )  	  //Use Romberg
        {
            da = b/cnq;	 //original int.start a = 0
            a = da;
            sum2 = RomoInt.RomoInt(iFunc2,p,a,b,nq,nqc) ;
            if (nqc[0] != 0)  {
                return 1.37E-12;
            }
            sum2 = sum2 + dsum2(p,da);
        }
        else                                     //Use Chebyshev
        {
            a = 0;
            sum2 = ChebyshevIntegration.qCheb(iFunc2,p,a,b,nmx,nc);
            //nc=results[2];
            if (nc[0] == nmx) {
                return 1.37E-13;
            }
        }
        
        tr = tw - Math.sqrt(rhok*rhok+zk*zk);
        return rhok*zk*( -sum1*Math.cos(tr) + sum2*Math.sin(tr) );
    }
    
    public double limOval(double rhok, double zk, double ak) {
        double ax,az,ra,za;
        ax = Math.PI/4.;
        az = 1.25*ak;
        if (az > ax)  {az = az;} else{ az=ax;}
        ra=(rhok/ax);
        za=(zk/az);
        return ra*ra + za*za;       
    }
            
    public double dsum1(double[] p, double da) {
        double rhok,zk,ak,rp;
        rhok = p[0];
        zk = p[1];
        ak = p[2];
        rp = Math.sqrt(rhok*rhok+zk*zk);
        return  (1.-3.0/(rp*rp))*(  Math.cos(ak) + 2.0*da*Math.sin(ak)/3.)*da*da/(rp*rp*rp);
    }
    
    public double dsum2(double[] p, double da) {
        double rhok,zk,ak,rp;
        rhok = p[0];
        zk = p[1];
        ak = p[2];
        rp = Math.sqrt(rhok*rhok+zk*zk);
        return  -3.*( Math.cos(ak) + 2.0*da*Math.sin(ak)/3. )*da*da/(rp*rp*rp*rp);
    }
}