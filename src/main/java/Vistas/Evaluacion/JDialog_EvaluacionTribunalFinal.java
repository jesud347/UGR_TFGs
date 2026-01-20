package Vistas.Evaluacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.EMFProvider;
import Modelos.Estudiante;
import Modelos.SesionUsuario;
import Modelos.Tribunal;
import Modelos.Usuario;
import java.awt.Dialog;
import java.awt.Window;
import javax.persistence.EntityManagerFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class JDialog_EvaluacionTribunalFinal extends javax.swing.JDialog {

    Usuario usuarioActual = SesionUsuario.getInstancia().getUsuario();
    private Estudiante estudiante;
    private Tribunal tribunal;
    private EntityManagerFactory emf;
    private Ctrl_Estudiantes ctrl_estudiante;
    private double notaFinal;

    public JDialog_EvaluacionTribunalFinal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //NO SE USA
    }

    public JDialog_EvaluacionTribunalFinal(Dialog parent, boolean modal, Estudiante estudiante, Tribunal tribunal, double notaMediaTribunal) {
        super(parent, modal);
        initComponents();

        try {
            emf = EMFProvider.getEmf();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }

        notaFinal = notaMediaTribunal;
        ctrl_estudiante = new Ctrl_Estudiantes(emf);

        setTitle("Evaluación Final");

        this.estudiante = estudiante;
        this.tribunal = tribunal;

        txt_tribunal.setText(tribunal.toString());
        txt_presidente.setText(usuarioActual.toString());
        txt_tfg.setText(estudiante.getTfg().getCodTFG() + " | Tutor: " + estudiante.getTfg().getTutor().getCodProfesor());
        txt_alumno.setText(estudiante.toString());
        txt_notaTutor.setText(String.valueOf(estudiante.getCalificacionTutor()));
        txt_notaTribunal.setText(String.valueOf(notaMediaTribunal));

        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_tfg = new javax.swing.JLabel();
        txt_tribunal = new javax.swing.JLabel();
        txt_presidente = new javax.swing.JLabel();
        txt_alumno = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_notaTribunal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_notaTutor = new javax.swing.JLabel();
        bt_calificar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FINALIZAR EVALUACIÓN DEL TRIBUNAL");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("TRIBUNAL:");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel3.setText("PRESIDENTE/A:");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setText("TFG:");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel5.setText("ALUMNO/A:");

        txt_tfg.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_tfg.setText("tfg");

        txt_tribunal.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_tribunal.setText("tribunal");

        txt_presidente.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_presidente.setText("presidente");

        txt_alumno.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_alumno.setText("alumno");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel6.setText("CALIFICACIÓN OBTENIDA POR EL TUTOR/A:");

        txt_notaTribunal.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_notaTribunal.setText("notaTribunal");

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel9.setText("CALIFICACIÓN OBTENIDA POR EL TRIBUNAL:");

        txt_notaTutor.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_notaTutor.setText("notaTutor");

        bt_calificar.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        bt_calificar.setText("CALIFICAR");
        bt_calificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_calificarActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 2, 14)); // NOI18N
        jLabel14.setText("Nota: La calificación final oscilará entre la puntuación del TUTOR/A menos un punto, hasta 10 puntos");

        jLabel8.setText("---------------------------------------------------------------------------------------------------------------------------------------");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_tribunal, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txt_presidente, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txt_tfg, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txt_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txt_notaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txt_notaTribunal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel14))
            .addGroup(layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(bt_calificar, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_tribunal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txt_presidente))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txt_tfg))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txt_alumno))
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txt_notaTutor))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txt_notaTribunal))
                .addGap(31, 31, 31)
                .addComponent(jLabel14)
                .addGap(93, 93, 93)
                .addComponent(bt_calificar)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON CALIFICAR
    private void bt_calificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_calificarActionPerformed


        ctrl_estudiante.asignarNotaTribunal(estudiante.getCodEstudiante(), notaFinal);
        JOptionPane.showMessageDialog(this, "La nota final ha sido de " + notaFinal);
        cerrarYVolver();
    }//GEN-LAST:event_bt_calificarActionPerformed

    private void cerrarYVolver() {
        dispose();

        // Subimos dos niveles
        Window parent = getOwner(); // Esto es EvaluacionTribunal (Dialog 2)
        if (parent instanceof JDialog) {
            Window grandParent = ((JDialog) parent).getOwner(); // Esto debería ser JDialog_Tribunal (Dialog 1)

            if (grandParent instanceof JDialog_Tribunal) {
                JDialog_Tribunal dialog1 = (JDialog_Tribunal) grandParent;
                dialog1.rellenarTabla(); // Refrescar
                dialog1.setVisible(true); // Mostrar de nuevo
            }
        }
    }

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
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTribunalFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTribunalFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTribunalFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTribunalFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_EvaluacionTribunalFinal dialog = new JDialog_EvaluacionTribunalFinal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_calificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel txt_alumno;
    private javax.swing.JLabel txt_notaTribunal;
    private javax.swing.JLabel txt_notaTutor;
    private javax.swing.JLabel txt_presidente;
    private javax.swing.JLabel txt_tfg;
    private javax.swing.JLabel txt_tribunal;
    // End of variables declaration//GEN-END:variables
}
