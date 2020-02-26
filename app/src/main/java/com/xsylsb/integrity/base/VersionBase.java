package com.xsylsb.integrity.base;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class VersionBase {

    /**
     * id : 1
     * androidAppVersion : 1.0.0
     * androidDownloadUrl : null
     * androidForceUpdate : null
     * androidDescription : null
     * iosVersion : 1.0.0
     * iosDownloadUrl : null
     * iosForceUpdate : null
     * iosDescription : null
     */

    private int id;
    private String androidAppVersion;
    private Object androidDownloadUrl;
    private Object androidForceUpdate;
    private Object androidDescription;
    private String iosVersion;
    private Object iosDownloadUrl;
    private Object iosForceUpdate;
    private Object iosDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

    public Object getAndroidDownloadUrl() {
        return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(Object androidDownloadUrl) {
        this.androidDownloadUrl = androidDownloadUrl;
    }

    public Object getAndroidForceUpdate() {
        return androidForceUpdate;
    }

    public void setAndroidForceUpdate(Object androidForceUpdate) {
        this.androidForceUpdate = androidForceUpdate;
    }

    public Object getAndroidDescription() {
        return androidDescription;
    }

    public void setAndroidDescription(Object androidDescription) {
        this.androidDescription = androidDescription;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public Object getIosDownloadUrl() {
        return iosDownloadUrl;
    }

    public void setIosDownloadUrl(Object iosDownloadUrl) {
        this.iosDownloadUrl = iosDownloadUrl;
    }

    public Object getIosForceUpdate() {
        return iosForceUpdate;
    }

    public void setIosForceUpdate(Object iosForceUpdate) {
        this.iosForceUpdate = iosForceUpdate;
    }

    public Object getIosDescription() {
        return iosDescription;
    }

    public void setIosDescription(Object iosDescription) {
        this.iosDescription = iosDescription;
    }
}
