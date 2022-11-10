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

import com.raissafrota.projetoSpringBoot.domain.Cidade;
import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.domain.Endereco;
import com.raissafrota.projetoSpringBoot.domain.enums.TipoCliente;
import com.raissafrota.projetoSpringBoot.dto.ClienteDTO;
import com.raissafrota.projetoSpringBoot.dto.ClienteNewDTO;
import com.raissafrota.projetoSpringBoot.repositories.ClienteRepository;
import com.raissafrota.projetoSpringBoot.repositories.EnderecoRepository;
import com.raissafrota.projetoSpringBoot.services.exceptions.DataIntegrityException;
import com.raissafrota.projetoSpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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

	public Page<Cliente> findPage(Integer pagina, Integer linhasPorPagina, String campoOrdenacao,
			String tipoOrdenacao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao),
				campoOrdenacao);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.converterParaEnum(objDto.getTipo()));

		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);

		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);

		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
}
