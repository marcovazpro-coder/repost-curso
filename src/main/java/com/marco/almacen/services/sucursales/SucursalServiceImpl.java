package com.marco.almacen.services.sucursales;

import com.marco.almacen.dto.sucursales.SucursalRequest;
import com.marco.almacen.dto.sucursales.SucursalResponse;
import com.marco.almacen.entities.Sucursal;
import com.marco.almacen.exceptions.RecursoNoEncontradoException;
import com.marco.almacen.mappers.SucursalMapper;
import com.marco.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService{

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar(String nombre, String direccion){
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAResponse).toList();

    }




    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id){
        return sucursalMapper.entidadAResponse(obtenerSucursalOExeption(id));
    }
    @Override
    public SucursalResponse registrar(SucursalRequest request){

        log.info("Registrando nueva sucursal");
        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        sucursalRepository.save(sucursal);
        log.info("Nueva sucursal registrada: {}", sucursal.getNombre());
        return sucursalMapper.entidadAResponse(sucursal);
    }
    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id){
        Sucursal sucursal = obtenerSucursalOExeption(id);
        log.info("Actualizando sucursal con id: {}", id);
        validarCambiosUnicos(request,id);
        sucursal.actualizar(request.nombre(), request.direccion());
        log.info("sucursal con id {} actualizada", id);
        return sucursalMapper.entidadAResponse(sucursal);
    }
    @Override
    public void eliminar(Long id){
        Sucursal sucursal=obtenerSucursalOExeption(id);
        log.info("Eliminando sucursal con id {} ",id);
        sucursalRepository.delete(sucursal);
        log.info("Sucursal con id {} eliminada",id);
    }

    private Sucursal obtenerSucursalOExeption(Long id){
        log.info("buscando sucursal con id {}", id);
        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: "+ id)
        );
    }
    private void validarDatosUnicos(SucursalRequest request){
        log.info("Validando nombre unico");
        if(sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException(
                "Ya existe una sucursal con el nombre de: "+ request.nombre());
    }
    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validando cambio en nombre unico");
        if(sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(),id))
            throw new IllegalArgumentException(
                    "Ya existe una sucursal con el nombre de: "+ request.nombre());
    }
}
