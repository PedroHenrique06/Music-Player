package br.ufrn.imd.model;

import java.io.File;

public class Musica {
private String titulo;
private int tamanhoSegundos;
private Boolean tocando;
private Boolean selecionado;
private String local;
private File arquivo;
public String getTitulo() {
	return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}
public int getTamanhoSegundos() {
	return tamanhoSegundos;
}
public void setTamanhoSegundos(int tamanhoSegundos) {
	this.tamanhoSegundos = tamanhoSegundos;
}
public Boolean getTocando() {
	return tocando;
}
public void setTocando(Boolean tocando) {
	this.tocando = tocando;
}
public Boolean getSelecionado() {
	return selecionado;
}
public void setSelecionado(Boolean selecionado) {
	this.selecionado = selecionado;
}
public String getLocal() {
	return local;
}
public void setLocal(String local) {
	this.local = local;
}
public File getArquivo() {
	return arquivo;
}
public void setArquivo(File arquivo) {
	this.arquivo = arquivo;
}

}
