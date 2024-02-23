import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.cadastro.controller.PessoaController;
import br.com.cadastro.exception.BusinessException;
import br.com.cadastro.exception.RecordNotFoundException;
import br.com.cadastro.model.Pessoa;
import br.com.cadastro.service.PessoaService;
import junit.framework.TestCase;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = { PessoaController.class })
class PessoaControllerTest extends TestCase{
	
    private final static String API = "/api/todasPessoas";
    private final static String API_CPF = "/api/pessoa/{cpf}";
    private final static String API_Register = "/api/cadastro";
    
    @Autowired
    private PessoaController pessoaController;

    @MockBean
    private PessoaService pessoaService;
    
    @BeforeEach
	protected
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    void testBuscarTodos() {
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaService.findAll()).thenReturn(pessoas);
        ResponseEntity<List<Pessoa>> response = pessoaController.buscarTodos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoas, response.getBody());
    }
    
    @Test
    void testBuscarPessoa_Success() throws RecordNotFoundException {
        String cpf = "12345678900";
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        when(pessoaService.findUser(cpf)).thenReturn(pessoa);
        ResponseEntity<?> response = pessoaController.buscarPessoa(cpf);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoa, response.getBody());
    }

    @Test
    void testBuscarPessoa_RecordNotFoundException() throws RecordNotFoundException {
        String cpf = "12345678900";
        doThrow(new RecordNotFoundException()).when(pessoaService).findUser(cpf);
        ResponseEntity<?> response = pessoaController.buscarPessoa(cpf);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void testCadastrarPessoa_Success() {
        Pessoa registerPessoa = new Pessoa();
        registerPessoa.setNome("John Doe");
        registerPessoa.setEmail("john@example.com");
        Pessoa savedPessoa = new Pessoa();
        savedPessoa.setId(1);
        savedPessoa.setNome("John Doe");
        savedPessoa.setEmail("john@example.com");
        when(pessoaService.registerUser(registerPessoa)).thenReturn(savedPessoa);
        ResponseEntity<?> responseEntity = pessoaController.cadastrarPessoa(registerPessoa);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof Pessoa);
        Pessoa responsePessoa = (Pessoa) responseEntity.getBody();
        assertEquals(savedPessoa.getId(), responsePessoa.getId());
        assertEquals(savedPessoa.getNome(), responsePessoa.getNome());
        assertEquals(savedPessoa.getEmail(), responsePessoa.getEmail());
    }

    @Test
    void testCadastrarPessoa_BusinessException() {
        Pessoa registerPessoa = new Pessoa();
        registerPessoa.setNome("John Doe");
        registerPessoa.setEmail("john@example.com");
        String errorMessage = "Error message from BusinessException";
        when(pessoaService.registerUser(registerPessoa)).thenThrow(new BusinessException(errorMessage));
        ResponseEntity<?> responseEntity = pessoaController.cadastrarPessoa(registerPessoa);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof String);
        String responseBody = (String) responseEntity.getBody();
        assertEquals(errorMessage, responseBody);
    }
	
}
