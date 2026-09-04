package com.marco.almacen.mappers;

import com.marco.almacen.dto.productos.ProductoRequest;
import com.marco.almacen.dto.productos.ProductoResponse;
import com.marco.almacen.entities.Producto;
import com.marco.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public Producto requestAEntidad(ProductoRequest request, Categoria categoria){
        if(request == null)
            return null;
        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();
    }
    public ProductoResponse entidadAResponse(Producto producto){
        if(producto == null)return null;
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria().getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad());
    }
}
