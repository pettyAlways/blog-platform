package org.yingzuidou.platform.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class HtmlUtils {

    public static String delHTMLTag(String htmlStr){
        // 定义script的正则表达式
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        // 定义style的正则表达式
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式
        String regExHtml="<[^>]+>";

        Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        // 过滤script标签
        htmlStr = mScript.replaceAll("");

        Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        // 过滤style标签
        htmlStr = mStyle.replaceAll("");

        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher m_html = pHtml.matcher(htmlStr);
        // 过滤html标签
        htmlStr=m_html.replaceAll("");

        return htmlStr.trim();
    }

}
