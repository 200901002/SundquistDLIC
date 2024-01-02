package simulations.objects.antennaMath;

public class Bphi{
    double tw;
    double rhok;
    double zk;
    double ak;
    
    public Bphi(double tw, double rhok, double zk, double ak) {
        //Magnetic field of linear antenna, rho-component.
        //using Romberg OR Chebyshev integration.
        this.tw=tw;
        this.rhok=rhok;
        this.zk=zk;
        this.ak=ak;
    }
    
    public double getBphi() {
        return Bphi(tw,rhok,zk,ak);
    }
    
    public double Bphi(double tw, double rhok, double zk, double ak) {
        
        int np,nmx,nq;
        double pp,tr,cnq,cnmx,a,sum1,sum2,dsum1,dsum2;
        int nc [] = {0};
        
        np = 3;
        double p [] = {rhok,zk,ak};
        int nqc [] = {0};
        pp = 2*Math.PI;
        cnq = 20.0*(2.0 + 4.0*ak/pp);	//# of Romberg integration steps
        cnmx = 16.0*(1.0 + ak/pp);          //max # of Chebyshev coefficients
        nq = (int)cnq;
        nmx = (int)cnmx;
        a=0.;
        S3 iFunc3 = new S3();
        S4 iFunc4 = new S4();
        
        //Contribution to S1
        if ( limOval(rhok, zk, ak) <= 1. )      //Use Romberg
        {
            sum1 = RombInt.RombInt(iFunc3,p,a,ak,nq,nqc) ;
            if  (nqc[0]  !=  0) {
                return 1.37E-10;
            }
        }
        else                                     //Use Chebyshev
        {
            sum1 = ChebyshevIntegration.qCheb(iFunc3,p,a,ak,nmx,nc);
            if (nc[0] == nmx) {
                return 1.37E-11;
            }
        }
        
        //Contribution to S2
        if ( limOval(rhok, zk, ak) <= 1. )  	  //Use Romberg
        {
            sum2 = RombInt.RombInt(iFunc4,p,a,ak,nq,nqc) ;
            if (nqc[0] != 0)  {
                return 1.37E-12;
            }
        }
        else                                     //Use Chebyshev
        {
            sum2 = ChebyshevIntegration.qCheb(iFunc4,p,a,ak,nmx,nc);
            if (nc[0] == nmx) {
                return 1.37E-13;
            }
        }
        
        tr = tw - Math.sqrt(rhok*rhok+zk*zk);
        return ( sum2*Math.cos(tr) + sum1*Math.sin(tr) );
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
}