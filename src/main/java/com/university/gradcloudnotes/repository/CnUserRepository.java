package com.university.gradcloudnotes.repository;

import com.university.gradcloudnotes.jpa.CnUser;
import org.springframework.data.repository.CrudRepository;

public interface CnUserRepository extends CrudRepository<CnUser, String> {
    /**通过用户名查询用户信息*/
    CnUser findAllByUserName(String userName);
}
