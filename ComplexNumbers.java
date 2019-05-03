
/**
 * Class describes and offers methods for work with complex numbers.
 * @author Borna Majstorović
 *
 */
public class ComplexNumbers{

	/**
	 * Private variables for real and imaginary part of complex number
	 */
	private double real, imaginary;

	/**
	 * Constructor for ComplexNumbers
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumbers(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Method creates complex number from real part
	 * @param real real part of complex number
	 * @return complexNumber (real,0)
	 */
	public static ComplexNumbers fromReal(double real) {

		return new ComplexNumbers(real, 0.0); 
	}

	/**
	 * Method creates complex number from imaginary part
	 * @param imaginary imaginary part of complex number
	 * @return complexNumber (0,imaginary)
	 */
	public static ComplexNumbers fromImaginary(double imaginary) {

		return new ComplexNumbers(0.0, imaginary);
	}

	/**
	 * Method creates complex number from his magnitude and angle through polar form
	 * @param magnitude  magnitude
	 * @param angle angle 
	 * @return complexNumber
	 */
	public static ComplexNumbers fromMagnitudeAndAngle(double magnitude, double angle) {

		return new ComplexNumbers(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Parse string into complex number 
	 * @param s string that will be parse
	 * @return complex number from string
	 */
	public static ComplexNumbers parse(String s) {
		s = s.replaceAll(" ", "");

		if(s.equals("i")) {
			return new ComplexNumbers(0, 1);
		}

		if(s.equals("-i")) {
			return new ComplexNumbers(0, -1);
		}

		if(!s.contains("i")) {
			try {
				return new ComplexNumbers(Double.parseDouble(s), 0);
			} catch(NumberFormatException e) {
				System.err.println("Can't parse " + e);
			}
		} else {

			s = s.substring(0, s.length()-1);

			if(s.contains("+")) {
				String[] parts = s.split("\\+");
				String real = parts[0];
				String imaginary = parts[1];

				try {
					return new ComplexNumbers(Double.parseDouble(real), Double.parseDouble(imaginary));
				} catch(NumberFormatException e) {
					System.err.println("Can't parse " + e);
				}
			} else if(s.contains("-")) {
				String[] parts = s.split("-");
				String real = parts[0];
				String imaginary = parts[1];

				try {
					return new ComplexNumbers(Double.parseDouble(real), -Double.parseDouble(imaginary));
				} catch(NumberFormatException e) {
					System.err.println("Can't parse " + e);
				}
			} else {
				try {
					return new ComplexNumbers(0, Double.parseDouble(s));
				} catch(NumberFormatException e) {
					System.err.println("Can't parse" + e);
				}

			}
		}


		return new ComplexNumbers(0.0,0.0);
	}

	/**
	 * Getter for real part
	 * @return real
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part
	 * @return imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Getter for magnitude through real and imaginary part
	 * @return magnitude (root of real squared plus imaginary squared)
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	/**
	 * Getter for angle from 0 to 2pi
	 * @return angle
	 */
	public double getAngle() {
		double result;
		if(Math.atan2(imaginary, real) < 0) {
			result = Math.atan2(imaginary, real) + 2*Math.PI;
		} else {
			result = Math.atan2(imaginary, real);
		}
		return result;
	}
	/**
	 * Method adds two complex numbers
	 * @param c second complex number
	 * @return new complex number that is sum of two complex numbers
	 */
	public ComplexNumbers add(ComplexNumbers c) {
		double reala = this.real + c.real;
		double imaginarya = this.imaginary + c.imaginary;
		return new ComplexNumbers(reala, imaginarya);
	}

	/**
	 * Method subtracts two complex numbers
	 * @param c second complex number
	 * @return new complex number that is difference of two given complex numbers
	 */
	public ComplexNumbers sub(ComplexNumbers c) {
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		return new ComplexNumbers(real, imaginary);
	}

	/**
	 * Method multiplies two complex numbers
	 * @param c second complex number
	 * @return new complex number that is product of two given complex numbers
	 * @throws NullPointerException
	 */
	public ComplexNumbers mul(ComplexNumbers c) {
		if (c == null) {
			throw new NullPointerException("Argument can't be null");
		}
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumbers(real, imaginary);
	}

	/**
	 * Method multiplies two complex numbers
	 * @param c second complex number
	 * @return new complex number that is
	 * @throws NullPointerException
	 */
	public ComplexNumbers div(ComplexNumbers c) {
		if (c == null) {
			throw new NullPointerException("Argument can't be null");
		}
		double magnitude = this.getMagnitude() / c.getMagnitude();
		double angle = this.getAngle() - c.getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Method calculates complex number to the power of n
	 * @param n  exponent
	 * @return complex number to the power of n
	 * @throws IllegalArgumentException
	 */
	public ComplexNumbers power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power can't be negative");
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Method calculates n-th root of complex number
	 * @param n root
	 * @return array of roots of complex numbers from 1 to n
	 * @throws IllegalArgumentException
	 */
	public ComplexNumbers[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root can't be negative or zero");
		}
		double angle = getAngle() / n;
		double magnitude = Math.pow(getMagnitude(), 1 / n);
		ComplexNumbers[] roots = new ComplexNumbers[n];
		for (int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(magnitude, angle);
			angle += 2 * Math.PI / n;
		}
		return roots;
	}

	/**
	 * Method returns string in form of real part + complex part i
	 */
	@Override
	public String toString() {
		if (real == 0) {
			return imaginary + "i";
		} else if(imaginary == 0) {
			return real + "";
		}else if(imaginary > 0){
			return real + " + " + imaginary + "i";
		} else {
			return real + "-" + imaginary + "i";
		}
	}



}
