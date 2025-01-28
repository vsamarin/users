package ru.otus.homework.api.orders.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.api.orders.controller.examples.OrderExamples;
import ru.otus.homework.api.orders.dto.Order;
import ru.otus.homework.api.orders.mapper.OrderMapper;
import ru.otus.homework.api.orders.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Operations about user order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(
            operationId = "order_create",
            summary = "Create order",
            description = "Create order"
    )
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created user object",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Order.class),
                            examples = @ExampleObject(value = OrderExamples.CREATE)
                    )
            )
    })
    public Order create(
            @Schema(example = OrderExamples.CREATE)
            @RequestBody Order order
    ) {
        return orderMapper.map(orderService.create(orderMapper.map(order)));
    }

    @Operation(
            operationId = "order_find",
            summary = "Find order",
            description = "Find order"
    )
    @GetMapping(
            path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user order",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Order.class),
                            examples = @ExampleObject(value = OrderExamples.GET)
                    )
            )
    })
    public Order find(
            @Parameter(description = "ID of order", example = "1")
            @PathVariable("id") Long id
    ) {
        return orderMapper.map(orderService.findById(id));
    }

}
