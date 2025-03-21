public class PackagedProduct extends Product {
    private double packagePrice ;

    public PackagedProduct(String name, double price, Category category, double packagePrice) {
        super(name, price, category);
        this.packagePrice = packagePrice;
    }

    public PackagedProduct(PackagedProduct product) {
        super(product);
        this.packagePrice = product.packagePrice;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    @Override
    public String toString() {
        return super.toString() + ", Special Package: Yes, Package Price: " + packagePrice + "$, Total Price: " + (this.getPrice() + packagePrice) + "$";
    }
}
