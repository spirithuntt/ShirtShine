package youcode.shirtshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import youcode.shirtshine.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
