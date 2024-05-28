package tqs.project.api.models;

import java.util.List;

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

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Requests' ID", example = "1", requiredMode = RequiredMode.AUTO)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Table number", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int mesa;

    @ManyToMany
    @Schema(description = "List of dishes in the request")
    private List<Prato> pratos;

    @ManyToMany
    @Schema(description = "List of drinks in the request")
    private List<Bebida> bebidas;

    @Column(nullable = false)
    @Schema(description = "Status number | 0 - PENDING | 1 - PREPARING | 2 - COMPLETED | 3 - CANCELLED", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int status;

    @Column(nullable = false)
    @Schema(description = "Time in ms when it was edited", example = "1716912178678", requiredMode = RequiredMode.REQUIRED)
    private Long lastModified;
}
