package com.vieceli.pontointeligente.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vieceli.pontointeligente.api.dtos.CadastroPFDto;
import com.vieceli.pontointeligente.api.entities.Empresa;
import com.vieceli.pontointeligente.api.entities.Funcionario;
import com.vieceli.pontointeligente.api.enums.PerfilEnum;
import com.vieceli.pontointeligente.api.services.EmpresaService;
import com.vieceli.pontointeligente.api.services.FuncionarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroPFControllerTest {
	
	private static final String URL_BASE = "/api/cadastrar-pf";
	private static final Long ID_FUNCIONARIO = (long) 8;
	private static final String NOME = "Teste Spring Boot";
	private static final String EMAIL = "teste@teste.com.br";	
	private static final String CPF = "19579533067";	
	private static final String TIPO = PerfilEnum.ROLE_USUARIO.name();

	private static final Long ID_EMPRESA = (long) 3;
	private static final String EMPRESA = "Spring Boot";
	private static final String CNPJ = "11861136000102";
		
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmpresaService empresaService;
	
	@MockBean
	private FuncionarioService funcionarioService;
	
	@Test
	@WithMockUser
	public void testCadastrarPessoaFisica() throws Exception {
		//Funcionario funcionario = obterDadosFunctionario();
		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(new Empresa(ID_EMPRESA, EMPRESA, CNPJ)));
		BDDMockito.given(this.funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(this.obterDadosFuncionario());
				
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_FUNCIONARIO))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.email").value(EMAIL))
				.andExpect(jsonPath("$.data.senha").isEmpty())
				.andExpect(jsonPath("$.data.cpf").value(CPF))
				.andExpect(jsonPath("$.data.cnpj").value(CNPJ))
				.andExpect(jsonPath("$.errors").isEmpty());		
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		cadastroPFDto.setId(null);
		cadastroPFDto.setNome(NOME);
		cadastroPFDto.setEmail(EMAIL);
		cadastroPFDto.setSenha("123456");
		cadastroPFDto.setCpf(CPF);
		cadastroPFDto.setValorHora(null);
		cadastroPFDto.setQtdHorasAlmoco(null);
		cadastroPFDto.setQtdHorasTrabalhoDia(null);
		cadastroPFDto.setCnpj(CNPJ);		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cadastroPFDto);
	}
	
	private Funcionario obterDadosFuncionario() {
		
		Funcionario funcionario = new Funcionario();		
		funcionario.setId(ID_FUNCIONARIO);
		funcionario.setNome(NOME);		
		funcionario.setEmail(EMAIL);		
		funcionario.setCpf(CPF);
		funcionario.setValorHora(null);
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionario.setQtdHorasAlmoco(null);
		funcionario.setPerfil(PerfilEnum.valueOf(TIPO));		
		funcionario.setEmpresa(new Empresa(ID_EMPRESA, EMPRESA, CNPJ));				
		return funcionario;
	}	

}
