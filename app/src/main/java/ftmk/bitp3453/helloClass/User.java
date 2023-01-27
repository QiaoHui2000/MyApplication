package ftmk.bitp3453.helloClass;

import java.io.Serializable;

                  //need to passing complex data
public class User implements Serializable {

    private String strName, strEmail, strPwd, strAddress, strGender, strBirth;

    public User(String strName, String strEmail, String strPwd, String strAddress, String strBirth, String strGender) {
        this.strName = strName;
        this.strEmail = strEmail;
        this.strPwd = strPwd;
        this.strAddress = strAddress;
        this.strBirth = strBirth;
        this.strGender = strGender;

    }


    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrPwd() {
        return strPwd;
    }

    public void setStrPwd(String strPwd) {
        this.strPwd = strPwd;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrBirth(){ return strBirth; }

    public void setStrBirth(String strBirth){ this.strBirth = strBirth; }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }




}
