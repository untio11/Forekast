package com.example.forekast.Suggestion;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.Clothing.Torso;
import com.example.forekast.ExternalData.Weather;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionModuleTest extends TestCase {

    private static final Outfit FAKE_OUTFIT = new Outfit();

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


    @Mock List<Clothing> mockTorsoList = mock(List.class);
    @Mock Weather mockWeather = mock(Weather.class);
    @Mock ClothingCriteria mockCriteria = mock(ClothingCriteria.class);

    @Before
    public void setFakeWardrobe() {
        Clothing clothing = new Clothing();
        switch (clothing.type) {
            case "T-Shirt":
                for (int i = 0; i < 5; i++){
                    clothing
                    tshirt.add()
                }
        }
    }

    @Test
    public void getAccessories() {
    }

    @Test
    public void setOutfit() {

        SuggestionModule sugg = new SuggestionModule();
        Outfit newOutfit = sugg.setOutfit();
        assertEquals(newOutfit, FAKE_OUTFIT);
    }

    // Testing to see if there are all the possible torso options
    @Test
    public void testTorsoOptions(){

    }

}