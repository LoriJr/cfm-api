package br.com.lorijr.cfm_api.repository;

import br.com.lorijr.cfm_api.domain.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Long> {
}
