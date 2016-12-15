package com.myapps35.tutorial.FabArcMenu;

/**
 * Created by span-tech on 4/22/2016.
 */
public enum MenuSideEnum {
    ARC_LEFT(0), ARC_RIGHT(1);
    int id;

    MenuSideEnum(int id) {
        this.id = id;
    }

    public static MenuSideEnum fromId(int id) {
        for (MenuSideEnum f : values()) {
            if (f.id == id) return f;
        }
        return ARC_LEFT;
    }
}