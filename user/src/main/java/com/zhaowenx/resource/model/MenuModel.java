package com.zhaowenx.resource.model;

/**
 * Created by zhaowenx on 2018/10/22.
 */
public class MenuModel {
    private String text;
    private String icon;
    private Object subset;
    private String href;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Object getSubset() {
        return subset;
    }

    public void setSubset(Object subset) {
        this.subset = subset;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", subset=" + subset +
                ", href='" + href + '\'' +
                '}';
    }
}
