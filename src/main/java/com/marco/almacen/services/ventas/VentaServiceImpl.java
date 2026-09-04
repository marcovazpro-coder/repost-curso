package com.marco.almacen.services.ventas;

import com.marco.almacen.dto.productos.ProductoRequest;
import com.marco.almacen.dto.productos.ProductoResponse;
import com.marco.almacen.dto.ventas.DetalleVentaRequest;
import com.marco.almacen.entities.DetalleVenta;
import com.marco.almacen.entities.Producto;
import com.marco.almacen.entities.Sucursal;
import com.marco.almacen.entities.Venta;
import com.marco.almacen.enums.Categoria;
import com.marco.almacen.enums.EstadoVenta;
import com.marco.almacen.exceptions.RecursoNoEncontradoException;
import com.marco.almacen.mappers.VentaMapper;
import com.marco.almacen.repositories.ProductoRepository;
import com.marco.almacen.repositories.SucursalRepository;
import com.marco.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marco.almacen.dto.ventas.VentaRequest;
import com.marco.almacen.dto.ventas.VentaResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService{

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    //Agregamos para poder consultar si existe la sucursal
    private final SucursalRepository sucursalRepository;
    //Agregamos para poder consultar si existe el producto
    private final ProductoRepository productoRepository;

    @Transactional
    @Override
    public VentaResponse registrar(VentaRequest request){
        log.info("Registrando nueva venta");

        //Revisar si existe Sucursal
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal no existe"));
        //Revisar si existe Producto
        for (DetalleVentaRequest detalleRequest : request.productos()) { //Recorremos la lista de productos

            Producto producto = productoRepository.findById(detalleRequest.idProducto()) //Vemos si encontramos el producto
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto con id " + detalleRequest.idProducto() + " no existe"
                    ));
            if (detalleRequest.cantidadProducto() > producto.getCantidad()) { // revisamos si hay en existencia el producto es decir la cantidad
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + producto.getNombre()
                );
            }
        }
        //Creamos la venta
        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .build();
        //Agregar Los detalles de venta
        for (DetalleVentaRequest detalleRequest : request.productos()) {

            Producto producto = productoRepository.findById(detalleRequest.idProducto())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto con id " + detalleRequest.idProducto() + " no existe"
                    ));

            DetalleVenta detalleVenta = DetalleVenta.builder()
                    .producto(producto)
                    .cantidadProducto(detalleRequest.cantidadProducto())
                    .precioProducto(producto.getPrecio())
                    .build();

            venta.agregarDetalle(detalleVenta);
        }Venta ventaGuardada = ventaRepository.save(venta); //Guardamos la veta

        /// //////////////////////////////////////////////////////////////////////////

        return ventaMapper.entidadAResponse(ventaGuardada); //Con esto el método devuelve la venta creada con sucursal, detalles, subtotales y total.
    }
    /*
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto");

        Producto producto = productoMapper.requestAEntidad(request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()));
        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }
    */

}
