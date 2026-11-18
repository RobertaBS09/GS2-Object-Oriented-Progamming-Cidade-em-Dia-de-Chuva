package br.fiap.roberta562139.mapper;

import br.fiap.roberta562139.domain.Leitura;
import br.fiap.roberta562139.domain.Trecho;
import br.fiap.roberta562139.dto.LeituraDTO;
import org.springframework.stereotype.Component;

@Component
public class LeituraMapper {

    public Leitura toEntity(LeituraDTO dto, Trecho trecho, String status) {
        Leitura leitura = new Leitura();
        leitura.setId(dto.getId());
        leitura.setDataHora(dto.getDataHora());
        leitura.setLaminaCm(dto.getLaminaCm());
        leitura.setStatus(status);
        leitura.setTrecho(trecho);
        return leitura;
    }

    public LeituraDTO toDTO(Leitura leitura) {
        Trecho trecho = leitura.getTrecho();
        String descricao = trecho.getLogradouro() + " (" + trecho.getBairro().getNome() + ")";

        return new LeituraDTO(
                leitura.getId(),
                trecho.getId(),
                descricao,
                leitura.getDataHora(),
                leitura.getLaminaCm(),
                leitura.getStatus()
        );
    }
}