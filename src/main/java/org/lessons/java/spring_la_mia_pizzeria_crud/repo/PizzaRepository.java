package org.lessons.java.spring_la_mia_pizzeria_crud.repo;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    
}
