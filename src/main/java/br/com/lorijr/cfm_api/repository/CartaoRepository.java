package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.CartaoDeCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoRepository extends JpaRepository<CartaoDeCredito, Long> {

    List<CartaoDeCredito> findByTitularId(Long pessoaId);
}
