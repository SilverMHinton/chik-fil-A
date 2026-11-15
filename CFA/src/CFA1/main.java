package CFA1;




import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Map;

public class main extends Application {

    private Restaurant restaurant = new Restaurant();

    private Stage window;
    private Scene scene;

    private Order currentOrder;
    private int nextOrderNumber = 1;

    private ListView<String> menuList = new ListView<>();
    private ListView<String> cartList = new ListView<>();

    private ObservableList<String> cartItems =
            FXCollections.observableArrayList();

    private TextArea receiptArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        window.setTitle("Chick-fil-A Management System");

        showHome();

        window.show();
    }

    // ---------------- HOME SCREEN ----------------

    private void showHome() {

        Label title = new Label("Chick-fil-A");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label subtitle = new Label("Restaurant Management System");

        Button customerButton = new Button("Customer");
        Button managerButton = new Button("Manager");

        customerButton.setPrefSize(180, 60);
        managerButton.setPrefSize(180, 60);

        customerButton.setOnAction(e -> showCustomer());

        managerButton.setOnAction(e -> showManager());

        VBox layout = new VBox(20,
                title,
                subtitle,
                customerButton,
                managerButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        scene = new Scene(layout, 750, 550);
        window.setScene(scene);
    }

    // ---------------- CUSTOMER SCREEN ----------------

    private void showCustomer() {

    	if (currentOrder == null) {
    	    currentOrder = new Order(
    	            1001,
    	            nextOrderNumber++,
    	            restaurant.getMenu()
    	    );
    	}

    	cartItems.setAll(currentOrder.getOrder());

        Label title = new Label("Customer Ordering");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Menu category buttons

        Button breakfastButton = new Button("Breakfast");
        Button entreesButton = new Button("Entrees");
        Button sidesButton = new Button("Sides");
        Button saladsButton = new Button("Salads");
        Button familyButton = new Button("Family Meals");

        HBox categoryButtons = new HBox(10,
                breakfastButton,
                entreesButton,
                sidesButton,
                saladsButton,
                familyButton
        );

        categoryButtons.setAlignment(Pos.CENTER);

        breakfastButton.setOnAction(e ->
                displayMenu(restaurant.getMenu().getBreakfast()));

        entreesButton.setOnAction(e ->
                displayMenu(restaurant.getMenu().getEntrees()));

        sidesButton.setOnAction(e ->
                displayMenu(restaurant.getMenu().getSides()));

        saladsButton.setOnAction(e ->
                displayMenu(restaurant.getMenu().getSalads()));

        familyButton.setOnAction(e ->
                displayMenu(restaurant.getMenu().getFamily()));

        // Menu and cart

        Label menuLabel = new Label("Menu Items");
        Label cartLabel = new Label("Your Order");

        menuList.setPrefHeight(250);
        cartList.setItems(cartItems);
        cartList.setPrefHeight(250);

        VBox menuBox = new VBox(10, menuLabel, menuList);
        VBox cartBox = new VBox(10, cartLabel, cartList);

        HBox lists = new HBox(20, menuBox, cartBox);
        HBox.setHgrow(menuBox, Priority.ALWAYS);
        HBox.setHgrow(cartBox, Priority.ALWAYS);

        // Quantity selection

        Spinner<Integer> quantitySpinner =
                new Spinner<>(1, 20, 1);

        Button addButton = new Button("Add to Order");

        addButton.setOnAction(e -> {

            String selectedItem =
                    menuList.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                showMessage("Please select a menu item.");
                return;
            }

            int quantity = quantitySpinner.getValue();

            for (int i = 0; i < quantity; i++) {
                currentOrder.addItem(selectedItem, 1);
                cartItems.add(selectedItem);
            }
        });

        Button checkoutButton = new Button("Checkout");
        Button backButton = new Button("Back");

        checkoutButton.setOnAction(e -> showCheckout());

        backButton.setOnAction(e -> showHome());

        HBox orderControls = new HBox(10,
                new Label("Quantity:"),
                quantitySpinner,
                addButton,
                checkoutButton
        );

        orderControls.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20,
                title,
                categoryButtons,
                lists,
                orderControls,
                backButton
        );

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 900, 650);
        window.setScene(scene);
    }

    // Displays the selected menu category

    private void displayMenu(Map<String, Double> items) {

        menuList.getItems().clear();

        for (String item : items.keySet()) {
            menuList.getItems().add(item);
        }
    }

    // ---------------- CHECKOUT SCREEN ----------------

    private void showCheckout() {

        if (currentOrder.getOrder().isEmpty()) {
            showMessage("Your order is empty.");
            return;
        }

        Transactions transaction =
                new Transactions(currentOrder);

        Label title = new Label("Checkout");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        receiptArea.clear();
        receiptArea.setEditable(false);

        StringBuilder receipt = new StringBuilder();

        receipt.append("------ RECEIPT ------\n");

        for (String item : currentOrder.getOrder()) {
            receipt.append(String.format(
                    "%s - $%.2f%n",
                    item,
                    currentOrder.getPrice(item)
            ));
        }

        receipt.append("---------------------\n");

        receipt.append(String.format(
                "Subtotal: $%.2f%n",
                transaction.getSubtotal()
        ));

        receipt.append(String.format(
                "Tax: $%.2f%n",
                transaction.getTax()
        ));

        receipt.append(String.format(
                "Total: $%.2f%n",
                transaction.getTotal()
        ));

        receiptArea.setText(receipt.toString());

        Button payButton = new Button("Complete Payment");
        Button backButton = new Button("Back to Menu");

        payButton.setOnAction(e -> {
            showMessage("Payment complete! (Simulation)");

            currentOrder = null;
            cartItems.clear();

            showHome();
        });

        backButton.setOnAction(e -> showCustomer());

        VBox layout = new VBox(20,
                title,
                receiptArea,
                payButton,
                backButton
        );

        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 500, 550);
        window.setScene(scene);
    }

    // ---------------- MANAGER SCREEN ----------------

    private void showManager() {

        Label title = new Label("Manager Dashboard");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Employee management

        Label employeeLabel = new Label(
                "Employees: " + restaurant.getEmployeeCount()
        );

        Button addEmployeeButton = new Button("Add Employee");
        Button removeEmployeeButton = new Button("Remove Employee");

        addEmployeeButton.setOnAction(e -> {
            restaurant.addEmployee();
            employeeLabel.setText(
                    "Employees: " + restaurant.getEmployeeCount()
            );
        });

        removeEmployeeButton.setOnAction(e -> {
            restaurant.removeEmployee();
            employeeLabel.setText(
                    "Employees: " + restaurant.getEmployeeCount()
            );
        });

        HBox employeeControls = new HBox(10,
                addEmployeeButton,
                removeEmployeeButton
        );

        // Schedule management

        TextField scheduleField = new TextField();

        scheduleField.setPromptText("Enter restaurant schedule");

        if (restaurant.getSchedule() != null) {
            scheduleField.setText(restaurant.getSchedule());
        }

        Button updateScheduleButton =
                new Button("Update Schedule");

        updateScheduleButton.setOnAction(e -> {
            restaurant.editSchedule(scheduleField.getText());
            showMessage("Schedule updated!");
        });

        // Inventory management

        TextField itemField = new TextField();
        itemField.setPromptText("Inventory item name");

        TextField amountField = new TextField();
        amountField.setPromptText("Quantity");

        Button updateInventoryButton =
                new Button("Update Inventory");

        ListView<String> inventoryList = new ListView<>();

        Runnable refreshInventory = () -> {
            inventoryList.getItems().clear();

            for (Map.Entry<String, Integer> entry :
                    restaurant.getInventory().entrySet()) {

                inventoryList.getItems().add(
                        entry.getKey() + ": " + entry.getValue()
                );
            }
        };

        refreshInventory.run();

        updateInventoryButton.setOnAction(e -> {

            String item = itemField.getText();

            try {
                int amount = Integer.parseInt(amountField.getText());

                restaurant.editInventory(item, amount);

                refreshInventory.run();

                itemField.clear();
                amountField.clear();

                showMessage("Inventory updated!");

            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid quantity.");
            }
        });

        VBox inventoryBox = new VBox(10,
                new Label("Inventory Management"),
                itemField,
                amountField,
                updateInventoryButton,
                inventoryList
        );

        Button backButton = new Button("Back to Home");

        backButton.setOnAction(e -> showHome());

        VBox layout = new VBox(15,
                title,
                new Label("Employee Management"),
                employeeLabel,
                employeeControls,
                new Label("Restaurant Schedule"),
                scheduleField,
                updateScheduleButton,
                inventoryBox,
                backButton
        );

        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 650, 750);
        window.setScene(scene);
    }

    // ---------------- MESSAGE BOX ----------------

    private void showMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Restaurant System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*
		
		//this is just a test to see that things are properly connected
		//we can change this later to be more adaptable to user
		
		// Create restaurant
        Restaurant restaurant = new Restaurant();

        // Add employees
        restaurant.addEmployee();
        restaurant.addEmployee();
        restaurant.addEmployee();

        // Set restaurant information
        restaurant.editSchedule("Monday - Saturday: 6:30 AM - 10:00 PM");

        // Add inventory
        restaurant.editInventory("Chick-fil-A Chicken Sandwich", 20);
        restaurant.editInventory("Waffle Fries (Medium)", 30);
        restaurant.editInventory("Chick-fil-A Nuggets", 25);

        // Display restaurant
        restaurant.displayRestaurant();

        System.out.println();

        // Create customer
        customer customer1 = new customer(
            101,
            "John",
            "Smith",
            "555-1234"
        );

        // Create order using restaurant's menu
        Order order1 = new Order(
            101,
            1,
            restaurant.getMenu()
        );

        // Add items
        order1.addItem("Chick-fil-A Chicken Sandwich", 2);
        order1.addItem("Waffle Fries (Medium)", 1);

        // Give order to customer
        customer1.placeOrder(order1);

        System.out.println();

        // Display customer
        customer1.displayCustomer();

        System.out.println();

        // Display customer's orders
        customer1.displayOrders();

        System.out.println();

        // Create transaction
        Transactions transaction = new Transactions(order1);

        // Display receipt and calculate total
        transaction.printReceipt();
        
		/*System.out.println("What month is it?");

		// print the menu

		System.out.println("What would you like to order?");

		System.out.println("Are you a rewards member?");

		System.out.println("Cash or Card?");

		System.out.println("Enter payment:");


	} 

}
*/