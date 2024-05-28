package tqs.project.api.models;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Booking's ID", example = "1", required = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizador")
    @Schema(description = "User associated with booking", required = true)
    private Utilizador utilizador;

    @Column(nullable = false)
    @Schema(description = "Number of tables required", example = "1", required = true)
    private int quantidadeMesas;

    @Column(nullable = false)
    @Schema(description = "Status number | 0 - PENDING | 1 - PREPARING | 2 - COMPLETED | 3 - CANCELLED", example = "1", required = true)
    private int status;

    @Column(nullable = false)
    @Schema(description = "Booking's date", example = "2024-05-28", required = true)
    private LocalDate dia;

    @Column(nullable = false)
    @Schema(description = "Booking's hour", example = "10:00", required = true)
    private LocalTime hora;
}
