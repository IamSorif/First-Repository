package com.payment.demo.Repository;

import com.payment.demo.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Additional methods if needed
}
