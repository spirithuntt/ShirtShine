package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Cart;
import youcode.shirtshine.dto.response.CartResponseDTO;

@Component
public interface CartService {

    CartResponseDTO addProductToCart(Long productId, int quantity);

    CartResponseDTO removeProductFromCart(Long productId);

    CartResponseDTO updateProductQuantityInCart(Long productId, int newQuantity);

    Double calculateTotalPrice();

    CartResponseDTO getCart();

    void clearCart();
}
