package Vistas.Coordinacion;

import Controladores.Ctrl_ProfesorTribunal;
import Controladores.Ctrl_Profesores;
import Controladores.Ctrl_Tribunales;
import Controladores.EMFProvider;
import Controladores.ExportadorExcelTribunales;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class JDialog_BuscarTribunales extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_ProfesorTribunal ctrl_pt;
    private DefaultTableModel dtm_pt;
    private List<Object[]> listapt;
    private ExportadorExcelTribunales exportador;

    public JDialog_BuscarTribunales(Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Ver Departamentos");
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
        ctrl_tribunales = new Ctrl_Tribunales(emf);
        ctrl_pt = new Ctrl_ProfesorTribunal(emf);

        dtm_pt = (DefaultTableModel) table_tribunales.getModel();
        rellenarComboboxDepartamentos();
        rellenarComboboxCursos();
        rellenarContadorTotal();
    }

    //RELLENAR TABLA TRIBUNALES POR DEPARTAMENTO
    public void rellenarTablaTribunales(String curso, String departamento) {
        dtm_pt.setRowCount(0);
        listapt = ctrl_pt.listarProfesoresTribunalesCursoDepartamento(curso, departamento);
        for (Object[] fila : listapt) {
            dtm_pt.addRow(fila);
        }
    }

    //RELLENAR COMBOBOX DEPARTAMENTOS
    public void rellenarComboboxDepartamentos() {
        combobox_departamentos.removeAllItems();
        List<String> departamentos = ctrl_profesores.sacarDepartamentos();
        for (String dpt : departamentos) {
            combobox_departamentos.addItem(dpt);
        }
    }

    //RELLENAR COMBOBOX CURSOS
    public void rellenarComboboxCursos() {
        combobox_cursos.removeAllItems();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combobox_cursos.addItem(c);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_tribunales = new javax.swing.JTable();
        combobox_departamentos = new javax.swing.JComboBox<>();
        bt_buscar = new javax.swing.JButton();
        txt_contador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        combobox_cursos = new javax.swing.JComboBox<>();
        txt_contador1 = new javax.swing.JLabel();
        txt_contadorTotal = new javax.swing.JLabel();
        txt_contadorTotal1 = new javax.swing.JLabel();
        bt_exportar = new javax.swing.JButton();

        table_tribunales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tribunal", "Curso", "Profesor1", "Profesor2", "Profesor3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_tribunales);
        if (table_tribunales.getColumnModel().getColumnCount() > 0) {
            table_tribunales.getColumnModel().getColumn(0).setMinWidth(80);
            table_tribunales.getColumnModel().getColumn(0).setMaxWidth(80);
            table_tribunales.getColumnModel().getColumn(1).setMinWidth(80);
            table_tribunales.getColumnModel().getColumn(1).setMaxWidth(80);
        }

        combobox_departamentos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        bt_buscar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        bt_buscar.setText("BUSCAR");
        bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarActionPerformed(evt);
            }
        });

        txt_contador.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contador.setText("x");

        jLabel2.setFont(new java.awt.Font("Dialog", 3, 36)); // NOI18N
        jLabel2.setText("BÚSQUEDA DE TRIBUNALES");

        jLabel3.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        jLabel3.setText("Selecciona un curso:");

        jLabel4.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        jLabel4.setText("Selecciona un departamento:");

        combobox_cursos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        combobox_cursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_cursosActionPerformed(evt);
            }
        });

        txt_contador1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contador1.setText("CONTADOR:");

        txt_contadorTotal.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contadorTotal.setText("x");

        txt_contadorTotal1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contadorTotal1.setText("CONTADOR TOTAL:");

        bt_exportar.setText("EXPORTAR TRIBUNALES");
        bt_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(262, 262, 262))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txt_contadorTotal1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_contadorTotal)
                        .addGap(150, 150, 150)
                        .addComponent(txt_contador1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_contador, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(combobox_cursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(79, 79, 79)
                            .addComponent(bt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_buscar)
                    .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_contador)
                        .addComponent(txt_contador1)
                        .addComponent(bt_exportar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_contadorTotal1)
                        .addComponent(txt_contadorTotal)))
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON BUSCAR POR DEPARTAMENTO Y CURSO
    private void bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarActionPerformed
        String departamento = (String) combobox_departamentos.getSelectedItem();
        String curso = (String) combobox_cursos.getSelectedItem();

        rellenarTablaTribunales(curso, departamento);
        int contador = table_tribunales.getRowCount();

        txt_contador.setText(String.valueOf(contador));
    }//GEN-LAST:event_bt_buscarActionPerformed

    private void combobox_cursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_cursosActionPerformed
        rellenarContadorTotal();
    }//GEN-LAST:event_combobox_cursosActionPerformed

    private void bt_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_exportarActionPerformed
        // Obtener el escritorio del usuario
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

        // Crear el JFileChooser con la ruta por defecto
        JFileChooser fileChooser = new JFileChooser(new File(desktopPath));
        fileChooser.setDialogTitle("Guardar archivo Excel");

        // Filtro para archivos .xlsx
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Asegurarse de que tenga extensión .xlsx
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try {
                exportador = new ExportadorExcelTribunales();
                String departamento = (String) combobox_departamentos.getSelectedItem();
                exportador.exportarDepartamento(table_tribunales, filePath, departamento);
                JOptionPane.showMessageDialog(this, "Exportado correctamente a:\n" + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar:\n" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_bt_exportarActionPerformed

    //RELLENAR CONTADOR TOTAL
    public void rellenarContadorTotal() {
        String curso = (String) combobox_cursos.getSelectedItem();
        long contadorTotal = ctrl_tribunales.sacarNumeroTribunales(curso);
        txt_contadorTotal.setText(String.valueOf(contadorTotal));
    }

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(JDialog_BuscarTribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_BuscarTribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_BuscarTribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_BuscarTribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_BuscarTribunales dialog = new JDialog_BuscarTribunales(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton bt_buscar;
    private javax.swing.JButton bt_exportar;
    private javax.swing.JComboBox<String> combobox_cursos;
    private javax.swing.JComboBox<String> combobox_departamentos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_tribunales;
    private javax.swing.JLabel txt_contador;
    private javax.swing.JLabel txt_contador1;
    private javax.swing.JLabel txt_contadorTotal;
    private javax.swing.JLabel txt_contadorTotal1;
    // End of variables declaration//GEN-END:variables

}
