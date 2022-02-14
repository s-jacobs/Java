/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlettercount;

import javax.swing.JFrame;

/**
 *
 * @author Sara
 */
public class WordLetterCountDriver {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WordLetterCountGUI wlc = new WordLetterCountGUI();
        wlc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wlc.setSize(700,700);
        wlc.setVisible(true);
    }
}
