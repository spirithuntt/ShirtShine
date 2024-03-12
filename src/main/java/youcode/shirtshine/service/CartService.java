package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Cart;
import youcode.shirtshine.dto.response.CartResponseDTO;

@Component
public interface CartService {

    CartResponseDTO addProductToCart(Long productId, int quantity);

    CartResponseDTO removeProductFromCart(Long productId);

    //total price with promotion : just iterate over productTotalPrice that we have in the cart and sum them
    Double calculateTotalPriceWithPromotion();

    CartResponseDTO updateProductQuantityInCart(Long productId, int newQuantity);


    Double calculateTotalPrice();

    Double calculateTotalPriceForOneProduct(Long productId);

    CartResponseDTO getCart();

    void clearCart();
}
