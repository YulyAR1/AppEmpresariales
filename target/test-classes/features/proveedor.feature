Feature: Proveedor functionality

  Scenario: List all Proveedores
    When I request a list of all Proveedores
    Then I should receive a list of all Proveedores

  Scenario Outline: Create a new Proveedor
    Given a Proveedor with id "<idProveedor>", numeroDocumento "<numeroDocumento>", nombre "<nombre>", correo "<correo>", tipoProducto "<tipoProducto>"
    When I create the Proveedor
    Then the Proveedor should be created successfully

    Examples:
      | idProveedor | numeroDocumento | nombre | correo           | tipoProducto |
      | 1           | 123456789       | John   | john@example.com | Electronics  |
      | 2           | 987654321       | Jane   | jane@example.com | Books        |


  Scenario: Delete a Proveedor
    Given a Proveedor with id 1
    When I delete the Proveedor
    Then the Proveedor should be deleted successfully