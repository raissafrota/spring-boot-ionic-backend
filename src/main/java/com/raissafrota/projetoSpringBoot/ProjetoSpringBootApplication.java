package com.raissafrota.projetoSpringBoot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raissafrota.projetoSpringBoot.domain.Categoria;
import com.raissafrota.projetoSpringBoot.repositories.CategoriaRepository;

@SpringBootApplication
public class ProjetoSpringBootApplication implements CommandLineRunner{

	@Autowired
	CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); 
	}

}
