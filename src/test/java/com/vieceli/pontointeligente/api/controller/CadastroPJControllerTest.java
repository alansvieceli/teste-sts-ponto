package com.vieceli.pontointeligente.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.vieceli.pontointeligente.api.dtos.CadastroPJDto;
import com.vieceli.pontointeligente.api.entities.Empresa;
import com.vieceli.pontointeligente.api.entities.Funcionario;
import com.vieceli.pontointeligente.api.services.EmpresaService;
import com.vieceli.pontointeligente.api.services.FuncionarioService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroPJControllerTest {
	
	private static final String URL_BASE = "/api/cadastrar-pj";
	private static final Long ID_FUNCIONARIO = (long) 8;
	private static final String NOME = "Teste CadastroPJ";
	private static final String EMAIL = "teste@pj.com";
	private static final String SENHA = "123456";
	private static final String CPF = "79939330057";
	private static final Long ID_EMPRESA= (long) 9;
	private static final String RAZAO_SOCIAL = "Testando IT";
	private static final String CNPJ = "36049993000133";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmpresaService empresaService;
	
	@MockBean
	private FuncionarioService funcionarioService;
	
	@Test
	@WithMockUser
	public void testCadastrarLancamento() throws Exception {
		Empresa empresa = this.obterDadosEmpresa();
		Funcionario funcionario = this.obterDadosFuncionario();
		
		BDDMockito.given(this.empresaService.persistir(Mockito.any(Empresa.class))).willReturn(empresa);
		BDDMockito.given(this.funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(funcionario);

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
				.andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(null);
		cadastroPJDto.setNome(NOME);
		cadastroPJDto.setEmail(EMAIL);
		cadastroPJDto.setSenha(SENHA);
		cadastroPJDto.setCpf(CPF);
		cadastroPJDto.setRazaoSocial(RAZAO_SOCIAL);
		cadastroPJDto.setCnpj(CNPJ);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cadastroPJDto);

	}
	
	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(ID_EMPRESA);
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		return empresa;
	}
	
	private Funcionario obterDadosFuncionario() {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(ID_FUNCIONARIO);
		funcionario.setNome(NOME);
		funcionario.setEmail(EMAIL);
		funcionario.setSenha(null);
		funcionario.setCpf(CPF);
		funcionario.setEmpresa(new Empresa(ID_EMPRESA, RAZAO_SOCIAL, CNPJ));
		return funcionario;
		
	}

}
