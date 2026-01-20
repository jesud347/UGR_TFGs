package Vistas.Coordinacion;

import Controladores.*;
import Modelos.*;
import java.io.File;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class JDialog_Tribunales extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_ProfesorTribunal ctrl_pt;
    private List<Profesor> listaProfesores;
    private List<Object[]> listapt;
    private DefaultTableModel dtm_profesores;
    private DefaultTableModel dtm_pt;
    private ExportadorExcelTribunales exportador;

    private JDialog_BuscarTribunales jd_buscarTribunales;
    private JDialog_Suplentes jd_verSuplentes;

    public JDialog_Tribunales(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Gestión de Tribunales");

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
        exportador = new ExportadorExcelTribunales(emf);

        dtm_profesores = (DefaultTableModel) tableProfesores.getModel();
        dtm_pt = (DefaultTableModel) tableTribunales.getModel();

        actualizar();
        contarProfesores();
    }

    //CONTAR PROFESORES EN JTABLE
    public void contarProfesores() {
        int contadorProfesores = tableProfesores.getRowCount();
        txt_contadorProfesores.setText(txt_contadorProfesores.getText() + contadorProfesores);
    }

    //RELLENAR TABLA PROFESORES
    public void rellenarTableProfesores() {
        dtm_profesores.setRowCount(0);
        listaProfesores = ctrl_profesores.listaProfesores();
        for (Profesor p : listaProfesores) {
            dtm_profesores.addRow(new Object[]{
                p.getCodProfesor(),
                p.getNombre(),
                p.getDepartamento()
            });
        }
    }

    //RELLENAR TABLE TRIBUNALES PROFESORES
    public void rellenarTablePT() {
        dtm_pt.setRowCount(0);
        listapt = ctrl_pt.listarProfesoresTribunales();
        for (Object[] fila : listapt) {
            dtm_pt.addRow(fila);
        }
    }

    //RELLENAR TABLE TRIBUNALES PROFESORES FILTRADO POR CURSO
    public void rellenarTablePTcurso(String curso) {
        dtm_pt.setRowCount(0);
        listapt = ctrl_pt.listarProfesoresTribunalesCurso(curso);
        for (Object[] fila : listapt) {
            dtm_pt.addRow(fila);
        }
    }

    //RELLENAR COMBOBOX DE CURSOS
    public void rellenarComboboxCursos() {
        combobox_cursos.removeAllItems();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combobox_cursos.addItem(c);
        }
    }

    //ACTUALIZAR
    public void actualizar() {
        rellenarComboboxCursos();
        rellenarTablePT();
        rellenarTableProfesores();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProfesores = new javax.swing.JTable();
        txt_contadorProfesores = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTribunales = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_curso = new javax.swing.JTextField();
        bt_generarTribunales = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        combobox_cursos = new javax.swing.JComboBox<>();
        bt_filtrarTribunales = new javax.swing.JButton();
        bt_quitarFiltro = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        bt_verDepartamentos = new javax.swing.JMenu();
        bt_verSuplentes = new javax.swing.JMenu();
        bt_editarTribunales = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        bt_exportarTodo = new javax.swing.JMenuItem();
        bt_exportarOrdinaria = new javax.swing.JMenuItem();
        bt_exportarExtraordinaria = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        bt_eliminarTribunalesCurso = new javax.swing.JMenuItem();
        bt_eliminarTodosTribunales = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DOCENTES DISPONIBLES");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 510, 40));

        tableProfesores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre Completo", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableProfesores);
        if (tableProfesores.getColumnModel().getColumnCount() > 0) {
            tableProfesores.getColumnModel().getColumn(0).setMinWidth(60);
            tableProfesores.getColumnModel().getColumn(0).setMaxWidth(60);
            tableProfesores.getColumnModel().getColumn(1).setResizable(false);
            tableProfesores.getColumnModel().getColumn(2).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 600, 460));

        txt_contadorProfesores.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contadorProfesores.setText("CONTADOR DOCENTES:");
        getContentPane().add(txt_contadorProfesores, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 350, 51));

        tableTribunales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tribunal", "Curso", "CodProfesor", "Profesor", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableTribunales);
        if (tableTribunales.getColumnModel().getColumnCount() > 0) {
            tableTribunales.getColumnModel().getColumn(0).setMinWidth(60);
            tableTribunales.getColumnModel().getColumn(0).setMaxWidth(60);
            tableTribunales.getColumnModel().getColumn(1).setMinWidth(60);
            tableTribunales.getColumnModel().getColumn(1).setMaxWidth(60);
            tableTribunales.getColumnModel().getColumn(2).setMinWidth(60);
            tableTribunales.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 150, 650, 410));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Introduce el curso con este formato: XX/XX -->");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));
        getContentPane().add(txt_curso, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 112, -1));

        bt_generarTribunales.setText("GENERAR TRIBUNALES");
        bt_generarTribunales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_generarTribunalesActionPerformed(evt);
            }
        });
        getContentPane().add(bt_generarTribunales, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, 158, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Selecciona el curso para ver sus tribunales -->");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, -1, -1));

        getContentPane().add(combobox_cursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 50, 112, -1));

        bt_filtrarTribunales.setText("FILTRAR POR CURSO");
        bt_filtrarTribunales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_filtrarTribunalesActionPerformed(evt);
            }
        });
        getContentPane().add(bt_filtrarTribunales, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 50, -1, -1));

        bt_quitarFiltro.setText("QUITAR FILTRO");
        bt_quitarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_quitarFiltroActionPerformed(evt);
            }
        });
        getContentPane().add(bt_quitarFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 100, 160, -1));

        jLabel6.setText("Pulsa para quitar el filtro");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 100, 144, -1));

        bt_verDepartamentos.setText("VER DEPARTAMENTOS  ");
        bt_verDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_verDepartamentosMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_verDepartamentos);

        bt_verSuplentes.setText("  VER SUPLENTES  ");
        bt_verSuplentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_verSuplentesMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_verSuplentes);

        bt_editarTribunales.setText("  EDITAR / ORGANIZAR TRIBUNALES   ");
        bt_editarTribunales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_editarTribunalesMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_editarTribunales);

        jMenu1.setText("   EXPORTADOR A EXCEL DE TRIBUNALES");

        bt_exportarTodo.setText("EXPORTAR TODOS LOS TRIBUNALES");
        bt_exportarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exportarTodoActionPerformed(evt);
            }
        });
        jMenu1.add(bt_exportarTodo);

        bt_exportarOrdinaria.setText("EXPORTAR TRIBUNALES ORDINARIA");
        bt_exportarOrdinaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exportarOrdinariaActionPerformed(evt);
            }
        });
        jMenu1.add(bt_exportarOrdinaria);

        bt_exportarExtraordinaria.setText("EXPORTAR TRIBUNALES EXTRAORDINARIA");
        bt_exportarExtraordinaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exportarExtraordinariaActionPerformed(evt);
            }
        });
        jMenu1.add(bt_exportarExtraordinaria);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("   ELIMINAR TRIBUNALES");

        bt_eliminarTribunalesCurso.setText("ELIMINAR TRIBUNALES DE UN CURSO");
        bt_eliminarTribunalesCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_eliminarTribunalesCursoActionPerformed(evt);
            }
        });
        jMenu2.add(bt_eliminarTribunalesCurso);

        bt_eliminarTodosTribunales.setBackground(new java.awt.Color(255, 0, 51));
        bt_eliminarTodosTribunales.setText("ELIMINAR TODOS LOS TRIBUNALES");
        bt_eliminarTodosTribunales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_eliminarTodosTribunalesActionPerformed(evt);
            }
        });
        jMenu2.add(bt_eliminarTodosTribunales);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON GENERAR TRIBUNALES
    private void bt_generarTribunalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_generarTribunalesActionPerformed
        String curso = txt_curso.getText().trim();

        if (curso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes elegir un curso");
            return;
        }

        // Preguntar al usuario si quiere Ordinaria o Extraordinaria
        String[] opcionesLlamada = {"Ordinaria", "Extraordinaria", "Cancelar"};
        int seleccionLlamada = JOptionPane.showOptionDialog(
                this,
                "¿Para qué tipo de profesores quieres generar los tribunales?",
                "Selecciona convocatoria",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesLlamada,
                opcionesLlamada[0]
        );

        if (seleccionLlamada == 2 || seleccionLlamada == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String tipoConvocatoria = seleccionLlamada == 0 ? "Ordinaria" : "Extraordinaria";

        // Preguntar al usuario el modo de generación
        String[] opciones = {"Forma 2+1", "Sin Restricción", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
                this,
                "¿Cómo quieres generar los tribunales para el curso " + curso + "?",
                "Selecciona el modo de generación",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        // Cancelado
        if (seleccion == 2 || seleccion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        try {
            // Opción 0: Forma 2+1
            if (seleccion == 0) {
                ctrl_tribunales.generarTribunalesParaCurso(curso, tipoConvocatoria);
            } // Opción 1: Sin Restricción
            else if (seleccion == 1) {
                ctrl_tribunales.generarTribunalesSinRestriccion(curso, tipoConvocatoria);
            }

            JOptionPane.showMessageDialog(this, "Tribunales generados correctamente.");

            // Generar suplentes
            ctrl_tribunales.generarTribunalSuplente(curso,tipoConvocatoria);
            JOptionPane.showMessageDialog(this, "Suplentes generados correctamente.");

            rellenarTablePT();
            rellenarComboboxCursos();

        } catch (Exception ex) {
            System.err.println("Error generando tribunales: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "El curso que has introducido, ya tiene los tribunales generados.");
        }
    }//GEN-LAST:event_bt_generarTribunalesActionPerformed

    //BOTON FILTRAR TRIBUNALES POR CURSO
    private void bt_filtrarTribunalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_filtrarTribunalesActionPerformed
        String cursoSeleccionado = (String) combobox_cursos.getSelectedItem();
        rellenarTablePTcurso(cursoSeleccionado);
    }//GEN-LAST:event_bt_filtrarTribunalesActionPerformed

    //BOTON QUITAR FILTRO
    private void bt_quitarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_quitarFiltroActionPerformed
        rellenarTablePT();
    }//GEN-LAST:event_bt_quitarFiltroActionPerformed

    //BOTON VER DEPARTAMENTOS
    private void bt_verDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_verDepartamentosMouseClicked
        jd_buscarTribunales = new JDialog_BuscarTribunales(this, true);
        jd_buscarTribunales.setVisible(true);
    }//GEN-LAST:event_bt_verDepartamentosMouseClicked

    //BOTON VER SUPLENTES
    private void bt_verSuplentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_verSuplentesMouseClicked
        jd_verSuplentes = new JDialog_Suplentes(this, true);
        jd_verSuplentes.setVisible(true);
    }//GEN-LAST:event_bt_verSuplentesMouseClicked

    //EDITAR TRIBUNALES
    private void bt_editarTribunalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_editarTribunalesMouseClicked
        JDialog_TribunalesEditar jd_editar = new JDialog_TribunalesEditar(this, true);
        jd_editar.setVisible(true);
    }//GEN-LAST:event_bt_editarTribunalesMouseClicked

    //BOTON MENU EXPORTAR TODOS LOS TRIBUNALES
    private void bt_exportarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_exportarTodoActionPerformed

        JComboBox<String> combo = new JComboBox<>();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combo.addItem(c);
        }

        Object[] mensaje = {
            "Selecciona el curso del que quieres exportar TODOS los tribunales:",
            combo
        };

        int resultado = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Seleccionar curso",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String seleccion = (String) combo.getSelectedItem();
            System.out.println("Curso seleccionado: " + seleccion);
            int confirmar = JOptionPane.showConfirmDialog(this, "Vas a exportar los tribunales del curso " + seleccion, "Aviso", JOptionPane.OK_CANCEL_OPTION);
            if (confirmar == JOptionPane.OK_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo Excel");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    exportador.exportarTribunalesConProfesores(seleccion, filePath);

                    JOptionPane.showMessageDialog(this, "Archivo Excel exportado correctamente:\n" + filePath);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Operación Cancelada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación Cancelada");
        }

    }//GEN-LAST:event_bt_exportarTodoActionPerformed

    //BOTON MENU EXPORTAR TRIBUNALES ORDINARIA
    private void bt_exportarOrdinariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_exportarOrdinariaActionPerformed

        JComboBox<String> combo = new JComboBox<>();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combo.addItem(c);
        }

        Object[] mensaje = {
            "Selecciona el curso del que quieres exportar los tribunales de la ORDINARIA:",
            combo
        };

        int resultado = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Seleccionar curso",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String seleccion = (String) combo.getSelectedItem();
            System.out.println("Curso seleccionado: " + seleccion);
            int confirmar = JOptionPane.showConfirmDialog(this, "Vas a exportar los tribunales del curso " + seleccion, "Aviso", JOptionPane.OK_CANCEL_OPTION);
            if (confirmar == JOptionPane.OK_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo Excel");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    exportador.exportarTribunalesOrdinarios(seleccion, filePath);

                    JOptionPane.showMessageDialog(this, "Archivo Excel exportado correctamente:\n" + filePath);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Operación Cancelada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación Cancelada");
        }

    }//GEN-LAST:event_bt_exportarOrdinariaActionPerformed

    //BOTON MENU ELIMINAR TRIBUNALES DE UN CURSO
    private void bt_eliminarTribunalesCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_eliminarTribunalesCursoActionPerformed

        JComboBox<String> combo = new JComboBox<>();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combo.addItem(c);
        }

        Object[] mensaje = {
            "Selecciona el curso del que quieres ELIMINAR LOS TRIBUNALES:",
            combo
        };

        int resultado = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Seleccionar curso",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String seleccion = (String) combo.getSelectedItem();
            System.out.println("Curso seleccionado: " + seleccion);
            int confirmar = JOptionPane.showConfirmDialog(this, "Vas a ELIMINAR los tribunales del curso " + seleccion, "Aviso", JOptionPane.OK_CANCEL_OPTION);
            if (confirmar == JOptionPane.OK_OPTION) {
                ctrl_pt.eliminarPTCurso(seleccion);
                actualizar();
                JOptionPane.showMessageDialog(this, "Se han eliminado tribunales del curso " + seleccion);

            } else {
                JOptionPane.showMessageDialog(this, "Operación Cancelada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación Cancelada");
        }

    }//GEN-LAST:event_bt_eliminarTribunalesCursoActionPerformed

    //BOTON ELIMINAR TODOS LOS TRIBUNALES
    private void bt_eliminarTodosTribunalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_eliminarTodosTribunalesActionPerformed

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estas seguro de borrar TODOS los tribunales?", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION);
        if (confirmacion == JOptionPane.OK_OPTION) {
            ctrl_pt.eliminarPT();
            actualizar();
            JOptionPane.showMessageDialog(this, "Se han eliminado TODOS los tribunales");
        } else {
            JOptionPane.showMessageDialog(this, "La operacion se ha cancelado");
        }

    }//GEN-LAST:event_bt_eliminarTodosTribunalesActionPerformed

    //EXPORTAR TRIBUNALES EXTRAORDINARIA
    private void bt_exportarExtraordinariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_exportarExtraordinariaActionPerformed

        JComboBox<String> combo = new JComboBox<>();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combo.addItem(c);
        }

        Object[] mensaje = {
            "Selecciona el curso del que quieres exportar los tribunales de la EXTRAORDINARIA:",
            combo
        };

        int resultado = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Seleccionar curso",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String seleccion = (String) combo.getSelectedItem();
            System.out.println("Curso seleccionado: " + seleccion);
            int confirmar = JOptionPane.showConfirmDialog(this, "Vas a exportar los tribunales del curso " + seleccion, "Aviso", JOptionPane.OK_CANCEL_OPTION);
            if (confirmar == JOptionPane.OK_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo Excel");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    exportador.exportarTribunalesExtraordinarios(seleccion, filePath);

                    JOptionPane.showMessageDialog(this, "Archivo Excel exportado correctamente:\n" + filePath);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Operación Cancelada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación Cancelada");
        }

    }//GEN-LAST:event_bt_exportarExtraordinariaActionPerformed

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
            java.util.logging.Logger.getLogger(JDialog_Tribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Tribunales dialog = new JDialog_Tribunales(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenu bt_editarTribunales;
    private javax.swing.JMenuItem bt_eliminarTodosTribunales;
    private javax.swing.JMenuItem bt_eliminarTribunalesCurso;
    private javax.swing.JMenuItem bt_exportarExtraordinaria;
    private javax.swing.JMenuItem bt_exportarOrdinaria;
    private javax.swing.JMenuItem bt_exportarTodo;
    private javax.swing.JButton bt_filtrarTribunales;
    private javax.swing.JButton bt_generarTribunales;
    private javax.swing.JButton bt_quitarFiltro;
    private javax.swing.JMenu bt_verDepartamentos;
    private javax.swing.JMenu bt_verSuplentes;
    private javax.swing.JComboBox<String> combobox_cursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableProfesores;
    private javax.swing.JTable tableTribunales;
    private javax.swing.JLabel txt_contadorProfesores;
    private javax.swing.JTextField txt_curso;
    // End of variables declaration//GEN-END:variables
}
