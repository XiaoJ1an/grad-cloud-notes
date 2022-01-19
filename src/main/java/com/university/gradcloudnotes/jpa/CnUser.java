package com.university.gradcloudnotes.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cn_user")
public class CnUser {

  @Id
  private String id;
  private String userName;
  private String password;
  private String name;
  private String sex;
  private String birthday;
  private String phoneNo;
  private String nickName;
  private String picture;
  private String userRole;
  private String makeDate;
  private String makeTime;
  private String modifyDate;
  private String modifyTime;

}
