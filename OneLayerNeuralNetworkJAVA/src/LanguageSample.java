public class LanguageSample {
	public double[] parameters;
	private String name;
	private String className;


	public LanguageSample(String name, String className) {
		parameters = new double[26];
		this.name = name;
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
	
	public String getName() {
		return name;
	}
}
