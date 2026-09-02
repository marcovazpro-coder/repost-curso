package com.marco.almacen.dto.productos;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
@Schema(description = "Datos necesarios para crear o actualizar un producto")

public record ProductoRequest(

    @Schema(
            description = "Nombre del producto",
            example = "Laptop Gamer"
    )


    @NotBlank(message = "El nombre es requerido")
    @Size(min=5, max=30, message="El nombre debe tener entre 5 y 30 caracteres")
    String nombre,

    @Schema(
            description = "Categoria del producto",
            example = "Electrónica"
    )

    @NotNull
    String categoria,

    @Schema(
            description = "Precio del producto",
            example = "15999.99"
    )

    @NotNull
    @Positive
    BigDecimal precio,

    @Schema(
            description = "Nombre del producto",
            example = "Laptop Gamer"
    )

    @NotNull
    @Positive
    Integer cantdad
){}