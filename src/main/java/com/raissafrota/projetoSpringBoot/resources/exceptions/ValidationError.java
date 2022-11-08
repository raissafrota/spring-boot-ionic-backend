package com.raissafrota.projetoSpringBoot.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String nomeDoCampo, String mensagem) {
		this.errors.add(new FieldMessage(nomeDoCampo, mensagem));
	}

	public ValidationError(Integer status, String mensagem, Long hora) {
		super(status, mensagem, hora);
	}

}
