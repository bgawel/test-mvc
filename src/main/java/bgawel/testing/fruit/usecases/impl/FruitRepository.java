package bgawel.testing.fruit.usecases.impl;

import org.springframework.data.jpa.repository.JpaRepository;

interface FruitRepository extends JpaRepository<Fruit, Long> {

    Fruit findByName(String name);
}
