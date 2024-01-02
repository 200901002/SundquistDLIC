package simulations.objects;  

import core.math.Vec3;


public class MagneticDipoleStatic extends BaseObject {

  static protected double Bfactor = 1.0;
  public Vec3 p, v, dipole, omega;  // position, velocity, dipole moment vector, angular velocity vector
  public double radius;  // hard sphere radius
  
  public MagneticDipoleStatic(Vec3 p, Vec3 dipole, Vec3 omega, double radius)
  {
    this.p = p;
    this.v = Vec3.Zero;
    this.dipole = dipole;
    this.radius = radius;
    this.omega = omega;
  }
  
  public MagneticDipoleStatic(Vec3 p, Vec3 v, Vec3 dipole, Vec3 omega, double radius)
  {
    this.p = p;
    this.v = v;
    this.dipole = dipole;
    this.radius = radius;
    this.omega = omega;
  }
  
  public Vec3 getM(double dt)
  {
    return dipole;
  }
   
  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    Vec3 r = x.sub(p);
    double rmag = r.len();
    if (rmag==0.0 || this.dipole.len()==0.)  return B.SetZero();
    else 
    {
      r.Scale(1.0/rmag);
      Vec3 B1 = r.scale(3.0*dipole.dot(r)).Sub(dipole).Scale(1.0/(rmag*rmag*rmag));
      return B.Set(B1).Scale(Bfactor);
    }
  }


  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    Vec3 r = x.sub(p);
    Vec3 vtotal = v.add(r.Cross(omega));
    double rmag = r.len();
    if (rmag==0.0 || this.dipole.len()==0.) return E.SetZero();
    else return Bfield(x, E).Cross(vtotal);
  }
  
    public Vec3 Pfield(Vec3 x, Vec3 P)
  {

    P.Set(x).Sub(p);

    if (!P.isZero() && this.dipole.len()!=0.)
      return P.Scale(p.len()*Bfactor*Math.pow(radius,9.)/Math.pow(P.len3(),4.));  // this is a 1/r11 (9+2) repulsion
    else
      return P.SetZero();
  }

      public String toString() 
    {
	    return "MagneticDipoleStatic:  radius "+ this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z +
	    ") velocity (" + this.v.x + ", " + this.v.y + ", " + this.v.z +") with dipole moment vector (" 
	    + this.dipole.x + ", " + this.dipole.y + ", " + this.dipole.z + ")";
    }

}
