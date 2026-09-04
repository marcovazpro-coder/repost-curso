package com.marco.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Detalle de un producto dentro de una vebta")
public record DetalleVentaResponse(

        @Schema(description = "Id del producto", example = "1")
        Long idProducto,

        @Schema(description = "Nombre del producto", example = "Laptop Gamer 5000")
        String nombreProducto,

        @Schema(description = "Cantidad del producto", example = "34")
        Integer cantidadProducto,

        @Schema(description = "Precio unitario del producto", example = "2000.00")
        BigDecimal precioProduto,

        @Schema(description = "Subtotal del producto", example = "200000.00")
        BigDecimal subtotal
) {}

