package online_shop_mvc;

public class Controller {

    private View view;
    private User user;
    private Database database;

    public static int TERMINATE_VALUE = 8;

    public Controller() {
        this.view = new View();
        this.database = new Database();
    }

    public void run() {
        authorizeUser();
        runMenu();
    }

    private void runMenu() {

        while (true) {
            try {
                int choice = view.printMenu();
                if (choice == 1) {
                    addItemToCart();
                } else if (choice == 2) {
                    getAllItemsFromShop();
                } else if (choice == 3) {
                    removeItemsFromCart();
                } else if (choice == 4) {
                    depositMoney();
                } else if (choice == 5) {
                    makePurchase();
                } else if (choice == 6) {
                    checkUserAccount();
                } else if (choice == 7) {
                    checkUserCart();
                } else if (choice == TERMINATE_VALUE) {
                    break;
                } else {
                    view.printMessage("--- ERROR: invalid input! ---");
                }

            } catch (Exception exception) {
                view.printMessage(exception.getMessage());
            }
        }

    }

    private void authorizeUser() {
        while (true) {
            String username = view.getTextInput("Input your username: ");
            String password = view.getTextInput("Input your password: ");
            User user = database.findUserByUsername(username);
            if (user == null) {
                // print message that username is wrong
                view.printMessage("--- ERROR: wrong username! ---");
            } else {
                if (user.getPassword().equals(password)) {
                    // login success
                    view.printMessage("--- LOGIN SUCCESSFUL! ---");
                    this.user = user;
                    break;
                } else {
                    // login failed, passwords don't match
                    view.printMessage("--- ERROR: invalid password! ---");
                }
            }
        }

    }

    private void addItemToCart() {
        getAllItemsFromShop();
        int ID = view.getIntegerInput("From list above which product do you want to add (ID)");
        Product product = database.findProductById(ID);
        if (product != null) {
            int quantity = view.getIntegerInput("How many entries of this product do you want to add?");
            // TODO: add exception for negative values
            user.getUserCart().addProduct(product, quantity);
            view.printMessage("--- Product " + product.getTitle() + " was added to your cart! ---");
        } else {
            view.printMessage("--- ERROR: product not found! ---");
        }
    }

    public void getAllItemsFromShop() {
        String text = database.getAllProductsText();
        view.printMessage(text);
    }

    public void depositMoney() throws Exception {
        // ask how much
        double amount = view.getDoubleInput("How much money would you like to deposit?");
        if (amount < 0) {
            throw new Exception("Invalid amount!");
        } else {
            user.addBalance(amount);
            view.printMessage(amount + "$ was added, total balance: " + user.getBalance() + "$");
        }
    }

    public void checkUserCart() {
        Cart cart = user.getUserCart();
        view.printMessage(cart.getMapString());
    }

    public void removeItemsFromCart() {
        checkUserCart();
        int id = view.getIntegerInput("Which product do you want to remove? (input id) --> ");
        Cart cart = user.getUserCart();
        boolean wasRemoved = cart.removeItemsFromCartById(id);
        if (wasRemoved == true) {
            view.printMessage("Item with ID " + id + " was removed!");
        } else {
            view.printMessage("Item with ID " + id + " was not found!");
        }
    }

    public void makePurchase() throws Exception {
        Cart cart = user.getUserCart();
        double cartTotal = cart.getTotalCost();
        if (user.getBalance() < cartTotal) {
            throw new Exception("Balance of " + user.getBalance() + "$ is less than your total: " + cartTotal + "$");
        } else {
            double userCurrentBalance = user.getBalance();
            double newBalance = userCurrentBalance - cartTotal;
            user.setBalance(newBalance);
            view.printMessage("Purchase successful, remaining balance: " + user.getBalance());
        }
    }


    public void checkUserAccount() {
        view.printMessage("==================");
        view.printMessage(user.toString());
        view.printMessage("==================");
    }
}
