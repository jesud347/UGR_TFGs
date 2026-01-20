package Vistas.Evaluacion;

import java.awt.Font;
import javax.swing.JScrollPane;

public class PanelEvaluacionTutor extends javax.swing.JPanel {

    private double nota;
    private boolean omitido = false;

    public PanelEvaluacionTutor(String titulo, String subtitulo, String descripcion, String[] criterios) {
        initComponents();

        txt_titulo.setText(titulo);
        txt_puntuacion.setText(subtitulo);

        textArea_rubrica.setText(descripcion + "\n\n" + String.join("\n", criterios));
        textArea_rubrica.setCaretPosition(0);
        textArea_rubrica.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea_rubrica.setLineWrap(true);
        textArea_rubrica.setWrapStyleWord(true);
        textArea_rubrica.setEditable(false);
        textArea_rubrica.setOpaque(false);
        textArea_rubrica.setFocusable(false);

        // Aquí puedes personalizar el jScrollPane existente si hace falta
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setBorder(null);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setOpaque(false);

    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getTxt_puntuacion() {
        return txt_puntuacion.getText();
    }

    public void setOmitido(boolean valor) {
        omitido = valor;
        nota = -1; // o simplemente ignorarlo en el cálculo
    }

    public boolean isOmitida() {
        return omitido;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_titulo = new javax.swing.JLabel();
        txt_puntuacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea_rubrica = new javax.swing.JTextArea();

        txt_titulo.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txt_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_titulo.setText("TITULO");

        txt_puntuacion.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        txt_puntuacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_puntuacion.setText("PUNTUACION");

        textArea_rubrica.setColumns(20);
        textArea_rubrica.setRows(5);
        jScrollPane1.setViewportView(textArea_rubrica);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_puntuacion, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                .addComponent(txt_puntuacion)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textArea_rubrica;
    private javax.swing.JLabel txt_puntuacion;
    private javax.swing.JLabel txt_titulo;
    // End of variables declaration//GEN-END:variables
}
