package tqs.project.api.models;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Menu's ID", example = "1", required = true)
    private Long id;

    @ManyToMany
    @Schema(description = "List of dishes in the menu")
    private List<Prato> pratos;

    @ManyToMany
    @Schema(description = "List of drinks in the menu")
    private List<Bebida> bebidas;

    @Column(nullable = false, unique = true)
    @Schema(description = "Menu's date", example = "2024-05-28", required = true)
    private LocalDate dia;
}
