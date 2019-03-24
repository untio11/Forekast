package com.example.forekast;

import com.example.forekast.homescreen.SuggestionModule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SuggestionModuleTests {

    SuggestionModule sut;

    @Before
    public void init() {
        sut = new SuggestionModule();
    }

    @Test
    public void testi() {
        Assert.assertEquals(true, true);
    }
}