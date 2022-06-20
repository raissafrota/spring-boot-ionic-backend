package com.raissafrota.projetoSpringBoot.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.raissafrota.projetoSpringBoot.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	/*
	 * Classe para interceptar as exceções e mostrar a mensagem bonitinha para o
	 * usuário.
	 */

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjectNotFoundException excecao,
			HttpServletRequest request) {
		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), excecao.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
}
