package com.marco.almacen.enums;

import com.marco.almacen.exceptions.RecursoNoEncontradoException;
import com.marco.almacen.utils.StringCustomUtils;
import com.marco.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String description;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for(EstadoVenta estadoVenta : values()){
            if(StringCustomUtils.quitarAcentos(estadoVenta.description).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con la descripcion"+ descripcion);
    }
    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        ValoresNumericosUtils.validarNumeroRequerido(codigo);

        for(EstadoVenta estadoVenta : values()){
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con el codigo"+ codigo);
    }

}
