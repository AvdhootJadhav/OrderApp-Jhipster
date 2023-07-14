package com.avdhoot.orders.service

import com.avdhoot.orders.entity.Order
import com.avdhoot.orders.model.ResponseModel
import com.avdhoot.orders.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    fun saveOrder(order: Order): ResponseModel {
        orderRepository.save(order)
        return ResponseModel(true, "Order saved in DB")
    }

    fun fetchOrders(): List<Order> {
        return orderRepository.findAll().toList()
    }

    fun deleteOrder(id: Int): ResponseModel {
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id)
        else
            return ResponseModel(false, "Order not found")

        return ResponseModel(true, "Order deleted")
    }

    fun updateOrder(order: Order): ResponseModel {
        if (orderRepository.existsById(order.id))
            orderRepository.save(order)
        else
            return ResponseModel(false, "Order not found")

        return ResponseModel(true, "Order updated")
    }
}
