package com.magicsoft.popuputils;

import java.io.Serializable;

/**
 * @author : Lss kiwilss
 * @FileName: User
 * @e-mail : kiwilss@163.com
 * @time : 2019-06-13
 * @desc : {DESCRIPTION}
 */
public class User implements Serializable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
