package CFA1;


import java.util.Map;
import java.util.HashMap;




public class Menu {

    Map<String, Double> entrees = new HashMap<>();
    Map<String, Double> breakfast = new HashMap<>();
    Map<String, Double> sides = new HashMap<>();
    Map<String, Double> family = new HashMap<>();

    public Menu() {
        loadMenu(entrees, MenuType.ENTREES);
        loadMenu(breakfast, MenuType.BREAKFAST);
        loadMenu(sides, MenuType.SIDES);
        loadMenu(family, MenuType.FAMILY);
    }

    private void loadMenu(Map<String, Double> menu,
                          MenuType type) {

        switch(type) {
            case ENTREES:
            	menu.put("Chick-fil-A Chicken Sandwich", 5.79);
	        	menu.put("Spicy Deluxe Sandwich", 6.89);
	        	menu.put("Spicy Chicken Sandwich", 6.09);
	        	menu.put("Chick-fil-A Nuggets", 5.79);
	        	menu.put("Grilled Nuggets", 6.69);
	        	menu.put("Chick-fil-A Deluxe Sandwich", 6.49);
	        	menu.put("Chick-fil-A Cool Wrap", 8.19);
	        	menu.put("Chick-fil-A Chick-n-Strips", 6.09);
	        	menu.put("Grilled Chicken Sandwich", 7.49);
	        	menu.put("Chick-fil-A Grilled Chicken Club Sandwich", 9.59);
	            break;

            case BREAKFAST:
            	menu.put("Chick-fil-A Chicken Biscuit", 3.65);
	        	menu.put("Spicy Chicken Biscuit", 3.89);
	        	menu.put("Chick-fil-A Chick-n-Minis", 4.65);
	        	menu.put("Egg White Grill", 5.79);
	        	menu.put("Hash Brown Scramble Burrito", 6.69);
	        	menu.put("Hash Brown Scramble Bowl", 6.49);
	        	menu.put("Chicken, Egg & Cheese Biscuit", 8.19);
	        	menu.put("Bacon, Egg & Cheese Biscuit", 6.09);
	        	menu.put("Sausage, Egg & Cheese Biscuit", 7.49);
	        	menu.put("Chicken, Egg & Cheese Muffin", 9.59);
	        	menu.put("Bacon, Egg & Cheese Muffin", 1.00);
	        	menu.put("Sausage, Egg & Cheese Muffin", 7.49);
	        	menu.put("Hash Browns", 7.49);
	        	menu.put("Berry Parfait", 7.49);
	        	menu.put("Fruit Cup", 7.49);
	            break;
            case SIDES:
            	menu.put("Waffle Fries (Small)", 2.49);
            	menu.put("Waffle Fries (Medium)", 2.90);
            	menu.put("Waffle Fries (Large)", 3.39);
            	menu.put("Mac & Cheese", 4.29);
            	menu.put("Fruit Cup", 4.49);
            	menu.put("Side Salad", 4.99);
            	menu.put("Kale Crunch Side", 3.99);
            	menu.put("Greek Yogurt Parfait", 4.69);
            	menu.put("Waffle Potato Chips", 2.25);
            	break;
            case SALADS:
                menu.put("Cobb Salad", 10.99);
                menu.put("Spicy Southwest Salad", 10.99);
                menu.put("Market Salad", 10.99);
                break;
            case FAMILY:
            	menu.put("Chick-fil-A Chick-n-Strips Family Style Meal", 34.99);
            	menu.put("Chick-fil-A Nuggets Family Style Meal", 32.99);
            	menu.put("Grilled Nuggets Family Style Meal", 36.99);
            	break;
            	
            default: break;
        }
    }
}

/*
public String addItems(String menu) {

return menu;
}
*/