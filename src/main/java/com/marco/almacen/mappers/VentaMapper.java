package com.marco.almacen.mappers;

import com.marco.almacen.dto.sucursales.SucursalResponse;
import com.marco.almacen.dto.ventas.DetalleVentaResponse;
import com.marco.almacen.dto.ventas.VentaRequest;
import com.marco.almacen.dto.ventas.VentaResponse;
import com.marco.almacen.entities.Venta;
import com.marco.almacen.enums.EstadoVenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class VentaMapper {
    public Venta requestAEntidad(VentaRequest request, EstadoVenta estadoVenta){
        if(request == null)
            return null;
        return Venta.builder()
                .estadoVenta(estadoVenta)
                .fecha(LocalDate.now())



                .build();
    }
    public VentaResponse entidadAResponse(Venta venta) {//agregado por chatgpt
        if (venta == null) {
            return null;
        }

        List<DetalleVentaResponse> detalles = venta.getDetalleVentas().stream()
                .map(detalle -> {
                    BigDecimal subtotal = detalle.getPrecioProducto()
                            .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()));

                    return new DetalleVentaResponse(
                            detalle.getProducto().getId(),
                            detalle.getProducto().getNombre(),
                            detalle.getCantidadProducto(),
                            detalle.getPrecioProducto(),
                            subtotal
                    );
                })
                .toList();

        BigDecimal total = detalles.stream()
                .map(DetalleVentaResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SucursalResponse sucursalResponse = new SucursalResponse(
                venta.getSucursal().getId(),
                venta.getSucursal().getNombre(),
                venta.getSucursal().getDireccion()
        );

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescription(),
                sucursalResponse,
                detalles,
                total
        );
    }
}
