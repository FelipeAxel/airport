package com.passagensAereas.axelnovais.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.assertj.core.api.Assertions;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


import com.passagensAereas.axelnovais.exceptions.ErroAutenticacao;
import com.passagensAereas.axelnovais.exceptions.RegraNegocioException;
import com.passagensAereas.axelnovais.model.entity.Usuario;
import com.passagensAereas.axelnovais.model.repositories.UsuarioRepository;
import com.passagensAereas.axelnovais.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Test
	public void deveSalvarUmUsuario() {
		
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
					.id(1l)
					.nome("nome")
					.email("email@email.com")
					.senha("senha").build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	
	@Test
	public void naoDeveSalvarUsuarioComEmailCadastrado() {
		
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario) ) ;
		
		
		Mockito.verify( repository, Mockito.never() ).save(usuario);
	}
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when( repository.findByEmail(email) ).thenReturn(Optional.of(usuario));
		
	
		Usuario result = service.autenticar(email, senha);
		
		
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test
	public void deveLancarErroQUandoNaoEncontrarUsuarioComOEmailDigitado() {
		
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123") );
		
			
		Assertions.assertThat(exception)
			.isInstanceOf(ErroAutenticacao.class)
			.hasMessage("Usuario não localizado com o email digitado");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaForErrada() {
		
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		
		Throwable exception = Assertions.catchThrowable( () ->  service.autenticar("email@email.com", "123") );
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha incorreta.");
		
	}
	
	@Test
	public void deveValidarEmail() {
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		
		service.validarEmail("email@email.com");
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@email.com"));
	}
}
