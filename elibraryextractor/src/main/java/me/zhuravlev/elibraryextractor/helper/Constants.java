package me.zhuravlev.elibraryextractor.helper;

public class Constants {
    public final static String authorUrl = "https://www.elibrary.ru/author_items.asp?authorid=";
    public final static String articleUrl = "https://www.elibrary.ru/item.asp?id=";
    public final static String authorArticles = "#restab > tbody:nth-child(1)";
    public final static String authorArticlesPages = ".menurb";

    public final static String authorName = "#thepage > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > form:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(15) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > font:nth-child(1) > b:nth-child(1)";
    public final static String authorOrg = "#thepage > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > form:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(15) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > a:nth-child(4)";
    public final static String authorDep = "#thepage > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > form:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(15) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1)";

    public final static String articleName = ".bigtext";
    public final static String tooltip = "div.tooltip";
    public final static String articleAuthorsBlock = "body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > div:nth-child(3) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)";
    public final static String articleDescriptionBlock = "body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > div:nth-child(3) > table:nth-child(4) > tbody:nth-child(1)";

}
