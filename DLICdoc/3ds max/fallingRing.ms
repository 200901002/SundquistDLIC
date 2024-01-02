-- FallingRing 3ds max script.   
-- The script creates a torus representing the falling ring, a cylinder representing the 
-- magnet, and a screen on which we will "project" the DLICs produced prior to this.  The script 
-- also computes the motion of the ring for the (alphaM betaM lambda) parameters parameterizing
-- that motion.  It also animates the motion of the ring, producing a 100 frame 
-- 3ds max sequence corresponding exactly to the scenario that produced the 100 DLICs.

-- fn FallingMagnetDynamics is a max script function 
-- which computes the derivatives of the evolution variables described in
-- Section 4.1 of the TEAL_Physics_Math documentation 
fn FallingMagnetDynamics Y_at_s:#(0,0,0) s_start:0 parameter:#(1.,1.) = 
(	
	local alphaMlocal,betaMlocal,dYds,F
	--falling magnet dynamics:  magnet/ring
	-- Y_at_s[1] is the vertical distance between the magnet and ring, normalized
	-- Y_at_s[2] is the current in the ring, normalized
	-- Y_at_s[3] is the speed of the ring, normlized
	-- dYds is the derivative of these parameters
	dYds=#(0.,0.,0)
	alphaMlocal = parameter[1]
	betaMlocal = parameter[2]
	F = 1.5*Y_at_s[1]/(1.+Y_at_s[1]^2)^2.5
	dYds[1]=Y_at_s[3]
	dYds[2]=-alphaMlocal*Y_at_s[2] + betaMlocal*F*Y_at_s[3]
	dYds[3]=-1. - F*Y_at_s[2]
	dYds
)

-- fn rungeKuttaFourthOrder is a max script function 
-- that takes one fourth order Runge Kutta step
fn rungeKuttaFourthOrder X_at_s:#() dXds_at_s:#() ndep:1 s_start:0. h:0.1 funcname:funcname parameter:#() =  
(
	local sa,sb,X_at_sa,Xt,dXa,dXb,ha,hb
	-- This is the basic fourth order RungeKutta stepping routine.
    -- Given the value X of the dependent variables at s, and a
    -- step size h, and the ability to evaluate the derivatives
    -- dX/ds at any s, the routine returns a 4th order estimate of the value
    -- of X at s + h.
    -- 
    -- X_at_s: This is the initial value of X at the starting value of s.
    -- dXds_at_s: This is the initial value of the derivatives dX/ds at the 
    --    starting value of s.  Must be provided to the routine.
    -- s_start: The initial value of the independent variable.
    -- h The step size.
    -- returns an estimate of the value of the dependent variables X at s + h.
	Xt=#()
	dXa=#()
	dXb=#()
	X_at_sa=#()
  	ha = h/6.
   	hb = h/2.
    for i = 1 to ndep do Xt[i] = X_at_s[i] + hb*dXds_at_s[i]
    sa = s_start + hb
    dXa = funcname Y_at_s:Xt s_start:sa parameter:parameter
   	for i = 1 to ndep do Xt[i] = X_at_s[i] + hb*dXa[i]
    dXb = funcname Y_at_s:Xt s_start:sa parameter:parameter 
    for i = 1 to ndep do  
    (
     	Xt[i] = X_at_s[i]+h*dXb[i]
       	dXb[i] = dXa[i]+dXb[i]
  	)
	sb = s_start+h
    dXa = funcname Y_at_s:Xt s_start:sb parameter:parameter 
    for i = 1 to ndep do X_at_sa[i] = X_at_s[i] + ha * (dXds_at_s[i] +dXa[i] + 2.*dXb[i])
    X_at_sa
)



-- This is the main routine that creates the geometic primitives, and 
-- calculates and animates the motion of the ring 

-- Set evolution parameters
alphaM = 0.   
betaM = 30.

-- position of the ring
x_ring=[0,0,0]

-- position of the magnet
x_magnet=[0,0,0]

-- radius of the ring
radius_ring =50.

-- initial height of the ring
InitialHeight=2*radius_ring
x_ring.z = InitialHeight

-- radius of the magnet
radius_magnet=.15*radius_ring

-- height of the magnet
height_magnet=2*radius_magnet

-- thickness of the torus representing the ring of current
thickness_ring=.05*radius_ring

-- create a torus representing the ring
CopperRing= torus radius1:radius_ring radius2:thickness_ring wirecolor:orange name:"CooperRing"

-- create a cylinder representing the magnet and center it at the origin
Magnet = cylinder radius:radius_magnet height:height_magnet wirecolor:white pos:[0,0,-height_magnet/2.] name:"Magnet"

-- create a box for the screen on which we project the DLICs, centered at the origin
Screen = Box lengthsegs:1 widthsegs:1 heightsegs:1 length:.01 width:320. height:240. pos:[0,0,-120] wirecolor:white name:"Screen"

-- define the number of frames in the animation
frames = 100
nrange = frames - 1 
animationrange=interval 0 nrange

-- set the time integration step size to get exactly one cycle in 100 frames 
-- for betaM = 30 and alphaM = 0
dtime=.042

-- set the initial configuraton of the system
-- V_at_t[1] is the height of the magnet above the ring, normalized to the radius of the ring
-- V_at_t[2] is the current in the ring, normalized
-- V_at_t[3] is the speed of the magnet, normlized
znorm=InitialHeight/radius_ring
-- start ring at znorm above the magnet with zero speed, no current in ring
V_at_t = #(znorm,0.,0.)  
s1 = 0.

-- now do the dynamics throughout the entire range of the animation
 for i = 0 to nrange do (		

-- set and print out in the listener window the location of the ring for the ith frame
	z = radius_ring*V_at_t[1]
	x_ring.z = z
	format "i % s1 % V_at_t % \n" i s1 V_at_t
	
-- animate the position of the ring  	
	frame=(i as time)
	animate on
	(
		at time frame 
		(
			CopperRing.pos=x_ring
		)
  	)
	
-- now evolve system one time step using the derivative function defined above and the fourth
-- order Runge Kutta integration defined above	
	dVdt_at_t = FallingMagnetDynamics Y_at_s:V_at_t t_start:0. parameter:#(alphaM, betaM)	
	RK = rungeKuttaFourthOrder X_at_s:V_at_t dXds_at_s:dVdt_at_t ndep:3 s_start:s1 h:dtime funcname:FallingMagnetDynamics parameter:#(alphaM,betaM) 
	
-- update the value of the position of the ring and of the time for the next frame
	for m = 1 to 3 do V_at_t[m] = RK[m]
	s1 = s1 + dtime
	
)

