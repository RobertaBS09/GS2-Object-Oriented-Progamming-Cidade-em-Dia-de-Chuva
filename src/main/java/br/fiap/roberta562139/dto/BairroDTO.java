package br.fiap.roberta562139.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BairroDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres.")
    private String nome;

    @NotBlank(message = "Selecione a zona.")
    private String zona;

    private Boolean possuiPiscinao;
}