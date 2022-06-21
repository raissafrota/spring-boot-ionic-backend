package com.raissafrota.projetoSpringBoot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raissafrota.projetoSpringBoot.domain.Categoria;
import com.raissafrota.projetoSpringBoot.domain.Cidade;
import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.domain.Endereco;
import com.raissafrota.projetoSpringBoot.domain.Estado;
import com.raissafrota.projetoSpringBoot.domain.Produto;
import com.raissafrota.projetoSpringBoot.domain.enums.TipoCliente;
import com.raissafrota.projetoSpringBoot.repositories.CategoriaRepository;
import com.raissafrota.projetoSpringBoot.repositories.CidadeRepository;
import com.raissafrota.projetoSpringBoot.repositories.ClienteRepository;
import com.raissafrota.projetoSpringBoot.repositories.EnderecoRepository;
import com.raissafrota.projetoSpringBoot.repositories.EstadoRepository;
import com.raissafrota.projetoSpringBoot.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetoSpringBootApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		criarObjetosBancoDeDados();
	}

	private void criarObjetosBancoDeDados() {
		criarCategoriasEProdutos();
		criarEstadosECidades();
	}

	private void criarClienteEEnderecos(Cidade cid1, Cidade cid2) {
		Cliente cli1 = new Cliente(null, "Raissa", "raissa@gmail.com", "12343287616", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("34567786", "998734521"));
		
		Endereco end1 = new Endereco(null, "Rua Monsenhor Bruno", "300", "Apto 203", "Aldeota", "60123543", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida Epitácio Pessoa", "500", "Sala 4", "Centro", "60734123", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
	}

	private void criarEstadosECidades() {
		Estado est1 = new Estado(null, "Ceará");
		Estado est2 = new Estado(null, "Paraíba");
		
		Cidade cid1 = new Cidade(null, "Fortaleza", est1);
		Cidade cid2 = new Cidade(null, "João Pessoa", est2);
		Cidade cid3 = new Cidade(null, "Campina Grande", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		this.estadoRepository.saveAll(Arrays.asList(est1, est2));
		this.cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		criarClienteEEnderecos(cid1, cid2);
	}

	private void criarCategoriasEProdutos() {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		this.produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}

}
