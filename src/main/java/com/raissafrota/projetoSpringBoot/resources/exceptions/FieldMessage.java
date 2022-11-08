package com.raissafrota.projetoSpringBoot.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeDoCampo;

	private String mensagem;

	public FieldMessage() {

	}

	public FieldMessage(String nomeDoCampo, String mensagem) {
		this.nomeDoCampo = nomeDoCampo;
		this.mensagem = mensagem;
	}

	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

	public void setNomeDoCampo(String nomeDoCampo) {
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
