package com.ipartek.formacion.skalada.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class ListenerInit
 *
 */
public class ListenerInit implements ServletContextListener {

	// @Unai: Se pone el nombre de la clase (LOG)
	private final static Logger LOG = Logger.getLogger(ListenerInit.class);

	Properties props = null;

	/**
	 * Default constructor.
	 */
	public ListenerInit() {
		// Cuando arranca la aplicación
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Cuando se despliega la aplicación
		// Inicializar log4j

		try {
			// Fichero configuracion de Log4j
			this.props = new Properties();
			this.props.load(this.getClass().getResourceAsStream(
					"/log4j.properties"));
			PropertyConfigurator.configure(this.props);

			LOG.info("Cargado con éxito");

		} catch (Exception e) {
			e.printStackTrace();
			// @Unai: No se puede sobreescribir este método
			// throw new Exception("No se puede cargar log4j");
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Cuando se elimina se destruye

		LOG.info("Destruyendo conexto aplicación");
		this.props = null;
	}

}
