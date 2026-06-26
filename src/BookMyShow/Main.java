package BookMyShow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        BookMyShowApp app = new BookMyShowApp();
        app.addCity("Delhi"); // todo use enums
        app.addCity("Mumbai");

        Venue venue1 = new Venue("Delhi", "venue_id_1", 100);
        app.addVenue(venue1);

        Event event1 = new Event("event_id_1", venue1, "time_slot_1");
        Event event2 = new Event("event_id_2", venue1, "time_slot_2");
        app.addEvent(event1);
        app.addEvent(event2);

        app.selectCity("Delhi");
        List<Event> events = app.listEvents(); // todo add filter on time slot

        app.selectEvent(events.get(0));

        List<Seat> seats = app.getAvailableSeats();

        List<Seat> selectedSeats = new ArrayList<>();
        selectedSeats.add(seats.get(0));
        selectedSeats.add(seats.get(1));

        app.createBooking("user_id_1", selectedSeats);

        List<Booking> bookings = app.showBookings("user_id_1");
    }
}

class BookMyShowApp {
    List<String> cities;
    List<Venue> venues;
    List<Event> events;
    List<Booking> bookings;
    List<BookingSeat> bookingSeats;

    String selectedCity;
    private Event selectedEvent;

    public BookMyShowApp(){
        this.cities = new ArrayList<>();
        this.venues = new ArrayList<>();
        this.events = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.bookingSeats = new ArrayList<>();
    }

    public void addCity(String city) {
        this.cities.add(city);
    }

    public void addVenue(String city, String venueId, int numSeats) {
        Venue venue = new Venue(city, venueId, numSeats);
        this.venues.add(venue);
    }

    public void selectCity(String city) {
        this.selectedCity = city;
    }

    public List<Event> listEvents() {
        List<Event> eventsInCity = new ArrayList<>();
        for(Event event : this.events){
            if(this.selectedCity.equals(event.getVenue().getCity())){
                eventsInCity.add(event);
            }
        }

        return eventsInCity;
    }

    public void selectEvent(Event event) {
        this.selectedEvent = event;
    }

    public void createBooking(String userId, List<Seat> seats) {
        String newBookingId = Integer.toString(this.bookings.size());
        Booking booking = new Booking(newBookingId, userId, this.selectedEvent.getEventId(), seats.size(), "SUCCESSFUL");
        this.bookings.add(booking);

        for(Seat seat : seats){
            this.bookingSeats.add(new BookingSeat(newBookingId, seat.getSeatId()));
        }
    }

    public List<Seat> getAvailableSeats() {
        // for a given event, get all the seats that are available -> this.event

        // 1. get bookings for this event
        List<String> bookingIds = new ArrayList<>();
        for(Booking booking : this.bookings){
            if(booking.getEventId().equals(this.selectedEvent.getEventId())){
                bookingIds.add(booking.getBookingId());
            }
        }

        // 2. get seats for those booking -> Set<Seat>
        Set<String> bookedSeatIds = new HashSet<>();
        for(BookingSeat bookingSeat : this.bookingSeats){
            if(bookingIds.contains(bookingSeat.getBookingId())){
                bookedSeatIds.add(bookingSeat.getSeatId());
            }
        }

        // 3. for my given venue, find the seats that are not in the above set
        Venue eventVenue = this.selectedEvent.getVenue();
        List<Seat> availableSeats = new ArrayList<>();
        for(Seat seat : eventVenue.seats){
            if(!bookedSeatIds.contains(seat.seatId)){
                availableSeats.add(seat);
            }
        }

        return availableSeats;
    }

    private Venue getVenueById(String venueId) {
        for(Venue venue : this.venues){
            if(venue.getVenueId().equals(venueId)){
                return venue;
            }
        }
        return null;
    }

    public List<Booking> showBookings(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for(Booking booking : this.bookings){
            if(booking.getUserId().equals(userId)){
                userBookings.add(booking);
            }
        }

        return userBookings;
    }

    public void addVenue(Venue venue) {
        this.venues.add(venue);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}

class Event {
    private String eventId;
    private Venue venue; // Venue venue
    private String timeSlot;

    public Event(String eventId, Venue venue, String timeSlot) {
        this.eventId = eventId;
        this.venue = venue;
        this.timeSlot = timeSlot;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}

class Venue {
    String city;
    String venueId;
    int numSeats;
    List<Seat> seats = new ArrayList<>();

    public Venue(String city, String venueId, int numSeats) {
        this.city = city;
        this.venueId = venueId;
        this.numSeats = numSeats;

        for(int i=0; i<numSeats; i++){
            seats.add(new Seat(venueId, i));
        }
    }

    public String getVenueId() {
        return this.venueId;
    }

    public String getCity() {
        return this.city;
    }
}

class Booking {
    String bookingId;
    String userId;
    private String eventId;
    int numSeats;
    String status;

    public Booking(String bookingId, String userId, String eventId, int numSeats, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.setEventId(eventId);
        this.numSeats = numSeats;
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return this.userId;
    }
}

class BookingSeat {
    private String bookingId;
    private String seatId;

    public BookingSeat(String bookingId, String seatId) {
        this.bookingId = bookingId;
        this.seatId = seatId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getSeatId() {
        return seatId;
    }
}

class Seat {
    String venueId;
    String seatId;

    public Seat(String venueId, int seatId) {
        this.venueId = venueId;
        this.seatId = String.valueOf(seatId);
    }

    public String getSeatId() {
        return this.seatId;
    }
}

/*
List of Entities

Venue
-venue_id
-seat_id / List<Seat>
-city_id

Seat
-

Event
-event_id
-event_name
-venue_id
-slot_id
-city_id

Booking
-booking_id
-event_id
-user_id
-num_seats
-status

BookingSeat
-booking_id
-seat_id
 */