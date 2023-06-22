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
				System.out.println("Diretório criado com sucesso!");
				return true;
			}
			else {
				System.out.println("Falha ao criar diretóri!o");
				return false;
			}
		}
		else {
			System.out.println("Diretório já existe!!!");
			return true;
		}
		
	}
}
