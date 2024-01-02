package simulations.objects;

import core.math.Vec3;

public class DataInputObject extends BaseObject {

	  public double t;
	  public int width;
	  public int height;
	  public double tstore = -10000.;
	  public double[][][] velocityField;

	  /** This is the object that both reads in the data array and then interpolates between values in the 
	   * array to produce a field B(x,t) for the DLIC program.
	   * @param width The width of the array
	   * @param height The height of the array
	   */
	  public DataInputObject(int width, int height)
	  {
	      this.width = width;
	      this.height = height;
	  }
	/** The method that returns the E field.  For the fluid flow purposes here, this is always zero.  */
	  public Vec3 Efield(Vec3 x, Vec3 E){
	      return E.SetZero();
	  }
	 	  
/** The method that returns the input velocity array evalutated at a position x on the screen.  
 * This method checks the time and if the time is different from the last time it inputs a new 
 * velocity array for the new time by invoking GetArray.
 */
	  public Vec3 Bfield(Vec3 x, Vec3 B){
		  /*if you have a new time, input a new velocity array appropriate for that new time 
		  by using the method GetArray */
	    if( t != tstore ) {
	    	System.out.println(" New array input at time:  " + t);
	    	velocityField = GetArray(width,height);
	    	tstore = t;
	    }
	    
	    /* map from the array input above to screen coordinates to find B at x */
	     int ix = (int)(x.x+ 1.*width/2.);
	     int iz = (int)(x.z + 1.*height/2.);
	     if ( ix < 0 ) ix = 0;
	     if ( ix > (width-1) ) ix = width-1;
	     if ( iz < 0 ) iz = 0;
	     if ( iz > (height-1) ) iz = height-1;
	     Vec3 fieldvector = new Vec3(velocityField[ix][iz][0],0.,velocityField[ix][iz][1]);
	     return B.Set(fieldvector);
	    }
	
	  /**  This method needs to be replaced with a method that reads in a data 
	   * array of velocities, one data array for each frame in the simulation. 
	   * To test the interface we make up the array values with a for loop.
	   */
	  	public double[][][] GetArray(int width, int height){
	  		double[][][] array = new double[width][height][2];
	  	    for (int i = 0; i < width; i++) {
	  	    	/* this the x component of velocity */
	    		for (int k = 0; k < height; k++) array[i][k][0] = -(k-height/2) ;}
	  	    for (int i = 0; i < width; i++) {
	  	    	/* this is the z component of velocity */
	    		for (int k = 0; k < height; k++) array[i][k][1] = +(i-width/2)-500.*this.t;	}
	  		return array;
	  	}
	  	
	  	
	       public String toString() 
	    {
	    	   return "Data Input Object Created:  width " + this.width + ";  height:  "+ this.height + " time:  " + this.t ;
	    }


}
