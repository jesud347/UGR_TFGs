package Controladores;

import java.io.FileNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportadorExcelTribunales {

    private EntityManagerFactory emf;
    private EntityManager em;

    public ExportadorExcelTribunales() {
    }

    public ExportadorExcelTribunales(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //EXPORTAR TRIBUNALES DE UN DEPARTAMENTO
    public void exportarDepartamento(JTable tabla, String rutaArchivo, String cabecera) throws FileNotFoundException, IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Tribunales");

        // Crear una fila en la primera posición para la cabecera
        Row filaCabecera = hoja.createRow(0);  // Fila 0 para cabecera
        Cell celdaCabecera = filaCabecera.createCell(0); // Primer celda
        celdaCabecera.setCellValue("Tribunales del departamento de " + cabecera);

        // Estilo en negrita y tamaño de fuente para la cabecera
        CellStyle estiloCabecera = workbook.createCellStyle();
        Font fuenteCabecera = workbook.createFont();
        fuenteCabecera.setBold(true);
        fuenteCabecera.setFontHeightInPoints((short) 20); // Aumenta el tamaño de la fuente
        estiloCabecera.setFont(fuenteCabecera);

        // Centrado horizontal y vertical
        estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
        estiloCabecera.setVerticalAlignment(VerticalAlignment.CENTER);

        celdaCabecera.setCellStyle(estiloCabecera);

        // Obtener los datos de la tabla
        TableModel modelo = tabla.getModel();
        int filaExcel = 2;

        // Estilo general con bordes
        CellStyle estiloBordes = workbook.createCellStyle();
        estiloBordes.setBorderTop(BorderStyle.THIN);
        estiloBordes.setBorderBottom(BorderStyle.THIN);
        estiloBordes.setBorderLeft(BorderStyle.THIN);
        estiloBordes.setBorderRight(BorderStyle.THIN);

        // Estilo de fuente para el contenido (más grande)
        Font fuenteContenido = workbook.createFont();
        fuenteContenido.setFontHeightInPoints((short) 14); // Aumenta el tamaño de la fuente
        estiloBordes.setFont(fuenteContenido);

        // Centrado horizontal y vertical para contenido
        estiloBordes.setAlignment(HorizontalAlignment.CENTER);
        estiloBordes.setVerticalAlignment(VerticalAlignment.CENTER);

        // Estilo para el tribunal con fondo gris y bordes
        CellStyle estiloTribunal = workbook.createCellStyle();
        estiloTribunal.setBorderTop(BorderStyle.THIN);
        estiloTribunal.setBorderBottom(BorderStyle.THIN);
        estiloTribunal.setBorderLeft(BorderStyle.THIN);
        estiloTribunal.setBorderRight(BorderStyle.THIN);

        // Fondo gris para el tribunal
        estiloTribunal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  // Gris claro
        estiloTribunal.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Fuente más grande y centrado
        Font fuenteTribunal = workbook.createFont();
        fuenteTribunal.setBold(true);
        fuenteTribunal.setFontHeightInPoints((short) 16); // Aumenta el tamaño de la fuente para el tribunal
        estiloTribunal.setFont(fuenteTribunal);
        estiloTribunal.setAlignment(HorizontalAlignment.CENTER);
        estiloTribunal.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int fila = 0; fila < modelo.getRowCount(); fila++) {
            // Obtener datos de cada fila
            String tribunal = modelo.getValueAt(fila, 0).toString(); // Tribunal
            String profesor1 = modelo.getValueAt(fila, 2).toString();
            String profesor2 = modelo.getValueAt(fila, 3).toString();
            String profesor3 = modelo.getValueAt(fila, 4).toString();

            // Escribir nombre del tribunal con estilo de fondo gris y bordes
            Row filaTribunal = hoja.createRow(filaExcel++);
            Cell celdaTribunal = filaTribunal.createCell(0);
            celdaTribunal.setCellValue(tribunal);
            celdaTribunal.setCellStyle(estiloTribunal);

            // Escribir profesores con bordes
            hoja.createRow(filaExcel++).createCell(0).setCellValue(profesor1);
            hoja.getRow(filaExcel - 1).getCell(0).setCellStyle(estiloBordes);
            hoja.createRow(filaExcel++).createCell(0).setCellValue(profesor2);
            hoja.getRow(filaExcel - 1).getCell(0).setCellStyle(estiloBordes);
            hoja.createRow(filaExcel++).createCell(0).setCellValue(profesor3);
            hoja.getRow(filaExcel - 1).getCell(0).setCellStyle(estiloBordes);

            // Línea vacía entre tribunales
            filaExcel++;
        }

        hoja.setColumnWidth(0, 30000);  // Ajusta el tamaño de la primera columna (tribunales y profesores)

        // Guardar archivo
        FileOutputStream salida = new FileOutputStream(rutaArchivo);
        workbook.write(salida);
        workbook.close();
        System.out.println("Exportación completada: " + rutaArchivo);
    }

    //EXPORTAR TODO
    public void exportarTribunalesConProfesores(String curso, String rutaArchivo) {
        em = emf.createEntityManager();
        try {
            Map<String, String> SIGLAS_DEPARTAMENTOS = new HashMap<>();
            SIGLAS_DEPARTAMENTOS.put("PB", "Psicobiología");
            SIGLAS_DEPARTAMENTOS.put("EX", "Psicología Experimental");
            SIGLAS_DEPARTAMENTOS.put("EV", "Psicología Evolutiva y de la Educación");
            SIGLAS_DEPARTAMENTOS.put("ME", "Metodología de las Ciencias del Comportamiento");
            SIGLAS_DEPARTAMENTOS.put("PE", "Personalidad, Evaluación y Tratamiento Psicológico");
            SIGLAS_DEPARTAMENTOS.put("SO", "Psicología Social");

            String sql = "SELECT t.codTribunal, t.curso, t.extraordinaria, p.codProfesor, p.nombre, p.departamento, pt.rol "
                    + "FROM tribunales t "
                    + "JOIN profesores_tribunales pt ON t.codTribunal = pt.codTribunal "
                    + "JOIN profesores p ON pt.codProfesor = p.codProfesor "
                    + "WHERE t.curso = ?1 "
                    + "ORDER BY t.codTribunal, p.codProfesor";

            List<Object[]> resultados = em.createNativeQuery(sql).setParameter(1, curso).getResultList();

            Map<String, List<Object[]>> tribunalesAgrupados = new LinkedHashMap<>();
            for (Object[] fila : resultados) {
                String codTribunal = (String) fila[0];
                tribunalesAgrupados.computeIfAbsent(codTribunal, k -> new ArrayList<>()).add(fila);
            }

            Map<String, List<String>> departamentosConTribunales = new LinkedHashMap<>();
            for (String codTribunal : tribunalesAgrupados.keySet()) {
                String siglas = SIGLAS_DEPARTAMENTOS.keySet()
                        .stream()
                        .filter(codTribunal::contains)
                        .findFirst()
                        .orElse("??");

                String nombreDepartamento = SIGLAS_DEPARTAMENTOS.getOrDefault(siglas, "Suplentes");
                departamentosConTribunales.computeIfAbsent(nombreDepartamento, k -> new ArrayList<>()).add(codTribunal);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tribunales");

            CellStyle tituloStyle = workbook.createCellStyle();
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle subTituloStyle = workbook.createCellStyle();
            Font subTituloFont = workbook.createFont();
            subTituloFont.setBold(true);
            subTituloFont.setFontHeightInPoints((short) 12);
            subTituloStyle.setFont(subTituloFont);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle borderedCellStyle = workbook.createCellStyle();
            borderedCellStyle.setBorderBottom(BorderStyle.THIN);
            borderedCellStyle.setBorderTop(BorderStyle.THIN);
            borderedCellStyle.setBorderLeft(BorderStyle.THIN);
            borderedCellStyle.setBorderRight(BorderStyle.THIN);

            int rowIndex = 0;
            int totalTribunalesCurso = 0;

            for (Map.Entry<String, List<String>> departamentoEntry : departamentosConTribunales.entrySet()) {
                String nombreDepartamento = departamentoEntry.getKey();
                List<String> tribunales = departamentoEntry.getValue();

                Row tituloRow = sheet.createRow(rowIndex++);
                Cell tituloCell = tituloRow.createCell(0);
                tituloCell.setCellValue("TRIBUNALES DE " + nombreDepartamento.toUpperCase());
                tituloCell.setCellStyle(tituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(tituloRow.getRowNum(), tituloRow.getRowNum(), 0, 2));

                for (String codTribunal : tribunales) {
                    List<Object[]> filas = tribunalesAgrupados.get(codTribunal);
                    if (filas == null || filas.isEmpty()) {
                        continue;
                    }

                    // Obtener valor de extraordinaria
                    boolean esExtraordinaria = false;
                    Object extraordinariaObj = filas.get(0)[2];
                    if (extraordinariaObj instanceof Boolean) {
                        esExtraordinaria = (Boolean) extraordinariaObj;
                    } else if (extraordinariaObj instanceof Number) {
                        esExtraordinaria = ((Number) extraordinariaObj).intValue() != 0;
                    }

                    String tipoTribunal = esExtraordinaria ? "EXTRAORDINARIA" : "ORDINARIA";

                    Row subTituloRow = sheet.createRow(rowIndex++);
                    Cell cell1 = subTituloRow.createCell(0);
                    cell1.setCellValue("Código del tribunal: " + codTribunal);
                    cell1.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 0, 1));

                    Cell cell2 = subTituloRow.createCell(2);
                    cell2.setCellValue("Curso: " + filas.get(0)[1] + " - " + tipoTribunal);
                    cell2.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 2, 3));

                    Row encabezado = sheet.createRow(rowIndex++);
                    String[] headers = {"Código Profesor", "Nombre", "Departamento", "Rol"};
                    for (int i = 0; i < headers.length; i++) {
                        Cell headerCell = encabezado.createCell(i);
                        headerCell.setCellValue(headers[i]);
                        headerCell.setCellStyle(headerStyle);
                    }

                    for (Object[] fila : filas) {
                        Row filaExcel = sheet.createRow(rowIndex++);
                        for (int i = 3; i < fila.length; i++) {
                            Cell cell = filaExcel.createCell(i - 3);
                            cell.setCellValue(fila[i].toString());
                            cell.setCellStyle(borderedCellStyle);
                        }
                    }

                    rowIndex++;
                }

                Row contadorRow = sheet.createRow(rowIndex++);
                Cell contadorCell = contadorRow.createCell(0);
                contadorCell.setCellValue("Total de tribunales en " + nombreDepartamento + ": " + tribunales.size());
                contadorCell.setCellStyle(subTituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(contadorRow.getRowNum(), contadorRow.getRowNum(), 0, 6));

                rowIndex++;

                totalTribunalesCurso += tribunales.size();
            }

            Row totalCursoRow = sheet.createRow(rowIndex++);
            Cell totalCursoCell = totalCursoRow.createCell(0);
            totalCursoCell.setCellValue("TOTAL DE TRIBUNALES DEL CURSO " + curso + ": " + totalTribunalesCurso);
            totalCursoCell.setCellStyle(subTituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(totalCursoRow.getRowNum(), totalCursoRow.getRowNum(), 0, 6));

            for (int i = 0; i <= 6; i++) {
                if (i == 0) {
                    sheet.setColumnWidth(i, 6000);
                } else {
                    sheet.setColumnWidth(i, 11000);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(rutaArchivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exportando a Excel: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    //EXPORTAR TRIBUNALES ORDINARIA
    public void exportarTribunalesOrdinarios(String curso, String rutaArchivo) {
        em = emf.createEntityManager();
        try {
            Map<String, String> SIGLAS_DEPARTAMENTOS = new HashMap<>();
            SIGLAS_DEPARTAMENTOS.put("PB", "Psicobiología");
            SIGLAS_DEPARTAMENTOS.put("EX", "Psicología Experimental");
            SIGLAS_DEPARTAMENTOS.put("EV", "Psicología Evolutiva y de la Educación");
            SIGLAS_DEPARTAMENTOS.put("ME", "Metodología de las Ciencias del Comportamiento");
            SIGLAS_DEPARTAMENTOS.put("PE", "Personalidad, Evaluación y Tratamiento Psicológico");
            SIGLAS_DEPARTAMENTOS.put("SO", "Psicología Social");

            String sql = "SELECT t.codTribunal, t.curso, t.extraordinaria, p.codProfesor, p.nombre, p.departamento, pt.rol "
                    + "FROM tribunales t "
                    + "JOIN profesores_tribunales pt ON t.codTribunal = pt.codTribunal "
                    + "JOIN profesores p ON pt.codProfesor = p.codProfesor "
                    + "WHERE t.curso = ?1 AND (t.extraordinaria = 0 OR t.extraordinaria IS NULL) "
                    + "ORDER BY t.codTribunal, p.codProfesor";

            List<Object[]> resultados = em.createNativeQuery(sql).setParameter(1, curso).getResultList();

            Map<String, List<Object[]>> tribunalesAgrupados = new LinkedHashMap<>();
            for (Object[] fila : resultados) {
                String codTribunal = (String) fila[0];
                tribunalesAgrupados.computeIfAbsent(codTribunal, k -> new ArrayList<>()).add(fila);
            }

            Map<String, List<String>> departamentosConTribunales = new LinkedHashMap<>();
            for (String codTribunal : tribunalesAgrupados.keySet()) {
                String siglas = SIGLAS_DEPARTAMENTOS.keySet()
                        .stream()
                        .filter(codTribunal::contains)
                        .findFirst()
                        .orElse("??");

                String nombreDepartamento = SIGLAS_DEPARTAMENTOS.getOrDefault(siglas, "Suplentes");
                departamentosConTribunales.computeIfAbsent(nombreDepartamento, k -> new ArrayList<>()).add(codTribunal);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tribunales Ordinarios");

            CellStyle tituloStyle = workbook.createCellStyle();
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle subTituloStyle = workbook.createCellStyle();
            Font subTituloFont = workbook.createFont();
            subTituloFont.setBold(true);
            subTituloFont.setFontHeightInPoints((short) 12);
            subTituloStyle.setFont(subTituloFont);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle borderedCellStyle = workbook.createCellStyle();
            borderedCellStyle.setBorderBottom(BorderStyle.THIN);
            borderedCellStyle.setBorderTop(BorderStyle.THIN);
            borderedCellStyle.setBorderLeft(BorderStyle.THIN);
            borderedCellStyle.setBorderRight(BorderStyle.THIN);

            int rowIndex = 0;
            int totalTribunalesCurso = 0;

            for (Map.Entry<String, List<String>> departamentoEntry : departamentosConTribunales.entrySet()) {
                String nombreDepartamento = departamentoEntry.getKey();
                List<String> tribunales = departamentoEntry.getValue();

                Row tituloRow = sheet.createRow(rowIndex++);
                Cell tituloCell = tituloRow.createCell(0);
                tituloCell.setCellValue("TRIBUNALES ORDINARIOS DE " + nombreDepartamento.toUpperCase());
                tituloCell.setCellStyle(tituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(tituloRow.getRowNum(), tituloRow.getRowNum(), 0, 2));

                for (String codTribunal : tribunales) {
                    List<Object[]> filas = tribunalesAgrupados.get(codTribunal);
                    if (filas == null || filas.isEmpty()) {
                        continue;
                    }

                    Row subTituloRow = sheet.createRow(rowIndex++);
                    Cell cell1 = subTituloRow.createCell(0);
                    cell1.setCellValue("Código del tribunal: " + codTribunal);
                    cell1.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 0, 1));

                    Cell cell2 = subTituloRow.createCell(2);
                    cell2.setCellValue("Curso: " + filas.get(0)[1] + " - ORDINARIA");
                    cell2.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 2, 3));

                    Row encabezado = sheet.createRow(rowIndex++);
                    String[] headers = {"Código Profesor", "Nombre", "Departamento", "Rol"};
                    for (int i = 0; i < headers.length; i++) {
                        Cell headerCell = encabezado.createCell(i);
                        headerCell.setCellValue(headers[i]);
                        headerCell.setCellStyle(headerStyle);
                    }

                    for (Object[] fila : filas) {
                        Row filaExcel = sheet.createRow(rowIndex++);
                        for (int i = 3; i < fila.length; i++) {
                            Cell cell = filaExcel.createCell(i - 3);
                            cell.setCellValue(fila[i].toString());
                            cell.setCellStyle(borderedCellStyle);
                        }
                    }

                    rowIndex++;
                }

                Row contadorRow = sheet.createRow(rowIndex++);
                Cell contadorCell = contadorRow.createCell(0);
                contadorCell.setCellValue("Total de tribunales ordinarios en " + nombreDepartamento + ": " + tribunales.size());
                contadorCell.setCellStyle(subTituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(contadorRow.getRowNum(), contadorRow.getRowNum(), 0, 6));

                rowIndex++;

                totalTribunalesCurso += tribunales.size();
            }

            Row totalCursoRow = sheet.createRow(rowIndex++);
            Cell totalCursoCell = totalCursoRow.createCell(0);
            totalCursoCell.setCellValue("TOTAL DE TRIBUNALES ORDINARIOS DEL CURSO " + curso + ": " + totalTribunalesCurso);
            totalCursoCell.setCellStyle(subTituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(totalCursoRow.getRowNum(), totalCursoRow.getRowNum(), 0, 6));

            for (int i = 0; i <= 6; i++) {
                if (i == 0) {
                    sheet.setColumnWidth(i, 6000);
                } else {
                    sheet.setColumnWidth(i, 11000);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(rutaArchivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exportando tribunales ordinarios a Excel: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    //EXPORTAR TRIBUNALES EXTRAORDINARIA
    public void exportarTribunalesExtraordinarios(String curso, String rutaArchivo) {
        em = emf.createEntityManager();
        try {
            Map<String, String> SIGLAS_DEPARTAMENTOS = new HashMap<>();
            SIGLAS_DEPARTAMENTOS.put("PB", "Psicobiología");
            SIGLAS_DEPARTAMENTOS.put("EX", "Psicología Experimental");
            SIGLAS_DEPARTAMENTOS.put("EV", "Psicología Evolutiva y de la Educación");
            SIGLAS_DEPARTAMENTOS.put("ME", "Metodología de las Ciencias del Comportamiento");
            SIGLAS_DEPARTAMENTOS.put("PE", "Personalidad, Evaluación y Tratamiento Psicológico");
            SIGLAS_DEPARTAMENTOS.put("SO", "Psicología Social");

            String sql = "SELECT t.codTribunal, t.curso, t.extraordinaria, p.codProfesor, p.nombre, p.departamento, pt.rol "
                    + "FROM tribunales t "
                    + "JOIN profesores_tribunales pt ON t.codTribunal = pt.codTribunal "
                    + "JOIN profesores p ON pt.codProfesor = p.codProfesor "
                    + "WHERE t.curso = ?1 AND t.extraordinaria = 1 "
                    + "ORDER BY t.codTribunal, p.codProfesor";

            List<Object[]> resultados = em.createNativeQuery(sql).setParameter(1, curso).getResultList();

            Map<String, List<Object[]>> tribunalesAgrupados = new LinkedHashMap<>();
            for (Object[] fila : resultados) {
                String codTribunal = (String) fila[0];
                tribunalesAgrupados.computeIfAbsent(codTribunal, k -> new ArrayList<>()).add(fila);
            }

            Map<String, List<String>> departamentosConTribunales = new LinkedHashMap<>();
            for (String codTribunal : tribunalesAgrupados.keySet()) {
                String siglas = SIGLAS_DEPARTAMENTOS.keySet()
                        .stream()
                        .filter(codTribunal::contains)
                        .findFirst()
                        .orElse("??");

                String nombreDepartamento = SIGLAS_DEPARTAMENTOS.getOrDefault(siglas, "Suplentes");
                departamentosConTribunales.computeIfAbsent(nombreDepartamento, k -> new ArrayList<>()).add(codTribunal);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tribunales Extraordinarios");

            CellStyle tituloStyle = workbook.createCellStyle();
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle subTituloStyle = workbook.createCellStyle();
            Font subTituloFont = workbook.createFont();
            subTituloFont.setBold(true);
            subTituloFont.setFontHeightInPoints((short) 12);
            subTituloStyle.setFont(subTituloFont);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle borderedCellStyle = workbook.createCellStyle();
            borderedCellStyle.setBorderBottom(BorderStyle.THIN);
            borderedCellStyle.setBorderTop(BorderStyle.THIN);
            borderedCellStyle.setBorderLeft(BorderStyle.THIN);
            borderedCellStyle.setBorderRight(BorderStyle.THIN);

            int rowIndex = 0;
            int totalTribunalesCurso = 0;

            for (Map.Entry<String, List<String>> departamentoEntry : departamentosConTribunales.entrySet()) {
                String nombreDepartamento = departamentoEntry.getKey();
                List<String> tribunales = departamentoEntry.getValue();

                Row tituloRow = sheet.createRow(rowIndex++);
                Cell tituloCell = tituloRow.createCell(0);
                tituloCell.setCellValue("TRIBUNALES EXTRAORDINARIOS DE " + nombreDepartamento.toUpperCase());
                tituloCell.setCellStyle(tituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(tituloRow.getRowNum(), tituloRow.getRowNum(), 0, 2));

                for (String codTribunal : tribunales) {
                    List<Object[]> filas = tribunalesAgrupados.get(codTribunal);
                    if (filas == null || filas.isEmpty()) {
                        continue;
                    }

                    Row subTituloRow = sheet.createRow(rowIndex++);
                    Cell cell1 = subTituloRow.createCell(0);
                    cell1.setCellValue("Código del tribunal: " + codTribunal);
                    cell1.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 0, 1));

                    Cell cell2 = subTituloRow.createCell(2);
                    cell2.setCellValue("Curso: " + filas.get(0)[1] + " - EXTRAORDINARIA");
                    cell2.setCellStyle(subTituloStyle);
                    sheet.addMergedRegion(new CellRangeAddress(subTituloRow.getRowNum(), subTituloRow.getRowNum(), 2, 3));

                    Row encabezado = sheet.createRow(rowIndex++);
                    String[] headers = {"Código Profesor", "Nombre", "Departamento", "Rol"};
                    for (int i = 0; i < headers.length; i++) {
                        Cell headerCell = encabezado.createCell(i);
                        headerCell.setCellValue(headers[i]);
                        headerCell.setCellStyle(headerStyle);
                    }

                    for (Object[] fila : filas) {
                        Row filaExcel = sheet.createRow(rowIndex++);
                        for (int i = 3; i < fila.length; i++) {
                            Cell cell = filaExcel.createCell(i - 3);
                            cell.setCellValue(fila[i].toString());
                            cell.setCellStyle(borderedCellStyle);
                        }
                    }

                    rowIndex++;
                }

                Row contadorRow = sheet.createRow(rowIndex++);
                Cell contadorCell = contadorRow.createCell(0);
                contadorCell.setCellValue("Total de tribunales extraordinarios en " + nombreDepartamento + ": " + tribunales.size());
                contadorCell.setCellStyle(subTituloStyle);
                sheet.addMergedRegion(new CellRangeAddress(contadorRow.getRowNum(), contadorRow.getRowNum(), 0, 6));

                rowIndex++;

                totalTribunalesCurso += tribunales.size();
            }

            Row totalCursoRow = sheet.createRow(rowIndex++);
            Cell totalCursoCell = totalCursoRow.createCell(0);
            totalCursoCell.setCellValue("TOTAL DE TRIBUNALES EXTRAORDINARIOS DEL CURSO " + curso + ": " + totalTribunalesCurso);
            totalCursoCell.setCellStyle(subTituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(totalCursoRow.getRowNum(), totalCursoRow.getRowNum(), 0, 6));

            for (int i = 0; i <= 6; i++) {
                if (i == 0) {
                    sheet.setColumnWidth(i, 6000);
                } else {
                    sheet.setColumnWidth(i, 11000);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(rutaArchivo);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exportando tribunales extraordinarios a Excel: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
