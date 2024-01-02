package simulations.objects;

import core.math.Vec3;

public class ConstantFields extends BaseObject {
  
  /* simulations.EM.ConstantFields: Constant E&M fields
   *
   * ConstantFields is a type of EMSource the fills all of space with
   * constant electric and magnetic fields.
   */
  
  private Vec3 E,B;
  
  public ConstantFields(Vec3 E, Vec3 B)
  /* Constructs a new ConstantFields with the 'E' as the electric field
   *   and 'B' as the magnetic field filling all of space. */
  {
    this.E = E;
    this.B = B;
  }

  public Vec3 Efield(Vec3 x, Vec3 E)
  /* Sets 'E' to the value of the electric field at 'x'. 'x' is not modified.
   * Returns: resulting 'E' */
  {
    return E.Set(this.E);
  }
  
  public Vec3 Bfield(Vec3 x, Vec3 B)
   /* Sets 'B' to the value of the magnetic field at 'x'. 'x' is not modified.
   * Returns: resulting 'B' */
 {
    return B.Set(this.B);
  }
  
}