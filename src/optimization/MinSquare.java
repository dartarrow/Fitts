package optimization;

/*
    LmderTest_f77.java copyright claim:

    This software is based on public domain MINPACK routines.
    It was translated from FORTRAN to Java by a US government employee 
    on official time.  Thus this software is also in the public domain.


    The translator's mail address is:

    Steve Verrill 
    USDA Forest Products Laboratory
    1 Gifford Pinchot Drive
    Madison, Wisconsin
    53705


    The translator's e-mail address is:

    steve@ws13.fpl.fs.fed.us


***********************************************************************

DISCLAIMER OF WARRANTIES:

THIS SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND. 
THE TRANSLATOR DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS 
REGARDING THE SOFTWARE OR DOCUMENTATION IN TERMS OF THEIR CORRECTNESS, 
RELIABILITY, CURRENCY, OR OTHERWISE. THE ENTIRE RISK AS TO 
THE RESULTS AND PERFORMANCE OF THE SOFTWARE IS ASSUMED BY YOU. 
IN NO CASE WILL ANY PARTY INVOLVED WITH THE CREATION OR DISTRIBUTION 
OF THE SOFTWARE BE LIABLE FOR ANY DAMAGE THAT MAY RESULT FROM THE USE 
OF THIS SOFTWARE.

Sorry about that.

***********************************************************************


History:

Date        Translator        Changes

11/28/00     Steve Verrill     Translated

*/

/**
*
*This class tests the Minpack_f77.lmder1_f77 method, a Java
*translation of the MINPACK lmder1 subroutine.
*This class is based
*on FORTRAN test code for lmder that is available at Netlib
*(<a href="http://www.netlib.org/minpack/ex/">
*http://www.netlib.org/minpack/ex/</a>).  
*The Netlib test code was provided by the authors of MINPACK,
*Burton S. Garbow, Kenneth E. Hillstrom, and Jorge J. More.
*
*@author (translator) Steve Verrill
*@version .5 --- November 28, 2000
*
*/

public class MinSquare extends Object implements Lmder_fcn {

// epsmch is the machine precision
   static final double epsmch = 2.22044604926e-16;
   static final double zero =    0.0;
   static final double one =    1.0;
   static final double ten =    10.0;
   public static final int NO_OUTPUT = 0;
   public static final int FULL_OUTPUT = 1;
   
   double A[][]={ {0} };
   
   int nfev = 0;
   int njev = 0;
   public int output;
   

/*

Here is a portion of the documentation of the FORTRAN version
of this test code:

c     **********
c
c     this program tests codes for the least-squares solution of
c     m nonlinear equations in n variables. it consists of a driver
c     and an interface subroutine fcn. the driver reads in data,
c     calls the nonlinear least-squares solver, and finally prints
c     out information on the performance of the solver. this is
c     only a sample driver, many other drivers are possible. the
c     interface subroutine fcn is necessary to take into account the
c     forms of calling sequences used by the function and jacobian
c     subroutines in the various nonlinear least-squares solvers.
c
c     subprograms called
c
c       user-supplied ...... fcn
c
c       minpack-supplied ... dpmpar,enorm,initpt,lmder1,ssqfcn
c
c       fortran-supplied ... Math.sqrt
c
c     argonne national laboratory. minpack project. march 1980.
c     burton s. garbow, kenneth e. hillstrom, jorge j. more
c
c     **********

*/
   /*
   public static void main(String Args[])
   {	
	   double ret[]={0,0,0,0};
	   MinSquare test = new MinSquare();
	   test.output=NO_OUTPUT;
	   double input[][]=
	   { { 0,0,0,0 },
		 { 0,1,1.6,-936 },
		 { 0,1,1.53,-843},
		 { 0,1,1.59,-842},
		 { 0,1,1.25,-921},
		 { 0,1,1.49,-998},
		 { 0,1,1.44,-1108},
		 { 0,1,1.39,-920},
		 { 0,1,1.68,-1248},
		 { 0,1,1.45,-842},
		 { 0,1,1.13,-812},
		 
	 
		};
	   test.setA(input);
	   test.solve(ret);
	   System.out.println(ret[1]+" "+ret[2]);
	   
   }*/
   
   public void setA(double in[][])
   {	int m = in[0].length;
   		int n = in.length;
   		
   		String s = "A=\n";
   		A = new double[n+1][m+1];
   		for(int i=0;i<n;i++)
   		{	for(int j=0;j<m;j++)
   			{	A[i+1][j+1]=in[i][j];
   				s += in[i][j]+" ";
   			}
   			s += "\n";
   		}
	    if(output == FULL_OUTPUT) System.out.println(s);
   }

