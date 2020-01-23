/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.estrazione;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.Costanti;
import steganografiasupdf.SteganografiaSuPdf;

/**
 *
 * @author Rocchina
 */
public class ApriEstrazione extends AbstractAction{
    
    private final Log log = LogFactory.getLog(ApriEstrazione.class);
    private final SteganografiaSuPdf steganografia;
    
    public ApriEstrazione(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Apri");
        this.putValue(Action.SHORT_DESCRIPTION, "Seleziona il PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_P);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In Apri Estrazione");  
        int valore = this.steganografia.getFrame().pdfSelezionato();
        if(valore == JFileChooser.APPROVE_OPTION){
            log.info("Nome del PDF selezionato: "
                    + this.steganografia.getFrame().getNomeFile());
            String pathFile = this.steganografia.getFrame().getPathFile();
            log.info("Percorso del file selezionato: " +  pathFile);
            this.steganografia.getFrame().getEstrazione().mostraPath(pathFile);
            this.steganografia.addObject(Costanti.pdfStego, pathFile);
            log.info("pdfStego nella mappa: " + this.steganografia.getObject(Costanti.pdfStego));
        }
    }
   
    
}