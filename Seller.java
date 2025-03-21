import java.util.Arrays;
import java.util.Comparator;

public class Seller extends User {
    private Product[] productsArray ;
    private int productsArraySize;

    public Seller(String userName, String password) {
        super(userName,password);
        this.productsArray = new Product[0];
        this.productsArraySize = 0;
    }

    public Seller (Seller seller){
        super(seller.getUserName(), seller.getPassword());
        this.productsArray = new Product[seller.productsArray.length];
        try {
            for (int i = 0; i < seller.productsArraySize; i++) {
                this.productsArray[i] = seller.productsArray[i].clone();
            }
        }catch (CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }
        this.productsArraySize = seller.productsArraySize;
    }

    //getters//
    public Product[] getProductsArray() {
        return productsArray;
    }

    public Product getProductsArray(int i) {
        return productsArray[i];
    }

    public int getProductsArraySize() {
        return productsArraySize;
    }

    //Setters//
    public void addToProductArray(Product[] productArray) {
        this.productsArray = productArray;
    }

    //Arrays Validators//
    public void validateSellersProductsArray() {
        if (productsArraySize == productsArray.length) {
            int newSize = productsArray.length * 2;
            newSize = Math.max(2, newSize);
            Product[] temp = new Product[newSize];
            for (int i = 0; i < productsArraySize; i++) {
                temp[i] = productsArray[i];
            }
            addToProductArray(temp);
        }
    }

    //Adders//
    public void addToProductArray(Product product){
        validateSellersProductsArray();
//        this.setProductArray(productsArraySize, product);
        this.productsArray[productsArraySize] = product;
        productsArraySize++;
    }

    //print and toStrings//
    @Override
    public String toString() {
        return super.toString() +
                ", products: " + Arrays.toString(productsArray);
    }

    public static class SellerProductsComparator implements Comparator<Seller> {
        @Override
        public int compare(Seller b1, Seller b2) {
            if (b1 == null && b2 == null) {
                return 0;
            } else if (b1 == null) {
                return 1;
            } else if (b2 == null) {
                return -1;
            } else {
                return Integer.compare(b1.getProductsArraySize(), b2.getProductsArraySize());
            }
        }
    }
}

