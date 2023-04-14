package br.com.screenmatch;

import lombok.Getter;
import lombok.Setter;

public class Titulo {    
    @Getter @Setter private String nome;    
    @Getter @Setter private int anoLancamento;
    
    public Titulo(TituloOmdb tituloOmdb) {
    	if (tituloOmdb.year().length() > 4) {
    		throw new ErroConversaoAnoException("Ano inválido");
    	}
    	this.nome = tituloOmdb.title();
    	this.anoLancamento = Integer.valueOf(tituloOmdb.year());
    }
    
    public String toPrint() {
        return "nome='" + nome + '\'' +
                ", anoLancamento=" + anoLancamento;
    }
}
