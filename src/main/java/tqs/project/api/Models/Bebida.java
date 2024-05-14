package tqs.project.api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
public class Bebida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = true)
    private Double hidratos_carbono;

    @Column(nullable = true)
    private Double proteina;

    @Column(nullable = true)
    private int kcal;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;
}
