package com.microservice.microserviceauth.utils;


import com.microservice.microserviceauth.exception.DatoNoIngresadoException;

public class EntidadNoNulaException {

    // Método genérico para verificar si una entidad es null
    public static void verificarEntidadNoNula(Object entidad, String mensajeError) {
        if (entidad == null) {
            throw new DatoNoIngresadoException(mensajeError);
        }
    }

}
