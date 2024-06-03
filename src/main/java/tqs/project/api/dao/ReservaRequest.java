package tqs.project.api.dao;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservaRequest {
    private LocalDate dia;
    private LocalTime hora;
    private int quantidadeMesas;
}