   public void solve(double ret[]) {

      int i,ic,k,m,n,ntries;
      int info[] = new int[2];
      double factor,tol;
      double fjac[][] = new double[502][5];
      double fvec[] = new double[502];
      double x[] = new double[5];
      int ipvt[] = new int[5];

      tol = Math.sqrt(epsmch);
      ic = 0;

      n = A[1].length-2;
      //System.out.println(n);
      m = A.length-1;
      //System.out.println(m);
      ntries = 1;
      //LmderTest2 lmdertest = new LmderTest2();
      factor = one;
      for (k = 1; k <= ntries; k++) 
      {		ic++;
/*
            LmderTest2.initpt_f77(n,x,factor);

            LmderTest2.ssqfcn_f77(m,n,x,fvec);

            fnorm1 = Minpack_f77.enorm_f77(m,fvec);

            System.out.print("\n\n\n\n dimensions:  " + n + "  " + m + "\n");
*/
            nfev = 0;
            njev = 0;

            Minpack_f77.lmder1_f77(this,m,n,x,fvec,fjac,tol,info,ipvt);
/*
            LmderTest2.ssqfcn_f77(m,n,x,fvec);

            fnorm2 = Minpack_f77.enorm_f77(m,fvec);

            fnm[ic] = fnorm2;

            System.out.print("\n Initial L2 norm of the residuals: " + fnorm1 +
                             "\n Final L2 norm of the residuals: " + fnorm2 +
                             "\n Number of function evaluations: " + lmdertest.nfev +
                             "\n Number of Jacobian evaluations: " + lmdertest.njev +
                             "\n Info value: " + info[1] +
                             "\n Final approximate solution: \n\n");*/
            String s="";
            for (i = 1; i <= n; i++) 
            {  s+=x[i]+" ";
               ret[i-1]=x[i];
            }
            if(output==FULL_OUTPUT) System.out.println(s);
         
            factor *= ten;
         }
      	
   }





   public void fcn(int m, int n, double x[], double fvec[],
                   double fjac[][], int iflag[]) {

/*

Documentation from the FORTRAN version:

      subroutine fcn(m,n,x,fvec,fjac,ldfjac,iflag)
      integer m,n,ldfjac,iflag
      double precision x(n),fvec(m),fjac(ldfjac,n)
c     **********
c
c     the calling sequence of fcn should be identical to the
c     calling sequence of the function subroutine in the nonlinear
c     least-squares solver. fcn should only call the testing
c     function and jacobian subroutines ssqfcn and ssqjac with
c     the appropriate value of problem number (nprob).
c
c     subprograms called
c
c       minpack-supplied ... ssqfcn,ssqjac
c
c     argonne national laboratory. minpack project. march 1980.
c     burton s. garbow, kenneth e. hillstrom, jorge j. more
c
c     **********

*/

	   if (iflag[1] == 1) 
	   {	ssqfcn_f77(m,n,x,fvec);
	   		nfev++;
	   }
      
	   if (iflag[1] == 2)
	   {	ssqjac_f77(m,n,x,fjac);
      		njev++;
	   }
	   return;

   }


   public void ssqjac_f77(int m, int n, double x[], double fjac[][]) {

/*

Documentation from the FORTRAN version:

      subroutine ssqjac(m,n,x,fjac,ldfjac,nprob)
      integer m,n,ldfjac,nprob
      double precision x(n),fjac(ldfjac,n)
c     **********
c
c     subroutine ssqjac
c
c     this subroutine defines the jacobian matrices of eighteen
c     nonlinear least squares problems. the problem dimensions are
c     as described in the prologue comments of ssqfcn.
c
c     the subroutine statement is
c
c       subroutine ssqjac(m,n,x,fjac,ldfjac,nprob)
c
c     where
c
c       m and n are positive integer input variables. n must not
c         exceed m.
c
c       x is an input array of length n.
c
c       fjac is an m by n output array which contains the jacobian
c         matrix of the nprob function evaluated at x.
c
c       ldfjac is a positive integer input variable not less than m
c         which specifies the leading dimension of the array fjac.
c
c       nprob is a positive integer variable which defines the
c         number of the problem. nprob must not exceed 18.
c
c     subprograms called
c
c       fortran-supplied ... Math.atan,dcos,dexp,dsin,Math.sqrt
c
c     argonne national laboratory. minpack project. march 1980.
c     burton s. garbow, kenneth e. hillstrom, jorge j. more
c
c     **********

*/

	   	String sx = "ssqjac:\n";
            
       	for (int i = 1; i <= m; i++) 
       	{   for (int j = 1; j <= n; j++) 
       		{ 	fjac[i][j]=A[i][j];
            	sx+=" "+fjac[i][j];                  
            }
            sx+="\n";
        }
        if(output==FULL_OUTPUT) System.out.println(sx);
        return;

   }




