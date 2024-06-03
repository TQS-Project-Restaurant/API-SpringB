package tqs.project.api.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tqs.project.api.dao.ReservaRequest;
import tqs.project.api.models.Reserva;
import tqs.project.api.services.ReservaService;

@RestController
@Tag(name = "Booking API")
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;
    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }


    @Operation(summary = "Get available slots by date", description = "Returns all available slots for booking on given date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Successfully retrieved", 
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", example = "[[11, 0], [12, 0], [22, 0]]")))
    })
    @GetMapping("availableSlots")
    public ResponseEntity<List<LocalTime>> getTimetable(@RequestParam("date") String date){
        LocalDate bookingDate = LocalDate.parse(date);

        List<LocalTime> availableSlots = reservaService.getAvailableSlots(bookingDate);

        logger.info("Retrieved all AVAILABLE SLOTS for date " + date);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }


    @Operation(summary = "Post booking on selected date", description = "Returns booked object and confirmation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                    description = "Successfully created"),
        @ApiResponse(responseCode = "400",
                    description = "Failed to create booking")
    })
    @PostMapping
    public ResponseEntity<Reserva> createBooking(@RequestBody ReservaRequest reserva){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null){
            logger.warn("Tried to verify authentication; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Reserva booking = reservaService.createBooking(reserva, authentication.getName());

        if (booking == null){
            logger.warn("Tried to create object RESERVA; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Created RESERVA.");
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all bookings from specific user", description = "Returns booking list associated with user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Failed to get authenticated user")
    })
    @GetMapping
    public ResponseEntity<List<Reserva>> getUserBookings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null){
            logger.warn("Tried to verify authentication; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Reserva> bookings = reservaService.getUserBookings(authentication.getName());

        logger.info("Retrieved all UTILIZADOR RESERVAS");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Operation(summary = "Get all pending bookings", description = "Returns list with all pending bookings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Failed to get authenticated user")
    })
    @GetMapping("/pending")
    public ResponseEntity<List<Reserva>> getPendingBookings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null){
            logger.warn("Tried to verify authentication; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Reserva> pendingBookings = reservaService.getPendingBookings();

        logger.info("Retrieved all PENDING RESERVAS");
        return new ResponseEntity<>(pendingBookings, HttpStatus.OK);
    }

    @Operation(summary = "Confirm booking", description = "Returns updated booking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Failed to get authenticated user")
    })
    @PutMapping("/confirm/{id}")
    public ResponseEntity<Reserva> confirmBooking(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null){
            logger.warn("Tried to verify authentication; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Reserva updatedBooking = reservaService.confirmBooking(id);

        logger.info("Updated RESERVA to COMPLETED");
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @Operation(summary = "Cancel booking", description = "Returns updated booking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Failed to get authenticated user")
    })
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Reserva> cancelBooking(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null){
            logger.warn("Tried to verify authentication; However, it failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Reserva updatedBooking = reservaService.cancelBooking(id);

        logger.info("Updated RESERVA to CANCELLED");
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }
}
