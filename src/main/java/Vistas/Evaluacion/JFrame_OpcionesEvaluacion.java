package Vistas.Evaluacion;

import Controladores.Ctrl_Tribunales;
import Controladores.EMFProvider;
import Modelos.SesionUsuario;
import Modelos.Usuario;
import Vistas.Coordinacion.JFrame_OpcionesGestion;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class JFrame_OpcionesEvaluacion extends javax.swing.JFrame {

    Usuario usuarioActual = SesionUsuario.getInstancia().getUsuario();
    private EntityManagerFactory emf;       
    private Ctrl_Tribunales ctrl_tribunales;

    public JFrame_OpcionesEvaluacion() {
        initComponents();

        try {
            emf = EMFProvider.getEmf();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }


        ctrl_tribunales = new Ctrl_Tribunales(emf);

        bt_gestion.setVisible(false);

        if (usuarioActual.getPermisos()) {
            bt_gestion.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bt_evaluarProfesor = new javax.swing.JButton();
        bt_evaluarTribunal = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();
        bt_gestion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("ELIGE EL MODO DE EVALUACIÓN");

        bt_evaluarProfesor.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        bt_evaluarProfesor.setText("EVALUAR COMO PROFESOR");
        bt_evaluarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_evaluarProfesorActionPerformed(evt);
            }
        });

        bt_evaluarTribunal.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        bt_evaluarTribunal.setText("EVALUAR COMO TRIBUNAL");
        bt_evaluarTribunal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_evaluarTribunalActionPerformed(evt);
            }
        });

        bt_salir.setBackground(new java.awt.Color(255, 0, 0));
        bt_salir.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        bt_salir.setText("SALIR");
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        bt_gestion.setText("IR A GESTIÓN");
        bt_gestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_gestionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(bt_evaluarProfesor)
                .addGap(18, 18, 18)
                .addComponent(bt_evaluarTribunal, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bt_gestion)
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_evaluarProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_evaluarTribunal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bt_gestion)
                .addGap(19, 19, 19))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON SALIR
    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_bt_salirActionPerformed

    //EVALUAR COMO PROFESOR
    private void bt_evaluarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_evaluarProfesorActionPerformed
        JDialog_Tutor jd_evalTutor = new JDialog_Tutor(this, true);
        jd_evalTutor.setVisible(true);
    }//GEN-LAST:event_bt_evaluarProfesorActionPerformed

    //IR A GESTION SI EL USUARIO TIENE PERMISOS
    private void bt_gestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_gestionActionPerformed
        JFrame_OpcionesGestion jframe_opcionesAdmin = new JFrame_OpcionesGestion();
        jframe_opcionesAdmin.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_gestionActionPerformed

    //EVALUAR COMO TRIBUNAL
    private void bt_evaluarTribunalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_evaluarTribunalActionPerformed
        // Obtenemos los cursos desde el controlador
        List<String> cursos = ctrl_tribunales.sacarCursos();
        String cursoSeleccionado = "";
        if (cursos == null || cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay cursos disponibles en la base de datos.");
            return;
        }

        // Creamos el JComboBox con los cursos obtenidos
        JComboBox<String> comboBox = new JComboBox<>(cursos.toArray(new String[0]));

        // Mostramos el cuadro de diálogo
        int opcion = JOptionPane.showConfirmDialog(
                this,
                comboBox,
                "Selecciona un curso",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Si el usuario pulsa "Aceptar"
        if (opcion == JOptionPane.OK_OPTION) {
            cursoSeleccionado = (String) comboBox.getSelectedItem();
            System.out.println("Curso seleccionado: " + cursoSeleccionado);

            // Aquí podrías usar el curso para buscar el tribunal, por ejemplo:
            // Tribunal t = ctrl_tribunales.tribunalDeUnProfesorEnCurso(codProfesor, cursoSeleccionado);
        }else{
            return;
        }
        JDialog_Tribunal jd_evalTrib = new JDialog_Tribunal(this, true, cursoSeleccionado);
        jd_evalTrib.setVisible(true);

    }//GEN-LAST:event_bt_evaluarTribunalActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_OpcionesEvaluacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesEvaluacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesEvaluacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_OpcionesEvaluacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_OpcionesEvaluacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_evaluarProfesor;
    private javax.swing.JButton bt_evaluarTribunal;
    private javax.swing.JButton bt_gestion;
    private javax.swing.JButton bt_salir;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
