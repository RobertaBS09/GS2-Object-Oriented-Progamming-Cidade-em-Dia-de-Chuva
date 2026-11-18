package br.fiap.roberta562139.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PainelItemDTO {

    private String logradouro;
    private String tipoCalcada;
    private Integer altitude;
    private Integer ultimaLamina;
    private String ultimoStatus;
}