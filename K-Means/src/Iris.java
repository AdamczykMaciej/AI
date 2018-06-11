

public class Iris {

	private double a;
	private double b;
	private double c;
	private double d;
	private String className;
	private int clusterId;

	public Iris( double a, double b, double c, double d, String className, int clusterId) {
		this.clusterId = clusterId;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.className = className;
	}

	public int getClusterId() {
		return clusterId;
	}
	
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
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

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public double getD() {
		return d;
	}
}
