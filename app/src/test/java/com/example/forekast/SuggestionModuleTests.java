package com.example.forekast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


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