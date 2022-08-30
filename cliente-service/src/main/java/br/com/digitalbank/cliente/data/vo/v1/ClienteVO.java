package br.com.digitalbank.cliente.data.vo.v1;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data @NotNull 
@JsonPropertyOrder({ "nomeCompleto", "cpf", "email", "telefone", "endereco"})
public class ClienteVO extends RepresentationModel<ClienteVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpf;
	
	@JsonProperty("nome_completo")
	private String nomeCompleto;
	
	private String email;
	
	private String telefone;
	
	private EnderecoVO endereco;
	
}
