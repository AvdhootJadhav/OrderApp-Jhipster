package com.avdhoot.orders.entity

import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(name = "product")
    var product: String = "",

    @Column(name = "quantity")
    var quantity: Int = 0,

    @Column(name = "cost")
    var cost: Int = 0
)
