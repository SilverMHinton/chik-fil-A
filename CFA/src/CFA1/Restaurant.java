package CFA1;

import java.util.HashMap;
import java.util.Map;

public class Restaurant {

	String name = "chik-fil-A";
	int employeeCount;
	String schedule;
	Menu menu;
	Map<String, Integer> inventory = new HashMap<>(); 
	
	public Restaurant() {
		menu = new Menu();
	}
	
	public void addEmployee() {
		employeeCount++;
	}
	
	public void removeEmployee() {
		if (employeeCount > 0) {
			employeeCount--;
		}
	}
	
	public void editSchedule(String newSchedule) {
		schedule = newSchedule;
	}
	
	public void editInventory(String item, int amount) {
		inventory.put(item, amount);
	}
	
	public void displayRestaurant() {
		System.out.println("Restaurant: " + name);
		System.out.println("Employee Count: " + employeeCount);
		System.out.println("Schedule: " + schedule);
		
		System.out.println("Inventory: ");
		for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
}
