package com.raissafrota.projetoSpringBoot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.raissafrota.projetoSpringBoot.domain.ItemPedido;
import com.raissafrota.projetoSpringBoot.domain.Pagamento;
import com.raissafrota.projetoSpringBoot.domain.PagamentoComBoleto;
import com.raissafrota.projetoSpringBoot.domain.PagamentoComCartao;
import com.raissafrota.projetoSpringBoot.domain.Pedido;
import com.raissafrota.projetoSpringBoot.domain.Produto;
import com.raissafrota.projetoSpringBoot.domain.enums.EstadoPagamento;
import com.raissafrota.projetoSpringBoot.domain.enums.TipoCliente;
import com.raissafrota.projetoSpringBoot.repositories.CategoriaRepository;
import com.raissafrota.projetoSpringBoot.repositories.CidadeRepository;
import com.raissafrota.projetoSpringBoot.repositories.ClienteRepository;
import com.raissafrota.projetoSpringBoot.repositories.EnderecoRepository;
import com.raissafrota.projetoSpringBoot.repositories.EstadoRepository;
import com.raissafrota.projetoSpringBoot.repositories.ItemPedidoRepository;
import com.raissafrota.projetoSpringBoot.repositories.PagamentoRepository;
import com.raissafrota.projetoSpringBoot.repositories.PedidoRepository;
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

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		criarObjetosBancoDeDados();
	}

	private void criarObjetosBancoDeDados() {
		criarCategoriasEProdutos();
	}

	private void criarClienteEEnderecos(Cidade cid1, Cidade cid2, Produto p1, Produto p2, Produto p3) {
		Cliente cli1 = new Cliente(null, "Raissa", "raissa@gmail.com", "12343287616", TipoCliente.PESSOA_FISICA);

		cli1.getTelefones().addAll(Arrays.asList("34567786", "998734521"));

		Endereco end1 = new Endereco(null, "Rua Monsenhor Bruno", "300", "Apto 203", "Aldeota", "60123543", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida Epitácio Pessoa", "500", "Sala 4", "Centro", "60734123", cli1,
				cid2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		criarPedidosEPagamentos(cli1, end1, end2, p1, p2, p3);
	}

	private void criarPedidosEPagamentos(Cliente cli1, Endereco end1, Endereco end2, Produto p1, Produto p2,
			Produto p3) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
			Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1, null);
			Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2, null);

			Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
			ped1.setPagamento(pagto1);

			Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
					sdf.parse("20/10/2017 00:00"), null);
			ped2.setPagamento(pagto2);

			cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

			pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
			pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

			ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
			ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
			ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

			ped1.getItens().addAll(Arrays.asList(ip1, ip2));
			ped2.getItens().addAll(Arrays.asList(ip3));

			p1.getItens().addAll(Arrays.asList(ip1));
			p2.getItens().addAll(Arrays.asList(ip3));
			p3.getItens().addAll(Arrays.asList(ip2));

			itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void criarEstadosECidades(Produto p1, Produto p2, Produto p3) {
		Estado est1 = new Estado(null, "Ceará");
		Estado est2 = new Estado(null, "Paraíba");

		Cidade cid1 = new Cidade(null, "Fortaleza", est1);
		Cidade cid2 = new Cidade(null, "João Pessoa", est2);
		Cidade cid3 = new Cidade(null, "Campina Grande", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		this.estadoRepository.saveAll(Arrays.asList(est1, est2));
		this.cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

		criarClienteEEnderecos(cid1, cid2, p1, p2, p3);
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

		criarEstadosECidades(p1, p2, p3);
	}

}
