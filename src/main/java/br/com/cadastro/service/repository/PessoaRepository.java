package br.com.cadastro.service.repository;

import br.com.cadastro.model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
	@Query("SELECT x FROM Pessoa x WHERE x.cpf = :userCpf")
	Pessoa findUserByCpf(final String userCpf);
	
	@Query("SELECT x FROM Pessoa x WHERE x.email = :userEmail")
	Pessoa findUserByEmail(final String userEmail);
	
}
