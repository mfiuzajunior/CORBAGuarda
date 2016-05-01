package br.edu.ifce.mflj.sensores.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.ifce.mflj.estacao.sensor.cromometro.ICromometroPOA;
import br.edu.ifce.mflj.observer.CromometroListener;
import br.edu.ifce.mflj.sensores.pojo.Cromometro;

public class CromometroDAO extends ICromometroPOA {

	private static final String LEITURA_PADRAO = "25";

	private Cromometro cromometro;
	private List<CromometroListener> cromometroListeners = new ArrayList<CromometroListener>();

	public CromometroDAO(){
		this.setCromometro( new Cromometro( true, CromometroDAO.LEITURA_PADRAO ) );
	}

	public CromometroDAO( Cromometro cromometro ){
		this.cromometro = cromometro == null ? new Cromometro( true, CromometroDAO.LEITURA_PADRAO ) : cromometro;
	}

	public Cromometro getCromometro(){
		return cromometro;
	}

	public void setCromometro( Cromometro cromometro ){
		this.cromometro = cromometro;
	}

	@Override
	public boolean ligado(){
		return this.getCromometro().getEstado();
	}

	@Override
	public String obterLeitura(){
		return this.getCromometro().getLeitura();
	}

	@Override
	public void configurarLeitura( String leitura ){
		this.getCromometro().setLeitura( leitura );
		notificarLeituraRecebida( this.getCromometro().getLeitura() );
	}

	@Override
	public String obterIdentificador() {
		return this.getCromometro().toString();
	}

	private void notificarLeituraRecebida( String leitura ){
		for( CromometroListener cromometroListener : cromometroListeners ){
			cromometroListener.leituraObtida( leitura );
		}
	}

	public void addCromometroListener( CromometroListener cromometroListener ){
		this.cromometroListeners.add( cromometroListener );
	}
}
