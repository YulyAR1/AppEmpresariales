
package com.ventas.ventas.service;

import com.ventas.ventas.entity.Producto;
import com.ventas.ventas.entity.Proveedor;
import com.ventas.ventas.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProveedorService {

    ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> list(){
        return proveedorRepository.findAll();
    }

    public void  save(Proveedor proveedor){
        proveedorRepository.save(proveedor);
    }

    public boolean existsById(int id){
        return proveedorRepository.existsById(id);
    }

    public void delete(int id){
        proveedorRepository.deleteById(id);
    }
    public boolean existsByNombre(String nombre){
        return proveedorRepository.existsByNombre(nombre);
    }


}

