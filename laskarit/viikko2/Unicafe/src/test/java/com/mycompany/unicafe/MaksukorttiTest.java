package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        int arvo = kortti.saldo();
        assertEquals(10, arvo);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(10);
        int arvo = kortti.saldo();
        assertEquals(20, arvo);
    }
    
    @Test
    public void rahanOttaminenToimii() {
        kortti.otaRahaa(5);
        int arvo = kortti.saldo();
        assertEquals(5, arvo);
    }
    
    @Test
    public void saldoEiVaheneJosRahaaEiOleRiittavasti() {
        kortti.otaRahaa(15);
        int arvo = kortti.saldo();
        assertEquals(10, arvo);
    }
    
    @Test
    public void palauttaakoMetodiOikeanArvonJosRahatRiitti() {
        assertEquals(true, kortti.otaRahaa(5));      
    }
    
    @Test
    public void palauttaakoMetodiOikeanArvonJosRahatEivatRiita() {
        assertEquals(false, kortti.otaRahaa(15));      
    }
    
    @Test
    public void toimiikoToString() {
        String syote = kortti.toString();
        assertEquals("saldo: 0.10", syote);
    }
}
