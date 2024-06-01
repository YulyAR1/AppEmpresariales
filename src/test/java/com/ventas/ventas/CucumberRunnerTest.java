package com.ventas.ventas;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.ventas.ventas",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
@ActiveProfiles("test")
public class CucumberRunnerTest {

}
