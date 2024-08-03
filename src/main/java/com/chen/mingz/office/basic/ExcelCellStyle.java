package com.chen.mingz.office.basic;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenmingzhi
 * @date 2019-07-17
 */
public class ExcelCellStyle {

    private Font font;

    private String fontName = "宋体";

    private String dateFormat;

    private CellStyle style;

    private Integer fontHeightToPoint = 12;  //字体大小

    private HorizontalAlignment hAlignment = HorizontalAlignment.CENTER;

    private VerticalAlignment vAlignment = VerticalAlignment.CENTER;

    private BorderStyle borderTop = BorderStyle.THICK;

    private BorderStyle borderBottom = BorderStyle.THICK;

    private BorderStyle borderLeft = BorderStyle.THICK;

    private BorderStyle borderRight = BorderStyle.THICK;


    public static Map<String, CellStyle> getBasicStyle(Workbook wb) {

        Map<String, CellStyle> styles = new HashMap<>();

        DataFormat digit = wb.createDataFormat();
        DataFormat percent = wb.createDataFormat();
        CellStyle style;

        //默认CellStyle
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        styles.put("default", style);


        //10号宋体 水平居右 垂直居中
        Font digitFontP = wb.createFont();
        digitFontP.setFontHeightInPoints((short) 10);
        digitFontP.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(digitFontP);
        styles.put("digitP", style);
        //
        Font digitCost = wb.createFont();
        digitCost.setFontHeightInPoints((short) 10);
        digitCost.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("￥0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(digitCost);
        styles.put("digitCost", style);
        //10号宋体 水平居右 垂直居中
        Font digitFont = wb.createFont();
        digitFont.setFontHeightInPoints((short) 10);
        digitFont.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(digitFont);
        styles.put("digit", style);
        //10号宋体，水平居右，垂直居中 黄色 费用
        Font yellowDigitCost = wb.createFont();
        yellowDigitCost.setFontHeightInPoints((short) 10);
        yellowDigitCost.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("￥0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(yellowDigitCost);
        styles.put("yellowDigitCost", style);

        //10号宋体 水平居右 垂直居中 黄色 内容主体 百分比
        Font yellowDigitP = wb.createFont();
        yellowDigitP.setFontHeightInPoints((short) 10);
        yellowDigitP.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(yellowDigitP);
        styles.put("yelloDigitP", style);
        //10号宋体 水平居右 垂直居中 黄色 内容主体 (小计那一行数字)
        Font yellowDigit = wb.createFont();
        yellowDigit.setFontHeightInPoints((short) 10);
        yellowDigit.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(yellowDigit);
        styles.put("yelloDigit", style);

        //11号宋体 水平居中  垂直居中
        Font commonFont = wb.createFont();
        commonFont.setFontHeightInPoints((short) 11);
        commonFont.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(commonFont);
        styles.put("common", style);

        //11号宋体 水平居中  垂直居中
        Font tollCommon = wb.createFont();
        tollCommon.setFontHeightInPoints((short) 11);
        tollCommon.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(tollCommon);
        styles.put("tollCommon", style);

        //11号宋体 水平居中  垂直居下 黄色 内容主体
        Font numericTitle = wb.createFont();
        numericTitle.setFontHeightInPoints((short) 11);
        numericTitle.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(numericTitle);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("yellowBig", style);

        //10号宋体 水平居右垂直居中 橘色
        Font sumFontCost = wb.createFont();
        sumFontCost.setFontHeightInPoints((short) 10);
        sumFontCost.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("￥0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(sumFontCost);
        styles.put("orangeDigitCost", style);
        //10号宋体 水平居右 垂直居中 橘色百分比
        Font sumFontP = wb.createFont();
        sumFontP.setFontHeightInPoints((short) 10);
        sumFontP.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(sumFontP);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("orangeDigitP", style);
        //10号宋体 水平居右 垂直居中 橘色
        Font sumFont = wb.createFont();
        sumFont.setFontHeightInPoints((short) 10);
        sumFont.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(sumFont);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("orangeDigit", style);

        //11号宋体 水平居中  垂直居下 橘色
        Font sumFontTitle = wb.createFont();
        sumFontTitle.setFontHeightInPoints((short) 11);
        sumFontTitle.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setFont(sumFontTitle);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("orangeBig", style);

        //11号宋体 wrap= true 水平居中 垂直居中 自动换行 收费区域
        Font tollFont = wb.createFont();
        tollFont.setFontHeightInPoints((short) 11);
        tollFont.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        style.setFont(tollFont);
        styles.put("tollArea", style);
        //12号Times New Roman 水平居右 垂直居中 内容主体 百分比
        Font valueP = wb.createFont();
        valueP.setFontHeightInPoints((short) 12);
        valueP.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(valueP);
        styles.put("valueP", style);
        //黑体 15 水平居中 垂直居中
        Font title = wb.createFont();
        title.setFontHeightInPoints((short) 15);
        title.setFontName("黑体");
        title.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(title);
        styles.put("title", style);

        //宋体 16 水平居中 垂直居中
        Font sumTitle = wb.createFont();
        sumTitle.setFontHeightInPoints((short) 16);
        sumTitle.setFontName("宋体");
        sumTitle.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumTitle);
        styles.put("sumTitle", style);

        //宋体 9 水平居中 垂直居中
        Font sumDigit = wb.createFont();
        sumDigit.setFontHeightInPoints((short) 9);
        sumDigit.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumDigit);
        styles.put("sumDigit", style);

        Font sumCommon = wb.createFont();
        sumCommon.setFontHeightInPoints((short) 9);
        sumCommon.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumCommon);
        styles.put("sumCommon", style);

        Font sumPercent = wb.createFont();
        sumPercent.setFontHeightInPoints((short) 9);
        sumPercent.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(percent.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumPercent);
        styles.put("sumPercent", style);

        //9号宋体 水平居中  垂直居中 常规
        Font sumYellowG = wb.createFont();
        sumYellowG.setFontHeightInPoints((short) 9);
        sumYellowG.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumYellowG);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("sumYellowG", style);

