using System;

class ComplexNumber {
    float real;
    float imaginary;
    // Constructor
    public ComplexNumber(float real, float imaginary) {
      this.real = real;
      this.imaginary = imaginary;
    }
    
    public override string ToString() {
        return(String.Format("( {0} + {1} i )", real, imaginary));
    }
    
    // Overloading function for + 
    public static ComplexNumber operator+(ComplexNumber c1, ComplexNumber c2) {
        return(new ComplexNumber(c1.real + c2.real, c1.imaginary + c2.imaginary));
    }
}

class MainClass {
    public static void Main() {
        ComplexNumber c1 = new ComplexNumber(11, 5);
        ComplexNumber c2 = new ComplexNumber(2, 6);
        ComplexNumber c3 = c1 + c2;
        // display results
        Console.WriteLine(c3);
    }
}