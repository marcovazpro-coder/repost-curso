package com.marco.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar una sucursal")
public record SucursalRequest(

        @Schema(
                description = "Nombre de la sucursal",
                example = "Sucursal Norte"
        )
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe de tener entre 5 y 50 caracteres ")
        String nombre,

        @Schema(
                description = "Nombre de la sucursal",
                example = "Sucursal Norte"
        )
        @NotBlank(message = "La direccion es requerida")
        @Size(min = 10, max = 150, message = "El nombre debe de tener entre 10 y 150 caracteres  ")
        String direccion

) {}
