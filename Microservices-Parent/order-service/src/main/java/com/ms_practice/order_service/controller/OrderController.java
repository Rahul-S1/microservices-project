package com.ms_practice.order_service.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ms_practice.order_service.dto.OrderRequest;
import com.ms_practice.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController
{

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {
        System.out.println(orderRequest);
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

//    @GetMapping
//    public Order getOrder()
//    {
//        return null;
//    }
}
