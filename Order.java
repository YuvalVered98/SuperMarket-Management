public class Order {
    private String date;
    private Product[] order;
    private int orderArraySize;
    private double totalPrice;

    public Order() {
        this.date = null;
        this.order = new Product[0];
        this.orderArraySize = 0;
        this.totalPrice = 0;
    }
    
    public Order(Order order) {
        this.date = order.date;
        this.order = new Product[order.order.length];
        try {
            for (int i = 0; i < order.orderArraySize; i++) {
                this.order[i] = order.order[i].clone();
            }
        }catch (CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }
        this.orderArraySize = order.orderArraySize;
        this.totalPrice = order.totalPrice;
    }

    //Getters//
    public String getDate() {
        return date;
    }

    public Product[] getOrder() {
        return order;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getOrderArraySize() {
        return orderArraySize;
    }

    //Setters//
    public void setDate(String date) {
        this.date = date;
    }

    //Validators//
    private void validateBuyerCartArray() {
        if (orderArraySize == order.length) {
            int newSize = order.length * 2;
            newSize = Math.max(2, newSize);
            Product[] temp = new Product[newSize];
            for (int i = 0; i < orderArraySize; i++) {
                temp[i] = order[i];
            }
            this.order = temp;
        }
    }

    //Adders//
    public void addProductToOrder(Product product){
        validateBuyerCartArray();
        this.order[orderArraySize] = product;
        orderArraySize++;
        this.totalPrice += product.getPrice();
    }

    //print and toStrings//
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Date: ").append(date)
        .append(", orders info: \n");
        for (Product product : order) {
            if (product == null) {
                break;
            }
            str.append(product).append("\n");
        }
        str.append("Total price of the cart: ").append(totalPrice).append("$");
        return str.toString();
    }

    public String printProducts() {
        StringBuilder str = new StringBuilder();
        int counter = 1;
        for (Product product : order) {
            if (product == null) {
                break;
            }
            str.append(counter).append(". ").append(product).append("\n");
            counter++;
        }
        return str.toString();
    }
}
