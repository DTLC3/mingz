package com.chen.mingz.office.basic;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @author chenmingzhi
 * @date 2019-08-15
 */
public interface ExcelExporter extends Exporter {

    //添加CellStyle
    void addCellStyle(String key, CellStyle style);

    void addCellStyleByFontName(String key, String fontName);

    void addCellStyleByFontSize(String key, Integer fontName);

    //设置列宽度
    void setColumnWidth(Sheet sheet, Integer column, Integer size);

    void setColumnWidth(Sheet sheet, List<Integer> columns, List<Integer> sizes);

    //设置行高度
    void setRowHeight(Sheet sheet, Integer row, Integer size);

    void setRowHeight(Sheet sheet, List<Integer> rows, List<Integer> sizes);


    //创建sheet
    Sheet createSheet();

    Sheet createSheet(String name);

    //创建row
    Row createRow(Sheet sheet, int row);

    //创建Cell
    Cell createCell(Sheet sheet, int row, int column);

    Cell createCell(Row row, int column);

    Cell createCell(Sheet sheet, int row, int column, Object value);

    Cell createCell(Row row, int column, Object value);

    Cell createCell(Sheet sheet, int row, int column, CellStyle style);

    Cell createCell(Row row, int column, CellStyle style);

    Cell createCell(Sheet sheet, int row, int column, Object value, CellStyle style);

    Cell createCell(Row row, int column, Object value, CellStyle style);

    Cell createCell(Sheet sheet, int row, int column, String style);

    Cell createCell(Row row, int column, String style);

    Cell createCell(Sheet sheet, int row, int column, Object value, String style);

    Cell createCell(Row row, int column, Object value, String style);

    //创建Excel指定Sheet的指定区域
    void createRegionCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol);

    void createRegionCell(Sheet sheet, CellRangeAddress region);

    //读取Cell
    Cell getCell(Sheet sheet, int row, int column);

    Cell getCell(Row row, int column);

    //设置Cell属性
    void setCellStyle(Sheet sheet, short row, short column, CellStyle style);

    void setCellStyle(Row row, short column, CellStyle style);


    void setCellStyle(Sheet sheet, short row, short column, String style);

    void setCellStyle(Row row, short column, String style);

    void setCellStyle(Cell cell, CellStyle style);

    void setCellStyle(Cell cell, String style);

    //设置区域范围内的单元格属性
    void setRegionCellStyle(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style);

    void setRegionCellStyle(Sheet sheet, CellRangeAddress region, CellStyle style);

    void setRegionCellStyle(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String style);

    void setRegionCellStyle(Sheet sheet, CellRangeAddress region, String style);

    //设置Cell值
    Cell setCellValue(Sheet sheet, int row, int column, Object value, String style);

    Cell setCellValue(Sheet sheet, int row, int column, Object value, CellStyle style);

    Cell setCellValue(Sheet sheet, int row, int column, Object value);

    Cell setCellValue(Row row, int column, Object value, String style);

    Cell setCellValue(Row row, int column, Object value, CellStyle style);

    Cell setCellValue(Row row, int column, Object value);

    Cell setCellValue(Cell cell, Object value);

    //合并单元格，并设置单元格为统一属性

    void mergeCell(Sheet sheet, CellRangeAddress region);

    void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol);

    void mergeCell(Sheet sheet, CellRangeAddress region, String style);

    void mergeCell(Sheet sheet, CellRangeAddress region, CellStyle style);

    void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String style);

    void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style);

    //设置freezepane区域
    void frezeRegion(Sheet sheet, int i, int i1);

    void frezeRegion(Sheet sheet, int i, int i1, int i2, int i3);
}