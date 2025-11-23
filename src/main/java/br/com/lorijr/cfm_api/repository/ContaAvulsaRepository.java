package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.ContaAvulsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContaAvulsaRepository extends JpaRepository<ContaAvulsa, Long>{

    @Query("SELECT c FROM ContaAvulsa c " +
            "WHERE c.familia.id = :familiaId " +
            "AND c.dataVencimento BETWEEN :inicio AND :fim")
    List<ContaAvulsa> buscarPorFatura(Long familiaId, LocalDate inicio, LocalDate fim);

    List<ContaAvulsa> findByFamiliaId(Long familiaId);

    @Query("SELECT c FROM ContaAvulsa c " +
            "WHERE c.familia.id = :familiaId " +
            "AND c.dataVencimento BETWEEN :inicio AND :fim")
    List<ContaAvulsa> buscarPorMes(Long familiaId, LocalDate inicio, LocalDate fim);
}
