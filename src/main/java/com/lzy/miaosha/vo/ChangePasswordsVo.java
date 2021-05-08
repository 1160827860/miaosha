package com.lzy.miaosha.vo;

import javax.validation.constraints.NotNull;

/**
 * 修改密码的
 * @author 李正阳
 */
public class ChangePasswordsVo {
    @NotNull
    String oldPasswords;
    @NotNull
    String newPasswords;
    @NotNull
    String checkPassWords;

    public String getOldPasswords() {
        return oldPasswords;
    }

    public void setOldPasswords(String oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    public String getNewPasswords() {
        return newPasswords;
    }

    public void setNewPasswords(String newPasswords) {
        this.newPasswords = newPasswords;
    }

    public String getCheckPassWords() {
        return checkPassWords;
    }

    public void setCheckPassWords(String checkPassWords) {
        this.checkPassWords = checkPassWords;
    }
}
