Feature: Producto functionality

  Scenario Outline: Create a new Producto
    Given a Producto with id "<id>", ean "<ean>", cantidad "<cantidad>", nombre "<nombre>", precio "<precio>", proveedor "<proveedor>"
    When I create the Producto
    Then the Producto should be created successfully

    Examples:
      | id | ean       | cantidad | nombre   | precio | proveedor |
      | 1  | 123456789 | 10       | Product1 | 100.0  | 1         |
      | 2  | 987654321 | 20       | Product2 | 200.0  | 2         |

  Scenario: List all Productos
    When I request a list of all Productos
    Then I should receive a list of all Productos


  Scenario Outline: Update a Producto
    Given a Producto with id "<id>", ean "<ean>", cantidad "<cantidad>", nombre "<nombre>", precio "<precio>", proveedor "<proveedor>"
    When I update the Producto
    Then the Producto should be updated successfully

    Examples:
      | id | ean       | cantidad | nombre   | precio | proveedor |
      | 1  | 123456789 | 10       | Product1 | 100.0  | 1         |
      | 2  | 987654321 | 20       | Product2 | 200.0  | 2         |

  Scenario: Get a Producto by id
    Given a Producto with id "1"
    When I request the Producto by id
    Then I should receive the Producto

  Scenario: Get a Producto by nombre
    Given a Producto with nombre "Product1"
    When I request the Producto by nombre
    Then I should receive the Producto

  Scenario: Delete a Producto by id
    Given a Producto with id "1"
    When I delete the Producto by id
    Then the Producto should be deleted successfully