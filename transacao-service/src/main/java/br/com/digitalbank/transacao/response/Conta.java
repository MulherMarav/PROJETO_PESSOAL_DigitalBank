package br.com.digitalbank.transacao.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data 
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String cpfCliente;
	
	protected BigDecimal valor;

	protected String tipoConta;
}
