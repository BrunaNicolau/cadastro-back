package br.com.cadastro.controller;

import br.com.cadastro.exception.BusinessException;
import br.com.cadastro.exception.RecordNotFoundException;
import br.com.cadastro.model.Pessoa;
import br.com.cadastro.service.PessoaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/todasPessoas")
    public ResponseEntity<List<Pessoa>> buscarTodos() {
        return ResponseEntity.ok(this.pessoaService.findAll());
    }
    
    @GetMapping("/pessoa/{cpf}")
    public ResponseEntity<?> buscarPessoa(@PathVariable String cpf) {
    	try {
    		return ResponseEntity.ok(this.pessoaService.findUser(cpf));
    	}catch (RecordNotFoundException e) {
    		return ResponseEntity.noContent().build();
    	}
    	
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarPessoa(@RequestBody Pessoa registerPessoa) {	
        try {
            Pessoa savedPessoa = pessoaService.registerUser(registerPessoa);
            return ResponseEntity.ok(savedPessoa);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    	
    }

}
