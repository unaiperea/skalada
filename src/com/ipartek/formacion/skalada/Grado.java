package com.ipartek.formacion.skalada;

import java.io.Serializable;

/**
 * Grado de dificultd para las vias o sectores
 * 
 * @author Curso
 *
 */
public enum Grado implements Serializable {
	
	//TODO Luego sera una tabla auxiliar en la BBDD
	FACIL, NORMAL, DIFICIL, EXTREMO;
	
}
