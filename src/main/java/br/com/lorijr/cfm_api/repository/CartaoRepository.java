package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.CartaoDeCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoDeCredito, Long> {

    List<CartaoDeCredito> findByTitularId(Long pessoaId);

    @Query("SELECT c FROM CartaoDeCredito c WHERE c.titular.familia.id = :familiaId")
    List<CartaoDeCredito> buscarPorFamilia(Long familiaId);
}
