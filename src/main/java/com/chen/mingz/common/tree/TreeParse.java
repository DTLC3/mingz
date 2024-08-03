package com.chen.mingz.common.tree;

import com.alibaba.fastjson.JSONObject;
import com.chen.mingz.common.tree.annotation.TreeCore;
import com.chen.mingz.common.tree.annotation.TreeLeaf;
import com.chen.mingz.common.utils.CBeanUtils;
import com.chen.mingz.common.utils.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by chenmingzhi on 18/2/28.
 */
public class TreeParse {

    public static List<JSONObject> transform(List<? extends Object> list) {
        if (list != null && list.size() > 0) {
            Object object = list.get(0);
            Class<?> clazz = object.getClass();
            return transform(list, clazz);
        }
        return new ArrayList<>();
    }


    public static List<JSONObject> transform(List<? extends Object> list, Class<?> clazz) {
        List<JSONObject> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            if (clazz.isAnnotationPresent(TreeCore.class)) {
                TreeCore treeCore = clazz.getAnnotation(TreeCore.class);
                String order = treeCore.order();
                Field[] fields = clazz.getDeclaredFields();
                Map<String, String> leftToName = new HashMap<>();
                List<Pair<Integer, String>> include = new ArrayList<>();
                for (Field field : fields) {
                    TreeLeaf treeLeaf = field.getAnnotation(TreeLeaf.class);
                    if (treeLeaf != null) {
                        if (treeLeaf.serialize()) {
                            leftToName.put(treeLeaf.name(), field.getName());
                            include.add(Pair.of(treeLeaf.order(), treeLeaf.name()));
                        }
                    } else {
                        leftToName.put(field.getName(), field.getName());
                        include.add(Pair.of(-1, field.getName()));
                    }
                }
                Collections.sort(include, new Comparator<Pair<Integer, String>>() {
                    @Override
                    public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                        Integer order1 = o1.getFirst();
                        Integer order2 = o2.getFirst();
                        return order1 - order2;
                    }
                });
                try {
                    List<TreeChildren> data = convertToTreeChildren(list, treeCore);
                    if (order != null && !"".equals(order.trim())) {
                        orderTree(data, order);
                    }
                    result = renderJson(data, treeCore, include, leftToName);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    private static void orderTree(List<TreeChildren> list, String order) {
        if (list != null && list.size() > 0) {
            list.sort((one, two) -> {
                Object rankOne = one.getNode().get(order);
                Object rankTwo = two.getNode().get(order);
                return Integer.valueOf(rankOne.toString()) - Integer.valueOf(rankTwo.toString());
            });
            list.forEach((leaf) -> {
                if (leaf.getChildren() != null && leaf.getChildren().size() > 0) {
                    orderTree(leaf.getChildren(), order);
                }
            });
        }
    }

    private static List<JSONObject> renderJson(List<TreeChildren> data,
                                               TreeCore treeCore,
                                               List<Pair<Integer, String>> include,
                                               Map<String, String> leftToName) {
        List<JSONObject> json = new ArrayList<>();
        String children = treeCore.child();
        for (TreeChildren node : data) {
            Map<String, Object> map = node.getNode();
            JSONObject left = new JSONObject();
            for (Pair<Integer, String> pair : include) {
                String name = pair.getSecond();
                String fieldName = leftToName.get(name);
                Object value = map.get(fieldName);
                left.put(name, value);
            }
            left.put(children, renderJson(node.getChildren(), treeCore, include, leftToName));
            json.add(left);
        }
        return json;
    }


    private static List<TreeChildren> convertToTreeChildren(List<? extends Object> list, TreeCore treeCore) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<TreeChildren> result = new ArrayList<>();
        String id = treeCore.id();
        String parentId = treeCore.parentId();
        Map<Object, TreeChildren> all = new HashMap<>();
        List<Map<String, Object>> treeList = new ArrayList<>();
        for (Object object : list) {
            Map<String, Object> map = CBeanUtils.transBean2Map(object);
            treeList.add(map);
            TreeChildren node = new TreeChildren(map);
            all.put(map.get(id), node);
        }
        for (Map<String, Object> node : treeList) {
            Object idValue = node.get(id);
            TreeChildren current = all.get(idValue);
            Object parentIdValue = current.getNode().get(parentId); // 父节点ID的值
            TreeChildren father = all.get(parentIdValue);
            if (father == null) { //不存在父节点，则为顶级节点
                result.add(current);
            } else {
                father.addChildren(current);
            }
        }
        return result;
    }


    private static void sorted(List<TreeChildren> list) {
        list.sort((first, second) -> {
            if (first.getNode().get("order") != null && second.getNode().get("order") != null) {
                Integer order0 = Integer.valueOf(first.getNode().get("order").toString());
                Integer order1 = Integer.valueOf(second.getNode().get("order").toString());
                return order0.compareTo(order1);
            }
            return 1;
        });
        for (TreeChildren children : list) {
            sorted(children.getChildren());
        }
    }

    public static List<TreeChildren> parse(List list, Class<?> original) {
        List<TreeChildren> result = new ArrayList();
        if (list != null && list.size() > 0) {
            if (original.isAnnotationPresent(TreeCore.class)) {
                TreeCore treeCore = original.getAnnotation(TreeCore.class);
                try {
                    result = convertToTreeChildren(list, treeCore);
                    sorted(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
