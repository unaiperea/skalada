package com.ipartek.formacion.skalada.listener;

import java.util.ArrayList;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;

/**
 * Application Lifecycle Listener implementation class ListenerSession
 *
 */
public class ListenerSession implements HttpSessionAttributeListener {

	public static ArrayList<Usuario> session_users = new ArrayList<Usuario>();

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	//@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		if (se.getName().equals(Constantes.KEY_SESSION_USER)) {
			if (se.getValue().getClass() == Usuario.class) {
				if (ListenerSession.session_users.contains(se.getValue())) {
					synchronized (this) {
						ListenerSession.session_users.remove(se.getValue());
					}
				}
			}
		}

	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	//@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		if (se.getName().equals(Constantes.KEY_SESSION_USER)) {
			if (se.getValue().getClass() == Usuario.class) {
				if (!ListenerSession.session_users.contains(se.getValue())) {
					synchronized (this) {
						ListenerSession.session_users.add((Usuario) se
								.getValue());
					}
				}
			}
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	//@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
	}

}
