package BookExchangeApp;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String condition; // e.g., New, Good, Used
    private double price;
    private String sellerID;

    public Book(String title, String author, String condition, double price, String sellerID) {
        this.title = title;
        this.author = author;
        this.condition = condition;
        this.price = price;
        this.sellerID = sellerID;
    }

    // Recommended Price Logic
    public static double getRecommendedPrice(double originalPrice, String condition) {
        double factor = 0.5; // Default
        switch (condition.toLowerCase()) {
            case "new": factor = 0.9; break;
            case "good": factor = 0.7; break;
            case "used": factor = 0.4; break;
        }
        return originalPrice * factor;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCondition() { return condition; }
    public double getPrice() { return price; }
    public String getSellerID() { return sellerID; }

    @Override
    public String toString() {
        return String.format(
        		"**%s** (Author: %s)\n" +
        		"  - Condition: %s\n" +
        		"  - Price: %.2f BDT\n" +
        		"  - Seller ID: %s",
        		title, author, condition, price, sellerID);
    }

    public String toTxt() {
        return title + "|" + author + "|" + condition + "|" + price + "|" + sellerID;
    }
}
