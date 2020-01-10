package com.airwallex.rxdemo

import com.airwallex.common.rx.db.RowMapper
import com.airwallex.common.rx.db.fromTable
import com.airwallex.common.rx.db.getValue
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.UUID

interface CustomerRepository : ReactiveCrudRepository<Customer, UUID>, CustomerOperations {

    @Query("SELECT * FROM customer WHERE name ILIKE :name")
    fun findByName(name: String): Flux<Customer>
}

interface CustomerOperations {
    fun search(search: CustomerSearch): Flux<Customer>
}

internal class CustomerRepositoryImpl(private val databaseClient: DatabaseClient) : CustomerOperations {

    private val mapper: RowMapper<Customer> = {
        Customer(
            id = it.getValue("id"),
            name = it.getValue("name"),
            email = it.getValue("email"),
            address = it.getValue("address"),
            createTime = it.getValue("create_time"),
            lastUpdate = it.getValue("last_update")
        )
    }

    override fun search(search: CustomerSearch) =
        databaseClient.fromTable("customer", mapper) {
            +("name" ilike search.name and ("email" eq search.email or ("address" eq search.address)))
        }.results

//    override fun search(search: CustomerSearch) =
//        databaseClient.runQuery(query {
//            tableName = "customer"
//
//            -predicate("name ILIKE :name", search.name)
//            search.name?.let { +param("name" to it.orWildcard()) }
//
//            +predicate("email = :email", search.email)
//            search.email?.let { +param("email" to it) }
//
//            +predicate("address = :address", search.address)
//            search.address?.let { +param("address" to it) }
//
//            fuzzySearch = true
//        }, mapper).results
}
