package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youcode.shirtshine.domain.*;
import youcode.shirtshine.domain.enums.StatusEnum;
import youcode.shirtshine.dto.request.OrderRequestDTO;
import youcode.shirtshine.dto.response.OrderResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import youcode.shirtshine.repository.CartRepository;
import youcode.shirtshine.repository.OrderItemRepository;
import youcode.shirtshine.repository.OrderRepository;
import youcode.shirtshine.service.OrderService;
import youcode.shirtshine.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    private OrderResponseDTO convertToDto(Order order) {
        return OrderResponseDTO.builder()
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phone(order.getPhone())
                .zipCode(order.getZipCode())
                .address(order.getAddress())
                .status(String.valueOf(order.getStatus()))
                .build();
    }

    private Order convertToEntity(OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .fullName(orderRequestDTO.getFullName())
                .email(orderRequestDTO.getEmail())
                .phone(orderRequestDTO.getPhone())
                .zipCode(orderRequestDTO.getZipCode())
                .address(orderRequestDTO.getAddress())
                .orderItems(new ArrayList<>())
                .build();
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return orders.stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching orders");
        }
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            return convertToDto(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching order with id: " + id);
        }
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        try {
            User user = userService.getCurrentUser();
            Order order = convertToEntity(orderRequestDTO);
            order.setUser(user);
            Order savedOrder = orderRepository.save(order);
            return convertToDto(savedOrder);
        } catch (Exception e) {
            throw new OperationException("Error occurred while creating order");
        }
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderResponseDTO) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            order.setFullName(orderResponseDTO.getFullName());
            order.setEmail(orderResponseDTO.getEmail());
            order.setPhone(orderResponseDTO.getPhone());
            order.setZipCode(orderResponseDTO.getZipCode());
            order.setAddress(orderResponseDTO.getAddress());
            Order updatedOrder = orderRepository.save(order);
            return convertToDto(updatedOrder);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while updating order with id: " + id);
        }
    }
    @Override
    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            order.setStatus(StatusEnum.valueOf(status));
            Order updatedOrder = orderRepository.save(order);
            return convertToDto(updatedOrder);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while updating order status with id: " + id);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        try {
            if (!orderRepository.existsById(id)) {
                throw new ResourceNotFoundException("Order not found with id: " + id);
            }
            orderRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while deleting order with id: " + id);
        }
    }


    @Transactional
    @Override
    public OrderResponseDTO completeCheckout(OrderRequestDTO orderRequestDTO) {
        try {
            User user = userService.getCurrentUser();
            Cart cart = user.getCart();

            if (cart == null || cart.getCartItems().isEmpty()) {
                throw new OperationException("Cart is empty");
            }

            Order order = convertToEntity(orderRequestDTO);
            order.setUser(user);
            order.setStatus(StatusEnum.PENDING);

            Order savedOrder = orderRepository.save(order);

            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setOrder(savedOrder);

                double totalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity() * (1 - cartItem.getProduct().getPromotion() / 100.0);
                orderItem.setTotal(totalPrice);

                orderItem = orderItemRepository.save(orderItem);

                savedOrder.getOrderItems().add(orderItem);
            }

            savedOrder = orderRepository.save(savedOrder);

            cart.getCartItems().clear();
            cartRepository.save(cart);

            return convertToDto(savedOrder);
        } catch (Exception e) {
            throw new OperationException("Error occurred while completing checkout");
        }
    }
}
