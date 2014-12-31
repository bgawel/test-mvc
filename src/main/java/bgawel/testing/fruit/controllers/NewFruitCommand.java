package bgawel.testing.fruit.controllers;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

class NewFruitCommand {

    @NotEmpty
    @Size(min=1, max=24)
    private String nameWithPadding;

    public NewFruitCommand() {
    }

    public NewFruitCommand(final String nameWithPadding) {
        this.nameWithPadding = nameWithPadding;
    }

    @NotEmpty
    @Size(min=1, max=16)
    public String getFruitName() {
        return nameWithPadding.split("-")[0];
    }

    public void setNameWithPadding(final String nameWithPadding) {
        this.nameWithPadding = nameWithPadding;
    }

    public String getNameWithPadding() {
        return nameWithPadding;
    }
}
