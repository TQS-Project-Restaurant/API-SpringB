package tqs.project.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
    @Schema(description = "Dish's ID", example = "1", requiredMode = RequiredMode.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Dish's name", example = "Francesinha", requiredMode = RequiredMode.REQUIRED)
    private String nome;

    @Column(nullable = false)
    @Schema(description = "Dish's price", example = "7.55", requiredMode = RequiredMode.REQUIRED)
    private Double preco;

    @Column(nullable = true)
    @Schema(description = "Dish's carbs", example = "30.2", requiredMode = RequiredMode.NOT_REQUIRED)
    private Double hidratosCarbono;

    @Column(nullable = true)
    @Schema(description = "Dish's protein", example = "23.19", requiredMode = RequiredMode.NOT_REQUIRED)
    private Double proteina;

    @Column(nullable = true)
    @Schema(description = "Dish's calories", example = "716", requiredMode = RequiredMode.NOT_REQUIRED)
    private int kcal;

    @Column(nullable = false)
    @Schema(description = "Current dish's stock", example = "10", requiredMode = RequiredMode.REQUIRED)
    private int stock;

    @ManyToMany(mappedBy = "pratos")
    @JsonIgnore
    private List<Menu> menus;

    @ManyToMany(mappedBy = "pratos")
    @JsonIgnore
    private List<Pedido> pedidos;

    @Column(nullable = true)
    @Schema(description = "Dish's image URL", example = "/images/food/francesinha.png", requiredMode = RequiredMode.NOT_REQUIRED)
    private String imagemUrl;
}
