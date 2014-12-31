package bgawel.testing.fruit.usecases;

public interface FruitUseCases {

    Iterable<FruitDTO> getAllFruits();

    FruitDTO getFruit(Long id);

    FruitDTO createFruit(FruitDTO fruitDTO);
}
