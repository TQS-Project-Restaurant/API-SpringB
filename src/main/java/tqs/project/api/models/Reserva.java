package tqs.project.api.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
    @Schema(description = "Booking's ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizador_id")
    @Schema(description = "User associated with booking", requiredMode = RequiredMode.REQUIRED)
    private Utilizador utilizador;

    @Column(nullable = false)
    @Schema(description = "Number of tables required", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int quantidadeMesas;

    @Column(nullable = false)
    @Schema(description = "Status number | 0 - PENDING | 1 - PREPARING | 2 - COMPLETED | 3 - CANCELLED", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int status;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "Booking's date", example = "2024-05-28", requiredMode = RequiredMode.REQUIRED)
    private LocalDate dia;

    @Column(nullable = false)
    @Schema(description = "Booking's hour", example = "10:00", requiredMode = RequiredMode.REQUIRED)
    @JsonFormat(pattern="HH:mm")
    private LocalTime hora;
}