   public static void initpt_f77(int n, double x[], double factor) {

/*

Documentation from the FORTRAN version:


      subroutine initpt(n,x,nprob,factor)
      integer n,nprob
      double precision factor
      double precision x(n)
c     **********
c
c     subroutine initpt
c
c     this subroutine specifies the standard starting points for the
c     functions defined by subroutine ssqfcn. the subroutine returns
c     in x a multiple (factor) of the standard starting point. for
c     the 11th function the standard starting point is zero, so in
c     this case, if factor is not unity, then the subroutine returns
c     the vector  x[j] = factor, j=1,...,n.
c
c     the subroutine statement is
c
c       subroutine initpt(n,x,nprob,factor)
c
c     where
c
c       n is a positive integer input variable.
c
c       x is an output array of length n which contains the standard
c         starting point for problem nprob multiplied by factor.
c
c       nprob is a positive integer input variable which defines the
c         number of the problem. nprob must not exceed 18.
c
c       factor is an input variable which specifies the multiple of
c         the standard starting point. if factor is unity, no
c         multiplication is performed.
c
c     argonne national laboratory. minpack project. march 1980.
c     burton s. garbow, kenneth e. hillstrom, jorge j. more
c
c     **********

*/


      for (int j = 1; j <= n; j++) {
               x[j] = one;
            }
      return;

   }



   public void ssqfcn_f77(int m, int n, double x[], 
                                 double fvec[]) {


/*

Documentaton from the FORTRAN version:


      subroutine ssqfcn(m,n,x,fvec,nprob)
      integer m,n,nprob
      double precision x(n),fvec(m)
c     **********
c
c     subroutine ssqfcn
c
c     this subroutine defines the functions of eighteen nonlinear
c     least squares problems. the allowable values of (m,n) for
c     functions 1,2 and 3 are variable but with m .ge. n.
c     for functions 4,5,6,7,8,9 and 10 the values of (m,n) are
c     (2,2),(3,3),(4,4),(2,2),(15,3),(11,4) and (16,3), respectively.
c     function 11 (watson) has m = 31 with n usually 6 or 9.
c     however, any n, n = 2,...,31, is permitted.
c     functions 12,13 and 14 have n = 3,2 and 4, respectively, but
c     allow any m .ge. n, with the usual choices being 10,10 and 20.
c     function 15 (chebyquad) allows m and n variable with m .ge. n.
c     function 16 (brown) allows n variable with m = n.
c     for functions 17 and 18, the values of (m,n) are
c     (33,5) and (65,11), respectively.
c
c     the subroutine statement is
c
c       subroutine ssqfcn(m,n,x,fvec,nprob)
c
c     where
c
c       m and n are positive integer input variables. n must not
c         exceed m.
c
c       x is an input array of length n.
c
c       fvec is an output array of length m which contains the nprob
c         function evaluated at x.
c
c       nprob is a positive integer input variable which defines the
c         number of the problem. nprob must not exceed 18.
c
c     subprograms called
c
c       fortran-supplied ... Math.atan,Math.cos,dexp,Math.sin,Math.sqrt,dsign
c
c     argonne national laboratory. minpack project. march 1980.
c     burton s. garbow, kenneth e. hillstrom, jorge j. more
c
c     **********

*/


      int i,j;
      // Function routine selector.

            String sx="ssqfcn:\n";
            sx+=" x :";
            for(j=1;j<=n;j++)
            	sx+=x[j]+" ";
            sx+="\n";
            sx+=" ";
            for(i=1;i<=m;i++)
            {	fvec[i]=0;
            	for(j=1;j<=n;j++)
            	{	fvec[i]+=A[i][j]*x[j];
            	}
            	fvec[i]+=A[i][n+1];
            	sx+=fvec[i]+" ";
            }
                        
            if(output==FULL_OUTPUT) System.out.println(sx);
            return;

   		

   }

}
