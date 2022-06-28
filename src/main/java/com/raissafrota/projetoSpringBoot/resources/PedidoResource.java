package com.raissafrota.projetoSpringBoot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raissafrota.projetoSpringBoot.domain.Pedido;
import com.raissafrota.projetoSpringBoot.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired // Instancia automaticamente
	PedidoService service;

	@RequestMapping(method = RequestMethod.GET, value = "/boas-vindas")
	public String informarQueEstouNaClassePedido() {
		return "Olá! Acessei o endpoint corretamente e estou na classe PedidoResource! :D";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer id) {

		Pedido pedido = service.find(id);

		return ResponseEntity.ok().body(pedido);
	}

}
