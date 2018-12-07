package com.digital.dance.document.business.bo;

import java.io.Serializable;
import java.util.Date;

public class BusinessDocBO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long businessType;
  private String businessNo;
  private Long businessSubType;
  private Long docId;
  private Integer category;
  private String fileName;
  private Integer fileType;
  private String fileTypeName;
  private String filePath;
  private String fileDescription;
  private String createUserId;
  private Date createTime;
  private Date updateTime;
  private long size;
  private String removeFlag;
  private String cacheFilePath;
  private String cacheDir;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBusinessType()
  {
    return this.businessType;
  }

  public void setBusinessType(Long businessType)
  {
    this.businessType = businessType;
  }

  public Long getBusinessSubType()
  {
    return this.businessSubType;
  }

  public void setBusinessSubType(Long businessSubType)
  {
    this.businessSubType = businessSubType;
  }

  public String getBusinessNo() {
    return this.businessNo;
  }

  public void setBusinessNo(String businessNo) {
    this.businessNo = businessNo;
  }

  public Long getDocId() {
    return this.docId;
  }

  public void setDocId(Long docId) {
    this.docId = docId;
  }

  public Integer getCategory() {
    return this.category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getFileType() {
    return this.fileType;
  }

  public void setFileType(Integer fileType) {
    this.fileType = fileType;
  }

  public String getFileTypeName() {
    return this.fileTypeName;
  }

  public void setFileTypeName(String fileTypeName) {
    this.fileTypeName = fileTypeName;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileDescription() {
    return this.fileDescription;
  }

  public void setFileDescription(String fileDescription) {
    this.fileDescription = fileDescription;
  }

  public String getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public long getSize() {
    return this.size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getRemoveFlag() {
    return this.removeFlag;
  }

  public void setRemoveFlag(String removeFlag) {
    this.removeFlag = removeFlag;
  }

  public String getCacheFilePath() {
    return this.cacheFilePath;
  }

  public void setCacheFilePath(String cacheFilePath) {
    this.cacheFilePath = cacheFilePath;
  }

  public void setCacheDir(String cacheDir) {
    this.cacheDir = cacheDir;
  }

  public String getCacheDir() {
    return this.cacheDir;
  }
}