package br.com.digitalbank.cliente.data.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import br.com.digitalbank.cliente.data.vo.v1.EnderecoVO;
import lombok.Data;

@Data
public class ClienteVO extends RepresentationModel<ClienteVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String cpf;
	
	@NotBlank
	private String nomeCompleto;

	@NotBlank
	private String email;

	@NotBlank
	private String telefone;
	
	@NotNull
	private EnderecoVO endereco;

	private Boolean ativado = this.ativado == null ? true : false;;

}
