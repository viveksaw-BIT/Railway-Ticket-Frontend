package com.ortb.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortb.booking.dto.BookingDTO;
import com.ortb.booking.dto.PassengerDTO;
import com.ortb.booking.dto.PaymentDTO;
import com.ortb.booking.dto.SeatDTO;
import com.ortb.booking.dto.SegmentBookedSeatsDTO;
import com.ortb.booking.model.Booking;
import com.ortb.booking.model.Passenger;
import com.ortb.booking.model.Payment;
import com.ortb.booking.model.Train;
import com.ortb.booking.model.Users;
import com.ortb.booking.repo.BookingRepository;
import com.ortb.booking.repo.PaymentRepository;
import com.ortb.booking.repo.SeatRepository;
import com.ortb.booking.repo.SegmentRepository;
import com.ortb.booking.repo.TrainRepository;
import com.ortb.booking.repo.UserRepository;
import com.ortb.booking.service.TrainService;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainRepository trainRepository;
    
    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private SegmentRepository segmentRepository;
    
    @Autowired
    private TrainService trainService;
    
    
//    @Transactional
//    public Booking createBooking(Booking booking, Long userId) {
//    	//Optional<Train> train = trainRepository.findById(userId);
//    	Optional<Users> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//            booking.setUser(user.get());
//            booking.setBookingStatus("PENDING");
//            Train train = trainRepository.findById(booking.getTrain().getId())
//                    .orElseThrow(() -> new RuntimeException("Train not found"));
//            booking.setTrain(train);
//            //Booking savedBooking = bookingRepository.save(booking);
//            Payment payment = new Payment();
//            payment.setAmount(booking.getTotalFare());
//            payment.setPaymentStatus("PENDING");
//            payment.setBooking(booking);
//            paymentRepository.save(payment);
//            booking.setPayment(payment);
//            return bookingRepository.save(booking);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
    
    @Transactional
    public Booking createBooking(BookingDTO bookingDTO, Long userId) {
    	Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
        	Booking booking = new Booking();
        	booking.setPnr(generatePNR());
        	booking.setJourneyDate(bookingDTO.getJourneyDate());
        	//booking.setTotalFare(bookingDTO.getTotalFare());
        	
        	List<PassengerDTO> passengerDTO = bookingDTO.getPassengers();
        	booking.setTotalFare(calculateTotalFare(passengerDTO.size(),bookingDTO.getSeatClass(),bookingDTO.getQuota()));// Trying to calculate fare dynamically
        	List<Passenger> passengers = new ArrayList<Passenger>();
        	for (int i = 0; i <= passengerDTO.size() - 1; i++)
        	{	
        		Passenger passenger = new Passenger();
        		passenger.setName(passengerDTO.get(i).getName());
        		passenger.setAge(passengerDTO.get(i).getAge());
        		passenger.setGender(passengerDTO.get(i).getGender());
        		passenger.setBooking(booking);
        		passengers.add(passenger);
        	}
        	
        	booking.setPassengers(passengers);
            booking.setUser(user.get());
//            booking.setBookingStatus("PENDING");
            booking.setBookingStatus("SUCCESS");
            Train train = trainRepository.findById(bookingDTO.getTrainId())
                    .orElseThrow(() -> new RuntimeException("Train not found"));
            booking.setTrain(train);
            //Booking savedBooking = bookingRepository.save(booking);
            Payment payment = new Payment();
            payment.setAmount(booking.getTotalFare());
//            payment.setPaymentStatus("PENDING");
            payment.setPaymentStatus("SUCCESS");
            payment.setBooking(booking);
            paymentRepository.save(payment);
            booking.setPayment(payment);
            //------- Code for Updating booked seat count in segments starts here --------
            List<SegmentBookedSeatsDTO> segmentBookedSeats = segmentRepository.findSegmentsWithBookedSeats(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation());
            if(segmentBookedSeats.isEmpty())
            	throw new RuntimeException("No segments found between the provided stations.");
            else
            {
            	List<Long> segmentIdsForSeatUpdate = new ArrayList<>();
            	for(SegmentBookedSeatsDTO segmentSeats: segmentBookedSeats)
            	{
            		segmentIdsForSeatUpdate.add(segmentSeats.getSegmentId());
            	}
            
            List<Integer> availableSeatNumbers = seatRepository.findSeatsByStatusSeatClassAndQuotaAcrossAllSegments(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), bookingDTO.getSeatClass(), bookingDTO.getQuota(), "Available");
            
            if(availableSeatNumbers.isEmpty())
            	throw new RuntimeException("No available seats for the given criteria.");
            else 
            {
            	int availableSeats = availableSeatNumbers.size();
            	if(availableSeats>=passengers.size())
            	{
            		List<Integer> seatNumbersForAllocation = new ArrayList<>();
            		for(int counter=0;counter<passengers.size();counter++)
            		{
            			seatNumbersForAllocation.add(availableSeatNumbers.get(counter));
            		}
            		seatRepository.updateSeatStatus(seatNumbersForAllocation, segmentIdsForSeatUpdate, "Reserved");
            		trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//            		for(Passenger passenger:passengers)
//            		{
//            			
//            		}
            		for(int counter=0;counter<passengers.size();counter++)
            		{
            			passengers.get(counter).setSeatNumber(Long.valueOf(seatNumbersForAllocation.get(counter)));
            			//seatNumbersForAllocation.add(availableSeatNumbers.get(counter));
            		}
            		booking.setPassengers(passengers);
//            		for(int seatNumber:availableSeatNumbers)
//            		{
//            			seatRepository
//            		}
            		
//            		for(SegmentBookedSeatsDTO segmentSeats: segmentBookedSeats)
//                    {
//                    	//SeatDTO availableSeatCount = seatRepository.countSeatsBySegmentSeatClassQuotaAndStatus(segmentSeats.getSegmentId(), bookingDTO.getSeatClass(), bookingDTO.getQuota(), "Available");
////                    	if(availableSeatCount == null)
////                    		throw new RuntimeException("No available seats for the given criteria.");
////                    	else
////                    	{
//                    		trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//                    	//}
//                    }
                }
            	else if(availableSeats==0)
        			throw new RuntimeException("All seats for this station are booked");
        		else
        			throw new RuntimeException("Only "+availableSeats+" seats are available");
            		
            	
            }
        }
