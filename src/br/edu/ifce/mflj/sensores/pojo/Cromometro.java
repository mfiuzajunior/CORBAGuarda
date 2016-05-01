package br.edu.ifce.mflj.sensores.pojo;

public class Cromometro {
	private Boolean	estado;
	private String	leitura;

	public Cromometro( Boolean estado, String leitura ){
		super();

		this.estado		= estado;
		this.leitura	= leitura;
	}

	public Boolean getEstado() {
		return estado;
	}

	public String getLeitura() {
		return leitura;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void setLeitura(String leitura) {
		this.leitura = leitura;
	}

	public String toString(){
		return "Cromometro";
	}
}
