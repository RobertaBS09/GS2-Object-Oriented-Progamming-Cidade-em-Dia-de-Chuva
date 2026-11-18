package br.fiap.roberta562139.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GS918_TRECHO")
@Getter
@Setter
@NoArgsConstructor
public class Trecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String logradouro;

    @Column(name = "tipo_calcada", nullable = false, length = 20)
    private String tipoCalcada;

    @Column(nullable = false)
    private Integer altitude;

    @ManyToOne
    @JoinColumn(name = "bairro_id", nullable = false)
    private Bairro bairro;
}