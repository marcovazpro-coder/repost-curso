package com.marco.almacen.dto.ventas;

import com.marco.almacen.dto.sucursales.SucursalResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(

        @Schema(description = "Identificador de venta", example = "1")
        Long id,

        @Schema(description = "Fecha de la venta", example = "02/09/26")
        String fecha,

        @Schema(description = "Estado de la venta", example = "Registrada")
        String estado,

        @Schema(description = "Sucursal donde se hizo a venta")
        SucursalResponse sucursal,

        @Schema(description = "Lista de productos de la venta")
        List<DetalleVentaResponse> detalles, //Coreccion de  List<DetalleVentaRequest> detalles

        @Schema(description = "Total de la venta", example = "1500.00")
        BigDecimal total


) {}
