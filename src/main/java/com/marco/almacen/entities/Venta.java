package com.marco.almacen.entities;

import com.marco.almacen.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "venta", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta){

        if(detalleVenta == null){
            throw new IllegalArgumentException("El detalle de venta es requerido");
        }
        if(this.detalleVentas == null){
            this.detalleVentas = new ArrayList<>();
        }
        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }
    public void Cancelar(){
        if(estadoVenta == EstadoVenta.CANCELADA)
            throw new IllegalArgumentException("La venta ya esta cancelada");

        estadoVenta = EstadoVenta.CANCELADA;
    }
}
