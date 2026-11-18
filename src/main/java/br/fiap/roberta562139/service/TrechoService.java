package br.fiap.roberta562139.service;

import br.fiap.roberta562139.domain.Bairro;
import br.fiap.roberta562139.domain.Trecho;
import br.fiap.roberta562139.dto.TrechoDTO;
import br.fiap.roberta562139.mapper.TrechoMapper;
import br.fiap.roberta562139.repository.LeituraRepository;
import br.fiap.roberta562139.repository.TrechoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrechoService {

    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;
    private final TrechoMapper trechoMapper;
    private final BairroService bairroService;


    public List<TrechoDTO> listar() {
        return trechoRepository.findAllByOrderByAltitudeAscLogradouroAsc()
                .stream()
                .map(trechoMapper::toDTO)
                .toList();
    }

    public TrechoDTO buscarPorId(Long id) {
        return trechoMapper.toDTO(buscarEntidade(id));
    }

    public Trecho buscarEntidade(Long id) {
        return trechoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Trecho não encontrado."));
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Bairro bairro = bairroService.buscarEntidade(dto.getBairroId());
        Trecho trecho = trechoMapper.toEntity(dto, bairro);
        return trechoMapper.toDTO(trechoRepository.save(trecho));
    }


    @Transactional
    public void excluir(Long id) {
        Trecho trecho = buscarEntidade(id);

        if (leituraRepository.existsByTrechoId(id)) {
            leituraRepository.deleteByTrechoId(id);
        }
        trechoRepository.delete(trecho);
    }
}