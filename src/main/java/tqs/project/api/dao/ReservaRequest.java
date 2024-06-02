package tqs.project.api.dao;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {
    private LocalDate dia;
    private LocalTime hora;
    private int quantidadeMesas;
}
