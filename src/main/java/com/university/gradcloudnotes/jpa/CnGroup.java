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
@Entity(name = "cn_group")
public class CnGroup {
  @Id
  private String id;
  private String grpName;
  private String userId;
  private String state;
  private String makeDate;
  private String makeTime;
  private String modifyDate;
  private String modifyTime;

}
