package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.CompraCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraCartaoRepository extends JpaRepository<CompraCartao, Long> {

    List<CompraCartao> findByCartaoIdAndDataCompraBetween(Long compraCartaoId, LocalDate inicio, LocalDate fim);

    @Query("SELECT c FROM CompraCartao c " +
            "WHERE c.cartao.titular.familia.id = :familiaId " +
            "AND c.dataCompra BETWEEN :inicio AND :fim")
    List<CompraCartao> buscarComprasPorFamiliaEPeriodo(Long familiaId, LocalDate inicio, LocalDate fim);
}
