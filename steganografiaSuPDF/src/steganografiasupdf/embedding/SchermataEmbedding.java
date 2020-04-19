/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class SchermataEmbedding
 */
package steganografiasupdf.embedding;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.SchermataPrincipale;

/**
 *
 * @author Rocchina
 */
public class SchermataEmbedding extends javax.swing.JPanel {

    private final Log log = LogFactory.getLog(SchermataEmbedding.class);
    private final SchermataPrincipale sp;
    
    
    /**
     * Creates new form SchermataEmbedding
     * @param sp
     */
    public SchermataEmbedding(SchermataPrincipale sp) {
        this.sp = sp;
        initComponents();
        postInizializza();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMessaggioInserito = new javax.swing.JTextArea();
        jButtonMessaggioInserito = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPassword = new javax.swing.JTextField();
        jButtonInserisciPassword = new javax.swing.JButton();
        jButtonPDFSelezionato = new javax.swing.JButton();
        jButtonInserisciMessaggio = new javax.swing.JButton();
        jTextFieldPdfSelezionato = new javax.swing.JTextField();

        jLabel1.setText("Inserisci messaggio:");

        jTextAreaMessaggioInserito.setColumns(20);
        jTextAreaMessaggioInserito.setRows(5);
        jScrollPane1.setViewportView(jTextAreaMessaggioInserito);

        jButtonMessaggioInserito.setText("OK ");

        jLabel2.setText("Inserisci Password:");

        jButtonInserisciPassword.setText("OK");
        jButtonInserisciPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserisciPasswordActionPerformed(evt);
            }
        });

        jButtonPDFSelezionato.setText("Apri");

        jButtonInserisciMessaggio.setText("Inserisci Messaggio");

        jTextFieldPdfSelezionato.setText("Seleziona PDF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldPdfSelezionato, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(jTextFieldPassword, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButtonMessaggioInserito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonInserisciPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButtonPDFSelezionato, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(155, Short.MAX_VALUE)
                .addComponent(jButtonInserisciMessaggio)
                .addGap(146, 146, 146))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jButtonMessaggioInserito)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonInserisciPassword)
                    .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPDFSelezionato)
                    .addComponent(jTextFieldPdfSelezionato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButtonInserisciMessaggio)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInserisciPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserisciPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonInserisciPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonInserisciMessaggio;
    private javax.swing.JButton jButtonInserisciPassword;
    private javax.swing.JButton jButtonMessaggioInserito;
    private javax.swing.JButton jButtonPDFSelezionato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaMessaggioInserito;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldPdfSelezionato;
    // End of variables declaration//GEN-END:variables

    private void postInizializza() {
        this.jButtonMessaggioInserito.setAction(this.sp.getSteganografia().getLm());
        this.jButtonInserisciPassword.setAction(this.sp.getSteganografia().getLpe());
        this.jButtonPDFSelezionato.setAction(this.sp.getSteganografia().geteRembedding());
        this.jButtonInserisciMessaggio.setAction(this.sp.getSteganografia().getIm());
    }
    
    public String getMessaggio(){
        return this.jTextAreaMessaggioInserito.getText();
    }
    
    public String getPassword(){
        return this.jTextFieldPassword.getText();
    }
    
    public void mostraPathPdf(String path){
        this.jTextFieldPdfSelezionato.setText(path);
    }
    
}