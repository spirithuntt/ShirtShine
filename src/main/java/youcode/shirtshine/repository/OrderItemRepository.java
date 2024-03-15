package youcode.shirtshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youcode.shirtshine.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
