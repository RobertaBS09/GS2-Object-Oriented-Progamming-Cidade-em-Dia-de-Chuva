package br.fiap.roberta562139.service;

import br.fiap.roberta562139.domain.Leitura;
import br.fiap.roberta562139.domain.Trecho;
import br.fiap.roberta562139.dto.PainelItemDTO;
import br.fiap.roberta562139.repository.LeituraRepository;
import br.fiap.roberta562139.repository.TrechoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PainelService {

    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;
    private final BairroService bairroService;

    public List<PainelItemDTO> montarPainel(Long bairroId) {
        bairroService.buscarEntidade(bairroId);

        return trechoRepository.findByBairroIdOrderByLogradouroAsc(bairroId)
                .stream()
                .map(this::montarItem)
                .toList();
    }

    public long contarPorStatus(List<PainelItemDTO> itens, String status) {
        return itens.stream()
                .filter(item -> status.equals(item.getUltimoStatus()))
                .count();
    }

    private PainelItemDTO montarItem(Trecho trecho) {
        Optional<Leitura> ultima =
                leituraRepository.findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId());

        return new PainelItemDTO(
                trecho.getLogradouro(),
                trecho.getTipoCalcada(),
                trecho.getAltitude(),
                ultima.map(Leitura::getLaminaCm).orElse(null),
                ultima.map(Leitura::getStatus).orElse(null)
        );
    }
}