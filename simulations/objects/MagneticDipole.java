package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

public class MagneticDipole extends BaseObject {

  static public double Bfactor = Constants.u0/(4.0*Math.PI);
  public Vec3 p, v, m;
  
  public MagneticDipole(Vec3 p, Vec3 m)
  {
    this.p = p;
    this.v = Vec3.Zero;
    this.m = m;
  }
  
  public MagneticDipole(Vec3 p, Vec3 v, Vec3 m)
  {
    this.p = p;
    this.v = v;
    this.m = m;
  }
  
  public void Evolve(double dt)
  {
    // Fix: Implement a better integration method
    p.AddScaled(v,dt);
  }

  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    return Bfield(x, E).Cross(v);
  }

  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    B.Set(x).Sub(p);
    double r2 = B.len2();
    r2 = r2;
    if (r2>0.0)
      return B.Scale(m.dot(B)*3.0/r2).Sub(m).Scale(1./Math.pow(r2,1.5));
    else
      return B.SetZero();
  }

}