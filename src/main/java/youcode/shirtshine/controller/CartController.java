package youcode.shirtshine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.domain.Cart;
import youcode.shirtshine.domain.User;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.CartResponseDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.service.CartService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity addProductToCart(@PathVariable Long productId, @PathVariable int quantity) {
        try {
            CartResponseDTO cart = cartService.addProductToCart(productId, quantity);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(@PathVariable Long productId) {
        try {
            CartResponseDTO cart = cartService.removeProductFromCart(productId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{productId}/{quantity}")
    public ResponseEntity<CartResponseDTO> updateProductQuantityInCart(@PathVariable Long productId, @PathVariable int quantity) {
        try {
            CartResponseDTO cart = cartService.updateProductQuantityInCart(productId, quantity);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        try {
            CartResponseDTO cart = cartService.getCart();
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        try {
            cartService.clearCart();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Double> calculateTotalPrice() {
        try {
            Double totalPrice = cartService.calculateTotalPrice();
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}