package com.passagensAereas.axelnovais.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.passagensAereas.axelnovais.model.entity.Voo;
import com.passagensAereas.axelnovais.model.enums.StatusVoo;
import com.passagensAereas.axelnovais.model.repositories.VooRepository;
import com.passagensAereas.axelnovais.service.VooService;


@Service
public class VooServiceImpl implements VooService{
	
	private VooRepository repository;
	
	public VooServiceImpl(VooRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Voo comprar(Voo voo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Voo atualizar(Voo voo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Voo voo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Voo> buscar(Voo vooFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarVoo(Voo voo, StatusVoo status) {
		// TODO Auto-generated method stub
		
	}

}
