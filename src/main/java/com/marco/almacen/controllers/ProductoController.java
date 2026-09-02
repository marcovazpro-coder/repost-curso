package com.marco.almacen.controllers;


import com.marco.almacen.dto.productos.ProductoRequest;
import com.marco.almacen.dto.productos.ProductoResponse;
import com.marco.almacen.services.productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
@Validated
@Tag(name = "Productos", description = "Endpoints para la gestion de productos")

public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    @Operation(
            summary = "Listar productos",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<List<ProductoResponse>> listar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) BigDecimal precioMin,
        @RequestParam(required = false) BigDecimal precioMax
    ){
        return ResponseEntity.ok(productoService.listar(nombre, categoria, precioMin, precioMax));
    }



    @GetMapping("/{id}")
    @Operation(
            summary = "Listar productos",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<ProductoResponse> obtenerPorId(
        @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // REGISTRAR

    @PostMapping("/{id}")
    @Operation(
            summary = "Registrar productos",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<ProductoResponse> registrar(
            @RequestBody ProductoRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    //ACTUALIZAR

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto existente",
            tags = {"Productos - Gestión"}
    )
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "El Id debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

    // Eliminar

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un producto",
            tags = {"Productos - Gestion"}
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
