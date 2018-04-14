package perceptron;

public class Iris {

	private int id;
	private double a;
	private double b;
	private double c;
	private double d;
	private String className;

	public Iris(int id, double a, double b, double c, double d, String className) {
		this.id = id;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.className = className;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getclassName() {
		return className;
	}

	public void setclassName(String className) {
		this.className = className;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}
}
