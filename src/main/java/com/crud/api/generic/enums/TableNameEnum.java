/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.crud.api.generic.enums;

/**
 *
 * @author NMSLAP570
 */
public enum TableNameEnum {

    NCR_SUPPLIER_LOGIN_DETAILS("com.crud.api.nuttycrunch.entity", "SupplierLoginDetailsEntity");

    private String packageName;
    private String className;
    private String qualifiedClassName;

    private TableNameEnum(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
        this.qualifiedClassName = packageName + "." + className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

}
