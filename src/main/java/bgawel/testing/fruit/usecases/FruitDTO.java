package bgawel.testing.fruit.usecases;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FruitDTO {

    private Long id;
    private String name;

    public FruitDTO(final String name) {
        this.name = name;
    }

    public FruitDTO(final Long id, final String name) {
        this(name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                    .append(id)
                    .append(name)
                    .hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        FruitDTO rhs = (FruitDTO) obj;
        return new EqualsBuilder()
                    .append(id, rhs.id)
                    .append(name, rhs.name)
                    .isEquals();
    }
}
