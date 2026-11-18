package br.fiap.roberta562139.mapper;

import br.fiap.roberta562139.domain.Bairro;
import br.fiap.roberta562139.dto.BairroDTO;
import org.springframework.stereotype.Component;

@Component
public class BairroMapper {

    public Bairro toEntity(BairroDTO dto) {
        Bairro bairro = new Bairro();
        bairro.setId(dto.getId());
        bairro.setNome(dto.getNome().trim());
        bairro.setZona(dto.getZona());
        bairro.setPossuiPiscinao(Boolean.TRUE.equals(dto.getPossuiPiscinao()));
        return bairro;
    }

    public BairroDTO toDTO(Bairro bairro) {
        return new BairroDTO(
                bairro.getId(),
                bairro.getNome(),
                bairro.getZona(),
                bairro.getPossuiPiscinao()
        );
    }
}