/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

/**
 *
 * @author Carlos Lozano, Marc Llobera
 */
public class Baralla {

    // ATRIBUTS
    public static final int MAXCARTESPERPAL = 13;
    public static final int MAXCARTES = MAXCARTESPERPAL * 4; // 52 Nombre màxim de cartes 13 cartes * 4 Pals

    private final Carta[] b;
    private int n; // Nombre de cartes de la baralla

    // CONSTRUCTOR
    public Baralla() {
        b = new Carta[MAXCARTES];
        n = MAXCARTES;
        int index = 0;
        for (Pal pal : Pal.values()) {
            for (int i = 0; i < MAXCARTESPERPAL; i++) {
                b[index] = new Carta(pal, i + 1);
                index++;
            }
        }
    }

    public void mescla() {// Fisher-Yates
        Random rnd = new Random();
        for (int i = MAXCARTES - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            Carta c = b[pos];
            b[pos] = b[i];
            b[i] = c;
        }
    }

    public Carta agafaCarta() {
        if (n == 0) {
            return null;
        }
        Carta c = b[n - 1];
        n--;
        return c;
    }

    public int numCartes() {
        return n;
    }

    public Carta[] getB() {
        return b;
    }
}
