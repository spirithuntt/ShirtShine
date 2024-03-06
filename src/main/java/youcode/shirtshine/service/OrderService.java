package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.dto.request.OrderRequestDTO;
import youcode.shirtshine.dto.response.OrderResponseDTO;

import java.util.List;

@Component
public interface OrderService {
    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id);


    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrderStatus(Long id, String status);

    void deleteOrder(Long id);
}
