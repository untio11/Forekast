package com.example.forekast;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.Torso;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClothingTests {
    @Test
    public void testToplevel1() {
        Clothing piece = new Clothing();
        assertEquals("", piece.location);
    }

    @Test
    public void testToplevel2() {
        Clothing piece = new Clothing();
        assertEquals("", piece.type);
    }

    @Test
    public void testLoclevel1() {
        Clothing piece = new Torso();
        assertEquals("Torso", piece.location);
    }

    @Test
    public void testLoclevel2() {
        Clothing piece = new Torso();
        assertEquals("", piece.type);
    }
    /*
    @Test
    public void testBotlevel1() {
        Clothing piece = new Jeans();
        assertEquals("Legs", piece.location);
    }

    @Test
    public void testBotlevel2() {
        Clothing piece = new Jeans();
        assertEquals("Jeans", piece.type);
    }*/
}
