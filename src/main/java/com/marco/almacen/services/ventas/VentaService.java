package com.marco.almacen.services.ventas;

import com.marco.almacen.dto.ventas.VentaRequest;
import com.marco.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {

    VentaResponse registrar(VentaRequest request);


}

