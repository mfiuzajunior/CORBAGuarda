package br.edu.ifce.mflj.museu.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.edu.ifce.mflj.museu.observer.SinoListener;
import br.edu.ifce.mflj.museu.observer.VisitantesListener;
import br.edu.ifce.mflj.museu.services.GuardaService;

public class GuardaGUI extends JFrame implements ActionListener, VisitantesListener, SinoListener {

	private static final long	serialVersionUID = 5883698476830081453L;
	private static final int	LARGURA			= 200,
								ALTURA			= 100;

	private int				quantidadeDeVisitantes;

	private GuardaService	guardaService;

	private JLabel			labelVisitantes;

	private JCheckBox		turnoNoite;

	public GuardaGUI( String[] args ){
		super();

		iniciarGuardaService( args );
		iniciarGUI();

		this.repaint();
	}

	public JCheckBox getLabelVisitantes() {
		if( turnoNoite == null ){
			turnoNoite = new JCheckBox( "Turno Noite" );
			turnoNoite.setBounds( 5, 35, 150, 25 );
			turnoNoite.addActionListener( this );
		}
		return turnoNoite;
	}

	private Component getCheckBoxNoite() {
		if( labelVisitantes == null ){
			labelVisitantes = new JLabel( "Visitantes: " + quantidadeDeVisitantes );
			labelVisitantes.setBounds( 5, 5, 100, 25 );
		}
		return labelVisitantes;
	}

	private void iniciarGuardaService( String[] args ) {
		guardaService = new GuardaService( args );
		guardaService.registrarVisitanteListener( this );
		guardaService.registrarSinoListener( this );
		new Thread( guardaService ).start();
	}

	private void iniciarGUI(){
		setDefaulLookAndFeel();

		setTitle( "Guarda" );
		setResizable( false );
		setBounds( 100, 100, GuardaGUI.LARGURA, GuardaGUI.ALTURA );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		getContentPane().setLayout( null );

		setVisible( true );

		iniciarComponentes();
	}

	private void iniciarComponentes() {
		getContentPane().add( getLabelVisitantes() );
		getContentPane().add( getCheckBoxNoite() );
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
		guardaService.turnoNoite( turnoNoite.isSelected() );
	}

	public static void main(String[] args) {
		new GuardaGUI( args );
	}

	@Override
	public void incrementarVisitantes() {
		labelVisitantes.setText( "Visitantes: " + ++quantidadeDeVisitantes );
	}

	@Override
	public void decrementarVisitantes() {
		labelVisitantes.setText( "Visitantes: " + ( quantidadeDeVisitantes == 0 ? 0 : --quantidadeDeVisitantes ) );
	}

	@Override
	public void tocarSom() {
		JOptionPane.showMessageDialog(this, "Bing");
	}
}