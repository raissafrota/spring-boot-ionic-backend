package com.raissafrota.projetoSpringBoot.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.raissafrota.projetoSpringBoot.domain.Categoria;
import com.raissafrota.projetoSpringBoot.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired //Instancia automaticamente
	CategoriaService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/boas-vindas" )
	public String informarQueEstouNaClasseCategoria() {
		return "Olá! Acessei o endpoint corretamente e estou na classe CategoriaResource! :D";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Integer id){
		
		Categoria categoria = service.buscar(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
