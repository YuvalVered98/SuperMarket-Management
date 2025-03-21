import java.util.Arrays;
import java.util.Comparator;

public class Buyer extends User {
    private final Address address;
    private Order cart = new Order();
    private Order[] orderHistoryArray = new Order[0];
    private  int orderHistoryArraySize = 0;

    public Buyer(String userName, String password, Address address) {
        super(userName, password);
        this.address = address;

    }

    public Buyer(Buyer buyer) {
        super(buyer.getUserName(), buyer.getPassword());
        this.address = new Address(buyer.address);
        this.cart = new Order(buyer.cart);
        this.orderHistoryArray = new Order[orderHistoryArray.length];
        for (int i = 0; i < orderHistoryArraySize; i++) {
            this.orderHistoryArray[i] = new Order(orderHistoryArray[i]);
        }
        this.orderHistoryArraySize = buyer.orderHistoryArraySize;
    }

    //Getters//
    public Order[] getOrderHistoryArray() {
        return orderHistoryArray;
    }

    public Order getOrderHistoryArray(int i) {
        return orderHistoryArray[i];
    }

    public int getOrderHistoryArraySize(){
        return orderHistoryArraySize;
    }

    public Order getCart() {
        return cart;
    }

    //Arrays Validators//
    private void validateOrderHistoryArray() {
        if (getOrderHistoryArraySize() == getOrderHistoryArray().length) {
            int newSize = getOrderHistoryArray().length * 2;
            newSize = Math.max(2, newSize);
            Order[] temp = new Order[newSize];
            for (int i = 0; i < getOrderHistoryArraySize(); i++) {
                temp[i] = getOrderHistoryArray(i);
            }
            this.orderHistoryArray = temp;
        }
    }

    //Adders//
    public void addOrderHistory(String date){
        validateOrderHistoryArray();
        getCart().setDate(date);
        Order oldCart = new Order(getCart());
        resetCart();
        this.orderHistoryArray[orderHistoryArraySize] = oldCart;
        orderHistoryArraySize++;
    }

    //General//
    public void resetCart(){
        this.cart = new Order();
    }

    public boolean isThereOrderHistory(){
        return orderHistoryArraySize != 0;
    }

    //prints and toStrings//
    public String toOrderHistoryString() {
        StringBuilder str = new StringBuilder();
        int counter = 1;
        for (Order order : orderHistoryArray) {
            if (order == null) {
                break;
            }
            str.append(counter).append(". ").append(order).append("\n");
            counter++;
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return super.toString() +
                ", address=" + address +
                "cart=" + cart.toString() +
                ", orderHistoryArray=" + Arrays.toString(orderHistoryArray);
    }

    //Sort//
    public static class BuyerUsernameComparator implements Comparator<Buyer> {
        @Override
        public int compare(Buyer b1, Buyer b2) {
            if (b1 == null && b2 == null) {
                return 0;
            } else if (b1 == null) {
                return 1;
            } else if (b2 == null) {
                return -1;
            } else if (b1.getUserName() == null && b2.getUserName() == null) {
                return 0;
            } else if (b1.getUserName() == null) {
                return 1;
            } else if (b2.getUserName() == null) {
                return -1;
            } else {
                return b1.getUserName().compareTo(b2.getUserName());
            }
        }
    }
}