package com.marco.almacen.mappers;

import com.marco.almacen.dto.sucursales.SucursalRequest;
import com.marco.almacen.dto.sucursales.SucursalResponse;
import com.marco.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public Sucursal requestAEntidad(SucursalRequest request){
        if (request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();
    }

    public SucursalResponse entidadAResponse(Sucursal sucursal){

        if(sucursal == null) return null;

        return new SucursalResponse(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion()
        );
    }
}
