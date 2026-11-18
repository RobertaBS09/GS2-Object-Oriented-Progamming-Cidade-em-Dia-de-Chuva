package br.fiap.roberta562139.repository;

import br.fiap.roberta562139.domain.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BairroRepository extends JpaRepository<Bairro, Long> {

    List<Bairro> findAllByOrderByNomeAsc();
}