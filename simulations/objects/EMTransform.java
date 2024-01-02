package simulations.objects;

import core.math.Vec3;

public class EMTransform extends BaseObject {
  
  // Note: EMTransform does *not* do concatenation of transformations
  // The net effect is to first rotate the EMSource and then translate it
  
  BaseObject source;
  
  Vec3[] rot = new Vec3[3];
  Vec3[] invrot = new Vec3[3];
  Vec3 origin;
 
  public EMTransform(BaseObject source)
  {
    this.source = source;
    Identity();
  }
  
  public void Identity()
  {
    rot[0] = Vec3.Xhat;
    rot[1] = Vec3.Yhat;
    rot[2] = Vec3.Zhat;
    invrot[0] = Vec3.Xhat;
    invrot[1] = Vec3.Yhat;
    invrot[2] = Vec3.Zhat;
    origin = Vec3.Zero;
  }
  
  public void Translation(Vec3 trans)
  {
    Identity();
    origin = trans.neg();
  }
  
  public void Rotation(Vec3 axis, double angle)
  {
    Identity();
    
    double w = Math.cos(-0.5*angle);
    Vec3 v = axis.scale(Math.sin(-0.5*angle));
    
    rot[0] = new Vec3(1.0 - 2.0*(v.x*v.x + v.y*v.y), 2.0*(v.x*v.y + w*v.z), 2.0*(v.x*v.z - w*v.y));
    rot[1] = new Vec3(2.0*(v.x*v.y + w*v.z), 1.0 - 2.0*(v.x*v.x + v.z*v.z), 2.0*(v.y*v.z - w*v.x));
    rot[2] = new Vec3(2.0*(v.x*v.z + w*v.y), 2.0*(v.y*v.z + w*v.x), 1.0 - 2.0*(v.x*v.x - v.y*v.y));
    
    invrot[0] = new Vec3(rot[0].x, rot[1].x, rot[2].x);
    invrot[1] = new Vec3(rot[0].y, rot[1].y, rot[2].y);
    invrot[2] = new Vec3(rot[0].z, rot[1].z, rot[2].z);
  }
  
  public void RigidTransform(Vec3 axis, double angle, Vec3 trans)
  {
    Rotation(axis, angle);
    origin = trans.neg();
  }
  
  private Vec3 temp = new Vec3();

  public Vec3 Efield(Vec3 x, Vec3 E)
  {
    temp.Set(rot[0].dot(x), rot[1].dot(x), rot[2].dot(x)).Add(origin);
    source.Efield(temp, E);
    return E.Set(invrot[0].dot(E), invrot[1].dot(E), invrot[2].dot(E));
  }
  
  public Vec3 Bfield(Vec3 x, Vec3 B)
  {
    temp.Set(rot[0].dot(x), rot[1].dot(x), rot[2].dot(x)).Add(origin);
    source.Bfield(temp, B);
    return B.Set(invrot[0].dot(B), invrot[1].dot(B), invrot[2].dot(B));
  }
  
}