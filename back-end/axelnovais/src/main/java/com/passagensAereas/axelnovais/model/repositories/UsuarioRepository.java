package com.passagensAereas.axelnovais.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.passagensAereas.axelnovais.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long >{
	
	boolean existsByEmail(String email);
	
	
}
