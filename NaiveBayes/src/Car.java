import java.util.LinkedHashMap;

public class Car {
	private LinkedHashMap<String, String> attributes;

	public Car(String buyingPrice, String priceOfMaintenance, String numberOfDoors, String capacity, String sizeOfLuggageBoot,
			String carSafety, String classOfCar) {
		attributes = new LinkedHashMap<>();
		
		attributes.put("buyingPrice", buyingPrice);
		attributes.put("priceOfMaintenance", priceOfMaintenance);
		attributes.put("numberOfDoors", numberOfDoors);
		attributes.put("capacity", capacity);
		attributes.put("sizeOfLuggageBoot", sizeOfLuggageBoot);
		attributes.put("carSafety", carSafety);
		attributes.put("classOfCar", classOfCar);
	}
	
	public LinkedHashMap<String, String> getAttributes() {
		return attributes;
	}
	
	public void setClassOfCar(String classOfCar) {
		attributes.put("classOfCar", classOfCar);
	}
}
