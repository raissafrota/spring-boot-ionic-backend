package com.raissafrota.projetoSpringBoot.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.dto.ClienteDTO;
import com.raissafrota.projetoSpringBoot.repositories.ClienteRepository;
import com.raissafrota.projetoSpringBoot.services.exceptions.DataIntegrityException;
import com.raissafrota.projetoSpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}
	
	public Cliente update(Cliente obj) {
		Cliente c = find(obj.getId());
		atualizandoInformacoes(c, obj);
		return repo.save(c);
	}

	private void atualizandoInformacoes(Cliente c, Cliente obj) {
		c.setNome(obj.getNome());
		c.setEmail(obj.getEmail());
	}
	
	@Transactional
	public Cliente updateNewVersion(Cliente obj) {
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer pagina, Integer linhasPorPagina, String campoOrdenacao, String tipoOrdenacao){
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao),
				campoOrdenacao);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
}
