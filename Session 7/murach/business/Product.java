package murach.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Product implements Serializable {
    private final String code;
    private final String description;
    private final BigDecimal price;

    public Product(String code, String description, String price) {
        this.code = code;
        this.description = description;
        this.price = new BigDecimal(price);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public String getPriceCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        return currency.format(price);
    }
}
