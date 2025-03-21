// Project done by: Idan Steinberg [ID: 207627399] & Yuval Vered [ID: 314649492]
// Class 2, lecturer: Keren Kalif
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Admin admin = new Admin();
        final int EXIT = 0;
        int menuSelection = -1;
        do {
            System.out.println("Welcome to the main menu, please select one of the following options below: \n" +
                    "For exit enter - 0 \n" +
                    "For adding a seller enter - 1 \n" +
                    "For adding a buyer enter - 2 \n" +
                    "For adding a product for a seller enter - 3 \n" +
                    "For adding a product for a buyer enter - 4 \n" +
                    "For payment enter - 5 \n" +
                    "For buyers info enter - 6 \n" +
                    "For sellers info enter - 7 \n" +
                    "For a specific category - 8\n" +
                    "For creating a new cart from your order history - 9\n" +
                    "Your pick: ");
            try {
                menuSelection = scanner.nextInt();
                scanner.nextLine();
                switch (menuSelection) {
                    case 1:
                        printAddSeller(admin);
                        break;
                    case 2:
                        printAddBuyer(admin);
                        break;
                    case 3:
                        printAddProductForSeller(admin);
                        break;
                    case 4:
                        printAddProductForBuyer(admin);
                        break;
                    case 5:
                        printPayment(admin);
                        break;
                    case 6:
                        if (admin.getBuyerArraySize() != 0) {
                            System.out.println(admin.printAllBuyersInfo());
                        }
                        break;
                    case 7:
                        if (admin.getSellerArraySize() != 0) {
                            System.out.println(admin.printAllSellersInfo());
                        }
                        break;
                    case 8:
                        System.out.println(printSpecificCategory(admin));
                        break;
                    case 9:
                        printNewCartFromHistory(admin);
                        break;
                    case EXIT:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Please enter a number between 0 and 9");
                scanner.nextLine();
            }
        } while (menuSelection != EXIT);
    }



    //case1//
    public static void printAddSeller(Admin admin) {
        boolean fContinue;
        String sellerUserName;
        do {
            System.out.println("Enter the user name of the seller: ");
            sellerUserName = scanner.next();
            if (admin.canAddUsers(sellerUserName)) {
                fContinue = false;
            } else {
                System.out.println("Error! The user name: " + sellerUserName + " is already taken.\n" +
                        "Please try again:");
                fContinue = true;
            }
        } while (fContinue);
        System.out.println("Enter your password: ");
        String sellerPassword = scanner.next();
        admin.addSeller(new Seller(sellerUserName, sellerPassword));
        System.out.println("Successfully added seller: " + sellerUserName);
    }

    //case 2//
    public static void printAddBuyer(Admin admin) {
        boolean fContinue;
        boolean validInput = false;
        String buyerUserName;
        int houseNumber = 0;
        long zip = 0;
        do {
            System.out.println("Enter the user name of the buyer: ");
            buyerUserName = scanner.next();
            if (admin.canAddUsers(buyerUserName)) {
                fContinue = false;
            } else {
                System.out.println("Error! The user name: " + buyerUserName + " is already taken.\n" +
                        "Please try again:");
                fContinue = true;
            }
        }while (fContinue);
        System.out.println("Enter your password: ");
        String buyerPassword = scanner.next();
        System.out.println("Enter your address: ");
        System.out.print("Street name: ");
        String streetName = scanner.next();
        while(!validInput){
            try {
                System.out.print("House number: ");
                scanner.nextLine();
                houseNumber = scanner.nextInt();
                validInput = true;
            }catch (InputMismatchException e) {
                System.out.println("Error! You need to enter an Integer in this field!!!");
            }
        }
        System.out.print("City: ");
        String city = scanner.next();
        System.out.print("State: ");
        String state = scanner.next();
        validInput = false;
        while (!validInput) {
            try {
                System.out.print("Zip: ");
                scanner.nextLine();
                zip = scanner.nextInt();
                validInput = true;
            }catch (InputMismatchException e) {
                System.out.println("Error! You need to enter an Integer in this field!!!");
            }
        }
        Address buyerAddress = new Address(streetName, houseNumber, city, state, zip);
        admin.addBuyer(new Buyer(buyerUserName, buyerPassword, buyerAddress));
        System.out.println("Successfully added buyer: " + buyerUserName);
    }

    //case 3//
    public static void printAddProductForSeller(Admin admin) {
        if (admin.getSellerArraySize() == 0) {
            System.out.println("There is no sellers yet!");
            return;
        }
        double price = 0;
        String categoryToCheck;
        double packagePrice = 0;
        boolean validInput = false;
        Seller thisSeller = null;
        Product.Category category = Product.Category.Kids;
        do {
            try {
                System.out.println("Enter the username of the seller: ");
                thisSeller = admin.getSeller(scanner.next());
                validInput = true;
            } catch (Admin.AdminException e) {
                System.out.println("Error! This seller does not exists \n" +
                        "Please try again: ");
            }
        }while (!validInput);
        System.out.println("Hi " + thisSeller.getUserName() + ", welcome!");
        System.out.println("Enter the name of the product: ");
        String productName = scanner.next();
        validInput = false;
        do {
            try {
                System.out.println("Enter the price of the product: ");
                price = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error! Invalid input! \nPlease try again:");
                scanner.nextLine();
            }
        }while (!validInput);
        validInput = false;
        do {
            try {
                System.out.println(Product.printAllCategories());
                categoryToCheck = scanner.next();
                category = Product.Category.valueOf(categoryToCheck);
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error! Invalid input! \nPlease try again:");
                scanner.nextLine();
            }
        }while (!validInput);
        System.out.println("Is the product requires a special packaging? (Y/N)");
        Product product;
        if (scanner.next().equalsIgnoreCase("y")) {
            validInput = false;
            do {
                try {
                    System.out.println("Enter the price for the packaging: ");
                    packagePrice = scanner.nextDouble();
                    validInput = true;
                }catch (InputMismatchException e) {
                    System.out.println("Error! Invalid input! \nPlease try again:");
                    scanner.nextLine();
                }
            }while (!validInput);
            product = new PackagedProduct(productName, price, category,packagePrice);
        } else {
            product = new Product(productName, price, category);
        }
        admin.addProductToSeller(thisSeller, product);
    }

    //case 4//
    public static void printAddProductForBuyer(Admin admin) {
        Admin.eAdminStatus status = admin.canAddProductToBuyer();
        if (status == Admin.eAdminStatus.ThereAreNoUsers) {
            System.out.println("There is no buyers or sellers yet!");
            return;
        } else if (status == Admin.eAdminStatus.ThereAreNoProducts) {
            System.out.println("Oops, seems like there are no products for any of the sellers yet");
            return;
        }
        Product product = null;
        Buyer thisBuyer = null;
        Seller thisSeller = null;
        boolean validInput = false;
        do {
            try {
                System.out.println(admin.printBuyers());
                System.out.println("Enter the user name of the buyer: ");
                thisBuyer = admin.getBuyer(scanner.next());
                System.out.println("Hi " + thisBuyer.getUserName() + ", welcome!");
                validInput = true;
            } catch (Admin.AdminException e) {
                System.out.println(e.getMessage()+ "\n" +
                        "Please try again: ");
                scanner.nextLine();
            }
        }while (!validInput);
        validInput = false;
        do {
            try {
                System.out.println(admin.printSellersWithProducts());
                System.out.println("please enter the user name of the seller you want to buy from?: ");
                thisSeller = admin.getSeller(scanner.next());
                System.out.println("Great! '" + thisSeller.getUserName() + "' was found!");
                System.out.println(admin.printSellerProducts(thisSeller));
                validInput = true;
            } catch (Admin.AdminException e) {
                System.out.println(e.getMessage()+ "\n" +
                        "Please try again: ");
                scanner.nextLine();
            }
        }while (!validInput);
        validInput = false;
        do {
            try {
                System.out.println("Please enter the index of the product you want to buy: ");
                product = thisSeller.getProductsArray(scanner.nextInt() - 1);
                validInput = true;
            }catch (InputMismatchException e) {
                System.out.println("Error! You need to enter an Integer in this field!!!");
                scanner.nextLine();
            }catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! You need to enter an index from the list in this field!!!");
                scanner.nextLine();
            }
        }while (!validInput);
        admin.addProductToCart(thisBuyer, product);
        System.out.println("The product was added to your cart successfully! ");
    }

    //case 5//
    public static void printPayment(Admin admin) {
        Buyer thisBuyer = null;
        if (!(admin.isThereBuyers())){
            System.out.println("There are no buyers yet!");
            return;
        }
        boolean validInput = false;
        do {
            try {
                System.out.println("Enter the user name of the buyer: ");
                thisBuyer = admin.getBuyer(scanner.next());
                validInput = true;
            }catch (Admin.AdminException e){
                System.out.println(e.getMessage() + "\nPlease try again:");
            }
        } while (!validInput);
        System.out.println(admin.paymentSummary(thisBuyer));
    }

    //case 8//
    public static String printSpecificCategory(Admin admin) {
        boolean validInput = false;
        Product.Category category = null;
        Admin.eAdminStatus status = admin.canPrintSpecificCategory();
        if (status == Admin.eAdminStatus.ThereAreNoUsers) {
            return "There are no sellers!";
        } else if (status == Admin.eAdminStatus.ThereAreNoProducts) {
            return "Oops, seems like there are no products for any of the sellers yet";
        }
        do {
            System.out.println(Product.printAllCategories());
            String categoryToCheck = scanner.next();
            try {
                category = Product.Category.valueOf(categoryToCheck);
                validInput = true;
            }catch (IllegalArgumentException e){
                System.out.println("Error! Invalid input! \nPlease try again:");
                scanner.nextLine();
            }
        }while (!validInput);
        return admin.printAllSellerProducts(category);
    }

    //case 9//
    public static void printNewCartFromHistory(Admin admin){
        if (!(admin.isThereBuyers())){
            System.out.println("There are no buyers yet!");
            return;
        }
        Buyer thisBuyer = null;
        Product[] buyerOrder = null;
        String wantToReset = "";
        boolean validInput = false;
        do {
            try {
                System.out.println("Enter the user name of the buyer: ");
                thisBuyer = admin.getBuyer(scanner.next());
                validInput = true;
            }catch (Admin.AdminException e) {
                System.out.println(e.getMessage() + "\nPlease try again:");
            }
        }while (!validInput);
            boolean isThereCart = thisBuyer.getCart().getOrderArraySize() != 0;
            if (isThereCart) {
                System.out.println("Warning!!! By going on in this process you will reset your cart!\n" +
                        "Would you like to replace your current cart? (Y/N)");
                wantToReset = scanner.next();
            }
            if (wantToReset.equalsIgnoreCase("y") || !isThereCart){
                if (!thisBuyer.isThereOrderHistory()) {
                    System.out.println("Oops! Seems like there are no order history for '" + thisBuyer.getUserName() + "'!");
                    return;
                }
                validInput = false;
                do {
                    try {
                        System.out.println(thisBuyer.toOrderHistoryString());
                        System.out.println("Please choose the index of the order from your history you would like to update as your current cart:");
                        int orderIndex = scanner.nextInt() - 1;
                        buyerOrder = thisBuyer.getOrderHistoryArray(orderIndex).getOrder();
                        validInput = true;
                    }catch (InputMismatchException e) {
                        System.out.println("Error! You need to enter an Integer in this field!!!");
                        scanner.nextLine();
                    }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error! You need to enter an index from the list in this field!!!");
                        scanner.nextLine();
                    }
                } while (!validInput);
                admin.newCartFromHistory(thisBuyer, buyerOrder);
                System.out.println("You cart was updated successfully!");
            }
        }
}
