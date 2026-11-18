package br.fiap.roberta562139.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "GS918_LEITURA")
@Getter
@Setter
@NoArgsConstructor
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "lamina_cm", nullable = false)
    private Integer laminaCm;

    @Column(nullable = false, length = 10)
    private String status;

    @ManyToOne
    @JoinColumn(name = "trecho_id", nullable = false)
    private Trecho trecho;
}