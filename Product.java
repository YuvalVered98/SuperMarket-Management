public class Product implements Cloneable{
    private final String name;
    private double price;
    public enum Category {Kids, Electricity, Office, Clothing}
    private final Category eCategory;
    private static int idCounter = 1000000;
    private final int id ;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.eCategory = category;
        idCounter++;
        this.id = idCounter;
    }

    public Product(Product product) {
        this.name = product.name;
        this.price = product.price;
        this.eCategory = product.eCategory;
        this.id = product.id;
    }

    //Getters//
    public String getName() {
        return name;
    }

    private int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public Category geteCategory() {
        return eCategory;
    }

    //prints and toStrings//
    @Override
    public String toString() {
        return "ID: " + id + ", Category: " + eCategory.name() +", Name: " + name + ", Total Price: " + price + "$";
    }

    public static String printAllCategories() {
        Category[] allCategories = Category.values();
        StringBuilder str = new StringBuilder();
        for (Category allCategory : allCategories) {
            str.append((allCategory.ordinal() + 1)).append(")").append(allCategory.name()).append("\n");
        }
        return str.toString();
    }

    @Override
    public Product clone() throws CloneNotSupportedException {
        return (Product)super.clone();
    }
}
