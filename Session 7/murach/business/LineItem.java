package murach.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class LineItem implements Serializable {
    private final Product product;
    private final int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    public BigDecimal getTotal() {
        BigDecimal price = product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    public String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        return currency.format(getTotal());
    }
}
