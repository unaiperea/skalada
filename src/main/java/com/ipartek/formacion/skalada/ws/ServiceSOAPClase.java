package com.ipartek.formacion.skalada.ws;

import java.util.ArrayList;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

public class ServiceSOAPClase implements ServiceSOAP {

	private ModeloUsuario modeloUsuario = null;
	private ModeloVia modeloVia = null;

	private Usuario usuario = null;

	private void ServiceSOAPClase() {
		modeloVia = new ModeloVia();
		modeloUsuario = new ModeloUsuario();
	}

	//@Override
	public ArrayList<Via> viasPOJO() {

		usuario = (Usuario) modeloUsuario.getById(Constantes.ROLE_ID_ADMIN);
		ArrayList<Via> vs = modeloVia.getAll(usuario);
		
		return vs;
	}

	//@Override
	public Via viaDetallePOJO(int id) {

		Via v = modeloVia.getById(id);

		return v;
	}
}
