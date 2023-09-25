package online_shop_mvc;

import java.util.ArrayList;

public class Database {

    private ArrayList<User> allUsers;
    private ArrayList<Product> allProducts;

    public Database() {
        this.allUsers = new ArrayList<>();
        this.allProducts = new ArrayList<>();
        initialiseUsers();
        initialiseProducts();
    }

    public void initialiseUsers() {
        allUsers.add(new User("Tom Smith", "tomsmith", "pass"));
        allUsers.add(new User("John Doe", "johndoe", "pass"));
    }

    public void initialiseProducts() {
        allProducts.add(new Product(12345, "Laptop", 1000));
        allProducts.add(new Product(7890, "iPhone", 1500));
        allProducts.add(new Product(34567, "Tablet", 800));
    }

    public String getAllProductsText() {
        String text = "";
        for (Product eachProduct : allProducts) {
            text += "ID: " + eachProduct.getProductId()
                    + ", Title: " + eachProduct.getTitle() + ", price: " + eachProduct.getPrice() + "\n";
        }
        return text;
    }

    public Product findProductById(int id){
        for (Product eachProduct : allProducts) {
            if(eachProduct.getProductId() == id){
                return eachProduct;
            }
        }
        return null;
    }

    public User findUserByUsername(String usernameInput) {
        for (User user : allUsers) {
            if (user.getUsername().equals(usernameInput)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
}
