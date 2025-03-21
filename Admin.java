import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Admin {
    private Seller[] sellersArray = new Seller[0];
    private int sellerArraySize = 0;
    private Buyer[] buyersArray = new Buyer[0];
    private int buyerArraySize = 0;
    public enum eAdminStatus {Succeed, ThereAreNoUsers, ThereAreNoProducts}
    public Admin() {
    }

    //Getters//
    public Seller[] getSellersArray() {
        return sellersArray;
    }
    public Seller getSellersArray(int i) {
        return sellersArray[i];
    }
    public int getSellerArraySize(){
        return sellerArraySize;
    }
    public Buyer[] getBuyersArray() {
        return buyersArray;
    }
    public Buyer getBuyersArray(int i) {
        return buyersArray[i];
    }
    public int getBuyerArraySize(){
        return buyerArraySize;
    }


    public Seller getSeller(String userName)throws AdminException {
        for (int i = 0; i< sellerArraySize; i++){
            if(sellersArray[i].getUserName().equals(userName)){
                return sellersArray[i];
            }
        }
        throw new AdminException("Error! This seller does not exists");
    }

    public Buyer getBuyer(String userName) throws AdminException {
        for (int i = 0; i< buyerArraySize; i++){
            if(buyersArray[i].getUserName().equals(userName)){
                return buyersArray[i];
            }
        }
        throw new AdminException("Error! This buyer does not exists");
    }


    //Validators//
    private void validateSellersArray() {
        if (sellerArraySize == getSellersArray().length) {
            int newSize = getSellersArray().length * 2;
            newSize = Math.max(2, newSize);
            Seller[] temp = new Seller [newSize];
            for (int i = 0; i < sellerArraySize; i++) {
                temp[i] = getSellersArray(i);
            }
            this.sellersArray = temp;
        }
    }

    private void validateBuyersArray() {
        if (buyerArraySize == getBuyersArray().length) {
            int newSize = getBuyersArray().length * 2;
            newSize = Math.max(2, newSize);
            Buyer[] temp = new Buyer [newSize];
            for (int i = 0; i < buyerArraySize; i++) {
                temp[i] = getBuyersArray(i);
            }
            this.buyersArray = temp;
        }
    }

    //Adders//
    public boolean canAddUsers(String username){
        return !(isSellerExist(username) || isBuyerExist(username));
    }

    public void addSeller(Seller seller) {
        validateSellersArray();
        this.sellersArray[sellerArraySize] = new Seller(seller);
        sellerArraySize++;
        Arrays.sort(sellersArray,new Seller.SellerProductsComparator());
    }

    public void addBuyer(Buyer buyer) {
        validateBuyersArray();
        this.buyersArray[buyerArraySize] = new Buyer(buyer);
        buyerArraySize++;
        Arrays.sort(buyersArray,new Buyer.BuyerUsernameComparator());
    }

    public void addProductToSeller(Seller seller, Product product){
        seller.addToProductArray(product);
        Arrays.sort(sellersArray,new Seller.SellerProductsComparator());
    }

    public eAdminStatus canAddProductToBuyer(){
        if (getBuyerArraySize() == 0 && getSellerArraySize() == 0 ) {
            return eAdminStatus.ThereAreNoUsers;
        } else if (isThereProducts()) {
            return eAdminStatus.ThereAreNoProducts;
        }else {
            return eAdminStatus.Succeed;
        }
    }

    public void addProductToCart(Buyer thisBuyer, Product thisProduct)throws ArrayIndexOutOfBoundsException{
        if (thisProduct instanceof PackagedProduct){
            PackagedProduct product = new PackagedProduct((PackagedProduct) thisProduct);
            thisBuyer.getCart().addProductToOrder(product);
        }
        else {
            Product product = new Product(thisProduct);
            thisBuyer.getCart().addProductToOrder(product);
        }
    }

    //General Functions//
    public boolean isSellerExist(String sellerName) {
        for (Seller seller : sellersArray) {
            if (seller == null){
                return false;
            }
            else if (seller.getUserName().equals(sellerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBuyerExist(String buyerName) {
        for (Buyer buyer : buyersArray) {
            if (buyer == null){
                return false;
            }
            else if (buyer.getUserName().equals(buyerName)){
                return true;
            }
        }
        return false;
    }

    public boolean isThereProducts(){
        for (Seller seller : sellersArray)  {
            if (seller == null){
                break;
            }
            if (seller.getProductsArraySize() > 0) {
                return false;
            }
        }
        return true;
    }

    public eAdminStatus canPrintSpecificCategory(){
        if (sellerArraySize==0){
            return eAdminStatus.ThereAreNoUsers;
        }
        else if (isThereProducts()){
            return eAdminStatus.ThereAreNoProducts;
        }
        return eAdminStatus.Succeed;
    }

    public boolean isThereBuyers(){
        if (buyerArraySize==0){
            return false;
        }
        return true;
    }

    public void newCartFromHistory(Buyer thisBuyer, Product[] buyerOrder) {
        thisBuyer.resetCart();
        for (Product product : buyerOrder) {
            if (product == null){
                return;
            }
            thisBuyer.getCart().addProductToOrder(product);
        }
    }

    public String paymentSummary(Buyer thisBuyer){
        if (thisBuyer.getCart().getOrderArraySize() == 0) {
            return "No cart was found for " + thisBuyer.getUserName() + "\n";
        }
        StringBuilder str = new StringBuilder();
        str.append("Your cart payment was completed successfully!\n")
                .append("The price of the cart is: ").append(thisBuyer.getCart().getTotalPrice()).append("$\n");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String purchaseDate = df.format(new Date());
        str.append("The date of the purchase is: ").append(purchaseDate).append("\n");
        thisBuyer.addOrderHistory(purchaseDate);
        return str.toString();
    }

    public static class AdminException extends Exception {
        public AdminException(String message) {
            super(message);
        }
    }

    //Prints and toStrings//
    public String printSellersWithProducts() {
        StringBuilder str = new StringBuilder();
        str.append("List of sellers:\n");
        int counter = 1;
        for (Seller seller : sellersArray) {
            if (seller == null) {
                break;
            }
            if (seller.getProductsArraySize() > 0) {
                str.append(counter).append(". ").append(seller.getUserName()).append("\n");
                counter++;
            }
        }
        return str.toString();
    }

    public String printAllSellersInfo() {
        StringBuilder str = new StringBuilder();
        str.append("List of sellers:\n");
        int counter = 1;
        for (Seller seller : sellersArray) {
            if (seller == null) {
                break;
            }
            int counterProducts = 1;
            String sellerUserName = seller.getUserName();
            str.append(counter).append(". ").append(sellerUserName).append("\n");
            counter++;
            str.append("List of all the products '").append(sellerUserName).append("' have to offer:\n");
            for (Product product : seller.getProductsArray()) {
                if (product == null) {
                    break;
                }
                str.append(counterProducts).append(". ").append(product).append("\n");
                counterProducts++;
            }
            if (counter <= sellersArray.length) {
                str.append("\nNext seller:\n");
            }
        }
        return str.toString();
    }

    public String printSellerProducts(Seller seller) throws AdminException {
        StringBuilder str = new StringBuilder();
        str.append("List of all the products '").append(seller.getUserName()).append("' have to offer:\n");
        int counter = 1;
        for (Product product : seller.getProductsArray()) {
            if (product == null) {
                break;
            }
            str.append(counter).append(".").append(product).append("\n");
            counter++;
        }

        return str.toString();
    }

    public String printAllSellerProducts(Product.Category category) {
        StringBuilder str = new StringBuilder();
        str.append(category.name()).append(":\n");
        int counter = 1;
        boolean foundProducts = false;
        for (Seller seller : sellersArray) {
            if (seller == null) {
                break;
            }
            for (Product product : seller.getProductsArray()) {
                if (product == null) {
                    break;
                } else if (product.geteCategory() == category) {
                    str.append(counter).append(". Seller: '").append(seller.getUserName()).append("', ").append(product).append("\n");
                    counter++;
                    foundProducts = true;
                }
            }
        }
        if (!foundProducts) {
            return "Oops, seems like there are no '" + category.name() + "' products for any of the sellers yet";
        }
        return str.toString();
    }

    public String printBuyers() {
        StringBuilder str = new StringBuilder();
        str.append("List of buyers:\n");
        int counter = 1;
        for (Buyer buyer : buyersArray) {
            if (buyer == null) {
                break;
            }
            str.append(counter).append(".").append(buyer.getUserName()).append("\n");
            counter++;
        }
        return str.toString();
    }

    public String printAllBuyersInfo() {
        StringBuilder str = new StringBuilder();
        str.append("List of buyers:\n");
        int counter = 1;
        for (Buyer buyer : buyersArray) {
            if (buyer == null) {
                break;
            }
            str.append(counter).append(".").append(buyer.getUserName()).append(":\n")
                    .append("Current cart:\n")
                    .append(buyer.getCart().printProducts()).append("\n")
                    .append("Order history:\n")
                    .append(buyer.toOrderHistoryString()).append("\n");
            counter++;
        }
        return str.toString();
    }
}

