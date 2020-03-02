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
public class LeggiMessaggio extends AbstractAction{

    private final SteganografiaSuPDF steganografia;
    
    private final Log logger = LogFactory.getLog(LeggiMessaggio.class);
    
    public LeggiMessaggio(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "OK");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio da inseriri nel PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("In LeggiMessaggio");
        String mess = this.steganografia.getSp().getsEmbedding().getMessaggio();
        logger.info("Messaggio inserito: " + mess);
        if(mess.isEmpty()){
            String errore = "ATTENZIONE!\n"
                    + "Il campo messaggio non può essere vuoto!";
            this.steganografia.getSp().finestraErrore(errore);
        }else{
            //aggiungere messaggio nella mappa
            logger.info("Posso aggiungere il messaggio nella mappa degli obj");
            this.steganografia.addObject(Costanti.messaggioInserito, mess);
        }
        String messInserito = (String)this.steganografia.getObject(Costanti.messaggioInserito);
        logger.info("Messaggio inserito nella mappa degli obj:" + messInserito);
    }
    
}
