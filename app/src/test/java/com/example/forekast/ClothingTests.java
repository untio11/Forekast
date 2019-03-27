package com.example.forekast;

import com.example.forekast.clothing.*;

import static org.junit.Assert.*;
import org.junit.Test;

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

    @Test
    public void testBotlevel1() {
        Clothing piece = new Jeans();
        assertEquals("Legs", piece.location);
    }

    @Test
    public void testBotlevel2() {
        Clothing piece = new Jeans();
        assertEquals("Jeans", piece.type);
    }
}
