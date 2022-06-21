package com.raissafrota.projetoSpringBoot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	ClienteService service;

	@RequestMapping(method = RequestMethod.GET, value = "/boas-vindas" )
	public String informarQueEstouNaClasseCliente() {
		return "Olá! Acessei o endpoint corretamente e estou na classe ClienteResource! :D";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> buscarClientePorId(@PathVariable Integer id){
		
		Cliente cliente = service.buscar(id);
		
		return ResponseEntity.ok().body(cliente);
	}
}
