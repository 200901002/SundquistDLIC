package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

public class InfiniteWire extends BaseObject {

  static protected double Bfactor = Constants.u0/(2.0*Math.PI);
  public double I, dIdt;
  public Vec3 p, d, v;
  
  public InfiniteWire(double I, Vec3 p, Vec3 d)
  {
    this.I = I;
    this.dIdt = 0.0;
    this.p = p;
    this.d = d.unit();
    this.v = Vec3.Zero;
  }
  
  public InfiniteWire(double I, double dIdt, Vec3 p, Vec3 d)
  {
    this.I = I;
    this.dIdt = dIdt;
    this.p = p;
    this.d = d.unit();
    this.v = Vec3.Zero;
  }
  
  public InfiniteWire(double I, Vec3 p, Vec3 d, Vec3 v)
  {
    this.I = I;
    this.dIdt = 0.0;
    this.p = p;
    this.d = d.unit();
    this.v = v;
  }
  
  public InfiniteWire(double I, double dIdt, Vec3 p, Vec3 d, Vec3 v)
  {
    this.I = I;
    this.dIdt = dIdt;
    this.p = p;
    this.d = d.unit();
    this.v = v;
  }
  
  public void Evolve(double dt)
  {
    // Fix: Implement a better integration method
    p.AddScaled(v,dt);
    I += dt*dIdt;
  }

  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    double r = E.Set(x).Sub(p).PerpUnit(d).len();
    if (r>0.0)
      return Bfield(x, E).Cross(v).AddScaled(d, Bfactor*Math.log(r)*dIdt);
    else
      return Bfield(x, E).Cross(v);
  }

  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    B.Set(x).Sub(p).PerpUnit(d);
    double r2 = B.len2();
    if (r2>0.0)
      return B.Cross(d).Scale(-Bfactor*I/r2);
    else
      return B.SetZero();
  }

}