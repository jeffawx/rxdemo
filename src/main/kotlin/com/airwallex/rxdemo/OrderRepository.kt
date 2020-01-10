package com.airwallex.rxdemo

import com.airwallex.common.rx.db.BaseEntityRepository
import com.airwallex.common.rx.db.fromTable
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrderRepository(databaseClient: DatabaseClient) :
    BaseEntityRepository<Order>(databaseClient, Order::class) {

    fun findByCustomer(customerId: UUID) =
        databaseClient.fromTable(tableName, mapper) {
            +("data->>'customerId'" eq customerId.toString())
        }.results
}
