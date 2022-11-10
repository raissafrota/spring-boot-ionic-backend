package com.raissafrota.projetoSpringBoot.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.raissafrota.projetoSpringBoot.domain.Cliente;
import com.raissafrota.projetoSpringBoot.domain.enums.TipoCliente;
import com.raissafrota.projetoSpringBoot.dto.ClienteNewDTO;
import com.raissafrota.projetoSpringBoot.repositories.ClienteRepository;
import com.raissafrota.projetoSpringBoot.resources.exceptions.FieldMessage;
import com.raissafrota.projetoSpringBoot.services.validation.utils.ValidationTiposCpfCnpjUtils;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo() != null && objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo())
				&& !ValidationTiposCpfCnpjUtils.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido!"));
		}
		
		if(objDto.getTipo() != null && objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo())
				&& !ValidationTiposCpfCnpjUtils.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido!"));
		}

		Cliente c = clienteRepository.findByEmail(objDto.getEmail());
		
		if(c != null) {
			list.add(new FieldMessage("email", "Email já existente!"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeDoCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