//            for(SegmentBookedSeatsDTO segmentSeats: segmentBookedSeats)
//            {
//            	if(segmentSeats.getTotalSeats()>segmentSeats.getBookedSeats()) {
//            		int availableSeats = segmentSeats.getTotalSeats()-segmentSeats.getBookedSeats();
//            		if(availableSeats>=passengers.size())
//            		{
//            		
//            			SeatDTO availableSeatCount = seatRepository.countSeatsBySegmentSeatClassQuotaAndStatus(segmentSeats.getSegmentId(), bookingDTO.getSeatClass(), bookingDTO.getQuota(), "Available");
//            			if(availableSeatCount == null)
//            				throw new RuntimeException("No available seats for the given criteria.");
//            			else
//            			{
//            				trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//            			}
//            		}
//            		else
//            			throw new RuntimeException("Only "+availableSeats+" seats are available");
//            	}
//            	else
//            	{
//            		throw new RuntimeException("All seats for this station are booked");
//            	}
//            }
            //------- Code for Updating booked seat count in segments ends here -------- 
            return bookingRepository.save(booking);
        } 
        else
        {
            throw new RuntimeException("User not found");
        }
    	//return null;
    }
    //Backup on 5Aug 11:21AM
//    @Transactional
//    public Booking createBooking(BookingDTO bookingDTO, Long userId) {
//    	//System.out.println(bookingDTO.toString()); 
////    	List<PassengerDTO> passengerDTO = bookingDTO.getPassengers();
////    	List<Passenger> passengers = new ArrayList<Passenger>();
////    	for (int i = 0; i <= passengerDTO.size() - 1; i++)
////    	{	
////    		Passenger passenger = new Passenger();
////    		passenger.setName(passengerDTO.get(i).getName());
////    		passenger.setAge(passengerDTO.get(i).getAge());
////    		passenger.setGender(passengerDTO.get(i).getGender());
////    		passengers.add(passenger);
////    	}
////      payment.setPaymentStatus("PENDING");
//    	
////    	System.out.println("Num of passengers: "+passengerDTO.size()+"/"+passengers.size());
////    	for(Passenger p: passengers)
////    	{
////    		System.out.println(p.getName()); 
////    	}
//    	//Optional<Train> train = trainRepository.findById(userId);
//    	Optional<Users> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//        	Booking booking = new Booking();
//        	booking.setPnr(generatePNR());
//        	booking.setJourneyDate(bookingDTO.getJourneyDate());
//        	booking.setTotalFare(bookingDTO.getTotalFare());
//        	List<PassengerDTO> passengerDTO = bookingDTO.getPassengers();
//        	List<Passenger> passengers = new ArrayList<Passenger>();
//        	for (int i = 0; i <= passengerDTO.size() - 1; i++)
//        	{	
//        		Passenger passenger = new Passenger();
//        		passenger.setName(passengerDTO.get(i).getName());
//        		passenger.setAge(passengerDTO.get(i).getAge());
//        		passenger.setGender(passengerDTO.get(i).getGender());
//        		passenger.setBooking(booking);
//        		passengers.add(passenger);
//        	}
//        	
//        	booking.setPassengers(passengers);
//            booking.setUser(user.get());
//            booking.setBookingStatus("PENDING");
//            Train train = trainRepository.findById(bookingDTO.getTrainId())
//                    .orElseThrow(() -> new RuntimeException("Train not found"));
//            booking.setTrain(train);
//            //Booking savedBooking = bookingRepository.save(booking);
//            Payment payment = new Payment();
//            payment.setAmount(booking.getTotalFare());
//            payment.setPaymentStatus("PENDING");
//            payment.setBooking(booking);
//            paymentRepository.save(payment);
//            booking.setPayment(payment);
//            //------- Code for Updating booked seat count in segments starts here --------
//            List<SegmentBookedSeatsDTO> segmentBookedSeats = segmentRepository.findSegmentsWithBookedSeats(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation());
//            for(SegmentBookedSeatsDTO segmentSeats: segmentBookedSeats)
//            {
//            	if(segmentSeats.getTotalSeats()>segmentSeats.getBookedSeats()) {
//            		int availableSeats = segmentSeats.getTotalSeats()-segmentSeats.getBookedSeats();
//            		if(availableSeats>=passengers.size())
//            		{
//            		
//            			SeatDTO availableSeatCount = seatRepository.countSeatsBySegmentSeatClassQuotaAndStatus(segmentSeats.getSegmentId(), bookingDTO.getSeatClass(), bookingDTO.getQuota(), "Available");
//            			if(availableSeatCount == null)
//            				throw new RuntimeException("No available seats for the given criteria.");
//            			else
//            			{
//            				trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//            			}
////            			List<SeatDTO> seatCountByClassQuota = seatRepository.countSeatsBySeatClassAndQuota(segmentSeats.getSegmentId());
////            			for(SeatDTO seatInfo: seatCountByClassQuota) {
////            				System.out.println(seatInfo.toString()); 
//////            				SeatDTO [seatClass=A/C, quota=Ladies Quota, count=1]
//////            						SeatDTO [seatClass=A/C, quota=General Quota, count=3]
//////            						SeatDTO [seatClass=Non-A/C, quota=Ladies Quota, count=2]
//////            						SeatDTO [seatClass=Non-A/C, quota=General Quota, count=6]
////            				if(seatInfo.getSeatClass().equalsIgnoreCase(bookingDTO.getSeatClass()) && seatInfo.getQuota().equalsIgnoreCase(bookingDTO.getQuota()))
////            				{
////            					SeatDTO seatCount = seatRepository.countSeatsBySegmentSeatClassQuotaAndStatus(segmentSeats.getSegmentId(), bookingDTO.getSeatClass(), bookingDTO.getQuota(), "Available");
////            					trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
////            				}
////            				//seatInfo.getSeatClass().equalsIgnoreCase(bookingDTO.getSeatClass());
////            			}
////            			trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//            		}
//            		else
//            			throw new RuntimeException("Only "+availableSeats+" seats are available");
//            	}
//            	else
//            	{
//            		throw new RuntimeException("All seats for this station are booked");
//            	}
//            }
//            //------- Code for Updating booked seat count in segments ends here -------- 
//            return bookingRepository.save(booking);
//        } 
//        else
//        {
//            throw new RuntimeException("User not found");
//        }
//    	//return null;
//    }
    //Functional code
