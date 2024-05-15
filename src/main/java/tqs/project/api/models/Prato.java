package tqs.project.api.models;

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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Prato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = true)
    private Double hidratosCarbono;

    @Column(nullable = true)
    private Double proteina;

    @Column(nullable = true)
    private int kcal;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "menu")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    @Column(nullable = true)
    private String imagemUrl;
}