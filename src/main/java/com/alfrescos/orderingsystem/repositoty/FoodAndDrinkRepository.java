package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
@Repository
public interface FoodAndDrinkRepository extends JpaRepository<FoodAndDrink, Long> {
    FoodAndDrink findById(Long id);

    @Query(value = "SELECT * FROM food_and_drink as fad WHERE fad.name like %:name% OR fad.name = :name", nativeQuery = true)
    List<FoodAndDrink> findByName(@Param("name") String name);
}
