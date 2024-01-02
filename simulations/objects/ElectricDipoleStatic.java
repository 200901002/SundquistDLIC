package simulations.objects;  

import simulations.Constants;
import core.math.Vec3;
/** static electric dipole including a pauli repulsion term */
public class ElectricDipoleStatic extends BaseObject {
  public Vec3 p, v, dipole, omega;  // position, velocity, dipole moment vector, angular velocity vector
  public double radius;  // hard sphere radius
  
  public ElectricDipoleStatic(Vec3 p, Vec3 dipole, Vec3 omega, double radius)
  {
    this.p = p;
    this.v = Vec3.Zero;
    this.dipole = dipole;
    this.radius = radius;
    this.omega = omega;
  }
  
  public ElectricDipoleStatic(Vec3 p, Vec3 v, Vec3 dipole, Vec3 omega, double radius)
  {
    this.p = p;
    this.v = v;
    this.dipole = dipole;
    this.radius = radius;
    this.omega = omega;
  }
  
  public Vec3 getP(double dt)
  {
    return dipole;
  }
  
  
  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    Vec3 r = x.sub(p);
    double rmag = r.len();
    if (rmag==0.0 || this.dipole.len()==0.)
      return E.SetZero();
    else {
      r.Scale(1.0/rmag);
      Vec3 E1 = r.scale(3.0*dipole.dot(r)).Sub(dipole).Scale(1.0/(rmag*rmag*rmag));
      return E.Set(E1).Scale(Constants.Efactor);
    }
  }
  
 /*  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    /*
    temp.Set(x).Sub(p);
    if (!temp.isZero()) {
      u.Set(temp).Unit().Scale(Constants.c).Sub(v);
      return E.Set(u).Scale(Constants.c2-v.len2()).Add(temp.cross(u.cross(a))).Scale(q*Constants.Efactor*temp.len()/Math.pow(temp.dot(u),3.0));
    } else
      return E.SetZero();
    
    E.Set(x).Sub(p);
    if (!E.isZero() )
      return E.Scale(1./E.len3());
    else
      return E.SetZero();
  } */

  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    Vec3 r = x.sub(p);
    Vec3 vtotal = v.add(r.Cross(omega));
    double rmag = r.len();
    if (rmag==0.0 || this.dipole.len()==0.)
      return B.SetZero();
    else {
      r.Scale(1.0/rmag);
      double dt = rmag/Constants.c;
      return Efield(x, B).Cross(vtotal).Scale(-1./Constants.c2);
      //Efield(x, B).Cross(v).Scale(-1.0/Constants.c2);
      //return B;
    }
  }
  
    public Vec3 Pfield(Vec3 x, Vec3 P)
  {

    P.Set(x).Sub(p);

    if (!P.isZero() && this.dipole.len()!=0.)
      return P.Scale(p.len()*Constants.Efactor*Math.pow(radius,9.)/Math.pow(P.len3(),4.));  // this is a 1/r11 (9+2) repulsion
    else
      return P.SetZero();
  }

      public String toString() 
    {
	    return "ElectricDipoleStatic:  radius "+ this.radius + " position (" + this.p.x + ", "
            + this.p.y + ", " + this.p.z +
	    ") velocity (" + this.v.x + ", " + this.v.y + ", " + this.v.z +") with dipole moment vector (" 
	    + this.dipole.x + ", " + this.dipole.y + ", " + this.dipole.z + ")";
    }

}