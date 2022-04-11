package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		usuarioRepository.save(new Usuario (0L,"Akiramenai Pedorosan","Akiramenai@pedorosan.com","akiramenai!","https://pin.it/6gbQtfj"));
		usuarioRepository.save(new Usuario (0L,"shi","4444@pedorosan.com","shi444","https://pin.it/4ZKSMyt"));
		usuarioRepository.save(new Usuario (0L,"Akiramenai Vichan","Akiramenai@vichan.com","12345678","https://pin.it/6gbQtfj"));
	}
	@Test
	@DisplayName("Retorna apenas um usuário")
	public void deveRetornaUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("Akiramenai@pedorosan.com");
		assertTrue(usuario.get().getUsuario().equals("Akiramenai@pedorosan.com"));
	}
	
	@Test
	@DisplayName("Retorna dois usuários")
	public void deveRetornarDoisUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Akira");
		assertEquals(2, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Akiramenai Pedorosan"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Akiramenai Vichan"));
	}
	
}