//    @Transactional
//    public Booking createBooking(BookingDTO bookingDTO, Long userId) {
//    	//System.out.println(bookingDTO.toString()); 
////    	List<PassengerDTO> passengerDTO = bookingDTO.getPassengers();
////    	List<Passenger> passengers = new ArrayList<Passenger>();
////    	for (int i = 0; i <= passengerDTO.size() - 1; i++)
////    	{	
////    		Passenger passenger = new Passenger();
////    		passenger.setName(passengerDTO.get(i).getName());
////    		passenger.setAge(passengerDTO.get(i).getAge());
////    		passenger.setGender(passengerDTO.get(i).getGender());
////    		passengers.add(passenger);
////    	}
////      payment.setPaymentStatus("PENDING");
//    	
////    	System.out.println("Num of passengers: "+passengerDTO.size()+"/"+passengers.size());
////    	for(Passenger p: passengers)
////    	{
////    		System.out.println(p.getName()); 
////    	}
//    	//Optional<Train> train = trainRepository.findById(userId);
//    	Optional<Users> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//        	Booking booking = new Booking();
//        	booking.setPnr(generatePNR());
//        	booking.setJourneyDate(bookingDTO.getJourneyDate());
//        	booking.setTotalFare(bookingDTO.getTotalFare());
//        	List<PassengerDTO> passengerDTO = bookingDTO.getPassengers();
//        	List<Passenger> passengers = new ArrayList<Passenger>();
//        	for (int i = 0; i <= passengerDTO.size() - 1; i++)
//        	{	
//        		Passenger passenger = new Passenger();
//        		passenger.setName(passengerDTO.get(i).getName());
//        		passenger.setAge(passengerDTO.get(i).getAge());
//        		passenger.setGender(passengerDTO.get(i).getGender());
//        		passenger.setBooking(booking);
//        		passengers.add(passenger);
//        	}
//        	
//        	booking.setPassengers(passengers);
//            booking.setUser(user.get());
//            booking.setBookingStatus("PENDING");
//            Train train = trainRepository.findById(bookingDTO.getTrainId())
//                    .orElseThrow(() -> new RuntimeException("Train not found"));
//            booking.setTrain(train);
//            //Booking savedBooking = bookingRepository.save(booking);
//            Payment payment = new Payment();
//            payment.setAmount(booking.getTotalFare());
//            payment.setPaymentStatus("PENDING");
//            payment.setBooking(booking);
//            paymentRepository.save(payment);
//            booking.setPayment(payment);
//            //------- Code for Updating booked seat count in segments starts here --------
//            List<SegmentBookedSeatsDTO> segmentBookedSeats = segmentRepository.findSegmentsWithBookedSeats(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation());
//            for(SegmentBookedSeatsDTO segmentSeats: segmentBookedSeats)
//            {
//            	if(segmentSeats.getTotalSeats()>segmentSeats.getBookedSeats()) {
//            		int availableSeats = segmentSeats.getTotalSeats()-segmentSeats.getBookedSeats();
//            		if(availableSeats>=passengers.size())
//            			trainService.updateBookedSeatsBetweenStationsByTrainIdAndStationNames(bookingDTO.getTrainId(), bookingDTO.getSourceStation(), bookingDTO.getDestinationStation(), passengers.size());
//            		else
//            			throw new RuntimeException("Only "+availableSeats+" seats are available");
//            	}
//            	else
//            	{
//            		throw new RuntimeException("All seats for this station are booked");
//            	}
//            }
//            //------- Code for Updating booked seat count in segments ends here -------- 
//            return bookingRepository.save(booking);
//        } 
//        else
//        {
//            throw new RuntimeException("User not found");
//        }
//    	//return null;
//    }
    
