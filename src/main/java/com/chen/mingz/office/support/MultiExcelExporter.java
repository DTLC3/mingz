package com.chen.mingz.office.support;

import com.chen.mingz.common.tree.TreeChildren;
import com.chen.mingz.office.basic.AbstractExcelExporter;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author chenmingzhi
 * @date 2019-10-21
 */
public class MultiExcelExporter extends AbstractExcelExporter {


    private void refresh() {
        firstRow = 0;
        firstColumn = 0;
        zeroRow = 0;
        secondRow = 0;
        firstDataRow = 1;
    }

    private void depth(TreeChildren root, Deque<TreeChildren> deque, StringBuilder builder) {
        if (root != null) {
            builder.append(root.getNode().get("name"));
            TreeChildren tag = new TreeChildren(new HashMap<>());
            tag.getNode().put("name", "#");
            deque.add(tag);
            depth(root.getChildren(), deque, builder);
        }
    }

    private void depth(List<TreeChildren> list, Deque<TreeChildren> deque, StringBuilder builder) {
        for (int i = 0, length = list.size() + 1; i < length; ++i) {
            if (i == length - 1) { //已经添加完所有当前节点的子节点到队列中
                TreeChildren first = deque.peek(); //读取当前第一个元素
                //判断当前节点是否为标记元素，用于判断广度遍历中当前一行的元素是否已经遍历完整
                if (first != null && first.getNode().get("name").toString().equals("#")) {
                    deque.pop();  //去掉标记元素
                    //同时向当前队列添加标记节点，用于表示这一行的数据到这里结束
                    TreeChildren tag = new TreeChildren(new HashMap<>());
                    tag.getNode().put("name", "#");
                    deque.add(tag);
                    builder.append("#");
                }
            } else {
                deque.add(list.get(i));
            }
        }
        if (deque.size() > 0) {
            TreeChildren pop = deque.pop();
            if (pop != null) {
                builder.append(pop.getNode().get("name").toString());
                depth(pop.getChildren(), deque, builder);
            }
        }
    }

    private int trees(List<TreeChildren> treeNodes) {
        int max = 0;
        //1.计算树最大深度
        for (TreeChildren node : treeNodes) { //计算顶级节点的所有节点的深度，取最大值
            Deque<TreeChildren> deque = new LinkedList<>();
            StringBuilder builder = new StringBuilder("");
            depth(node, deque, builder);
            String[] level = builder.toString().split("#");
            int deep = level.length;
            if (deep > max) {
                max = deep;
            }
        }
        return max > 0 ? max - 1 : max;
    }

    //叶子节点的个数
    private void leftCount(List<TreeChildren> list, List<Integer> length) {
        for (TreeChildren node : list) {
            if (node.getChildren().size() == 0) {
                length.add(0);
            } else {
                leftCount(node.getChildren(), length);
            }
        }
    }

    private void createTitle(Sheet sheet, List<TreeChildren> list, int row, int column) {
        int depth = trees(list);
        for (int index = 0, length = list.size(); index < length; ++index) {
            TreeChildren node = list.get(index);
            int childSize = node.getChildren().size();
            if (childSize > 0) {
                List<Integer> foliage = new ArrayList<>();
                leftCount(node.getChildren(), foliage);
                int count = foliage.size();
                mergeCell(sheet, row, row, column, column + count - 1);
                setCellValue(sheet, row, column, node.getNode().get("name").toString(), "orangeBig");
                createTitle(sheet, node.getChildren(), row + 1, column);
                column += count;
            } else {
                mergeCell(sheet, row, row + depth, column, column);
                setCellValue(sheet, row, column, node.getNode().get("name").toString(), "orangeBig");
                ++column;
            }
        }
    }

    @Override
    public void make(Object data) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("/Users/chenmingzhi/Desktop/cmz.xls");
        try {
            Map<String, Object> param = (Map<String, Object>) data;
            //   HttpServletResponse response = (HttpServletResponse) param.get("response");
//            String title = param.get("title").toString();
//            String filename = URLDecoder.decode(title, "UTF-8") + ".xls";
            //  fileOut = response.getOutputStream();
            //设置相应类型Application/octet-stream
            //      response.setContentType("Application/x-msdownload");
            //设置文件头信息，解决下载的文件文件名乱码问题
            //    String resName = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            //    response.addHeader("Content-Disposition", "attachment;filename=" + resName);
            Map<String, List<Map<String, String>>> sheetList = (Map<String, List<Map<String, String>>>) param.get("sheetList");
            for (Map.Entry<String, List<Map<String, String>>> map : sheetList.entrySet()) {
                String key = map.getKey();
                List<String> fields = (List<String>) param.get("fields");

                List<TreeChildren> treeChildren = (List<TreeChildren>) param.get(key);
                int depth = trees(treeChildren);
                Sheet sheet = createSheet(key);
                createRegionCell(sheet, firstRow, depth + 1, firstColumn, fields.size());
                createTitle(sheet, treeChildren, 0, 0);

//                int index = 0;
//                secondRow = 0;
//                firstDataRow = 1;
//                for (String label : columns) {
//                    setCellValue(sheet, secondRow, index, label, "orangeBig");
//                    ++index;
//                }
//                for (Map<String, ?> row : list) {
//                    int columnIndex = 0;
//                    for (String lable : fields) {
//                        setCellValue(sheet, firstDataRow, columnIndex++, row.get(lable), "common");
//                    }
//                    ++firstDataRow;
//                }
                refresh();
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
