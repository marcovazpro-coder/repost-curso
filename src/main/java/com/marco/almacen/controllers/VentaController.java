package com.marco.almacen.controllers;

import com.marco.almacen.dto.productos.ProductoRequest;
import com.marco.almacen.dto.productos.ProductoResponse;
import com.marco.almacen.dto.ventas.VentaRequest;
import com.marco.almacen.dto.ventas.VentaResponse;
import com.marco.almacen.services.ventas.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venta")
@AllArgsConstructor
@Validated
@Tag(name = "Ventas", description = "Endpoint para la gestion de Ventas")
public class VentaController {
    private final VentaService ventaService;

    //REGISTRO DE VENTAS

    @PostMapping("/{id}")
    @Operation(
            summary = "Registrar Venta",
            tags = {"Ventas - Consultas"}
    )
    public ResponseEntity<VentaResponse> registrar(
            @RequestBody VentaRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }



}
