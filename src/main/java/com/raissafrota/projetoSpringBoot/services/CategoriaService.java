package com.raissafrota.projetoSpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.raissafrota.projetoSpringBoot.domain.Categoria;
import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.dto.CategoriaDTO;
import com.raissafrota.projetoSpringBoot.repositories.CategoriaRepository;
import com.raissafrota.projetoSpringBoot.services.exceptions.DataIntegrityException;
import com.raissafrota.projetoSpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria c = find(obj.getId());
		atualizandoInformacoes(c, obj);
		return repo.save(c);
	}

	private void atualizandoInformacoes(Categoria c, Categoria obj) {
		c.setNome(obj.getNome());
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer pagina, Integer linhasPorPagina, String campoOrdenacao,
			String tipoOrdenacao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao),
				campoOrdenacao);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
	}

}
