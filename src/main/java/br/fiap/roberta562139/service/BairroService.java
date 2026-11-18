package br.fiap.roberta562139.service;

import br.fiap.roberta562139.domain.Bairro;
import br.fiap.roberta562139.dto.BairroDTO;
import br.fiap.roberta562139.mapper.BairroMapper;
import br.fiap.roberta562139.repository.BairroRepository;
import br.fiap.roberta562139.repository.TrechoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;
    private final TrechoRepository trechoRepository;
    private final BairroMapper bairroMapper;

    public List<BairroDTO> listar() {
        return bairroRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(bairroMapper::toDTO)
                .toList();
    }

    public BairroDTO buscarPorId(Long id) {
        return bairroMapper.toDTO(buscarEntidade(id));
    }

    public Bairro buscarEntidade(Long id) {
        return bairroRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Bairro não encontrado."));
    }

    @Transactional
    public BairroDTO salvar(BairroDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Bairro bairro = bairroMapper.toEntity(dto);
        return bairroMapper.toDTO(bairroRepository.save(bairro));
    }

    @Transactional
    public void excluir(Long id) {
        Bairro bairro = buscarEntidade(id);


        if (trechoRepository.existsByBairroId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir o bairro " + bairro.getNome()
                            + ": ele possui trechos cadastrados.");
        }
        bairroRepository.delete(bairro);
    }
}