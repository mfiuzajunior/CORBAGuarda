package br.edu.ifce.mflj.museu.services;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import br.edu.ifce.mflj.museu.Sino.ISino;
import br.edu.ifce.mflj.museu.Sino.ISinoHelper;
import br.edu.ifce.mflj.museu.impl.Guarda;
import br.edu.ifce.mflj.museu.observer.VisitantesListener;

public class GuardaService implements Runnable {
	private ORB				orb;
	private Object			nameService;
	private NamingContext	namingContext;

	private ISino			sino;
	private Guarda			guarda = new Guarda();

	public GuardaService( String[] args ){
		setUp( args );

		registrarGuarda();
		obterSino();
	}

	private void setUp(String[] args){
		try {
			orb				= ORB.init( args, null );
			nameService		= orb.resolve_initial_references( "NameService" );
			namingContext	= NamingContextExtHelper.narrow( nameService );
		}
		catch (InvalidName e) {
			e.printStackTrace();
		}
	}

	private void registrarGuarda() {
		try {
			Object	objPOA	= orb.resolve_initial_references( "RootPOA" );
			POA		rootPOA	= POAHelper.narrow( objPOA );

			Object			referenciaCromometro	= rootPOA.servant_to_reference( guarda );
			NameComponent	nameComponent[]			= { new NameComponent( "Guarda", "Guarda" ) };

			namingContext.rebind( nameComponent, referenciaCromometro );
			rootPOA.the_POAManager().activate();
		}
		catch (InvalidName e) {
			e.printStackTrace();
		}
		catch (ServantNotActive e) {
			e.printStackTrace();
		}
		catch (WrongPolicy e) {
			e.printStackTrace();
		}
		catch (NotFound e) {
			e.printStackTrace();
		}
		catch (CannotProceed e) {
			e.printStackTrace();
		}
		catch (AdapterInactive e) {
			e.printStackTrace();
		}
		catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			e.printStackTrace();
		}
	}

	private void obterSino() {
		try {
			NameComponent	nameComponent[]		= { new NameComponent( "Sino", "Sino" ) };
			Object			referenciaSino		= namingContext.resolve( nameComponent );

			sino	= ISinoHelper.narrow( referenciaSino );
		}
		catch (NotFound e) {
			System.err.println("Sino não registrado.");
		}
		catch (CannotProceed e) {
			e.printStackTrace();
		}
		catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			e.printStackTrace();
		}
	}

	public void turnoNoite( boolean noite ){
		try {
			sino.noite( noite );

		} catch( NullPointerException nullPointerException ){
			System.err.println("Sino não registrado");
		}
	}

	public void registrarVisitanteListener( VisitantesListener visitantesListener ){
		guarda.addVisitantesListener( visitantesListener );
	}

	public void run(){
		this.orb.run();
	}
}