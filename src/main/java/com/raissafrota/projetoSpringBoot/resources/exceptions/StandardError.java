package com.raissafrota.projetoSpringBoot.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String mensagem;
	private Long hora;

	public StandardError() {
		super();
	}

	public StandardError(Integer status, String mensagem, Long hora) {
		super();
		this.status = status;
		this.mensagem = mensagem;
		this.hora = hora;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getHora() {
		return hora;
	}

	public void setHora(Long hora) {
		this.hora = hora;
	}

}
