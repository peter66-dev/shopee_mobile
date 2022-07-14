package Model;

import java.io.Serializable;

public class CartDetails implements Serializable {

    private int cartDetailId;
    private int cartId;
    private int productId;
    private int quantity;

    public CartDetails() {
    }

    public CartDetails(int cartDetailId, int cartId, int productId, int quantity) {
        this.cartDetailId = cartDetailId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(int cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartDetails{" +
                "cartDetailId=" + cartDetailId +
                ", cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
