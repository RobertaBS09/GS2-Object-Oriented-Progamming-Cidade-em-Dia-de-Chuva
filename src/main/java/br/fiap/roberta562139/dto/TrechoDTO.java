package br.fiap.roberta562139.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrechoDTO {

    private Long id;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres.")
    private String logradouro;

    @NotBlank(message = "Selecione o tipo de calçada.")
    private String tipoCalcada;

    @NotNull(message = "A altitude é obrigatória.")
    @Min(value = 0, message = "A altitude deve estar entre 0 e 2000 m.")
    @Max(value = 2000, message = "A altitude deve estar entre 0 e 2000 m.")
    private Integer altitude;

    @NotNull(message = "Selecione o bairro.")
    private Long bairroId;

    private String bairroNome;
}