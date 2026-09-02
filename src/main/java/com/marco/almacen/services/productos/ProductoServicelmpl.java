package com.marco.almacen.services.productos;

import com.marco.almacen.dto.productos.ProductoRequest;
import com.marco.almacen.dto.productos.ProductoResponse;
import com.marco.almacen.entities.Producto;
import com.marco.almacen.enums.Categoria;
import com.marco.almacen.exceptions.RecursoNoEncontradoException;
import com.marco.almacen.mappers.ProductoMapper;
import com.marco.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j

public class ProductoServicelmpl implements ProductoService{
    
    private final ProductoRepository productoRepository;
    
    private final ProductoMapper productoMapper;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {

        log.info("Listando los productos");
        return productoRepository.findAll().stream()
                .map(productoMapper::entidadAResponse).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto");

        Producto producto = productoMapper.requestAEntidad(request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()));
        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);
        log.info("Actualizando producto con id {}", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(
                        request.categoria()),
                request.precio(),
                request.cantdad()
        );
        productoRepository.save(producto); //No es necesario por dirty checking
        log.info("Producto con id {} actualizado",id);
        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Eliminando producto con id {}",id);
        productoRepository.delete(producto);
        log.info("Producto con id {} eliminado",id);

    }

    private Producto obtenerProductoOException(Long id){
        log.info("Buscando producto con id {}",id);
        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id" + id
                ));

    }
}
