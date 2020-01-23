/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.estrazione;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class EstraiMessaggio extends AbstractAction{
    
    private final Log log = LogFactory.getLog(EstraiMessaggio.class);
    private final SteganografiaSuPdf steganografia;
    private String stego;
    //Lista sottosequenze estratte:
    private List listaSottoseq = new ArrayList();
    
    public EstraiMessaggio(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Estrai Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Estrai il messaggio dal PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl M"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In EstraiMessaggio");
        this.listaSottoseq.removeAll(this.listaSottoseq);
        this.stego = (String)this.steganografia.getObject(Costanti.pdfStego);
        if(this.stego == null){
            String errore = "ATTENZIONE!!!\n"
                    + "Non è stato selezionato nessun file PDF!";
            this.steganografia.getFrame().finestraErrore(errore);
        }
        estraiMessaggio();
        /*  Per ricomporre il messEstratto, leggiamo la listaSottoseEstratte
            al contrario, perchè la procedura che mi permette di leggere le 
            immagini dal PDF, parte dall'ultima immagine alla prima.
        */ 
        String messEstratto = "";
        for(int i = listaSottoseq.size()-1; i >= 0; i--){
            String s = (String)listaSottoseq.get(i);
            log.info("Sottosequenza estratta: " + s);
            messEstratto = messEstratto + s;
        }
        log.info("Messaggio binario estratto: " + messEstratto);
        //procedura per convertire la stringa binaria nel messaggio:
        if(listaSottoseq.isEmpty()){
            //nessun messaggio estratto
            String info = "Il PDF non contiene alcuna informazione steganografica!";
            this.steganografia.getFrame().finestraInfo(info);
        }else{
            //convertiamo il messaggio
            String mess = convertiMessaggio(messEstratto);
            log.info("Messaggio estratto: " + mess);
            this.steganografia.getFrame().getEstrazione().setTesto(mess);
        }
    }

    private void estraiMessaggio() {
        try {
            PdfReader reader = new PdfReader(this.stego);
            log.info("Pagine PDF selezionato: " + reader.getNumberOfPages());
            int j = 1;
            for(int i = 1; i <= reader.getXrefSize(); i++){
                PdfObject pdfo = reader.getPdfObject(i);
                if(pdfo != null && pdfo.isStream()){
                    PRStream stream = (PRStream)pdfo;
                    PdfObject type = stream.get(PdfName.SUBTYPE);
                    log.info("Type: " + type);
                    if(type != null && type.toString().equals(PdfName.IMAGE.toString())){
                        PdfImageObject pio = new PdfImageObject(stream);
                        BufferedImage im = pio.getBufferedImage();
                        //Faccio un filtro sulle immagini:
                        //Inizializzare la lista delle sottosequenze:
                        if(im.getHeight()== 4 && im.getWidth() == 4){
                            log.info("Sottosequenza 00");
                            listaSottoseq.add("00");
                        }else if(im.getHeight() == 8 && im.getWidth() == 8){
                            log.info("Sottosequenza 01");
                            listaSottoseq.add("01");
                        }else if(im.getHeight() == 16 && im.getWidth() == 16){
                            log.info("Sottosequenza 10");
                            listaSottoseq.add("10");
                        }else if(im.getHeight() == 32 && im.getWidth() == 32){
                            log.info("Sottosequenza 11");
                            listaSottoseq.add("11");
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            String errore = "ATTENZIONE!!!\n"
                    + "Errore durante il caricamento da file!";
            this.steganografia.getFrame().finestraErrore(errore);
        }
    }

    private String convertiMessaggio(String messEstratto) {
        log.info("Messaggio estratto: " + messEstratto);
        String risultato = "";
        /* Prendiamo la stringa binaria e creiamo delle sottosequenze di 8 caratteri
            dato che 1 byte = 8 bit
        */
        int i = 0;
        int c = 8;
        while(messEstratto.length() != 0){
            String sottos = messEstratto.substring(i, c);
            log.info("Sottosequenza: " + sottos);
            /* Togliamo la sottosequenza di 8 bit appena esaminata dal testo 
                in binario: creiamo una stringa di appoggio per fare ciò
            */
            String copy = messEstratto;
            messEstratto = copy.substring(c, copy.length());
            //chiamare la funzione che mi calcola il carattere corrispondente
            risultato = risultato + converti(sottos);
        }
        log.info("Messaggio convertito: " + risultato);
        return risultato;
    }

    private String converti(String sottos) {
        String carattere = "";
        //codice ascii corrispondente agli 8 bit:
        int ascii = Integer.parseInt(sottos, 2); 
        //mi ricavo la lettera corrispondente al codice ascii:
        char c = (char)ascii;
        carattere = carattere + c;
        return carattere;
    }
    
}
