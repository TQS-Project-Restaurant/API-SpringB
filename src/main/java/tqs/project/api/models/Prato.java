package tqs.project.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Prato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Dish's ID", example = "1", required = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Dish's name", example = "Francesinha", required = true)
    private String nome;

    @Column(nullable = false)
    @Schema(description = "Dish's price", example = "7.55", required = true)
    private Double preco;

    @Column(nullable = true)
    @Schema(description = "Dish's carbs", example = "30.2", required = false)
    private Double hidratosCarbono;

    @Column(nullable = true)
    @Schema(description = "Dish's protein", example = "23.19", required = false)
    private Double proteina;

    @Column(nullable = true)
    @Schema(description = "Dish's calories", example = "716", required = false)
    private int kcal;

    @Column(nullable = false)
    @Schema(description = "Current dish's stock", example = "10", required = true)
    private int stock;

    @ManyToMany(mappedBy = "pratos")
    @JsonIgnore
    private List<Menu> menus;

    @ManyToMany(mappedBy = "pratos")
    @JsonIgnore
    private List<Pedido> pedidos;

    @Column(nullable = true)
    @Schema(description = "Dish's image URL", example = "/images/food/francesinha.png", required = false)
    private String imagemUrl;
}
