package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {
	private final String orderId;
	private final BigDecimal amount;
	private final String email;

	Order(String orderId, BigDecimal amount, String email) {
		this.email = email;
		this.orderId = orderId;
		this.amount = amount;
	}
	
	public String getEmail() {
		return email;
	}
	
    public BigDecimal getAmount() {
        return amount;
    }

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", amount=" + amount + ", email=" + email + "]";
	}
    
}
