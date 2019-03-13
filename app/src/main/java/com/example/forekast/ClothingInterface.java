package com.example.forekast;

abstract class ClothingInterface extends ClothingProperties {
    ClothingProperties getProperties() {
        return new ClothingProperties(ID, owner, type, warmth, formality, comfort, preference,
                color, washing_machine, last_washing_state, washing_time, picture);
    }
}
