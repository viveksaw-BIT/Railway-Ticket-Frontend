package com.ortb.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ortb.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByPnr(String pnr);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.id DESC")
    List<Booking> findAllByUserIdOrderByBookingIdDesc(@Param("userId") Long userId);

}