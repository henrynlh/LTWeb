package murach.data;

import java.util.ArrayList;
import murach.business.Product;

// Fixed CD catalog. This example does not need a database connection.
public class ProductDB {

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("8601",
                "86 (the band) - True Life Songs and Pictures", "14.95"));
        products.add(new Product("pf01",
                "Paddlefoot - The first CD", "12.95"));
        products.add(new Product("pf02",
                "Paddlefoot - The second CD", "14.95"));
        products.add(new Product("jr01",
                "Joe Rut - Genuine Wood Grained Finish", "14.95"));

        return products;
    }

    public static Product selectProduct(String productCode) {
        ProductDB productDB = new ProductDB();
        ArrayList<Product> products = productDB.getProducts();

        for (Product product : products) {
            if (product.getCode().equals(productCode)) {
                return product;
            }
        }

        return null;
    }
}
