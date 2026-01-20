package Vistas.Coordinacion;

import Controladores.Ctrl_Profesores;
import Controladores.Ctrl_Usuarios;
import Controladores.EMFProvider;
import Modelos.Profesor;
import Modelos.Usuario;
import java.awt.Dialog;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

public class JDialog_DocentesEditar extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_Usuarios ctrl_usuarios;
    private String codProfesorSeleccionado;
    private JDialog_Docentes jdDocentes;

    public JDialog_DocentesEditar(Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //ESTE NO SE USA
    }

    public JDialog_DocentesEditar(Dialog parent, boolean modal, String codProfesor, JDialog_Docentes jd_Docentes) {
        super(parent, modal);
        initComponents();

        setTitle("Editar Docentes");
        
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
        ctrl_usuarios = new Ctrl_Usuarios(emf);

        jdDocentes = jd_Docentes;
        
        codProfesorSeleccionado = codProfesor;

        txt_codProfesor.setEnabled(false);
        txt_nombre.setEnabled(false);
        txt_passwd.setEnabled(false);
        txt_usuario.setEnabled(false);
        combobox_departamentos.setEnabled(false);
        checkBoxPermisos.setEnabled(false);
        bt_guardar.setEnabled(false);

        rellenarDatosDocente();
        rellenarDatosUsuario();

    }

    //RELLENAR DATOS DOCENTE
    public void rellenarDatosDocente() {
        Profesor p = ctrl_profesores.buscarProfesor(codProfesorSeleccionado);
        txt_codProfesor.setText(p.getCodProfesor());
        txt_nombre.setText(p.getNombre());
        combobox_departamentos.addItem(p.getDepartamento());
    }

    //RELLENAR DATOS USUARIO
    public void rellenarDatosUsuario() {
        Usuario u = ctrl_usuarios.buscarUsuario(codProfesorSeleccionado);
        txt_usuario.setText(u.getUsuario());
        txt_passwd.setText(u.getPasswd());
        checkBoxPermisos.setSelected(u.getPermisos());
    }

    //RELLENAR COMBOBOX DEPARTAMENTOS
    public void rellenarComboboxDepartamentos() {
        combobox_departamentos.removeAllItems();
        List<String> departamentos = ctrl_profesores.sacarDepartamentos();
        for (String dpt : departamentos) {
            combobox_departamentos.addItem(dpt);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        bt_activarEdicion = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_codProfesor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        combobox_departamentos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_passwd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        checkBoxPermisos = new javax.swing.JCheckBox();
        bt_guardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("EDITAR DOCENTE");

        jLabel2.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel2.setText("DATOS DOCENTE");

        jLabel3.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel3.setText("DATOS USUARIO");

        bt_activarEdicion.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_activarEdicion.setText("ACTIVAR EDICIÓN");
        bt_activarEdicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_activarEdicionActionPerformed(evt);
            }
        });

        jLabel4.setText("Código Profesor");

        jLabel5.setText("Nombre Completo");

        jLabel6.setText("Departamento");

        jLabel7.setText("Usuario");

        jLabel8.setText("Contraseña");

        jLabel9.setText("Permisos");

        checkBoxPermisos.setText("Activar / Desactivar");

        bt_guardar.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        bt_guardar.setText("GUARDAR CAMBIOS");
        bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(bt_activarEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(219, 219, 219))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_codProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(160, 160, 160))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_passwd)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(txt_usuario)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(checkBoxPermisos)
                                .addGap(19, 19, 19)))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(193, 193, 193))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bt_activarEdicion)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_codProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_passwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxPermisos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON ACTIVAR EDICION
    private void bt_activarEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_activarEdicionActionPerformed
        txt_codProfesor.setEnabled(false);
        txt_nombre.setEnabled(true);
        txt_passwd.setEnabled(true);
        txt_usuario.setEnabled(true);
        combobox_departamentos.setEnabled(true);
        String dptDelDocente = (String) combobox_departamentos.getSelectedItem();
        checkBoxPermisos.setEnabled(true);
        bt_guardar.setEnabled(true);
        rellenarComboboxDepartamentos();
        combobox_departamentos.setSelectedItem(dptDelDocente);

    }//GEN-LAST:event_bt_activarEdicionActionPerformed

    //BOTON GUARDAR LOS CAMBIOS
    private void bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_guardarActionPerformed
        String nombre = txt_nombre.getText().trim();
        String departamento = (String) combobox_departamentos.getSelectedItem();
        Profesor p = new Profesor(codProfesorSeleccionado, nombre, departamento);

        String usuario = txt_usuario.getText().trim();
        String passwd = txt_passwd.getText().trim();
        boolean permisos = checkBoxPermisos.isSelected();
        Usuario u = new Usuario(usuario, passwd, permisos, p);

        int resultado = ctrl_profesores.editarDocenteYUsuario(p, u);

        if (resultado == 1) {
            JOptionPane.showMessageDialog(this, "Docente actualizado con éxito");
            jdDocentes.rellenarTablaDocentes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar docente: " + resultado);
        }
    }//GEN-LAST:event_bt_guardarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialog_DocentesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_DocentesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_DocentesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_DocentesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_DocentesEditar dialog = new JDialog_DocentesEditar(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton bt_activarEdicion;
    private javax.swing.JButton bt_guardar;
    private javax.swing.JCheckBox checkBoxPermisos;
    private javax.swing.JComboBox<String> combobox_departamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txt_codProfesor;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_passwd;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
