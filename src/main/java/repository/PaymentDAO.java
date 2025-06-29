package repository;

import entity.Payment;

public class PaymentDAO extends BaseDAO<Payment> {
    public PaymentDAO() {
        super(Payment.class);
    }
}
