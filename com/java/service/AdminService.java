package com.java.service;

import com.java.pojo.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {


    Admin selectAdmin(String Name);


    List<Map<String, Object>> selectClassName();


    boolean insertAdmin(Admin admin);

}
