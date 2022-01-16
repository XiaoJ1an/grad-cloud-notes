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
@Entity(name = "cn_note_picture")
public class CnNotePicture {

  @Id
  private String id;
  private String noteId;
  private String pictureId;

}
