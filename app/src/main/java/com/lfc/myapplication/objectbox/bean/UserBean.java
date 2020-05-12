package com.lfc.myapplication.objectbox.bean;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by LFC
 * on 2020/4/28.
 */
@Entity
public class UserBean {

    @Id
    private long id;
    private String strNmae = "";
    @SerializedName("id")
    private String strID = "";
    private String strSkill = "";
    private int age = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrNmae() {
        return strNmae == null ? "" : strNmae;
    }

    public void setStrNmae(String strNmae) {
        this.strNmae = strNmae;
    }

    public String getStrID() {
        return strID == null ? "" : strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getStrSkill() {
        return strSkill == null ? "" : strSkill;
    }

    public void setStrSkill(String strSkill) {
        this.strSkill = strSkill;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", strNmae='" + strNmae + '\'' +
                ", strID='" + strID + '\'' +
                ", strSkill='" + strSkill + '\'' +
                ", age=" + age +
                '}';
    }
}
