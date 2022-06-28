package com.raissafrota.projetoSpringBoot.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.raissafrota.projetoSpringBoot.domain.Categoria;
import com.raissafrota.projetoSpringBoot.dto.CategoriaDTO;
import com.raissafrota.projetoSpringBoot.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired // Instancia automaticamente
	CategoriaService service;

	@RequestMapping(method = RequestMethod.GET, value = "/boas-vindas")
	public String informarQueEstouNaClasseCategoria() {
		return "Olá! Acessei o endpoint corretamente e estou na classe CategoriaResource! :D";
	}

	// @PathVariable é para receber o parâmetro da URL
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Integer id) {

		Categoria categoria = service.find(id);

		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodasCategorias() {

		List<Categoria> categorias = service.findAll();

		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(categoriasDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/paginas")
	public ResponseEntity<Page<CategoriaDTO>> buscarCategoriasPorPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "campoOrdenacao", defaultValue = "nome") String campoOrdenacao,
			@RequestParam(value = "tipoOrdenacao", defaultValue = "ASC") String tipoOrdenacao) {

		Page<Categoria> lista = service.findPage(pagina, linhasPorPagina, campoOrdenacao, tipoOrdenacao);

		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));

		return ResponseEntity.ok().body(listaDTO);
	}

	// @RequestBody é para receber o objeto JSON que eu vou criar
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
