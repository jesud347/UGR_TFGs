package Vistas.Coordinacion;

import Vistas.Evaluacion.JFrame_OpcionesEvaluacion;

public class JFrame_OpcionesGestion extends javax.swing.JFrame {

    public JFrame_OpcionesGestion() {
        initComponents();
        setTitle("Opciones Gestión");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_tribunales = new javax.swing.JButton();
        bt_docentes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        bt_salir = new javax.swing.JButton();
        bt_tfgs = new javax.swing.JButton();
        bt_estudiantes = new javax.swing.JButton();
        bt_irEvaluacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_tribunales.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_tribunales.setText("GESTIÓN DE TRIBUNALES");
        bt_tribunales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tribunalesActionPerformed(evt);
            }
        });

        bt_docentes.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_docentes.setText("GESTIÓN DE DOCENTES");
        bt_docentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_docentesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selecciona la opción que deseas abrir.");

        bt_salir.setBackground(new java.awt.Color(255, 0, 0));
        bt_salir.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        bt_salir.setText("SALIR");
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        bt_tfgs.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_tfgs.setText("GESTIÓN DE TFGS");
        bt_tfgs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tfgsActionPerformed(evt);
            }
        });

        bt_estudiantes.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_estudiantes.setText("GESTIÓN DE ESTUDIANTES");
        bt_estudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_estudiantesActionPerformed(evt);
            }
        });

        bt_irEvaluacion.setText("IR A EVALUACION");
        bt_irEvaluacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_irEvaluacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111)
                        .addComponent(bt_irEvaluacion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_tfgs, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_estudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_tribunales, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                                .addComponent(bt_docentes, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_docentes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_tribunales, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_estudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_tfgs, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_irEvaluacion, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //ABRIR TRIBUNALES
    private void bt_tribunalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tribunalesActionPerformed
        JDialog_Tribunales jdialog_tribunales = new JDialog_Tribunales(this, true);
        jdialog_tribunales.setVisible(true);
    }//GEN-LAST:event_bt_tribunalesActionPerformed

    //ABRIR DOCENTES
    private void bt_docentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_docentesActionPerformed
        JDialog_Docentes jdialog_docentes = new JDialog_Docentes(this, true);
        jdialog_docentes.setVisible(true);
    }//GEN-LAST:event_bt_docentesActionPerformed

    //SALIR
    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_bt_salirActionPerformed

    //ABRIR TFGS
    private void bt_tfgsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tfgsActionPerformed
        JDialog_TFGS jdialog_tfgs = new JDialog_TFGS(this, true);
        jdialog_tfgs.setVisible(true);
    }//GEN-LAST:event_bt_tfgsActionPerformed

    //ABRIR ESTUDIANTES
    private void bt_estudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_estudiantesActionPerformed
        JDialog_Estudiantes jdialog_estudiantes = new JDialog_Estudiantes(this, true);
        jdialog_estudiantes.setVisible(true);
    }//GEN-LAST:event_bt_estudiantesActionPerformed

    //BOTON IR A EVALUACION
    private void bt_irEvaluacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_irEvaluacionActionPerformed
        JFrame_OpcionesEvaluacion jframe_eval = new JFrame_OpcionesEvaluacion();
        jframe_eval.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_irEvaluacionActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesGestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesGestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesGestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesGestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_OpcionesGestion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_docentes;
    private javax.swing.JButton bt_estudiantes;
    private javax.swing.JButton bt_irEvaluacion;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_tfgs;
    private javax.swing.JButton bt_tribunales;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
