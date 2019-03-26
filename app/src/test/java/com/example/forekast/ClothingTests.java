package com.example.forekast;

import com.example.forekast.clothing.*;

import org.junit.Assert;
import org.junit.Test;

public class ClothingTests {
    @Test
    public void testToplevel1() {
        Clothing piece = new Clothing();
        Assert.assertEquals("", piece.location);
    }

    @Test
    public void testToplevel2() {
        Clothing piece = new Clothing();
        Assert.assertEquals("", piece.type);
    }

    @Test
    public void testLoclevel1() {
        Clothing piece = new Torso();
        Assert.assertEquals("Torso", piece.location);
    }

    @Test
    public void testLoclevel2() {
        Clothing piece = new Torso();
        Assert.assertEquals("", piece.type);
    }

    @Test
    public void testBotlevel1() {
        Clothing piece = new Jeans();
        Assert.assertEquals("Legs", piece.location);
    }

    @Test
    public void testBotlevel2() {
        Clothing piece = new Jeans();
        Assert.assertEquals("Jeans", piece.type);
    }
}
