package simulations.objects;

import core.math.Vec3;

public class MovingMagneticField extends BaseObject {
  
  private Vec3 B, VelB;
  
  public MovingMagneticField(Vec3 B, Vec3 VelB)
  /* Constructs a new ConstantFields with the 'E' as V x B 
   *   and 'B' as the magnetic field filling all of space. */
  {
    this.B = B;
    this.VelB = VelB;
  }
  
  public Vec3 Bfield(Vec3 x, Vec3 B)
   /* Sets 'B' to the value of the magnetic field at 'x'. 'x' is not modified.
   * Returns: resulting 'B' */
 {
    return B.Set(this.B);
  }
  
    public Vec3 Efield(Vec3 x, Vec3 E)
  {
    Bfield(x, E).Cross(this.VelB).Scale(-1.);
    return E;
         //return E.Set(new Vec3(0,0,0));
   }

}