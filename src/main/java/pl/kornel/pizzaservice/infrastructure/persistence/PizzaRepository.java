package pl.kornel.pizzaservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
    PizzaEntity findByName(String name);

    boolean existsByName(String name);

    @Transactional
    void deleteByName(String name);
}
