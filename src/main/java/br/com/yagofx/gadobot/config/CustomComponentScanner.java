package br.com.yagofx.gadobot.config;

import br.com.yagofx.gadobot.annotation.InheritedComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = "br.com.yagofx.gadobot.commands",
        includeFilters = @ComponentScan.Filter(
                InheritedComponent.class
        )
)
public class CustomComponentScanner {}
