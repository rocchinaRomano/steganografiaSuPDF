/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.F5;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class EmbeddingF5 {
    private final Log log = LogFactory.getLog(EmbeddingF5.class);
    
    private static final int quality = 75;
    private final String messaggio;
    private final String password;
    private final File cover;
    private final File stego;
    private FileOutputStream fos;
    private final SteganografiaSuPDF steganografia;
    
    
    public EmbeddingF5(SteganografiaSuPDF steganografia,String messaggio, String password, File cover, File stego){
        log.info("Nel COSTRUTTORE di EmbeddingF5");
        this.steganografia = steganografia;
        this.messaggio = messaggio;
        this.password = password;
        this.cover = cover;
        this.stego = stego;
        postInizializza();
    }

    private void postInizializza() {
        try {
            //qua richiamo JpegEncoder
            log.info("In postInizializza() di EmbeddingF5");
            log.info("Messaggio da inserire: " + this.messaggio);
            log.info("Password da inserire: " + this.password);
            log.info("File cover: " + this.cover.getName());
            String pathCover = this.cover.getPath();
            log.info("Path cover: " + pathCover);
            Image coverImage = Toolkit.getDefaultToolkit().getImage(pathCover);
            String pathStego = this.stego.getAbsolutePath();
            this.fos = new FileOutputStream(pathStego);
            JpegEncoder jpg = new JpegEncoder(coverImage, quality, fos, "commento");
            InputStream embeddedData = new ByteArrayInputStream(this.messaggio.getBytes());
            jpg.Compress(embeddedData, password);
            fos.close();
        } catch (FileNotFoundException ex) {
            String errore = "ATTENZIONE!\n"
                    + "File non trovato!";
            this.steganografia.getSp().finestraErrore(errore);
        } catch (IOException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il processo creazione dell'immagine STEGO!";
            this.steganografia.getSp().finestraErrore(errore);
        }
        
    }
}   