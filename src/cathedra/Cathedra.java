package cathedra;


import account.Account;
import subject.*;
import faculty.*;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Dmi3
 */
public class Cathedra {
    private String name;
    private Faculty faculty;
    private ArrayList<Subject> subjects;
    private ArrayList<Account> listAccount;

    public Cathedra(String name) {
        this.name = name;
        this.subjects = new ArrayList<Subject>();
        this.listAccount = new ArrayList<Account>();
    }

    public Cathedra(String name, ArrayList<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
        this.listAccount = new ArrayList<Account>();
    }

    public Cathedra(String name, ArrayList<Subject> subjects, ArrayList<Account> accounts) {
        this.name = name;
        this.subjects = subjects;
        this.listAccount = accounts;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSubject(Subject subj) {
        this.subjects.add(subj);
    }

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubject(Subject subj, int num) {
        this.subjects.set(num, subj);
    }

    public void addAccount(Account acc) {
        this.listAccount.add(acc);
    }

    public ArrayList<Account> getAccounts() {
        return this.listAccount;
    }

    public void setAccount(Account acc, int num) {
        this.listAccount.set(num, acc);
    }

    public Faculty getFaculty() {
        return this.faculty;
    }
}
