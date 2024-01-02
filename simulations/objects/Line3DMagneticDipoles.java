package simulations.objects;

import simulations.Constants;
import core.math.Vec3;

/** 
 * Line 3D Magnetic Dipoles:  This BaseObject calculates the non-relativistic electric and magnetic fields of 
 * a line of magnetic dipoles is at position p moving with velocity v.  The axis of the line is given by the 
 * unit vector tline and the magnetic dipole moment per unit length vector is given by mdipole.  */
public class Line3DMagneticDipoles extends BaseObject {

	
  /** the magnetic dipole moment per unit length vector of the line of dipoles */
  public Vec3 mdipole;
  /** A unit vector along the axis of the line of dipoles */
  public Vec3 tline;
  /** the position of the beginning of the line of dipoles */
  public Vec3 p;
  /** Velocity of the line of magnetic dipoles */
  public Vec3 v; 
  /** The location of the thin conducting sheet in z  */
  public double zplane; 
  /** image charge parameter for line of dipoles.  If image = 0 this is just a normal line of dipoles;  
   * if image = 1 then this is a line dipole whose magnetic and electric fields are non-zero only for 
   * observers with z > 0; if image = -1 then this is a line dipole whose magnetic and electric fields 
   * are non-zero for observers with z < 0  */
  public int image; 
  
/** constructor for the line of dipoles  
 *  @param mdipole the magnetic dipole moment per unit length
 *  @param tline A unit vector along the axis of the line of dipoles. 
 *  @param p The position of the beginning of the line of dipoles.
 *  @param zplane The location of the conducting plane.  
 *  @param image The image charge parameter for the line of dipoles.  
 *  */
  public Line3DMagneticDipoles(Vec3 mdipole, Vec3 tline, Vec3 p, Vec3 v, double zplane, int image){
    this.mdipole = mdipole;
    this.p = p;
    this.v = v;
    this.tline = tline;
    this.image = image;
    this.zplane = zplane;
  }
  /** the magnetic field of a non-relativistic line of moving magnetic dipoles
   * @param x the position of the observer
   * @param B the magnetic field at the position of the observer due to the line dipole at p (calculated)
   * @return B the magnetic field at the position of the observer due to the line dipole at p (calculated)
   */
  public Vec3 Bfield(Vec3 x, Vec3 B) {
    /* calculates B */
	Vec3 rad = new Vec3(0.,0.,0.);
	Vec3 radperp = new Vec3(0.,0.,0.);
	Vec3 mdipoleperp = new Vec3(0.,0.,0.);
	Vec3 radperpunit = new Vec3(0.,0.,0.);
	Vec3 vect1= new Vec3(0.,0.,0.);
	Vec3 vect2= new Vec3(0.,0.,0.);
	Vec3 vect3= new Vec3(0.,0.,0.);
	double radperpmag,dotmr,dottm,dotrperpunitm,dottr,radmag3,radmag,inter;
	int printout = 0;
    rad.Set(x).Sub(p);  
	radmag3=rad.len3();
    radperp.Set(rad.perp(tline));  // this is the perp part of the relative radius vector
    radperpmag=radperp.len(); // this is the magnitude of the perp part of the relative radius vector
    if (radmag3 != 0. && radperpmag !=0 ) {
    	radmag = rad.len();
        tline.Unit();  // makes sure that tline is a unit vector
        mdipoleperp.Set(mdipole.perp(tline));  // this is the perp part of mdipole

        radperpunit.Set(rad.perpunit(tline));  //  this is the unit vector of the perp part of the radius vector
        radperpunit.Unit();
     
        dotmr =  mdipole.dot(rad);  // this is the dot product of m and r
        dotrperpunitm = mdipole.dot(radperpunit); // this is the dot product of m and rperpunit
        dottr= tline.dot(rad);  // this is the dot product of tline and rad
        dottm = tline.dot(mdipole);  // this is the dot product of tline and mdipole
        inter= radperpmag*dottm-dottr*dotrperpunitm;
	    vect1.Set(radperpunit.scale(inter));
	    vect1.Scale(-1./radmag3);
	    vect2.Set(tline.scale(-dotmr/radmag3));
	    vect3.Set(mdipoleperp.sub(radperpunit.scale(2.*dotrperpunitm)));
	    //System.out.println("vect3  vx" + vect3.x + "  vy " + vect3.y + "   vz " + vect3.z);
	    vect3.Scale((-1.-dottr/radmag)/(radperpmag*radperpmag));
	    B.Set(vect1.add(vect2.add(vect3)));
	    B.Scale(Constants.Bfactor);
	    if( printout == 1){
	    System.out.println("Bfactor" + Constants.Bfactor);
	    System.out.println("mdipole  mx" + mdipole.x + "  my " + mdipole.y + "   mz " + mdipole.z);
	    System.out.println("radius  rx" + rad.x + "  ry " + rad.y + "   rz " + rad.z);
	    System.out.println("radperp  rx" + radperp.x + "  ry " + radperp.y + "   rz " + radperp.z);
	    System.out.println("radperpunit  rx" + radperpunit.x + "  ry " + radperpunit.y + "   rz " + radperpunit.z);
	    System.out.println("radmag " + radmag + " radperpmag " + radperpmag + " dotmr " + dotmr + "  dotrperpm " + dotrperpunitm + " dottr "  + dottr + " dottm " + dottm );
	    System.out.println("inter " + inter + " radmag3 " + radmag3 + " dotmr " + dotmr + "  dotrperpm " + dotrperpunitm + " dottr "  + dottr + " dottm " + dottm );
	    System.out.println("vect1  vx" + vect1.x + "  vy " + vect1.y + "   vz " + vect1.z);
	    System.out.println("vect2  vx" + vect2.x + "  vy " + vect2.y + "   vz " + vect2.z);
	    System.out.println("vect3  vx" + vect3.x + "  vy " + vect3.y + "   vz " + vect3.z);
	    System.out.println("         "  );
	    }
    }
    else B.SetZero();
    // set image line field to zero if above or below the plane, depending on the value of image (or not if image = 0)
    if (image == 1 && x.z > zplane) B.SetZero();
    if (image == -1 && x.z <= zplane) B.SetZero();
    //System.out.println("zplane" + zplane);
    return B;
  }
  
  /** the electric field of a moving monopole
   * @param x the position of the observer
   * @param E the electric field at the observer's position if the monopope is at p (calculated) 
   * @return zero for a stationary charge */  
  public Vec3 Efield(Vec3 x, Vec3 E) {
     return Bfield(x, E).Cross(v);
	}

  
  /** writes properties of the point charge to a string */  

       public String toString() {
	    return "Line of Magnetic Dipoles: position (" + this.p.x + ", " + this.p.y + ", " + this.p.z + ";  mdipole (" + this.mdipole.x + ", " + this.mdipole.y
	    + ", " + this.mdipole.z + ";  tline (" + this.tline.x + ", " + this.tline.y
	    + ", " + this.tline.z + ";  velocity (" + this.v.x + ", "
            + this.v.y + ", " + this.v.z + ")";
    }

}