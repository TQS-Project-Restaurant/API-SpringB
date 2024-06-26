package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.others.ROLES;
import tqs.project.api.others.Restaurant;
import tqs.project.api.others.STATUS;
import tqs.project.api.models.Reserva;
import tqs.project.api.models.Utilizador;
import tqs.project.api.repositories.ReservaRepository;
import tqs.project.api.repositories.UtilizadorRepository;
import tqs.project.api.services.impl.ReservaServiceImpl;
import tqs.project.api.dao.ReservaRequest;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {
    @Mock(strictness = Mock.Strictness.LENIENT )
    ReservaRepository reservaRepository;

    @Mock(strictness = Mock.Strictness.LENIENT )
    UtilizadorRepository utilizadorRepository;

    @InjectMocks
    ReservaServiceImpl service;

    Restaurant restaurant = new Restaurant();
    LocalDate day = LocalDate.parse("2024-01-01");
    LocalDate day2 = LocalDate.parse("2024-01-02");
    Utilizador utilizador = new Utilizador();
    Utilizador utilizador2 = new Utilizador();
    Reserva reserva = new Reserva();

    @BeforeEach
    void setUp(){
        List<Reserva> noBookings = new ArrayList<>();
        when(reservaRepository.findByDia(day)).thenReturn(noBookings);

        utilizador.setEmail("user@gmail.com");
        utilizador.setPassword("123123123");
        utilizador.setRole(ROLES.USER);

        utilizador2.setEmail("nolimits88@live.com.pt");
        utilizador2.setPassword("0987654321");
        utilizador2.setRole(ROLES.USER);

        reserva.setUtilizador(utilizador);
        reserva.setQuantidadeMesas(10);
        reserva.setStatus(STATUS.COMPLETED.ordinal());
        reserva.setDia(day);
        reserva.setHora(LocalTime.of(11,00));

        Reserva reserva2 = new Reserva();
        reserva2.setUtilizador(utilizador2);
        reserva2.setQuantidadeMesas(1);
        reserva2.setStatus(STATUS.COMPLETED.ordinal());
        reserva2.setDia(day);
        reserva2.setHora(LocalTime.of(12,00));

        Reserva reserva3 = new Reserva();
        reserva3.setUtilizador(utilizador2);
        reserva3.setQuantidadeMesas(1);
        reserva3.setStatus(STATUS.PENDING.ordinal());
        reserva3.setDia(day);
        reserva3.setHora(LocalTime.of(12,00));

        List<Reserva> someBookings = Arrays.asList(reserva, reserva2);
        when(reservaRepository.findByDia(day2)).thenReturn(someBookings);
        when(reservaRepository.findAllByStatus(STATUS.PENDING.ordinal())).thenReturn(Arrays.asList(reserva3));
    }

    @Test
    void givenNoReservas_whenGetReservasByDay_thenReturnReservas(){
        List<LocalTime> availableSlots = service.getAvailableSlots(day);

        assertThat(availableSlots).hasSize(restaurant.getDailySlots().size());
        verify(reservaRepository, times(1)).findByDia(Mockito.any());
    }

    @Test
    void givenSomeReservas_whenGetReservasByDay_thenReturnAvailableReservas(){
        List<LocalTime> availableSlots = service.getAvailableSlots(day2);

        assertThat(availableSlots)
            .hasSize(restaurant.getDailySlots().size() - 1)
            .doesNotContain(LocalTime.of(11,00));

        verify(reservaRepository, times(1)).findByDia(Mockito.any());
    }

    @Test
    void givenReserva_whenCreateReserva_thenReturnReserva(){
        Reserva innerReserva = new Reserva();
        innerReserva.setHora(LocalTime.now());
        innerReserva.setDia(LocalDate.now());
        innerReserva.setQuantidadeMesas(1);
        innerReserva.setStatus(STATUS.PENDING.ordinal());
        innerReserva.setUtilizador(utilizador);

        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setQuantidadeMesas(1);
        reservaRequest.setDia(LocalDate.now());
        reservaRequest.setHora(LocalTime.now());

        when(utilizadorRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(utilizador));
        when(reservaRepository.save(Mockito.any())).thenReturn(innerReserva);

        Reserva found = service.createBooking(reservaRequest, utilizador.getEmail());

        assertThat(utilizador.getReservas()).isNotNull();
        assertThat(found).isEqualTo(innerReserva);
        verify(reservaRepository, times(1)).save(Mockito.any());
    }

    @Test
    void givenReservaWithTooManyPeople_whenCreateReserva_thenReturnNull(){
        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setQuantidadeMesas(12);
        reservaRequest.setDia(LocalDate.now());
        reservaRequest.setHora(LocalTime.now());

        Reserva found = service.createBooking(reservaRequest, utilizador.getEmail());

        assertThat(found).isNull();
        verify(reservaRepository, never()).save(Mockito.any());
    }

    @Test
    void givenUtilizador_whenGetReserva_thenReturnList() {
        List<Reserva> utilizadorReservas = Arrays.asList(reserva);

        utilizador.setReservas(utilizadorReservas);
        when(reservaRepository.findByUtilizador(utilizador)).thenReturn(utilizadorReservas);
        when(utilizadorRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(utilizador));

        List<Reserva> bookings = service.getUserBookings("user@gmail.com");

        assertThat(bookings).hasSize(1);
        verify(reservaRepository, times(1)).findByUtilizador(Mockito.any());
    }

    @Test
    void givenBookings_whenGetPendingBookings_thenReturnPendingList(){
        List<Reserva> bookings = service.getPendingBookings();

        assertThat(bookings).hasSize(1);
        verify(reservaRepository, times(1)).findAllByStatus(STATUS.PENDING.ordinal());
    }

    @Test
    void givenBookingToUpdate_whenConfirmBooking_thenReturnUpdatedBooking() {
        Reserva reservaToUpdate = new Reserva();
        reservaToUpdate.setQuantidadeMesas(5);
        reservaToUpdate.setStatus(STATUS.PENDING.ordinal());
        reservaToUpdate.setDia(day);
        reservaToUpdate.setHora(LocalTime.of(11,00));

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaToUpdate));
        when(reservaRepository.save(Mockito.any())).thenReturn(reserva);
        
        Reserva updatedReserva = service.confirmBooking(1L);

        assertThat(updatedReserva.getStatus()).isEqualTo(STATUS.COMPLETED.ordinal());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidBookingIdToUpdate_whenConfirmBooking_thenReturnNull() {
        Reserva found = service.confirmBooking(1L);

        assertThat(found).isNull();
        verify(reservaRepository, times(1)).findById(Mockito.any());
    }

    @Test
    void givenBookingToUpdate_whenCancelBooking_thenReturnUpdatedBooking() {
        Reserva reservaToUpdate = new Reserva();
        reservaToUpdate.setQuantidadeMesas(5);
        reservaToUpdate.setStatus(STATUS.PENDING.ordinal());
        reservaToUpdate.setDia(day);
        reservaToUpdate.setHora(LocalTime.of(11,00));

        Reserva reservaCancelled = new Reserva();
        reservaCancelled.setQuantidadeMesas(5);
        reservaCancelled.setStatus(STATUS.CANCELLED.ordinal());
        reservaCancelled.setDia(day);
        reservaCancelled.setHora(LocalTime.of(11,00));

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaToUpdate));
        when(reservaRepository.save(Mockito.any())).thenReturn(reservaCancelled);
        
        Reserva updatedReserva = service.cancelBooking(1L);

        assertThat(updatedReserva.getStatus()).isEqualTo(STATUS.CANCELLED.ordinal());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidBookingIdToUpdate_whenCancelBooking_thenReturnNull() {
        Reserva found = service.cancelBooking(1L);

        assertThat(found).isNull();
        verify(reservaRepository, times(1)).findById(Mockito.any());
    }
}
