package com.ventas.ventas;

import com.ventas.ventas.controller.ProveedorController;
import com.ventas.ventas.dto.ProveedorDto;
import com.ventas.ventas.entity.Proveedor;
import com.ventas.ventas.service.ProveedorService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ProveedorSteps {

    private Proveedor proveedor;
    private ProveedorService proveedorService = Mockito.mock(ProveedorService.class);
    private ProveedorController proveedorController = new ProveedorController(proveedorService);
    private ResponseEntity<?> response;

    @When("I request a list of all Proveedores")
    public void iRequestAListOfAllProveedores() {
        response = proveedorController.list();
    }

    @Then("I should receive a list of all Proveedores")
    public void iShouldReceiveAListOfAllProveedores() {
        Assert.assertTrue(response.getBody() instanceof List);
    }

    @Given("a Proveedor with id {string}, numeroDocumento {string}, nombre {string}, correo {string}, tipoProducto {string}")
    public void aProveedorWithInformation(String idProveedor, String numeroDocumento, String nombre, String correo, String tipoProducto) {
        proveedor = new Proveedor();
        proveedor.setIdProveedor(Integer.parseInt(idProveedor));
        proveedor.setNumeroDocumento(numeroDocumento);
        proveedor.setNombre(nombre);
        proveedor.setCorreo(correo);
        proveedor.setTipoProducto(tipoProducto);
    }

    @When("I create the Proveedor")
    public void iCreateTheProveedor() {
        ProveedorDto proveedorDto = new ProveedorDto();
        proveedorDto.setIdProveedor(proveedor.getIdProveedor());
        proveedorDto.setNumeroDocumento(proveedor.getNumeroDocumento());
        proveedorDto.setNombre(proveedor.getNombre());
        proveedorDto.setCorreo(proveedor.getCorreo());
        proveedorDto.setTipoProducto(proveedor.getTipoProducto());
        response = proveedorController.create(proveedorDto);
    }

    @Then("the Proveedor should be created successfully")
    public void theProveedorShouldBeCreatedSuccessfully() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Given("a Proveedor with id {int}")
    public void aProveedorWithId(int id) {
        proveedor = new Proveedor();
        proveedor.setIdProveedor(id);
        Mockito.when(proveedorService.existsById(id)).thenReturn(true);
    }

    @When("I delete the Proveedor")
    public void iDeleteTheProveedor() {
        response = proveedorController.delete(proveedor.getIdProveedor());
    }

    @Then("the Proveedor should be deleted successfully")
    public void theProveedorShouldBeDeletedSuccessfully() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}