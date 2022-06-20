package com.raissafrota.projetoSpringBoot.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String mensagem) {
		super(mensagem);
	}

	public ObjectNotFoundException(String mensagem, Throwable causaExcecao) {
		super(mensagem, causaExcecao);
	}
}
