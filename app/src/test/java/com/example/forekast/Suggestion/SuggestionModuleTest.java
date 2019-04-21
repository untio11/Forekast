package com.example.forekast.Suggestion;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.ExternalData.Repository;
import com.example.forekast.ExternalData.Weather;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionModuleTest extends TestCase {

    /** Clothing items for fake wardrobe */
    // Tops
    private List <Clothing> tshirt = new ArrayList<>();
    private List <Clothing> shirt = new ArrayList<>();
    private List <Clothing> sweater = new ArrayList<>();
    private List <Clothing> dress = new ArrayList<>();
    private List <Clothing> jacket = new ArrayList<>();
    private List <Clothing> tanktop = new ArrayList<>();

    private List <Clothing> jeans = new ArrayList<>();
    private List <Clothing> shorts = new ArrayList<>();
    private List <Clothing> skirt = new ArrayList<>();
    private List <Clothing> trousers = new ArrayList<>();
    private List <Clothing> sweatpants = new ArrayList<>();

    private List <Clothing> sandals = new ArrayList<>();
    private List <Clothing> sneakers = new ArrayList<>();
    private List <Clothing> formal = new ArrayList<>();
    private List <Clothing> boots = new ArrayList<>();


    @Mock Weather mockWeather = mock(Weather.class);
    @Mock ClothingCriteria mockCriteria = mock(ClothingCriteria.class);

    private static final String FAKE_OUTFIT = "";

    @Before
    public void setFakeWardrobe() {
        Clothing clothing = new Clothing();
        switch (clothing.type) {
            case "T-Shirt":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5;
                    clothing.owner = "Tester";
                    clothing.overwearable = false;
                    clothing.underwearable = true;
                    tshirt.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Shirt":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 - i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 5 + i;
                    clothing.owner = "Tester";
                    clothing.overwearable = true;
                    clothing.underwearable = true;
                    shirt.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Sweater":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 3 + i;
                    clothing.owner = "Tester";
                    clothing.overwearable = true;
                    clothing.underwearable = false;
                    sweater.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Dress":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5 + i;
                    clothing.owner = "Tester";
                    clothing.overwearable = false;
                    clothing.underwearable = true;
                    dress.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Jacket":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5;
                    clothing.owner = "Tester";
                    clothing.overwearable = false;
                    clothing.underwearable = true;
                    jacket.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Tanktop":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5;
                    clothing.owner = "Tester";
                    clothing.overwearable = false;
                    clothing.underwearable = true;
                    tanktop.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Jeans":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 - i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 3 + i;
                    clothing.owner = "Tester";
                    jeans.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Shorts":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5 - i;
                    clothing.owner = "Tester";
                    shorts.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Skirt":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 4 + i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 3 + i;
                    clothing.owner = "Tester";
                    skirt.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Trousers":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 8 - i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 5 + i;
                    clothing.owner = "Tester";
                    trousers.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Sweatpants":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 5 - i;
                    clothing.owner = "Tester";
                    sweatpants.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Sandals":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 - i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5 - i;
                    clothing.owner = "Tester";
                    sandals.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Sneakers":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 8 - i;
                    clothing.formality = 5 - i;
                    clothing.owner = "Tester";
                    sneakers.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Formal":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 - i;
                    clothing.warmth = 5 - i;
                    clothing.formality = 5 + i;
                    clothing.owner = "Tester";
                    formal.add(clothing);
                    Repository.addClothing(clothing);
                }
            case "Boots":
                for (int i = 0; i < 5; i++){
                    clothing.comfort = 5 + i;
                    clothing.warmth = 5 + i;
                    clothing.formality = 5 + i;
                    clothing.owner = "Tester";
                    boots.add(clothing);
                    Repository.addClothing(clothing);
                }
        }


    }

    @Test
    public void getAccessories() {

    }

    @Test
    public void setOutfit() {
        SuggestionModule sugg = new SuggestionModule();
        sugg.setCurrentCriteria(mockCriteria, mockWeather);
        Outfit newOutfit = sugg.setOutfit();
        assertEquals(newOutfit.toString(), FAKE_OUTFIT);
    }

    // Testing to see if there are all the possible torso options
    @Test
    public void testTorsoOptions(){

    }

}