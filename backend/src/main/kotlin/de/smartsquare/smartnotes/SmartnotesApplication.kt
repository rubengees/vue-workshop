package de.smartsquare.smartnotes

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
class SmartnotesApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(arrayOf(SmartnotesApplication::class.java), args)
        }
    }

    @Bean
    fun corsConfigurer() = object : WebFluxConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .exposedHeaders("location")
                .allowedHeaders("*")
                .allowedOrigins("*")
        }
    }
}
