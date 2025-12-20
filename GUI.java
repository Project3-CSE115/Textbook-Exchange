package BookExchangeApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {
    UserAccount engine = new UserAccount();
    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("NSU Book Exchange");
        showStartMenu(); // Program starts here now
        window.show();
    }

    // --- SCREEN 1: START MENU ---
    private void showStartMenu() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome to NSU Book Exchange");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button goToLogin = new Button("Sign In");
        Button goToSignUp = new Button("Sign Up (New User)");

        goToLogin.setMinWidth(150);
        goToSignUp.setMinWidth(150);

        goToLogin.setOnAction(e -> showLogin());
        goToSignUp.setOnAction(e -> showSignUp());

        root.getChildren().addAll(welcomeLabel, goToLogin, goToSignUp);
        window.setScene(new Scene(root, 350, 300));
    }

    // --- SCREEN 2: SIGN UP ---
    private void showSignUp() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        TextField nameField = new TextField(); nameField.setPromptText("Full Name");
        TextField idField = new TextField(); idField.setPromptText("Student ID");
        PasswordField passField = new PasswordField(); passField.setPromptText("Password");
        TextField emailField = new TextField(); emailField.setPromptText("Email Address");
        TextField contactField = new TextField(); contactField.setPromptText("Contact Number");

        // User Type Selection
        Label typeLabel = new Label("Register as:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Student", "Seller");
        typeBox.setValue("Student");

        Button registerBtn = new Button("Create Account");
        Button backBtn = new Button("Back");

        registerBtn.setOnAction(e -> {
            String id = idField.getText();
            if (id.isEmpty() || nameField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Name and ID cannot be empty!").show();
                return;
            }

            // DUPLICATE CHECK
            if (engine.isIdDuplicate(id)) {
                new Alert(Alert.AlertType.ERROR, "Error: Student ID already exists!").show();
            } else {
                User newUser;
                if (typeBox.getValue().equals("Seller")) {
                    newUser = new Seller(nameField.getText(), id, passField.getText(), emailField.getText(), contactField.getText());
                } else {
                    newUser = new Student(nameField.getText(), id, passField.getText(), emailField.getText(), contactField.getText());
                }
                
                engine.registerUser(newUser);
                new Alert(Alert.AlertType.INFORMATION, "Account Created Successfully!").show();
                showLogin(); // Go to login after signing up
            }
        });

        backBtn.setOnAction(e -> showStartMenu());

        root.getChildren().addAll(new Label("Create New Account"), nameField, idField, passField, emailField, contactField, typeLabel, typeBox, registerBtn, backBtn);
        window.setScene(new Scene(root, 400, 450));
    }

    // --- SCREEN 3: SIGN IN ---
    private void showLogin() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        TextField idField = new TextField(); idField.setPromptText("Student ID");
        PasswordField passField = new PasswordField(); passField.setPromptText("Password");
        Button loginBtn = new Button("Login");
        Button backBtn = new Button("Back");

        loginBtn.setOnAction(e -> {
            if (engine.login(idField.getText(), passField.getText())) {
                showDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid ID or Password!").show();
            }
        });

        backBtn.setOnAction(e -> showStartMenu());

        root.getChildren().addAll(new Label("Sign In to Continue"), idField, passField, loginBtn, backBtn);
        window.setScene(new Scene(root, 300, 300));
    }

    // --- SCREEN 4: DASHBOARD (Marketplace/Selling) ---
    private void showDashboard() {
        BorderPane layout = new BorderPane();
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setStyle("-fx-background-color: #f4f4f4;");
        
        Button viewBtn = new Button("Marketplace");
        Button sellBtn = new Button("Sell a Book");
        Button logoutBtn = new Button("Logout");
        viewBtn.setMinWidth(100); sellBtn.setMinWidth(100); logoutBtn.setMinWidth(100);
        
        sideMenu.getChildren().addAll(viewBtn, sellBtn, logoutBtn);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Selling logic with Recommended Price
        sellBtn.setOnAction(e -> {
            content.getChildren().clear();
            TextField title = new TextField(); title.setPromptText("Book Title");
            TextField author = new TextField(); author.setPromptText("Author");
            TextField originalPrice = new TextField(); originalPrice.setPromptText("Original Price");
            ComboBox<String> cond = new ComboBox<>();
            cond.getItems().addAll("New", "Good", "Fair", "Poor");
            cond.setValue("Good");

            Button postBtn = new Button("Post for Sale");
            postBtn.setOnAction(ev -> {
                try {
                    double op = Double.parseDouble(originalPrice.getText());
                    double rec = Book.getRecommendedPrice(op, cond.getValue());
                    engine.listBook(new Book(title.getText(), author.getText(), cond.getValue(), rec, engine.getThisUser().getID()));
                    new Alert(Alert.AlertType.INFORMATION, "Book listed at recommended price: " + rec).show();
                    content.getChildren().clear();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid price format!").show();
                }
            });
            content.getChildren().addAll(new Label("List a New Book"), title, author, originalPrice, cond, postBtn);
        });

        // Buying/Communication Logic
        viewBtn.setOnAction(e -> {
            content.getChildren().clear();
            content.getChildren().add(new Label("Current Marketplace Listings:"));
            for (Book b : engine.getBooks()) {
                HBox card = new HBox(15);
                card.setStyle("-fx-border-color: #ddd; -fx-padding: 10; -fx-background-color: white;");
                
                VBox details = new VBox(5);
                details.getChildren().addAll(new Label("Title: " + b.getTitle()), new Label("Price: " + b.getPrice() + " BDT"));
                
                Button contactBtn = new Button("Contact Seller");
                contactBtn.setOnAction(ev -> {
                    User seller = engine.findUserByID(b.getSellerID());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Seller Contact");
                    alert.setHeaderText("Communication Details for " + b.getTitle());
                    alert.setContentText("Name: " + seller.getName() + "\nEmail: " + seller.getEmail() + "\nPhone: " + seller.getContact());
                    alert.show();
                });
                
                card.getChildren().addAll(details, contactBtn);
                content.getChildren().add(card);
            }
        });

        logoutBtn.setOnAction(e -> { engine.logout(); showStartMenu(); });

        layout.setLeft(sideMenu);
        layout.setCenter(new ScrollPane(content));
        window.setScene(new Scene(layout, 700, 500));
    }

    public static void main(String[] args) { launch(args); }
}