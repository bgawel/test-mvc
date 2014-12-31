package bgawel.testing.fruit.usecases.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bgawel.testing.fruit.usecases.FruitDTO;
import bgawel.testing.fruit.usecases.FruitUseCases;
import bgawel.testing.fruit.usecases.ValidationException;

@Service
@Transactional
class FruitUseCasesFacade implements FruitUseCases {

    @Autowired
    private FruitRepository fruitRepository;

    @Override
    public Iterable<FruitDTO> getAllFruits() {
        List<Fruit> dbFruits = fruitRepository.findAll();
        List<FruitDTO> dtoFruits = new ArrayList<>(dbFruits.size());
        for (Fruit dbFruit : dbFruits) {
            dtoFruits.add(new FruitDTO(dbFruit.getId(), dbFruit.getName()));
        }
        return dtoFruits;
    }

    @Override
    public FruitDTO getFruit(final Long id) {
        Fruit fruit = fruitRepository.getOne(id);
        return new FruitDTO(fruit.getId(), fruit.getName());
    }

    @Override
    public FruitDTO createFruit(final FruitDTO fruitDTO) {
        if (fruitRepository.findByName(fruitDTO.getName()) != null) {
            throw new ValidationException("name", "Fruit's name must be unique");
        }
        Fruit fruit = fruitRepository.save(new Fruit(fruitDTO.getName()));
        return new FruitDTO(fruit.getId(), fruit.getName());
    }
}
