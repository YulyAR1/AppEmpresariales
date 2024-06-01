package com.ventas.ventas;

import com.ventas.ventas.controller.ProductoController;
import com.ventas.ventas.dto.ProductoDto;
import com.ventas.ventas.entity.Producto;
import com.ventas.ventas.entity.Proveedor;
import com.ventas.ventas.service.ProductoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class ProductoSteps {

    private Producto producto;
    private ProductoService productoService = Mockito.mock(ProductoService.class);
    private ProductoController productoController = new ProductoController(productoService);
    private ResponseEntity<?> response;

    @Given("a Producto with id {string}, ean {string}, cantidad {string}, nombre {string}, precio {string}, proveedor {string}")
    public void aProductoWithInformation(String id, String ean, String cantidad, String nombre, String precio, String proveedorId) {
        producto = new Producto();
        producto.setId(Integer.parseInt(id));
        producto.setEan(ean);
        producto.setCantidad(Integer.parseInt(cantidad));
        producto.setNombre(nombre);
        producto.setPrecio(Float.parseFloat(precio));
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(Integer.parseInt(proveedorId));
        producto.setProveedor(proveedor);
        Mockito.when(productoService.getOne(producto.getId())).thenReturn(Optional.of(producto));
        Mockito.when(productoService.existsById(producto.getId())).thenReturn(true);
    }

    @When("I create the Producto")
    public void iCreateTheProducto() {
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(producto.getId());
        productoDto.setEan(producto.getEan());
        productoDto.setCantidad(producto.getCantidad());
        productoDto.setNombre(producto.getNombre());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setProveedor(producto.getProveedor());
        response = productoController.create(productoDto);
    }



    @Then("the Producto should be created successfully")
    public void theProductoShouldBeCreatedSuccessfully() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("I request a list of all Productos")
    public void iRequestAListOfAllProductos() {
        response = productoController.list();
    }

    @Then("I should receive a list of all Productos")
    public void iShouldReceiveAListOfAllProductos() {
        Assert.assertTrue(response.getBody() instanceof List);
    }

    @Given("a Producto with id {string}")
    public void aProductoWithId(String id) {
        producto = new Producto();
        producto.setId(Integer.parseInt(id));
        Mockito.when(productoService.getOne(producto.getId())).thenReturn(Optional.of(producto));
        Mockito.when(productoService.existsById(producto.getId())).thenReturn(true);
    }

    @When("I request the Producto by id")
    public void iRequestTheProductoById() {
        response = productoController.getById(producto.getId());
    }

    @Then("I should receive the Producto")
    public void iShouldReceiveTheProducto() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody() instanceof Producto);
    }

    @Given("a Producto with nombre {string}")
    public void aProductoWithNombre(String nombre) {
        producto = new Producto();
        producto.setNombre(nombre);
        Mockito.when(productoService.getByNombre(producto.getNombre())).thenReturn(Optional.of(producto));
        Mockito.when(productoService.existsByNombre(producto.getNombre())).thenReturn(true);
    }

    @When("I request the Producto by nombre")
    public void iRequestTheProductoByNombre() {
        response = productoController.getByNombre(producto.getNombre());
    }



    @When("I update the Producto")
    public void iUpdateTheProducto() {
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(producto.getId());
        productoDto.setEan(producto.getEan());
        productoDto.setCantidad(producto.getCantidad());
        productoDto.setNombre(producto.getNombre());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setProveedor(producto.getProveedor());
        Mockito.when(productoService.save(Mockito.any(Producto.class))).thenReturn(producto);
        response = productoController.update(producto.getId(), productoDto);
    }

    @Then("the Producto should be updated successfully")
    public void theProductoShouldBeUpdatedSuccessfully() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("I delete the Producto")
    public void iDeleteTheProducto() {
        response = productoController.delete(producto.getId());
    }

    @When("I delete the Producto by id")
    public void iDeleteTheProductoById() {
        response = productoController.delete(producto.getId());
    }

    @Then("the Producto should be deleted successfully")
    public void theProductoShouldBeDeletedSuccessfully() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}