package br.ufrn.imd.model;

import java.io.File;

public class Diretorio {
	private String nome;
	private File diretorio;
	
	public Diretorio(String nome) {
		this.nome = nome;
		diretorio = new File(nome);
	}
	
	public Diretorio() {
		nome = "output";
		diretorio = new File(nome);
	}
	
	public String getNome() {
		return nome;
	}
	
	
	public boolean ehValido() {	
		if(!diretorio.exists()) {
			if(diretorio.mkdir()) {
				// Cria o diretório 
				return true;
			}
			else {
				// Falha ao criar o diretório 
				return false;
			}
		}
		else {
			// O diretório já existe
			return true;
		}
		
	}
}
