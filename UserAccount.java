package BookExchangeApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserAccount {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private User thisUser = null;

    private final String USER_FILE = "users.txt";
    private final String BOOK_FILE = "books.txt";

    public UserAccount() {
        loadUsers();
        loadBooks();
    }

    public void saveData() {
        File userFile = new File(USER_FILE);
        File bookFile = new File(BOOK_FILE);

        if (userFile.exists()) {
            userFile.delete();
        }
        if (bookFile.exists()) {
            bookFile.delete();
        }
        try (PrintWriter userOut = new PrintWriter(new FileWriter(userFile));
            PrintWriter bookOut = new PrintWriter(new FileWriter(bookFile))) {
            
            //Writing all users from the current ArrayList
            for (User u : users) {
                userOut.println(u.toTxt());
            }
            
            //Writing all books from the current ArrayList
            for (Book b : books) {
                bookOut.println(b.toTxt());
            }
            
            System.out.println("Data successfully refreshed in text files.");
            
        } catch (IOException e) {
            System.out.println("Error saving to txt files: " + e.getMessage());
        }
    }

    private void loadUsers() {
        File file = new File(USER_FILE); 
        
        try (Scanner reader = new Scanner(file)) { 
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                
                if (parts.length < 6) continue; 

                if (parts[0].equals("Seller")) {
                    users.add(new Seller(parts[1], parts[2], parts[3], parts[4], parts[5]));
                } else {
                    users.add(new Student(parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with a fresh user list.");
        } catch (Exception e) {
            System.out.println("An error occurred while loading users: " + e.getMessage());
        }
    }

    private void loadBooks() {
        File file = new File(BOOK_FILE);
        
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] p = line.split("\\|");

                if (p.length >= 5) {
                    String title = p[0];
                    String author = p[1];
                    String condition = p[2];
                    double price = Double.parseDouble(p[3]);
                    String sellerID = p[4];

                    books.add(new Book(title, author, condition, price, sellerID));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Books file not found. Starting with an empty inventory.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price: Make sure the 4th column is a number.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public boolean isIdDuplicate(String id) {
        for (User u : users) {
            if (u.getID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public void registerUser(User u) {
        users.add(u);
        saveData();
    }

    public boolean login(String id, String pass) {
        for (User u : users) {
            if (u.getID().equals(id) && u.password.equals(pass)) {
                thisUser = u;
                return true;
            }
        }
        return false;
    }

    public void listBook(Book b) {
        books.add(b);
        saveData();
    }

    public User getThisUser() { return thisUser; }
    public ArrayList<Book> getBooks() { return books; }
    public void logout() { thisUser = null; }
    
    public User findUserByID(String id) {
        for(User u : users) if(u.getID().equals(id)) return u;
        return null;
    }
}

