package com.marco.almacen.controllers;
import com.marco.almacen.dto.sucursales.SucursalRequest;
import com.marco.almacen.dto.sucursales.SucursalResponse;
import com.marco.almacen.services.sucursales.SucursalService;
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
@RequestMapping("/api/sucursal")
@AllArgsConstructor
@Validated
@Tag(name = "Sucursal", description = "Endpoints para la gestion de Sucursal")

public class SucursalController {
    private final SucursalService sucursalService;

    @GetMapping
    @Operation(
            summary = "Listar Sucursals",
            tags = {"Sucursals - Consultas"}
    )
    public ResponseEntity<List<SucursalResponse>> listar(    ){
        return ResponseEntity.ok(sucursalService.listar());
    }



    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener sucursal por id",
            tags = {"Sucursales - Consultas"}
    )
    public ResponseEntity<SucursalResponse> obtenerPorId(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    // REGISTRAR

    @PostMapping("/{id}")
    @Operation(
            summary = "Registrar una nueva sucursal",
            tags = {"Sucursales - Gestión"}
    )
    public ResponseEntity<SucursalResponse> registrar(
            @RequestBody SucursalRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.registrar(request));
    }

    //ACTUALIZAR

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un Sucursal existente",
            tags = {"Sucursals - Gestión"}
    )
    public ResponseEntity<SucursalResponse> actualizar(
            @PathVariable @Positive(message = "El Id debe ser positivo") Long id,
            @Valid @RequestBody SucursalRequest request
    ){
        return ResponseEntity.ok(sucursalService.actualizar(request, id));
    }

    // Eliminar

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un Sucursal",
            tags = {"Sucursals - Gestion"}
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
