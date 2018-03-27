
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate(); 
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void kassaLuodaanOikein() {
        int saldo = kassa.kassassaRahaa();
        int myytyjaLounaita = kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty();
        assertEquals(100000, saldo);
        assertEquals(0, myytyjaLounaita);
    }
    
    @Test
    public void  kateisostoRiittavallaMaksulla() {
        
        assertEquals(100, kassa.syoEdullisesti(340));
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        kassa = new Kassapaate();
        
        assertEquals(100, kassa.syoMaukkaasti(500));
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        
    }
        
    @Test
    public void  kateisostoKunMaksuEiRiita() {
        
        assertEquals(100, kassa.syoEdullisesti(100));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        
        kassa = new Kassapaate();
        
        assertEquals(100, kassa.syoMaukkaasti(100));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        
    }
    
    @Test
    public void  korttiostoRiittavallaMaksulla() {
        
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(760, kortti.saldo());
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
        
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(600, kortti.saldo());
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        
    }
    
    @Test
    public void  korttiostoKunMaksuEiRiita() {
        
        kortti.otaRahaa(900);
        
        assertEquals(false, kassa.syoEdullisesti(kortti));
        assertEquals(100, kortti.saldo());
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        
        kassa = new Kassapaate();
        
        assertEquals(false, kassa.syoMaukkaasti(kortti));
        assertEquals(100, kortti.saldo());
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        
    }
    
    @Test
    public void kortilleLatausToimii() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, kassa.kassassaRahaa());
        
        // miinus-lataus
        
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, kassa.kassassaRahaa());
    }
    
    
}
