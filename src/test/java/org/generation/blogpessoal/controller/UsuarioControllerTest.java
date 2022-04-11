package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private UsuarioService usuarioService;
	
	//somente no teste de controlller, pois usamos o padrão rest que utiliza dos verbos e metodos HTTP -> GET-POST-PUT-DELETE
	@Autowired
	private TestRestTemplate testRestTemplate;  
	
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuário")
	public void deveCadastrarUmUsuario() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,"Jose Maria","jose@maria.com","zelove","https://my.vioo.world/wp-content/uploads/2021/08/07163545/Excel-macros-e-VBA-Foco-em-trabalho_feature_image"));
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
	
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Alterar um Usuário")
	public void deveAlterarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService
				.cadastrarUsuario(new Usuario(0L, "claudia", "claudia@teste.com.br", "12345678", ""));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Claudia Tenshi","claudiatenshi@teste", "12345678", "https://i.imgur.com/LjpWZVF.png");

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
		assertEquals(usuarioUpdate.getFoto(), resposta.getBody().getFoto());		
		

	}

}