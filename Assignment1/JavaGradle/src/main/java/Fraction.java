import java.io.*;
/**
 * Purpose: demonstrate simple Java Fraction class with command line,
 * jdb debugging, and Ant build file.
 *
 * Ser321 Foundations of Distributed Applications
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version January 2020
 */
public class Fraction {

   private int numerator, denominator;

   public Fraction(){
      numerator = denominator = 0;
   }

   public void print() {
    System.out.print(numerator + "/" + denominator );
   }

   public void setNumerator (int n ){
      numerator = n;
   }

   public void setDenominator (int d) {
      denominator = d;
   }

   public int getDenominator() {
      return denominator;
   }

   public int getNumerator() {
      return numerator;
   }

   public static void main (String args[]) {
      if (args.length == 2){
          int argX = 0;
          int argY = 1;
          try {
            // create a new instance
            // Fraction *frac = [[Fraction alloc] init];
            Fraction frac = new Fraction();
            argX = Integer.parseInt(args[0]);
            argY = Integer.parseInt(args[1]);
            // set the values
            frac.setNumerator(argX);
            frac.setDenominator(argY);

            // print it
            System.out.print("The fraction is: ");
            frac.print();
            System.out.println("");

          }catch(Exception e) {
           //e.printStackTrace();
	    //System.out.println("Not enough arguments or denominator of 0. Executing default fraction...");
            //frac.setNumerator(1);
            //frac.setDenominator(3);

            // print it
            //System.out.print("The fraction is: ");
            //frac.print();
            //System.out.println("");
 	    System.out.println("Arguments must be integers and denominator must not be 0.");
	    System.exit(1);
          }
      }
      else{
          System.out.println("Exactly 2 arguments should be provided.\n gradle runFraction --args='1 2'");
      }
}
}

