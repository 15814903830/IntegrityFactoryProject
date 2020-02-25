package com.xsylsb.integrity.base;

public class FileBase {

    /**
     * fileName : 1455445337733.jpg
     * fileExt : .jpg
     * fileSize : 0
     * filePath : UploadFiles/PermitSign/201912/614f8b22-a63f-4efc-ab8b-ed13365d861b.jpg
     * fileUrl : http://liugangapi.gx11.cn/UploadFiles/PermitSign/201912/614f8b22-a63f-4efc-ab8b-ed13365d861b.jpg
     * success : true
     * msg : 上传成功
     */

    private String fileName;
    private String fileExt;
    private int fileSize;
    private String filePath;
    private String fileUrl;
    private boolean success;
    private String msg;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
