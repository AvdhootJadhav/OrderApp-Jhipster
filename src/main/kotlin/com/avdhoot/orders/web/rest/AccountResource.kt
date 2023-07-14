package com.avdhoot.orders.web.rest

import com.avdhoot.orders.entity.Order
import com.avdhoot.orders.model.ResponseModel
import com.avdhoot.orders.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class AccountResource {

    @Autowired
    private lateinit var orderService: OrderService

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/new")
    fun saveOrder(@RequestBody order: Order): ResponseModel {
        logger.debug(">>>>>\n${order.toString()}")
        val response = orderService.saveOrder(order)
        logger.debug(response.toString())
        return response
    }

    @GetMapping("/all")
    fun fetchOrders(): List<Order> {
        return orderService.fetchOrders()
    }

    @DeleteMapping("/delete/{id}")
    fun deleteOrder(@PathVariable("id") id: Int): ResponseModel {
        return orderService.deleteOrder(id)
    }

    @PutMapping("/update")
    fun updateOrder(@RequestBody order: Order): ResponseModel {
        return orderService.updateOrder(order)
    }
}
