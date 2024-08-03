package com.chen.mingz.common.auto.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuerySql {


    //读取MySQL数据库表名
    public static String mysqlName(String url) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(url);
        String port = "3306";
        if (matcher.find()) {
            port = matcher.group();
        }
        Integer mark = url.indexOf("?");
        if (mark != -1) {
            url = url.substring(0, mark);
        }
        Integer portIndex = url.indexOf(port);
        String name = url.substring(portIndex + port.length() + 1);
        return name;
    }

    public static String queryPage(String url, String database, String sql) {
        String query = "";
        String type = dataType(url, database);
        switch (type) {
            case "MYSQL":
                query = "select * from (" + sql + ") tmp limit 1";
                break;
            case "ORACLE":
                query = "select * from (" + sql + ") tmp where rownum < 2";
                break;
            case "SQLSERVER":
                query = "select  top 1 *  from (" + sql + ") tmp ";
                break;
            default:
                break;
        }
        return query;
    }

    /**
     * 返回数据库类型
     *
     * @param url
     * @param database
     * @return
     */
    public static String dataType(String url, String database) {
        String type = "";
        if (database != null && !"".equals(database.trim())) {
            return database.toUpperCase();
        }
        if (url != null && !"".equals(url.trim())) {
            Integer mysql = url.indexOf("mysql");
            Integer oracle = url.indexOf("oracle");
            Integer sqlserver = url.indexOf("sqlserver");
            if (mysql != -1) {
                type = "MYSQL";
            }
            if (oracle != -1) {
                type = "ORACLE";
            }
            if (sqlserver != -1) {
                type = "SQLSERVER";
            }
        }
        return type;
    }

    public static String inQuery(String url, String database) {
        String query = "", baseName = "";
        if (database != null && !"".equals(database.trim())) {
            switch (database.toUpperCase()) {
                case "MYSQL":
                    query = QuerySql.mysql;
                    baseName = QuerySql.mysqlName(url);
                    query = String.format(query, baseName);
                    break;
                case "ORACLE":
                    query = QuerySql.oracle;
                    break;
                case "SQLSERVER":
                    query = QuerySql.sqlserver;
                    break;
                default:
                    break;
            }
        }
        return query;
    }

    public static String queryExist(String database) {
        String query = "ORACLE";
        if (database != null && !"".equals(database.trim())) {
            switch (database.toUpperCase()) {
                case "MYSQL":
                    query = QuerySql.mysqlQuery;
                    break;
                case "ORACLE":
                    query = QuerySql.oracleQuery;
                    break;
                case "SQLSERVER":
                    query = QuerySql.sqlServerQuery;
                    break;
                default:
                    break;
            }
        }
        return query;
    }

    public static String createTable(String database) {
        String query = "ORACLE";
        if (database != null && !"".equals(database.trim())) {
            switch (database.toUpperCase()) {
                case "MYSQL":
                    query = QuerySql.mysqlCreate;
                    break;
                case "ORACLE":
                    query = QuerySql.oracleCreate;
                    break;
                case "SQLSERVER":
                    query = QuerySql.sqlServerCreate;
                    break;
                default:
                    break;
            }
        }
        return query;
    }

    private static String mysql = "select TABLE_NAME " +
            "from information_schema.TABLES " +
            "where TABLE_SCHEMA = '%s' ";

    private static String oracle = "select object_name from user_objects " +
            "where object_type in ('TABLE','table','VIEW','view')";


    private static String sqlserver = "select name from sysobjects where xtype='u' ";


    private static String oracleCreate = "create table cmz_search\n" +
            "(" +
            "  service varchar2(50)\n" +
            "   constraint cmz_search_pk\n" +
            "   primary key,\n" +
            "  inquire varchar2(3000) not null,\n" +
            "  include varchar2(200),\n" +
            "  create_time date,\n" +
            "  enable int,\n" +
            "  mark varchar2(100)\n" +
            ")";

    private static String oracleQuery = "select * from cmz_search where ROWNUM < 2";

    private static String mysqlCreate = "create table cmz_search\n" +
            "(\n" +
            "  service varchar(50) not null,\n" +
            "  inquire varchar(3000) not null,\n" +
            "  include varchar(200) null,\n" +
            "  create_time date null,\n" +
            "  enable int null,\n" +
            "  mark varchar(100) null,\n" +
            "  constraint cmz_search_pk\n" +
            "  primary key (service)\n" +
            ")";

    private static String mysqlQuery = " SELECT * from cmz_search limit 1 ";

    private static String sqlServerCreate = "create table cmz_search  " +
            "( " +
            "  inquire varchar(3000) not null ,  " +
            "  service varchar(50) not null,  " +
            "  include varchar(200) null,  " +
            "  create_time date null, " +
            "  enable int null " +
            ")  ";

    private static String sqlServerQuery = " SELECT top 1 *  from cmz_search ";


    public static String queryTable = " SELECT INQUIRE,SERVICE,INCLUDE,CREATE_TIME,ENABLE  from cmz_search WHERE ENABLE=1 ";

}
