package com.raissafrota.projetoSpringBoot.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.dto.ClienteDTO;
import com.raissafrota.projetoSpringBoot.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	ClienteService service;

	@RequestMapping(method = RequestMethod.GET, value = "/boas-vindas")
	public String informarQueEstouNaClasseCliente() {
		return "Olá! Acessei o endpoint corretamente e estou na classe ClienteResource! :D";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id) {

		Cliente cliente = service.find(id);

		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscarTodasClientes() {

		List<Cliente> clientes = service.findAll();

		List<ClienteDTO> clientesDTO = clientes.stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(clientesDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/paginas")
	public ResponseEntity<Page<ClienteDTO>> buscarClientesPorPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "campoOrdenacao", defaultValue = "nome") String campoOrdenacao,
			@RequestParam(value = "tipoOrdenacao", defaultValue = "ASC") String tipoOrdenacao) {

		Page<Cliente> lista = service.findPage(pagina, linhasPorPagina, campoOrdenacao, tipoOrdenacao);

		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listaDTO);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/newUpdate/{id}")
	public ResponseEntity<Cliente> updateNewVersion(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		
		Cliente cliente = service.find(id);
		BeanUtils.copyProperties(objDTO, cliente);
		cliente.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(service.update(cliente));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
