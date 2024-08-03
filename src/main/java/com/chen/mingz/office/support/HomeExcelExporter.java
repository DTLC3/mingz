package com.chen.mingz.office.support;

import com.chen.mingz.office.basic.AbstractExcelExporter;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenmingzhi
 * @date 2019-08-15
 */
public class HomeExcelExporter extends AbstractExcelExporter {

    @Override
    public void make(Object data) throws IOException {
        OutputStream fileOut = null;
        try {
            Map<String, Object> param = (Map<String, Object>) data;
            HttpServletResponse response = (HttpServletResponse) param.get("response");
            String title = param.get("title").toString();
            String sheetName = param.get("sheet") != null ? param.get("sheet").toString() : "CDRG";
            String filename = URLDecoder.decode(title, "UTF-8") + ".xls";
            fileOut = response.getOutputStream();
            //设置相应类型Application/octet-stream
            response.setContentType("Application/x-msdownload");
            //设置文件头信息，解决下载的文件文件名乱码问题
            String resName = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + resName);
            List<String> fields = (List<String>) param.get("fields");
            List<Map<String, String>> list = (List<Map<String, String>>) param.get("list");
            List<String> columns = (List<String>) param.get("columns");
            List<Integer> gaps = (List<Integer>) param.get("gaps");
            Integer freezeRow = param.get("freezeRow") != null ? Integer.valueOf(param.get("freezeRow").toString()) : 2;
            Integer freezeColumn = param.get("freezeColumn") != null ? Integer.valueOf(param.get("freezeColumn").toString()) : 0;
            Integer columnLength = fields.size() - 1;          //一共有几列数据
            Integer rowLength = list.size() + 2;                //一共有几行还要加上标题和列描述行
            Sheet sheet = createSheet(sheetName);
            sheet.createFreezePane(freezeColumn, freezeRow);
            createRegionCell(sheet, firstRow, rowLength, firstColumn, columnLength);
            mergeCell(sheet, firstRow, zeroRow, firstColumn, columnLength, styles.get("title"));
            setCellValue(sheet.getRow(firstRow).getCell(firstColumn), title);
            Integer basicColumnSize = sheet.getColumnWidth(firstRow); //默认生成的列宽度
            Short basicRowHeight = sheet.getRow(firstRow).getHeight(); //默认生成的行高度
            int index = 0;
            //列名
            for (String name : columns) {
                setCellValue(sheet, secondRow, index, name, "orangeBig");
                ++index;
            }
            List<Integer> widths = new ArrayList<>();
            List<Integer> indexs = new ArrayList<>();
            index = 0;
            if (gaps != null) {
                for (Integer gap : gaps) {
                    widths.add(gap * basicColumnSize);
                    indexs.add(index);
                    ++index;
                }
            }
            setColumnWidth(sheet, indexs, widths);
            for (Map<String, ?> row : list) {
                int columnIndex = 0;
                for (String lable : fields) {
                    setCellValue(sheet, firstDataRow, columnIndex, row.get(lable), "common");
                    ++columnIndex;
                }
                ++firstDataRow;
            }
            workbook.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

}
