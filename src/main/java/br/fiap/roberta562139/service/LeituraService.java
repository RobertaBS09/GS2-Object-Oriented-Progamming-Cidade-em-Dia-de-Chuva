package br.fiap.roberta562139.service;

import br.fiap.roberta562139.domain.Leitura;
import br.fiap.roberta562139.domain.Trecho;
import br.fiap.roberta562139.dto.LeituraDTO;
import br.fiap.roberta562139.mapper.LeituraMapper;
import br.fiap.roberta562139.repository.LeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeituraService {

    private static final int LIMIAR_ALERTA = 14;

    private final LeituraRepository leituraRepository;
    private final LeituraMapper leituraMapper;
    private final TrechoService trechoService;

    public List<LeituraDTO> listar() {
        return leituraRepository.findAllByOrderByDataHoraDesc()
                .stream()
                .map(leituraMapper::toDTO)
                .toList();
    }

    public LeituraDTO buscarPorId(Long id) {
        return leituraMapper.toDTO(buscarEntidade(id));
    }

    public Leitura buscarEntidade(Long id) {
        return leituraRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Leitura não encontrada."));
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Trecho trecho = trechoService.buscarEntidade(dto.getTrechoId());
        String status = calcularStatus(dto.getLaminaCm());   // R1
        Leitura leitura = leituraMapper.toEntity(dto, trecho, status);
        return leituraMapper.toDTO(leituraRepository.save(leitura));
    }

    @Transactional
    public void excluir(Long id) {
        Leitura leitura = buscarEntidade(id);
        leituraRepository.delete(leitura);
    }

    public String calcularStatus(int laminaCm) {
        if (laminaCm < LIMIAR_ALERTA / 2) {
            return "NORMAL";
        } else if (laminaCm < LIMIAR_ALERTA) {
            return "ATENCAO";
        } else {
            return "ALERTA";
        }
    }
}