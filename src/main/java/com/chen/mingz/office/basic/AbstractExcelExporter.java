package com.chen.mingz.office.basic;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author chenmingzhi
 * @date 2019-08-15
 */
public abstract class AbstractExcelExporter extends AbstractExporter implements ExcelExporter {

    protected Map<String, CellStyle> styles;

    protected Integer firstRow = 0;

    protected Integer secondRow = 1;

    protected Integer firstDataRow = 2;

    protected Integer zeroRow = 0;

    protected Integer firstColumn = 0;

    protected Integer secondColumn = 2;

    protected Integer zeroColumn = 0;

    protected Class<?> type;

    protected Map<String, Object> param;


    protected Workbook workbook;

    protected Integer fontSize;

    protected Integer defaultFontSize = 12;

    protected String defaultFontName = "宋体";

    protected CellStyle defaultStyle;

    public AbstractExcelExporter() {
        reload();
    }

    protected void reload() {
        this.workbook = new HSSFWorkbook();
        styles = ExcelCellStyle.getBasicStyle(workbook);
        defaultStyle = workbook.createCellStyle();
    }

    //添加CellStyle
    public void addCellStyle(String key, CellStyle style) {
        styles.put(key, style);
    }

    public void addCellStyleByFontName(String key, String fontName) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(defaultFontSize.shortValue());
        style.setFont(font);
        styles.put(key, style);
    }

    public void addCellStyleByFontSize(String key, Integer fontName) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(defaultFontName);
        font.setFontHeightInPoints(fontName.shortValue());
        style.setFont(font);
        styles.put(key, style);
    }

    //设置列宽度
    public void setColumnWidth(Sheet sheet, Integer column, Integer size) {
        setColumnWidth(sheet, Arrays.asList(column), Arrays.asList(size));
    }

    public void setColumnWidth(Sheet sheet, List<Integer> columns, List<Integer> sizes) {
        if (columns.size() != sizes.size()) {
            System.out.println("参数不对，sheet设置列宽度没有生效");
            return;
        }
        for (int i = 0, length = columns.size(); i < length; ++i) {
            sheet.setColumnWidth(columns.get(i), sizes.get(i));
        }
    }

    //设置行高度
    public void setRowHeight(Sheet sheet, Integer row, Integer size) {
        setRowHeight(sheet, Arrays.asList(row), Arrays.asList(size));
    }

    public void setRowHeight(Sheet sheet, List<Integer> rows, List<Integer> sizes) {
        if (rows.size() != sizes.size()) {
            System.out.println("参数不对，sheet设置行高度没有生效");
            return;
        }
        for (int i = 0, length = rows.size(); i < length; ++i) {
            sheet.getRow(rows.get(i)).setHeight(sizes.get(i).shortValue());
        }
    }


    //创建sheet
    public Sheet createSheet() {
        return createSheet(null);
    }

    public Sheet createSheet(String name) {
        Sheet sheet = null;
        if (name == null) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

    //创建row
    public Row createRow(Sheet sheet, int row) {
        if (sheet == null) {
            sheet = createSheet();
        }
        return sheet.createRow(row);
    }

    //创建Cell
    public Cell createCell(Sheet sheet, int row, int column) {
        if (sheet == null) {
            sheet = createSheet();
        }
        return createCell(sheet.getRow(row), column);
    }

    public Cell createCell(Row row, int column) {
        Cell cell = row.createCell(column);
        return cell;
    }

    public Cell createCell(Sheet sheet, int row, int column, Object value) {
        if (sheet == null) {
            sheet = createSheet();
        }
        Row var = sheet.getRow(row);
        if (var == null) {
            var = createRow(sheet, row);
        }
        return createCell(var, column, value);
    }

    public Cell createCell(Row row, int column, Object value) {
        Cell cell = row.createCell(column);
        if (value != null) {
            setCellValue(cell, value);
        }
        return cell;
    }

    public Cell createCell(Sheet sheet, int row, int column, CellStyle style) {
        if (sheet == null) {
            sheet = createSheet();
        }
        return createCell(sheet.getRow(row), column, style);
    }

    public Cell createCell(Row row, int column, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(style);
        return cell;
    }

    public Cell createCell(Sheet sheet, int row, int column, Object value, CellStyle style) {
        if (sheet == null) {
            sheet = createSheet();
        }
        Row var = sheet.getRow(row);
        if (var == null) {
            var = createRow(sheet, row);
        }
        return createCell(var, column, value, style);
    }

    public Cell createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(style);
        if (value != null) {
            setCellValue(cell, value);
        }
        return cell;
    }

    public Cell createCell(Sheet sheet, int row, int column, String style) {
        if (sheet == null) {
            sheet = createSheet();
        }
        return createCell(sheet.getRow(row), column, style);
    }

    public Cell createCell(Row row, int column, String style) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(styles.get(style) != null ? styles.get(style) : defaultStyle);
        return cell;
    }

    public Cell createCell(Sheet sheet, int row, int column, Object value, String style) {
        if (sheet == null) {
            sheet = createSheet();
        }
        Row var = sheet.getRow(row);
        if (var == null) {
            var = createRow(sheet, row);
        }
        return createCell(var, column, value, style);
    }

    public Cell createCell(Row row, int column, Object value, String style) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(styles.get(style) != null ? styles.get(style) : defaultStyle);
        if (value != null) {
            setCellValue(cell, value);
        }
        return cell;
    }

    public void createRegionCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        createRegionCell(sheet, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    //创建Excel指定Sheet的指定区域
    public void createRegionCell(Sheet sheet, CellRangeAddress region) {
        int firstR = region.getFirstRow();
        int lastR = region.getLastRow();
        int firstC = region.getFirstColumn();
        int lastC = region.getLastColumn();
        for (int i = firstR; i <= lastR; ++i) {
            Row row = sheet.createRow(i);
            for (int j = firstC; j <= lastC; ++j) {
                row.createCell(j);
            }
        }
    }

    //读取Cell
    public Cell getCell(Sheet sheet, int row, int column) {
        return sheet.getRow(row).getCell(column);
    }

    public Cell getCell(Row row, int column) {
        return row.getCell(column);
    }

    //设置Cell属性
    public void setCellStyle(Sheet sheet, short row, short column, CellStyle style) {
        setCellStyle(sheet.getRow(row), column, style);
    }

    public void setCellStyle(Row row, short column, CellStyle style) {
        setCellStyle(row.getCell(column), style);
    }

    public void setCellStyle(Cell cell, CellStyle style) {
        cell.setCellStyle(style);
    }

    public void setCellStyle(Sheet sheet, short row, short column, String style) {
        setCellStyle(sheet.getRow(row), column, style);
    }

    public void setCellStyle(Row row, short column, String style) {
        setCellStyle(row.getCell(column), style);
    }

    public void setCellStyle(Cell cell, String style) {
        cell.setCellStyle(styles.get(style) != null ? styles.get(style) : defaultStyle);
    }

    //设置区域范围内的单元格属性
    public void setRegionCellStyle(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style) {
        setRegionCellStyle(sheet, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), style);
    }

    public void setRegionCellStyle(Sheet sheet, CellRangeAddress region, CellStyle style) {
        int first = region.getFirstRow();
        int last = region.getLastRow();
        int top = region.getFirstColumn();
        int bottom = region.getLastColumn();
        for (int i = first; i <= last; ++i) {
            Row row = sheet.getRow(i);
            for (int j = top; j <= bottom; ++j) {
                Cell cell = row.getCell(j);
                cell.setCellStyle(style);
            }
        }
    }

    public void setRegionCellStyle(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String style) {
        setRegionCellStyle(sheet, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), style);
    }

    public void setRegionCellStyle(Sheet sheet, CellRangeAddress region, String style) {
        int first = region.getFirstRow();
        int last = region.getLastRow();
        int top = region.getFirstColumn();
        int bottom = region.getLastColumn();
        for (int i = first; i <= last; ++i) {
            Row row = sheet.getRow(i);
            for (int j = top; j <= bottom; ++j) {
                Cell cell = row.getCell(j);
                cell.setCellStyle(styles.get(style) != null ? styles.get(style) : defaultStyle);
            }
        }
    }


    //设置Cell值
    public Cell setCellValue(Sheet sheet, int row, int column, Object value, String style) {
        setCellStyle(sheet, (short) row, (short) column, style);
        return setCellValue(getCell(sheet, row, column), value);
    }

    public Cell setCellValue(Sheet sheet, int row, int column, Object value, CellStyle style) {
        setCellStyle(sheet, (short) row, (short) column, style);
        return setCellValue(getCell(sheet, row, column), value);
    }

    public Cell setCellValue(Sheet sheet, int row, int column, Object value) {
        return setCellValue(getCell(sheet, row, column), value);
    }

    public Cell setCellValue(Row row, int column, Object value, String style) {
        setCellStyle(row, (short) column, style);
        return setCellValue(getCell(row, column), value);
    }

    public Cell setCellValue(Row row, int column, Object value, CellStyle style) {
        setCellStyle(row, (short) column, style);
        return setCellValue(getCell(row, column), value);
    }

    public Cell setCellValue(Row row, int column, Object value) {
        return setCellValue(getCell(row, column), value);
    }

    public Cell setCellValue(Cell cell, Object value) {
        if (value != null) {
            Class<?> type = value.getClass();
            if (type == Integer.class || type == int.class) {
                cell.setCellValue((Integer) value);
            } else if (type == Double.class || type == double.class) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Calendar) {
                cell.setCellValue((Calendar) value);
            } else if (value instanceof java.util.Date) {
                cell.setCellValue((java.util.Date) value);
            } else if (value instanceof RichTextString) {
                cell.setCellValue((RichTextString) value);
            } else if (value instanceof BigDecimal) {
                Double temp = ((BigDecimal) value).doubleValue();
                cell.setCellValue(temp);
            } else {
                cell.setCellValue(value.toString());
            }
        } else {
            cell.setCellValue("");
        }

        return cell;
    }


    //合并单元格，并设置单元格为统一属性
    public void mergeCell(Sheet sheet, CellRangeAddress region) {
        mergeCell(sheet, region, "");
    }

    public void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (firstRow < lastRow || firstCol < lastCol) {
            mergeCell(sheet, firstRow, lastRow, firstCol, lastCol, "");
        }
    }

    public void mergeCell(Sheet sheet, CellRangeAddress region, String style) {
        if (style == null || style.equals("")) {
            style = "default";
        }
        setRegionCellStyle(sheet, region, style);
        sheet.addMergedRegion(region);
    }

    public void mergeCell(Sheet sheet, CellRangeAddress region, CellStyle style) {
        if (style == null) {
            style = defaultStyle;
        }
        setRegionCellStyle(sheet, region, style);  //设置区域范围内的单元格属性
        sheet.addMergedRegion(region);
    }

    public void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String style) {
        if (style == null || style.equals("")) {
            style = "default";
        }
        if (firstRow < lastRow || firstCol < lastCol) {
            mergeCell(sheet, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), style);
        }
    }

    public void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style) {
        if (style == null) {
            style = defaultStyle;
        }
        mergeCell(sheet, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), style);
    }

    //设置freezepane区域
    //int colSplit, int rowSplit
    public void frezeRegion(Sheet sheet, int i, int i1) {
        sheet.createFreezePane(i, i1);
    }

    //int colSplit, int rowSplit, int leftmostColumn, int topRow
    //前两个参数是你要用来拆分的列数和行数。后两个参数是下面窗口的可见象限
    // 其中第三个参数是右边区域可见的左边列数，第四个参数是下面区域可见的首行
    public void frezeRegion(Sheet sheet, int i, int i1, int i2, int i3) {
        sheet.createFreezePane(i, i1, i2, i3);
    }

    //设置
    public void regionCircle(Sheet sheet) {

    }
}
