package com.raissafrota.projetoSpringBoot.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method = RequestMethod.GET)
	public String informarQueEstouNaClasseCategoria() {
		return "Olá! Acessei o endpoint corretamente e estou na classe CategoriaResource! :D";
	}
}
