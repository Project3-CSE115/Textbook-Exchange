package BookExchangeApp;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String ID;
    protected String password;
    protected String email;
    protected String contact;    

    public User(String username, String ID, String password, String email, String contact) {
        this.username = username;
        this.ID = ID;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    public String getName() { return username; }
    public String getID() { return ID; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
    public void setContact(String contact) { this.contact = contact; }

    public abstract void displayUserProfile();

    public String toTxt() {
        //Format: Type|Name|ID|Password|Email|Contact
        String type = (this instanceof Seller) ? "Seller" : "Student";
        return type + "|" + username + "|" + ID + "|" + password + "|" + email + "|" + contact;
    }
}

