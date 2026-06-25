package BookMyShow;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello there");

        BookMyShowApp app = new BookMyShowApp();
        app.addCity("Delhi"); // todo use enums
        app.addCity("Mumbai");

        app.addVenue("Delhi", "venue_id_1", 100); // todo create a separate class for venue

        app.addEvent(new Event("Delhi", "event_id_1", "venue_id_1", "time_slot_1"));
        app.addEvent(new Event("Delhi", "event_id_2", "venue_id_1", "time_slot_2"));
    }
}

class BookMyShowApp {
    List<String> cities;
    List<Venue> venues;
    List<Event> events;

    public void addCity(String city) {
        this.cities.add(city);
    }

    public void addVenue(String city, String venueId, int numSeats) {
        Venue venue = new Venue(city, venueId, numSeats);
        this.venues.add(venue);
    }


    public void addEvent(Event event) {

    }
}

class Event {

    public Event(String city, String eventId, String venueId, String timeSlot) {
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
}

class Booking {

}

class BookingSeat {

}

class Seat {
    String venueId;
    int seatId;

    public Seat(String venueId, int seatId) {
        this.venueId = venueId;
        this.seatId = seatId;
    }
}

/*
List of Entities

Venue
-venue_id
-seat_id / List<Seat>
-city_id

Event
-event_id
-event_name
-venue_id
-slot_id
-city_id

Booking
-booking_id
-num_seats
-status

BookingSeat
-booking_id
-seat_id
 */