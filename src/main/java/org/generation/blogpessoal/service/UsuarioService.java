package org.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario>cadastrarUsuario(Usuario usuario){
			
		if(usuarioRepository.findByUsuario(usuario.getEmail()).isPresent()){
			return Optional.empty();
		} 
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.of(usuarioRepository.save(usuario));
	}
	public  Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getEmail());
			if(usuario.isPresent()) {
				if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
					usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setFoto(usuario.get().getFoto());
					usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getSenha()));
					usuarioLogin.get().setEmail(usuario.get().getEmail());
					usuarioLogin.get().setSenha(usuario.get().getSenha());
			
					return usuarioLogin;
				}
			}
			return Optional.empty();
	}
	//-----------------------ATUALIZAR--------------------
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getEmail());

			if (buscaUsuario.isPresent()) {				
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Email já foi cadastrado", null);
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));
		} 
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email não foi encontrado", null);		
	}	
	
	
	private boolean compararSenhas(String senhaDigitada, String senhaDoServidor) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(senhaDigitada, senhaDoServidor);
			}
	//criptografarSenha = pode ser qualquer nome
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.encode(senha);
	}
	
	private String geradorBasicToken(String usuario, String senha) {
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
			return "Basic " + new String(tokenBase64);
 	}
	
	
}
