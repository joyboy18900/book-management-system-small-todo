package com.example.book_management_system_for_test_scg.book;

import java.util.List;

public class BookResponse {
    private String code;
    private List<Book> accountList;
    private Boolean status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Book> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Book> accountList) {
        this.accountList = accountList;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
