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
@Entity(name = "cn_picture")
public class CnPicture {

  @Id
  private String id;
  private String url;
  private String type;
  private String state;
  private String makeDate;
  private String makeTime;

}
