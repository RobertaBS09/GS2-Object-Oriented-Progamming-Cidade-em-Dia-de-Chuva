package br.fiap.roberta562139.repository;

import br.fiap.roberta562139.domain.Trecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrechoRepository extends JpaRepository<Trecho, Long> {

    List<Trecho> findAllByOrderByAltitudeAscLogradouroAsc();

    List<Trecho> findByBairroIdOrderByLogradouroAsc(Long bairroId);

    boolean existsByBairroId(Long bairroId);
}