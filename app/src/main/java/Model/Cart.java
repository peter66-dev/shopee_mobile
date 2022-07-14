package Model;

import java.io.Serializable;

public class Cart implements Serializable {

    private int cartId;
    private int userId;
    private int isPaid; // 0: not paid, 1: paid

    public Cart() {
    }

    public Cart(int cartId, int userId, int isPaid) {
        this.cartId = cartId;
        this.userId = userId;
        this.isPaid = isPaid;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", isPaid=" + isPaid +
                '}';
    }
}
