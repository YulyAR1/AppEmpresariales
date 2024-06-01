package com.ventas.ventas;

import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = com.ventas.ventas.VentasApplication.class)
public class CucumberSpringConfiguration {

}