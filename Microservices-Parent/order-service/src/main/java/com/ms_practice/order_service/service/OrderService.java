package com.ms_practice.order_service.service;

import com.ms_practice.order_service.dto.InventoryResponse;
import com.ms_practice.order_service.dto.OrderLineItemsDto;
import com.ms_practice.order_service.dto.OrderRequest;
import com.ms_practice.order_service.model.Order;
import com.ms_practice.order_service.model.OrderLineItems;
import com.ms_practice.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }






    public void placeOrder(OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getOrderLineItemsDtoList() == null) {
            throw new IllegalArgumentException("Order request or order line items cannot be null");
        }

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skucodes = order.getOrderLineItemsList().stream()
                                .map(OrderLineItems::getSkuCode)
                                .toList();


       InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        UriBuilder->UriBuilder.queryParam("skucode",skucodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        Arrays.stream(inventoryResponsesArray).allMatch()

       if(result)
       {
           orderRepository.save(order);
       }

        else {
            throw new IllegalArgumentException("Product is not in the stock please try again letter");
       }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;

    }



}