        //9号宋体 水平居中  垂直居中 数字
        Font sumYellowD = wb.createFont();
        sumYellowD.setFontHeightInPoints((short) 9);
        sumYellowD.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumYellowD);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("sumYellowD", style);

        //9号宋体 水平居中  垂直居中 百分比
        Font sumYellowP = wb.createFont();
        sumYellowP.setFontHeightInPoints((short) 9);
        sumYellowP.setFontName("宋体");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sumYellowP);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("sumYellowP", style);

        //20号华文仿宋 水平居中 垂直居中 黑体
        Font sontFont = wb.createFont();
        sontFont.setFontHeightInPoints((short) 20);
        sontFont.setFontName("华文仿宋");
        sontFont.setBold(true);
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(sontFont);
        styles.put("yearTitle", style);
        //12号Times New Roman 水平居中 垂直居中
        Font general = wb.createFont();
        general.setFontHeightInPoints((short) 12);
        general.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.NONE);
        style.setFont(general);
        styles.put("centerRight", style);

        Font sign = wb.createFont();
        sign.setFontHeightInPoints((short) 12);
        sign.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.NONE);
        style.setFont(sign);
        styles.put("sign", style);
        //12号Times New Roman 水平居中 垂直居中
        Font digitNewRoman = wb.createFont();
        digitNewRoman.setFontHeightInPoints((short) 12);
        digitNewRoman.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(digitNewRoman);
        styles.put("general", style);

        //12号Times New Roman 水平居右 垂直居中
        Font NewRomanTwelve = wb.createFont();
        NewRomanTwelve.setFontHeightInPoints((short) 12);
        NewRomanTwelve.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(NewRomanTwelve);
        styles.put("valueG", style);

        //12号Times New Roman 水平居右 垂直居中
        Font alignLeft = wb.createFont();
        alignLeft.setFontHeightInPoints((short) 12);
        alignLeft.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(alignLeft);
        styles.put("alignLeft", style);

        Font tollFontNew = wb.createFont();
        tollFontNew.setFontHeightInPoints((short) 12);
        tollFontNew.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        style.setFont(tollFontNew);
        styles.put("captain", style);

        //11号宋体 水平居中  垂直居中
        Font commonFontEl = wb.createFont();
        commonFontEl.setFontHeightInPoints((short) 12);
        commonFontEl.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(commonFontEl);
        styles.put("border", style);

        Font tollDouble = wb.createFont();
        tollDouble.setFontHeightInPoints((short) 12);
        tollDouble.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(tollDouble);
        styles.put("topDouble", style);

        Font topLeft = wb.createFont();
        topLeft.setFontHeightInPoints((short) 12);
        topLeft.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(topLeft);
        styles.put("topLeft", style);

        Font topRight = wb.createFont();
        topRight.setFontHeightInPoints((short) 12);
        topRight.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DOUBLE);
        style.setFont(topLeft);
        styles.put("topRight", style);

        Font bottom = wb.createFont();
        bottom.setFontHeightInPoints((short) 12);
        bottom.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASH_DOT);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DASHED);
        style.setFont(bottom);
        styles.put("bottomDouble", style);

        Font bottomLeft = wb.createFont();
        bottomLeft.setFontHeightInPoints((short) 12);
        bottomLeft.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.NONE);
        style.setFont(bottomLeft);
        styles.put("bottomLeft", style);

        Font RightFont = wb.createFont();
        RightFont.setFontHeightInPoints((short) 12);
        RightFont.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setDataFormat(digit.getFormat("0%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.DASHED);
        style.setBorderBottom(BorderStyle.DASHED);
        style.setBorderLeft(BorderStyle.DASHED);
        style.setBorderRight(BorderStyle.DOUBLE);
        style.setFont(RightFont);
        styles.put("RightDouble", style);

        Font RigthDTopT = wb.createFont();
        RigthDTopT.setFontHeightInPoints((short) 12);
        RigthDTopT.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.DOUBLE);
        style.setWrapText(true);
        style.setFont(RigthDTopT);
        styles.put("RigthDTopT", style);


        Font LeftThick = wb.createFont();
        LeftThick.setFontHeightInPoints((short) 12);
        LeftThick.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.NONE);
        style.setFont(LeftThick);
        styles.put("LeftThick", style);

        Font LeftGener = wb.createFont();
        LeftGener.setFontHeightInPoints((short) 12);
        LeftGener.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(LeftGener);
        styles.put("LeftGener", style);

        Font RightBottom = wb.createFont();
        RightBottom.setFontHeightInPoints((short) 12);
        RightBottom.setFontName("Times New Roman");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.DOUBLE);
        style.setFont(RightBottom);
        styles.put("RightBottom", style);

        return styles;
    }

}
