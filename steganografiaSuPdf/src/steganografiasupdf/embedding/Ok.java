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
import steganografiasupdf.SteganografiaSuPdf;

/**
 *
 * @author Rocchina
 */
public class Ok extends AbstractAction{

    private final SteganografiaSuPdf steganografia;
    private final Log log = LogFactory.getLog(Ok.class);
    
    public Ok(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Ok");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Legge il messaggio inserito dall'utente
        log.info("In Ok");
        String messaggio = this.steganografia.getFrame().getEmbedding().getMessaggio();
        log.info("Messaggio inserito: " + messaggio);
        if(messaggio.isEmpty()){
            String errore = "ATTENZIONE!\n"
                    + "Il campo messaggio non può essere nullo!";
            this.steganografia.getFrame().finestraErrore(errore);
        }
        //aggiungiamo il messaggio nella mappa degli oggetti
        this.steganografia.addObject(Costanti.messaggioInserito, messaggio);
        String messI = (String)steganografia.getObject(Costanti.messaggioInserito);
        log.info("Messaggio inserito nella mappa: " + messI);
    }
    
}