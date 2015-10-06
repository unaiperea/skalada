package com.ipartek.formacion.skalada.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;

/**
 * Application Lifecycle Listener implementation class ListenerUserCounter
 *
 */
public class ListenerUserCounter
		implements
			HttpSessionListener,
			HttpSessionAttributeListener {

	// @Unai: Se pone el nombre de la clase (LOG)
	private final static Logger LOG = Logger.getLogger(ListenerInit.class);

	public static int contadorSession = 0;

	/**
	 * Default constructor.
	 */
	public ListenerUserCounter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// Eliminamos una sesión
		LOG.trace("Sesión eliminada");

		if (Constantes.KEY_SESSION_USER.equals(se.getName())) {
			this.contadorSession--;
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// Añadimos una sesión nueva
		LOG.trace("Nueva sesión iniciada");

		if (Constantes.KEY_SESSION_USER.equals(se.getName())) {
			this.contadorSession++;
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

}
