package com.ortb.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortb.booking.dto.BookingDTO;
import com.ortb.booking.model.Booking;
import com.ortb.booking.model.Passenger;
import com.ortb.booking.service.BookingService;
import com.ortb.booking.service.PassengerService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

//    @PostMapping("/{userId}")
//    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking, @PathVariable Long userId) {
//        Booking createdBooking = bookingService.createBooking(booking, userId);
//        return ResponseEntity.ok(createdBooking);
//    }
    @PostMapping("/{userId}")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO, @PathVariable Long userId) {
        Booking createdBooking = bookingService.createBooking(bookingDTO, userId);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<Booking> getBookingByPnr(@PathVariable String pnr) {
        Booking booking = bookingService.getBookingByPnr(pnr);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping("bookingid/{bookingId}")
    public ResponseEntity<Booking> getBookingByBookingId(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingByBookingId(bookingId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getAllBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getAllBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{pnr}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable String pnr, @RequestBody String status) {
        Booking updatedBooking = bookingService.updateBookingStatus(pnr, status);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/passenger")
    public ResponseEntity<Passenger> addPassenger(@RequestBody Passenger passenger) {
        Passenger addedPassenger = passengerService.addPassenger(passenger);
        return ResponseEntity.ok(addedPassenger);
    }

    @GetMapping("/passenger")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }
}