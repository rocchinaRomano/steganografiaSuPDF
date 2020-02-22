/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.extraction;

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
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class EsploraRisorseEstrazione extends AbstractAction{
    
    private final SteganografiaSuPDF steganografia;
    private final Log log = LogFactory.getLog(EsploraRisorseEstrazione.class);
    
    public EsploraRisorseEstrazione(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Apri");
        this.putValue(Action.SHORT_DESCRIPTION, "Seleziona il PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In EsploraRisorseEstrazione");
        int valore = this.steganografia.getSp().pdfSElezionato();
        log.info("Valore selezionato: " + valore);
        if(valore == JFileChooser.APPROVE_OPTION){
            log.info("Nome del file selezionato: " + 
                    this.steganografia.getSp().getNomePdf());
            String pathFile = this.steganografia.getSp().getPathPdf();
            log.info("Nome PDF selezionato: " + pathFile);
            this.steganografia.getSp().getsEstrazione().mostraPath(pathFile);
            this.steganografia.addObject(Costanti.stegoPdf, pathFile);
            log.info("PDF selezionato nella mappa: " + 
                    this.steganografia.getObject(Costanti.stegoPdf));
        }
    }
    
}
