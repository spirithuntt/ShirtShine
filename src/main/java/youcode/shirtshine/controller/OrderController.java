package youcode.shirtshine.controller;

import ch.qos.logback.core.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.dto.request.OrderRequestDTO;
import youcode.shirtshine.dto.response.OrderResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        try {
            List<OrderResponseDTO> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        try {
            OrderResponseDTO order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO order) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.createOrder(order);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO order) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.updateOrder(id, order);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.updateOrderStatus(id, status);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> completeCheckout(@RequestBody OrderRequestDTO order) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.completeCheckout(order);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}