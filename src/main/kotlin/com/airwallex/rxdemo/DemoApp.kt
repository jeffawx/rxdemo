package com.airwallex.rxdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import java.util.UUID

@SpringBootApplication
class DemoApp {

    @Bean
    fun routes(customerRepo: CustomerRepository, orderRepo: OrderRepository) = router {
        "/customers".nest {
            GET("/searchByName") { req ->
                ok().body(customerRepo.findByName(req.queryParam("name").get().let { "%$it%" }))
            }

            // Get all customers
            GET("/") { ok().body(customerRepo.findAll()) }

            // Get customer by id
            GET("/{id}") { ok().body(customerRepo.findById(UUID.fromString(it.pathVariable("id")))) }

            // Create new customer
            POST("/") { accepted().body(it.bodyToMono(Customer::class.java).flatMap(customerRepo::save)) }

            // Update existing customer
            PUT("/{id}") { ok().body(it.bodyToMono(Customer::class.java).flatMap(customerRepo::save)) }

            // Delete customer
            DELETE("/{id}") { ok().body(customerRepo.deleteById(UUID.fromString(it.pathVariable("id")))) }

            POST("/search") { ok().body(it.bodyToMono(CustomerSearch::class.java).flatMapMany(customerRepo::search)) }
        }

        "/orders".nest {
            GET("/") { ok().body(orderRepo.findAll()) }

            GET("/searchByCustomer") { ok().body(orderRepo.findByCustomer(UUID.fromString(it.queryParam("id").get()))) }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApp>(*args)
}
