package com.university.gradcloudnotes.repository;

import com.university.gradcloudnotes.jpa.CnUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CnUserRepository extends CrudRepository<CnUser, String> {
    /**通过用户名查询用户信息*/
    List<CnUser> findAllByUserName(String userName);
    //通过手机号码查询用户信息
    List<CnUser> findAllByPhoneNo(String phoneNo);
}
