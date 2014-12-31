package bgawel.testing.fruit.usecases.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("!int-test")
class BootstrapUseCaseData implements InitializingBean {

    @Autowired
    private FruitRepository fruitRepository;

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        initializeDevelopmentData();
    }

    private void initializeDevelopmentData() {
        fruitRepository.save(new Fruit("banana"));
        fruitRepository.save(new Fruit("orange"));
    }
}
