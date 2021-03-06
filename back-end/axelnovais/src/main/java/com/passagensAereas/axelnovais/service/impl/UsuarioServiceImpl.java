package com.passagensAereas.axelnovais.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.passagensAereas.axelnovais.exceptions.ErroAutenticacao;
import com.passagensAereas.axelnovais.exceptions.RegraNegocioException;
import com.passagensAereas.axelnovais.model.entity.Usuario;
import com.passagensAereas.axelnovais.model.repositories.UsuarioRepository;
import com.passagensAereas.axelnovais.service.UsuarioService;



@Service
public class UsuarioServiceImpl implements UsuarioService{

	private UsuarioRepository repository;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuario não localizado com o email digitado");
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha incorreta.");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		
		if(existe) {
			throw new RegraNegocioException("Ja existe um usuario cadastrado com esse email");
		}
		
	}

}
