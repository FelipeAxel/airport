package com.passagensAereas.axelnovais.service;

import java.util.List;

import com.passagensAereas.axelnovais.model.entity.Voo;
import com.passagensAereas.axelnovais.model.enums.StatusVoo;

public interface VooService {
	Voo comprar(Voo voo);
	Voo atualizar(Voo voo);
	void deletar (Voo voo);
	List<Voo> buscar(Voo vooFiltro);
	void atualizarVoo(Voo voo, StatusVoo status);
}
