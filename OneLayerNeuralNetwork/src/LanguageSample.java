public class LanguageSample {
	public double[] parameters;
	private String className;

	public LanguageSample(String className) {
		parameters = new double[26];
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
