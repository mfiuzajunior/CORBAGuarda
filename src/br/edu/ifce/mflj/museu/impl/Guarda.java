package br.edu.ifce.mflj.museu.impl;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.mflj.museu.guarda.IGuardaPOA;
import br.edu.ifce.mflj.museu.observer.SinoListener;
import br.edu.ifce.mflj.museu.observer.VisitantesListener;

public class Guarda extends IGuardaPOA {

	private List<VisitantesListener>	visitantesListeners = new ArrayList<VisitantesListener>();
	private List<SinoListener>			sinoListeners		= new ArrayList<SinoListener>();

	public void addVisitantesListener( VisitantesListener visitantesListener ){
		visitantesListeners.add( visitantesListener );
	}

	public void removeVisitantesListener( VisitantesListener visitantesListener ){
		visitantesListeners.remove( visitantesListener );
	}

	public void addSinoListener( SinoListener sinoListener ){
		sinoListeners.add( sinoListener );
	}

	public void removeSinoListener( SinoListener sinoListener ){
		sinoListeners.remove( sinoListener );
	}

	@Override
	public void entradaDeCliente() {
		for( VisitantesListener listener : visitantesListeners ){
			listener.incrementarVisitantes();
		}
	}

	@Override
	public void saidaDeCliente() {
		for( VisitantesListener listener : visitantesListeners ){
			listener.decrementarVisitantes();
		}
	}

	@Override
	public void entradaNaoAutorizada() {
		for( SinoListener listener : sinoListeners ){
			listener.tocarSom();
		}
	}
}