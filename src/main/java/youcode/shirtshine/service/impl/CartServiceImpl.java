package youcode.shirtshine.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Cart;
import youcode.shirtshine.domain.CartItem;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.response.CartResponseDTO;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import youcode.shirtshine.repository.CartRepository;
import youcode.shirtshine.repository.ProductRepository;
import youcode.shirtshine.repository.UserRepository;
import youcode.shirtshine.service.CartService;
import youcode.shirtshine.service.UserService;
import youcode.shirtshine.domain.User;
import youcode.shirtshine.dto.response.CartItemResponseDTO;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;


    private CartResponseDTO convertToDto(Cart cart) {
        return CartResponseDTO.builder()
                .cartItems(cart.getCartItems().stream()
                        .map(cartItem -> CartItemResponseDTO.builder()
                                .productId(cartItem.getProduct().getId())
                                .productName(cartItem.getProduct().getName())
                                .quantity(cartItem.getQuantity())
                                .image(cartItem.getProduct().getImage())
                                .price(cartItem.getProduct().getPrice())
                                .build())
                        .toList())
                .build();
    }
    private Cart convertToEntity(CartResponseDTO cartResponseDTO) {
        return Cart.builder()
                .cartItems(cartResponseDTO.getCartItems().stream()
                        .map(cartItemResponseDTO -> CartItem.builder()
                                .product(Product.builder().id(cartItemResponseDTO.getProductId()).build())
                                .quantity(cartItemResponseDTO.getQuantity())
                                .build())
                        .toList())
                .build();
    }
    @Override
    public CartResponseDTO addProductToCart(Long productId, int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("The quantity of the product being added to the cart exceeds the product's stock");
        }
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        } else {
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new IllegalArgumentException("The total quantity of the product in the cart exceeds the product's stock");
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        return convertToDto(cart);
    }

    @Override
    public CartResponseDTO removeProductFromCart(Long productId) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cart.getCartItems().remove(cartItem);
            }
        }
        cartRepository.save(cart);
        return convertToDto(cart);
    }

    @Override
    public CartResponseDTO updateProductQuantityInCart(Long productId, int newQuantity) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("The quantity of the product being updated exceeds the product's stock");
            }
            cartItem.setQuantity(newQuantity);
        }
        cartRepository.save(cart);
        return convertToDto(cart);
    }


    @Override
    public Double calculateTotalPrice() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            return 0.0;
        }
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }



    @Override
    public CartResponseDTO getCart() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            return null;
        }
        return convertToDto(cart);
    }

    @Override
    public void clearCart() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}