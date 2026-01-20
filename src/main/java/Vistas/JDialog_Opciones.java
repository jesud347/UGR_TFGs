package Vistas;

import Vistas.Evaluacion.JFrame_OpcionesEvaluacion;
import Vistas.Coordinacion.JFrame_OpcionesGestion;
import Controladores.Ctrl_Profesores;
import Controladores.EMFProvider;
import Modelos.SesionUsuario;
import Modelos.Usuario;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

public class JDialog_Opciones extends javax.swing.JDialog {

    Usuario usuarioActual = SesionUsuario.getInstancia().getUsuario();
    private Ctrl_Profesores ctrl_profesores;
    private EntityManagerFactory emf;

    public JDialog_Opciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Opciones");

        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }
        ctrl_profesores = new Ctrl_Profesores(emf);
        txt_nombreUsuario.setText(ctrl_profesores.nombreProfesor(usuarioActual.getProfesor().getCodProfesor()) + ",");

        comprobarPermisos(usuarioActual);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_evaluacionAlumnos = new javax.swing.JButton();
        bt_tribunales = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_nombreUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bt_evaluacionAlumnos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bt_evaluacionAlumnos.setText("EVALUACIÓN DE ALUMNOS");
        bt_evaluacionAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_evaluacionAlumnosActionPerformed(evt);
            }
        });

        bt_tribunales.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bt_tribunales.setText("GESTIÓN / COORDINACIÓN");
        bt_tribunales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tribunalesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("te damos la bienvenida");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Elige una opción.");

        txt_nombreUsuario.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_nombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_nombreUsuario.setText("x");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bt_tribunales, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(bt_evaluacionAlumnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(93, 93, 93))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_nombreUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(105, 105, 105)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(83, 83, 83)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txt_nombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(bt_tribunales, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(bt_evaluacionAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(105, 105, 105)
                    .addComponent(jLabel2)
                    .addContainerGap(376, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //COMPROBAR PERMISOS PARA HABILITAR BOTONES
    public void comprobarPermisos(Usuario usuario) {
        if (usuario.getPermisos()) {
            bt_evaluacionAlumnos.setEnabled(true);
            bt_tribunales.setEnabled(true);
        } else {
            bt_tribunales.setEnabled(false);
        }
    }

    //BOTON IR A GESTION
    private void bt_tribunalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tribunalesActionPerformed
        JFrame_OpcionesGestion jframe_opcionesAdmin = new JFrame_OpcionesGestion();
        jframe_opcionesAdmin.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_tribunalesActionPerformed

    //BOTON IR A EVALUACION
    private void bt_evaluacionAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_evaluacionAlumnosActionPerformed
        JFrame_OpcionesEvaluacion jframe_opcionesEval = new JFrame_OpcionesEvaluacion();
        jframe_opcionesEval.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_evaluacionAlumnosActionPerformed

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
            java.util.logging.Logger.getLogger(JDialog_Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Opciones dialog = new JDialog_Opciones(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bt_evaluacionAlumnos;
    private javax.swing.JButton bt_tribunales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel txt_nombreUsuario;
    // End of variables declaration//GEN-END:variables
}