//    public String generatePNR() {
//        // Implement PNR generation logic here
//        return UUID.randomUUID().toString(); // Example PNR generation
//    }
    public String generatePNR() {
        // Generate a UUID and extract its numeric representation
        long numericPart = Math.abs(UUID.randomUUID().getMostSignificantBits());
        
        // Convert to a string and ensure it is 10 digits long
        String pnr = String.valueOf(numericPart);
        
        // If the numeric part is longer than 10 digits, truncate it
        if (pnr.length() > 10) {
            pnr = pnr.substring(0, 10);
        } else {
            // If the numeric part is shorter than 10 digits, pad it with zeros
            pnr = String.format("%010d", numericPart);
            pnr = "PNR"+pnr;
        }
        
        return pnr;
    }
    
    public double calculateTotalFare(int passengerCount, String seatClass, String quota) {
    	double baseFare = 150.0;
    	if(seatClass.equalsIgnoreCase("A/C")) {
//    		if(quota.equalsIgnoreCase("General Quota"))
//    			baseFare = baseFare + 100.0;
    			baseFare = baseFare + 100.0;
    		if(quota.equalsIgnoreCase("Ladies Quota"))
    			baseFare = baseFare + 150.0;
    	}
    	else if(seatClass.equalsIgnoreCase("Non-A/C")) {
    		if(quota.equalsIgnoreCase("Ladies Quota"))
    			baseFare = baseFare + 50.0;
    		
    	}
    	double totalFare = baseFare * passengerCount;
    	return totalFare;
    }
    
    
    public Booking getBookingByPnr(String pnr) {
        return bookingRepository.findByPnr(pnr);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public List<Booking> getAllBookingsByUserId(Long userId)
    {
    	return bookingRepository.findAllByUserIdOrderByBookingIdDesc(userId);
    }

    public Booking updateBookingStatus(String pnr, String status) {
        Booking booking = bookingRepository.findByPnr(pnr);
        if (booking != null) {
            booking.setBookingStatus(status);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    
//    @Transactional(readOnly = true)
    @Transactional
    public List<Integer> findAvailableSeatsBetweenStations(Long trainId, String startStationName, String endStationName) {
        // Find segments between the start and end stations
        List<Long> segmentIds = segmentRepository.findSegmentIdsBetweenStations(trainId, startStationName, endStationName);

        if (segmentIds.isEmpty()) {
            //return Collections.emptyList();
        	throw new RuntimeException("No segments found between the provided stations.");
        }

        // Find available seat numbers across all segments
        long segmentCount = segmentIds.size();
        return seatRepository.findAvailableSeatNumbersAcrossSegments(segmentIds, segmentCount);
    }

	public Booking getBookingByBookingId(Long bookingId) {
		return bookingRepository.findById(bookingId).orElse(null);
	}
}