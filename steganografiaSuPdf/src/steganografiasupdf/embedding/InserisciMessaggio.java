/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.embedding;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
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
public class InserisciMessaggio extends AbstractAction{
    
    private final SteganografiaSuPdf steganografia;
    private final Log log = LogFactory.getLog(InserisciMessaggio.class);
    private String messaggio;
    private String messBinario;
    private String path = "temp/";
    
    //cover Pdf:
    private String coverPdf;
    private String coverAppoggio;
    
    //stego Pdf 
    private final String stegoPdf = "stego.pdf";
    
    //IMMAGINI:
    
    //immagine 4x4 -> 00
    private final String immagine1 = "immaginiBianche/immagine1.jpg";
    
    //immagine 8x8 -> 01
    private final String immagine2 = "immaginiBianche/immagine2.jpg";
    
    //immagine 16x16 -> 10
    private final String immagine3 = "immaginiBianche/immagine3.jpg";
    
    //immagine 32x32 -> 11
    private final String immagine4 = "immaginiBianche/immagine4.jpg";
    
    //Lista di sottosequenze di 2 bit del messaggio binario:
    private final List listaSottosequenze = new ArrayList<>();
    
    
    public InserisciMessaggio(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Inserisci Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio nel PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In InserisciMessaggio");
        //Azzerare la lista delle sottosequenze:
        this.listaSottosequenze.removeAll(listaSottosequenze);
        svuotaCartella(this.path);
        log.info("Dimensione lista sottoseq: " + this.listaSottosequenze.size());
        log.info("Dimensione mappa: " + this.steganografia.getSize());
        this.messaggio = (String)this.steganografia.getObject(Costanti.messaggioInserito);
        this.coverPdf = (String)this.steganografia.getObject(Costanti.pdfcover);
        if(this.messaggio == null || this.coverPdf == null){
            String errore = "ATTENZIONE!\n"
                    + "\n1. Il campo messaggio non può essere nullo!"
                    + "\n2. Nessun file PDF selezionato!";;
            this.steganografia.getFrame().finestraErrore(errore);
        }else{
           
            log.info("Messaggio da inserire: " + this.messaggio);
            log.info("Path file.pdf: " + this.coverPdf);
            //Convertire il messaggio in binario:
            convertiMessInBinario(this.messaggio);
            log.info("Messaggio da inserire: " + messaggio 
                + " ,Messaggio da inserire in binario: " + this.messBinario);
            //Suddividere il messaggio binario in sottosequenze di 2 bit
            suddividiMessaggioInSottoseq();
            log.info("Dimensione lista della sottosequenze:"
                + this.listaSottosequenze.size());
            //Aggiungere le immagini nel pdf
            inserisciMessaggio();
            svuotaCartella(path);
            //apri esplora risorse nella cartella in cui è salvato lo stego pdf
            //String pathStego = 
            
            File fileStego = new File(stegoPdf);
            String pathStego = fileStego.getAbsolutePath();
            String info = "Il PDF è stato salvato qui: \n"
                    + pathStego;
            this.steganografia.getFrame().finestraInfo(info);
        }
    }

    private void convertiMessInBinario(String messaggio) {
        /*Converte il messaggio nella stringa binaria corrispondente
            utilizzando la TEBELLA ASCII*/
        
        this.messBinario = "";
        for(int i = 0; i < messaggio.length(); i++){
            String s = Integer.toBinaryString(messaggio.charAt(i));
            log.info("Stringa parziale: " + s);
            /*Dato che 1 byte è uguale a 8bit, dobbiamo assicurarci
                che ciascun carattere contenga 8 bit.
                Se così non fosse, aggiungiamo tanti zeri
                fino a quando non ho una stringa di 8 bit.
            */
            while(s.length() < 8){
                s = "0" + s;
            }
            this.messBinario = this.messBinario + s;
        }
        log.info("Stringa: " + messaggio + " ,Stringa binaria: " + this.messBinario);
    }

    private void suddividiMessaggioInSottoseq() {
        String mess = this.messBinario;
        int i = 0;
        int j = 2;
        while(mess.length() != 0){
            String sottoseq = mess.substring(i, j);
            //log.info("Sottosequenza: " + sottoseq);
            this.listaSottosequenze.add(sottoseq);
            /*Togliamo la sottosequenza di 2 bit appena esaminata dal testo in binario.
                Pertanto, creiamo una stringa di appoggio: */
            String copy = mess;
            mess = copy.substring(j, copy.length());
        }
    }

    private void svuotaCartella(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        for(File file : files){
            file.delete();
        }
    }

    private void inserisciMessaggio(){
        svuotaCartella(this.path);
        try {
            log.info("Path coverPdf: " + this.coverPdf);
            coverAppoggio = this.coverPdf;
            log.info("Path coverPdfAppoggio: " + coverAppoggio);
            //per ogni pag del PDF aggiungo una sottosequenza e così via...
            //prelevo una sottosequenza e l'aggiungo in tutte le pag del PDF.
            float x1 = 1;
            float y1 = 773;
            for(int i = 0; i < this.listaSottosequenze.size(); i++){
                // ciclo della lista sottosequenze:
                // i -> sottosequenza i-esima
                String sottoSeq = (String)this.listaSottosequenze.get(i);
                log.info("Sottosequenza prelevata: " + sottoSeq);
                /*  in base alla sottosequenza effettuiamo l'inserimento
                    dell'immagine nel PDF:
                    1. 00 -> immagine 4x4
                    2. 01 -> immagine 8x8
                    3. 10 -> immagine 16x16
                    4. 11 -> immagine 32x32
                */
                // se la sottosequenza è 00, ad esempio, inseriamo un immagine
                // 4x4 in tutte le pagine del pdf
                String pdfStego = "temp/stego";
                
                if(sottoSeq.equalsIgnoreCase("00")){
                    //inserire immagine 4x4
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stegoPdf;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine1);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("01")){
                    //inserire immagine 8x8
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stegoPdf;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine2);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("10")){
                    //inserire immagine 16x16
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stegoPdf;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine3);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("11")){
                    //inserire immagine 32x32
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stegoPdf;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine4);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }
                
                if(x1 > 550){
                    log.info("nell'if x1 > 550");
                    //inserisce l'immagine nella riga successiva:
                    x1 = 1;
                    y1 = y1 - 32 - 1;
                }
            }
            //NOTA:
            //posso verificare quanti caratteri può contenere al max una pagina del pdf
            //in pratica, qual è l'ultima coordinata della pagina.
        } catch (IOException | BadElementException ex) {
            String errore = "ATTENZIONE!!!\n"
                + ("Errore durante il processo di Embedding!");
            this.steganografia.getFrame().finestraErrore(errore);
        }
    }
    
    private void aggiungiSottosequenza(float x1, float y1, Image image, String pdfStego) {
        try {
            //per ogni pagina del pdf aggiungi l'immagine
            PdfReader reader = new PdfReader(coverAppoggio);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdfStego));
            for(int i = 1; i <= reader.getNumberOfPages(); i++){
                image.setAbsolutePosition(x1, y1);
                stamper.getUnderContent(i).addImage(image);
            }
            stamper.close();
            reader.close();
        } catch (IOException | DocumentException ex) {
            String errore = "ATTENZIONE!!!\n"
                + ("Errore durante il processo di Embedding!");
            this.steganografia.getFrame().finestraErrore(errore);
        }
    }
}