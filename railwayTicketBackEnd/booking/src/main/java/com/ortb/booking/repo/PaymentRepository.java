package com.ortb.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortb.booking.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
