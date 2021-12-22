package pl.kornel.pizzaservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
    PizzaEntity findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
