package com.ipartek.formacion.skalada;

import java.io.Serializable;

/**
 * Grado de dificultad para las vias o sectores
 * @author ur00
 *
 */
public enum Grado implements Serializable{

	//TODO luego sera una tabla auxiliar en la BBDD
	FACIL, NORMAL, DIFICIL, EXTREMO;
}
