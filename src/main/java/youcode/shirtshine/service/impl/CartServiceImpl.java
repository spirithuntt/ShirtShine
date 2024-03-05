package youcode.shirtshine.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Cart;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.response.CartResponseDTO;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import youcode.shirtshine.repository.CartRepository;
import youcode.shirtshine.repository.ProductRepository;
import youcode.shirtshine.repository.UserRepository;
import youcode.shirtshine.service.CartService;
import youcode.shirtshine.service.UserService;
import youcode.shirtshine.domain.User;

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
                .products(cart.getProducts())
                .build();
    }
    private Cart convertToEntity(CartResponseDTO cartResponseDTO) {
        return Cart.builder()
                .products(cartResponseDTO.getProducts())
                .build();
    }

    @Override
    public CartResponseDTO addProductToCart(Long productId, int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProducts(new ArrayList<>());
            cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        cart.getProducts().add(product);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return convertToDto(cart);
    }

    @Override
    public CartResponseDTO removeProductFromCart(Long productId) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cart.getProducts().remove(product);
        cartRepository.save(cart);

        return convertToDto(cart);
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
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
}