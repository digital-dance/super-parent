package com.digital.dance.document.business.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessUtil
{
  private static final Logger logger = LoggerFactory.getLogger(BusinessUtil.class);

  public static byte[] getBytes(String filePath) throws Exception {
	  BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));        
      ByteArrayOutputStream out = new ByteArrayOutputStream(1024);        

      logger.info("Available bytes:" + in.available());        

      byte[] temp = new byte[1024];        
      int size = 0;        
      while ((size = in.read(temp)) != -1) {        
              out.write(temp, 0, size);        
      }        
      in.close();        

      byte[] content = out.toByteArray();
      out.close();
      return content;
  }

  public static File getFile(byte[] in, String filePath, String fileName) throws Exception {
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    File file = null;
    try {
      File dir = new File(filePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      file = new File(filePath, fileName);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(in);
      return file;
    } catch (Exception e) {
      logger.error("BusinessUtil.BusinessUtil Exception", e);
      return null;
    } finally {
      if (bos != null) {
        bos.close();
      }
      if (fos != null)
        fos.close();
    }
  }
}