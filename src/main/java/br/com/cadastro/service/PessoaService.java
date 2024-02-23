package br.com.cadastro.service;

import br.com.cadastro.exception.BusinessException;
import br.com.cadastro.exception.RecordNotFoundException;
import br.com.cadastro.model.Pessoa;
import br.com.cadastro.service.repository.PessoaRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
    
    public Pessoa findUser(String userCpf) {
    	
        Pessoa pessoa = this.pessoaRepository.findUserByCpf(userCpf);
        if (pessoa != null) {
            return pessoa;
        } else {
            throw new RecordNotFoundException();
        }
    	    
    }
    
    public Pessoa registerUser(Pessoa registerPessoa) {
  	    	               
            String cpf = registerPessoa.getCpf();
            Pessoa existingPessoaCpf = pessoaRepository.findUserByCpf(cpf);
            if (existingPessoaCpf != null) {
                throw new BusinessException("Pessoa com CPF " + cpf + " já existe");
            }
            
            String email = registerPessoa.getEmail();
            Pessoa existingPessoaEmail = pessoaRepository.findUserByEmail(email);
            if (existingPessoaEmail != null) {
                throw new BusinessException("Pessoa com email " + email + " já existe");
            }
            
            return this.pessoaRepository.save(registerPessoa);
    }

}
