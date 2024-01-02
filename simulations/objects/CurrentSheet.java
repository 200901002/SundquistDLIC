package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

public class CurrentSheet extends BaseObject {

  public Vec3 x, K;
  double Econstant;
  
  public CurrentSheet(Vec3 x, Vec3 K, double Econstant)
  {
    this.x = x;
    this.K = K;
    this.Econstant = Econstant;
  }
  
  public Vec3 getK(double dt)
  {
    return K;
  }
  
  
  public void Evolve(double dt)
  {
  
  }
 
  public Vec3 Efield(Vec3 xobs, Vec3 E)
  {
    //Vec3 xdif = x.Sub(this.x);
    //System.out.println(" Efield this.x.x " + this.x.x + " this.x.y " + this.x.y + "  this.x.z  " + this.x.z );
    //System.out.println(" Output from Efield x.x " + x.x + " x.y " + x.y + "  x.z  " + x.z );
    //double xxdif = xdif.dot(Vec3.Xhat);
    double xxdif = xobs.x - this.x.x;
    //System.out.println(" xxdiff   " + xxdif );
    double xmag = Math.abs(xxdif);
    double dtplus = xmag/Constants.c;
    Vec3 kplus = getK(dtplus);
    //System.out.println(" Output from Efield kplus.x " + kplus.x + " kplux.y " + kplus.y + " kplus.z  " + kplus.z );
    //System.out.println(" Output from Efield kminus.x " + kminus.x + " kminus.y " + kminus.y + " kminus.z  " + kminus.z );
    if ( xxdif > 0. ) E.Set(kplus).Scale(-0.5);
    else E.Set(kplus).Scale(-0.5);
    // add in constant field in the x direction if appropriate
    if ( xxdif > 0. ) E.AddScaled(Vec3.Xhat,Econstant);
    else E.AddScaled(Vec3.Xhat,-Econstant);
    //System.out.println(" Output from Efield E.x " + E.x + " E.y " + E.y );
    //if( x.x == -20 ) System.out.println(" xxdif " + xxdif + " x sht " + x.x + " x obs " + xobs.x + " E.x " + E.x  );
    return E;
    
  }

  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    double xdif = x.x-this.x.x;
    if ( xdif < 0. ) Efield(x, B).Cross(Vec3.Xhat).Scale(1.0/Constants.c);
    else Efield(x, B).Cross(Vec3.Xhat).Scale(-1.0/Constants.c);
    return B;
   }

}