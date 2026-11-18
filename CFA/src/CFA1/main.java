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
    private int nextOrderNumber = 1; //first order

    private ListView<String> menuList = new ListView<>();
    private ListView<String> cartList = new ListView<>();

    private ObservableList<String> cartItems =
            FXCollections.observableArrayList(); //cart items

    private TextArea receiptArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage; // main window

        window.setTitle("Chick-fil-A Management System");

        showHome();

        window.show();
    }

    //Home screen

    private void showHome() {

        Label title = new Label("Chick-fil-A");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label subtitle = new Label("Restaurant Management System");

		Button customerButton = new Button("Customer");
		Button managerButton = new Button("Manager");

		customerButton.setPrefSize(180, 60);
		managerButton.setPrefSize(180, 60);

		customerButton.setOnAction(e -> showCustomer());

		managerButton.setOnAction(e -> showManagerLogin());

		VBox layout = new VBox(20, title, subtitle, customerButton, managerButton);

		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(30));

		scene = new Scene(layout, 750, 550);
		window.setScene(scene);
	}

	//Customer screen

	private void showCustomer() {

		if (currentOrder == null) {
			currentOrder = new Order(1001, nextOrderNumber++, restaurant.getMenu());
		} //Starting a new order if necessary

		cartItems.setAll(currentOrder.getOrder());

		Label title = new Label("Customer Ordering");
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		//Menu category buttons

		Button breakfastButton = new Button("Breakfast");
		Button entreesButton = new Button("Entrees");
		Button sidesButton = new Button("Sides");
		Button saladsButton = new Button("Salads");
		Button familyButton = new Button("Family Meals");
		Button beveragesButton = new Button("Beverages");

		HBox categoryButtons = new HBox(10, breakfastButton, entreesButton, sidesButton, saladsButton, familyButton,
				beveragesButton);

		categoryButtons.setAlignment(Pos.CENTER);
		
		//When category button pressed, display those menuItems

		breakfastButton.setOnAction(e -> displayMenu(restaurant.getMenu().getBreakfast()));

		entreesButton.setOnAction(e -> displayMenu(restaurant.getMenu().getEntrees()));

		sidesButton.setOnAction(e -> displayMenu(restaurant.getMenu().getSides()));

		saladsButton.setOnAction(e -> displayMenu(restaurant.getMenu().getSalads()));

		familyButton.setOnAction(e -> displayMenu(restaurant.getMenu().getFamily()));

		beveragesButton.setOnAction(e -> displayMenu(restaurant.getMenu().getBeverages()));

		//Menu on left and cart on right

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

        //Select quantity 1-20

        Spinner<Integer> quantitySpinner =
                new Spinner<>(1, 20, 1);

        Button addButton = new Button("Add Item");
        Button removeButton = new Button("Remove Item");

        addButton.setOnAction(e -> {

        	String selectedItem =
        		    menuList.getSelectionModel().getSelectedItem();

        		if (selectedItem == null) {
        		    showMessage("Please select a menu item.");
        		    return;
        		}

        		selectedItem = selectedItem.substring(
        		    0, selectedItem.lastIndexOf(" - $")
			); //selecting items

			int quantity = quantitySpinner.getValue(); //get quantity

			for (int i = 0; i < quantity; i++) {
				currentOrder.addItem(selectedItem, 1);
				cartItems.add(String.format("%s - $%.2f", selectedItem, currentOrder.getPrice(selectedItem)));
				//add to cart
			}
			
			quantitySpinner.getValueFactory().setValue(1); //reset value to 1
		});
        
        removeButton.setOnAction(e -> {

            String selectedItem =
                cartList.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                showMessage("Please select an item to remove.");
                return;
            }

            currentOrder.removeItem(selectedItem, 1);

            cartItems.remove(selectedItem);

            showMessage("Item removed!");

        }); //removing items from cart

		Button checkoutButton = new Button("Checkout");
		Button backButton = new Button("Back");

		checkoutButton.setOnAction(e -> showCheckout());

		backButton.setOnAction(e -> showHome());

		HBox orderControls = new HBox(10,
			    new Label("Quantity:"),
			    quantitySpinner,
			    addButton,
			    removeButton,
			    checkoutButton
			); //box for buttons

        orderControls.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20,
                title,
                categoryButtons,
                lists,
                orderControls,
                backButton
        ); //layout of page

        layout.setPadding(new Insets(20));
		layout.setAlignment(Pos.CENTER);

		scene = new Scene(layout, 900, 650);
		window.setScene(scene);
	}

	//show menu items in selected category

	private void displayMenu(Map<String, Double> items) {

		menuList.getItems().clear();

		for (Map.Entry<String, Double> entry : items.entrySet()) {

			String item = entry.getKey();
			double price = entry.getValue();

			menuList.getItems().add(String.format("%s - $%.2f", item, price));
		}
	}

	//checkout screen

	private void showCheckout() {

		if (currentOrder.getOrder().isEmpty()) {
			showMessage("Your order is empty.");
			return;
		}

		Transactions transaction = new Transactions(currentOrder);

		Label title = new Label("Checkout");
		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

		receiptArea.clear();
		receiptArea.setEditable(false);

		StringBuilder receipt = new StringBuilder();

		receipt.append("------ RECEIPT ------\n");

		for (String item : currentOrder.getOrder()) {
			receipt.append(String.format("%s - $%.2f%n", item, currentOrder.getPrice(item)));
		}

		receipt.append("---------------------\n");

		receipt.append(String.format("Subtotal: $%.2f%n", transaction.getSubtotal()));

		receipt.append(String.format("Tax: $%.2f%n", transaction.getTax()));

		receipt.append(String.format("Total: $%.2f%n", transaction.getTotal()));

		receiptArea.setText(receipt.toString());

		//Payment controls
		Label paymentLabel = new Label("Enter amount to pay:");

		TextField paymentField = new TextField();
		paymentField.setPromptText("Enter amount (example: 20.00)");

		Label changeLabel = new Label();

		Button payButton = new Button("Complete Payment");
		Button backButton = new Button("Back to Menu");

		//Payment button
		payButton.setOnAction(e -> {

			try {

				double amountPaid = Double.parseDouble(paymentField.getText());

				if (amountPaid < 0) {
					changeLabel.setText("Please enter a positive amount.");
					return;
				}

				double change = transaction.calculateChange(amountPaid);

				changeLabel.setText(String.format("Payment successful! Your change: $%.2f", change));

				transaction.printReceipt();

				payButton.setDisable(true);
				paymentField.setDisable(true);

				showMessage(String.format("Payment complete! Your change: $%.2f", change));

				currentOrder = null;
				cartItems.clear();

			} catch (NumberFormatException ex) {

				changeLabel.setText("Please enter a valid dollar amount.");

			} catch (IllegalArgumentException ex) {

				changeLabel.setText(ex.getMessage());
			}
		});

		backButton.setOnAction(e -> showCustomer());

		//Layout
		VBox layout = new VBox(15, title, receiptArea, paymentLabel, paymentField, payButton, changeLabel, backButton);

		layout.setPadding(new Insets(25));
		layout.setAlignment(Pos.CENTER);

		scene = new Scene(layout, 500, 550);
		window.setScene(scene);
	}
	
	//manager login page
	
	private void showManagerLogin() {
		Label title = new Label("Manager Login");
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		TextField usernameField = new TextField();
		usernameField.setPromptText("Enter username");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Enter password");

		Label messageLabel = new Label();

		Button loginButton = new Button("Login");
		Button backButton = new Button("Back");

		loginButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			if (username.equals("admin") && password.equals("Secret555")) {
				showManager();
			} else {
				messageLabel.setText("Incorrect username or password.");
			}
		});

		backButton.setOnAction(e -> showHome());

		VBox layout = new VBox(12, title, new Label("Username:"), usernameField, new Label("Password:"), passwordField,
				loginButton, messageLabel, backButton);

		layout.setPadding(new Insets(30));
		layout.setAlignment(Pos.CENTER);

		scene = new Scene(layout, 500, 450);
		window.setScene(scene);
	}

	//Manager screen

    private void showManager() {
    	

        Label title = new Label("Manager Dashboard");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        //Employee management

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

        //Schedule management

        TextField scheduleField = new TextField();

        scheduleField.setPromptText("Enter restaurant schedule");

        if (restaurant.getSchedule() != null) {
            scheduleField.setText(restaurant.getSchedule());
        }
        
        Label currentScheduleLabel = new Label();

        if (restaurant.getSchedule() != null) {
            currentScheduleLabel.setText(
                "Current Schedule: " + restaurant.getSchedule()
            );
        } else {
            currentScheduleLabel.setText("Current Schedule: Not set");
        }

        Button updateScheduleButton =
                new Button("Update Schedule");

        updateScheduleButton.setOnAction(e -> {
        	restaurant.editSchedule(scheduleField.getText());

            currentScheduleLabel.setText(
                "Current Schedule: " + restaurant.getSchedule()
            );

            showMessage("Schedule updated!");
        });


     //Inventory management

     Label inventoryLabel = new Label("Inventory Management");

     //Category dropdown
     ComboBox<String> categoryBox = new ComboBox<>();
     categoryBox.getItems().addAll(
         "Breakfast",
         "Entrees",
         "Sides",
         "Salads",
         "Family Meals",
         "Beverages"
     );
     categoryBox.setPromptText("Select menu category");

     //Food item dropdown
     ComboBox<String> itemBox = new ComboBox<>();
     itemBox.setPromptText("Select Menu Item");

     //When category changes, update available food items
     categoryBox.setOnAction(e -> {

         itemBox.getItems().clear();

         String category = categoryBox.getValue();

         Map<String, Double> selectedMenu = null;

         switch (category) {

             case "Breakfast":
                 selectedMenu = restaurant.getMenu().getBreakfast();
                 break;

			case "Entrees":
				selectedMenu = restaurant.getMenu().getEntrees();
				break;

			case "Sides":
				selectedMenu = restaurant.getMenu().getSides();
				break;

			case "Salads":
				selectedMenu = restaurant.getMenu().getSalads();
				break;

			case "Family Meals":
				selectedMenu = restaurant.getMenu().getFamily();
				break;
				
			case "Beverages":
				selectedMenu = restaurant.getMenu().getBeverages();
				break;
			}
			

			if (selectedMenu != null) {
				itemBox.getItems().addAll(selectedMenu.keySet());
			}
		});

		//Quantity field
		TextField amountField = new TextField();
		amountField.setPromptText("Enter inventory quantity");

		//Update button
		Button updateInventoryButton = new Button("Update Inventory");

		//Inventory display
		ListView<String> inventoryList = new ListView<>();

		Runnable refreshInventory = () -> {

			inventoryList.getItems().clear();

			for (Map.Entry<String, Integer> entry : restaurant.getInventory().entrySet()) {

				inventoryList.getItems().add(entry.getKey() + ": " + entry.getValue());
			}
		};

		refreshInventory.run();

		//Update inventory action
		updateInventoryButton.setOnAction(e -> {

			String item = itemBox.getValue();

			if (item == null) {
				showMessage("Please select a food item.");
				return;
			}

			try {

				int amount = Integer.parseInt(amountField.getText());

				if (amount < 0) {
					showMessage("Quantity cannot be negative.");
					return;
				}

				restaurant.editInventory(item, amount);

				refreshInventory.run();

				amountField.clear();

				showMessage("Inventory updated!");

			} catch (NumberFormatException ex) {

				showMessage("Please enter a valid quantity.");
			}
		});

		VBox inventoryBox = new VBox(10, inventoryLabel, categoryBox, itemBox, amountField, updateInventoryButton,
				inventoryList);
        

        Button backButton = new Button("Back to Home");

        backButton.setOnAction(e -> showHome());

        VBox layout = new VBox(15,
                title,
                new Label("Employee Management"),
                employeeLabel,
                employeeControls,
                new Label("Restaurant Schedule"),
                currentScheduleLabel,
                scheduleField,
                updateScheduleButton,             
                inventoryBox,
                backButton
        );

        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 650, 750);
        window.setScene(scene);
    }

    //All-purpose message box method

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

