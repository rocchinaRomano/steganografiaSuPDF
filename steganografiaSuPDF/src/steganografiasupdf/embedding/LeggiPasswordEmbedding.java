/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.embedding;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.Costanti;
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class LeggiPasswordEmbedding extends AbstractAction{

    private final SteganografiaSuPDF steganografia;
    private final Log log = LogFactory.getLog(LeggiPasswordEmbedding.class);
    
    public LeggiPasswordEmbedding(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "OK");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci la password");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_K);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In LeggiPasswordEmbedding");
        String password = this.steganografia.getSp().getsEmbedding().getPassword();
        log.info("Password inserita: " + password);
        if(password.isEmpty()){
            String errore = "ATTENZIONE!\n"
                    + "Il campo password non può essere vuoto!";
            this.steganografia.getSp().finestraErrore(errore);
        }else{
            log.info("Aggiungere la password nella mappa degli obj");
            this.steganografia.addObject(Costanti.passwordEmbedding, password);
        }
        String pass = (String)this.steganografia.getObject(Costanti.passwordEmbedding);
        log.info("Password salvata nella mappa degli obj: " + pass);
    }
    
}