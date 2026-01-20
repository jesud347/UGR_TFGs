package Vistas.Coordinacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.Ctrl_Tfgs;
import Controladores.EMFProvider;
import Modelos.Estudiante;
import Modelos.Tfg;
import java.awt.Dialog;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_AsignarTFG extends javax.swing.JDialog {

    private Estudiante estudiante;
    private EntityManagerFactory emf;
    private DefaultTableModel dtm_tfgs;
    private List<Tfg> listaTfgs;
    private Ctrl_Tfgs ctrl_tfg;
    private Ctrl_Estudiantes ctrl_estudiante;
    private String codTFG;

    public JDialog_AsignarTFG(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //NO SE USA
    }

    public JDialog_AsignarTFG(Dialog parent, boolean modal, Estudiante estudianteSeleccionado) {
        super(parent, modal);
        initComponents();
        setTitle("Asignar TFGs");

        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }
        this.estudiante = estudianteSeleccionado;

        ctrl_tfg = new Ctrl_Tfgs(emf);
        ctrl_estudiante = new Ctrl_Estudiantes(emf);

        dtm_tfgs = (DefaultTableModel) table_tfgs.getModel();
        table_tfgs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txt_id.setText(txt_id.getText() + estudiante.getCodEstudiante());
        txt_nombre.setText(txt_nombre.getText() + estudiante.getNombre());
        txt_apellidos.setText(txt_apellidos.getText() + estudiante.getApellidos());
        txt_email.setText(txt_email.getText() + estudiante.getEmail());

        rellenarTablaTfgs();

    }

    //RELLENAR TABLA TFGS
    public void rellenarTablaTfgs() {
        dtm_tfgs.setRowCount(0);
        listaTfgs = ctrl_tfg.listarTfgs();
        for (Tfg tfg : listaTfgs) {
            String codTribunal = (tfg.getTribunal() != null) ? tfg.getTribunal().getCodTribunal() : "";

            dtm_tfgs.addRow(new Object[]{
                tfg.getTutor(), // Aquí también podrías comprobar si es null si lo necesitas
                tfg.getCodTFG(),
                tfg.getTitulo(),
                tfg.getProfesor2(),
                codTribunal,
                tfg.getEspecial()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_id = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JLabel();
        txt_apellidos = new javax.swing.JLabel();
        txt_email = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bt_asignarTFG = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_tfgs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("ASIGNAR TFG A ESTUDIANTE");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        jLabel2.setText("ESTUDIANTE SELECCIONADO:");

        txt_id.setText("ID: ");

        txt_nombre.setText("Nombre: ");

        txt_apellidos.setText("Apellidos: ");

        txt_email.setText("Email: ");

        jLabel7.setText("Lista de TFGS:");

        bt_asignarTFG.setText("ASIGNAR TFG");
        bt_asignarTFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_asignarTFGActionPerformed(evt);
            }
        });

        table_tfgs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tutor", "CodTFG", "Titulo", "Profesor 2", "Tribunal", "Especial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_tfgs);
        if (table_tfgs.getColumnModel().getColumnCount() > 0) {
            table_tfgs.getColumnModel().getColumn(1).setMinWidth(65);
            table_tfgs.getColumnModel().getColumn(1).setMaxWidth(65);
            table_tfgs.getColumnModel().getColumn(4).setMinWidth(65);
            table_tfgs.getColumnModel().getColumn(4).setMaxWidth(65);
            table_tfgs.getColumnModel().getColumn(5).setMinWidth(65);
            table_tfgs.getColumnModel().getColumn(5).setMaxWidth(65);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(768, 768, 768)
                                .addComponent(bt_asignarTFG, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txt_id)
                .addGap(18, 18, 18)
                .addComponent(txt_nombre)
                .addGap(18, 18, 18)
                .addComponent(txt_apellidos)
                .addGap(18, 18, 18)
                .addComponent(txt_email)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bt_asignarTFG, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON ASIGNAR TFG
    private void bt_asignarTFGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_asignarTFGActionPerformed

        int tfgSeleccionado = table_tfgs.getSelectedRow();
        if (tfgSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para asignar un TFG a un ESTUDIANTE, deberas seleccionar el TFG");
        } else {
            codTFG = (String) dtm_tfgs.getValueAt(tfgSeleccionado, 1);
            int resultado = ctrl_estudiante.asignarTFG(codTFG, estudiante.getCodEstudiante());

            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "TFG asignado correctamente al estudiante.");

            } else {
                JOptionPane.showMessageDialog(this, "Error al asignar el TFG. Revisa los datos.");
            }
        }

    }//GEN-LAST:event_bt_asignarTFGActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(JDialog_AsignarTFG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTFG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTFG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTFG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_AsignarTFG dialog = new JDialog_AsignarTFG(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bt_asignarTFG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_tfgs;
    private javax.swing.JLabel txt_apellidos;
    private javax.swing.JLabel txt_email;
    private javax.swing.JLabel txt_id;
    private javax.swing.JLabel txt_nombre;
    // End of variables declaration//GEN-END:variables
}
