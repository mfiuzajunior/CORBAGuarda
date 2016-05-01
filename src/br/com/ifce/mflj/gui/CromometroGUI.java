package br.com.ifce.mflj.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.edu.ifce.mflj.observer.CromometroListener;
import br.edu.ifce.mflj.sensores.services.CromometroService;

public class CromometroGUI extends JFrame implements ActionListener, CromometroListener {

	private static final long	serialVersionUID = 5883698476830081453L;
	private static final int	LARGURA			= 200,
								ALTURA			= 100;

	private CromometroService	cromometroService;

	private JLabel				leituraAtual;
	private JTextArea			valorLeituraAtual;
	private JButton				enviarLeituraAtual;

	public CromometroGUI( String[] args ){
		super();

		iniciarCromometroService( args );
		iniciarGUI();

		this.repaint();
	}

	public JLabel getLeituraAtual() {
		if( leituraAtual == null ){
			leituraAtual = new JLabel( "Leitura Atual: " );
			leituraAtual.setBounds( 0, 0, 100, 25 );
		}
		return leituraAtual;
	}

	public JTextArea getValorLeituraAtual() {
		if( valorLeituraAtual == null ){
			valorLeituraAtual = new JTextArea( this.cromometroService.getCromometroDAO().getCromometro().getLeitura() );
			valorLeituraAtual.setBounds( 105, 0, 50, 25 );
		}
		return valorLeituraAtual;
	}

	public JButton getEnviarLeituraAtual() {
		if( enviarLeituraAtual == null ){
			enviarLeituraAtual = new JButton( "Enviar Leitura" );
			enviarLeituraAtual.setBounds( 55, 30, 100, 30 );
			enviarLeituraAtual.addActionListener( this );
		}
		return enviarLeituraAtual;
	}

	private void iniciarCromometroService( String[] args ) {
		cromometroService = new CromometroService( args );
		cromometroService.getCromometroDAO().addCromometroListener( this );
		new Thread( cromometroService ).start();
	}

	private void iniciarGUI(){
		setDefaulLookAndFeel();

		this.setTitle( "Cromometro" );
		this.setResizable( false );
		this.setBounds( 100, 100, CromometroGUI.LARGURA, CromometroGUI.ALTURA );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		this.getContentPane().setLayout( null );

		this.setVisible( true );

		iniciarComponentes();
	}

	private void iniciarComponentes() {
		this.getContentPane().add( getLeituraAtual() );
		this.getContentPane().add( getValorLeituraAtual() );
		this.getContentPane().add( getEnviarLeituraAtual() );
	}

	private void setDefaulLookAndFeel(){
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch( UnsupportedLookAndFeelException unsupportedLookAndFeelException ){}
		catch( ClassNotFoundException classNotFoundException ){}
		catch( InstantiationException instantiationException ){}
		catch( IllegalAccessException illegalAccessException ){}		
	}

	@Override
	public void actionPerformed( ActionEvent actionEvent ){
		cromometroService.enviarLeituraAtual( this.getValorLeituraAtual().getText() );
	}

	@Override
	public void leituraObtida( String leitura ){
		this.getValorLeituraAtual().setText( leitura );
	}

	public static void main(String[] args) {
		new CromometroGUI( args );
	}
}