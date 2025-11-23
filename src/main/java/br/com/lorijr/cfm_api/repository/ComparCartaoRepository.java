package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.CompraCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ComparCartaoRepository extends JpaRepository<CompraCartao, Long> {

    List<CompraCartao> findByCartaoIdAndDataCompraBetween(Long cartaoId, LocalDate inicio, LocalDate fim);
}
