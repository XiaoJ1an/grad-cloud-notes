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
@Entity(name = "cn_note")
public class CnNote {

  @Id
  private String id;
  private String title;
  private String content;
  private String type;
  private String state;
  private String groupId;
  private String userId;
  private String pushDate;
  private String pushTime;
  private String makeDate;
  private String makeTime;
  private String modifyDate;
  private String modifyTime;


}
