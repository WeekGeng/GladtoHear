package com.yieryi.gladtohear.bean.commodity;

import com.yieryi.gladtohear.tools.recycleviewanimation.effects.StandardEffect;

/**
 * Created by Administrator on 2015/9/28 0028.
 */
public class SelRecord {
    private int position;
    private int index;
    private String title;
    private String hid;
    private int selWhich;

    public int getSelWhich() {
        return selWhich;
    }

    public void setSelWhich(int selWhich) {
        this.selWhich = selWhich;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public String getHid() {
        return hid;
    }
    public void setHid(String hid) {
        this.hid = hid;
    }
}
