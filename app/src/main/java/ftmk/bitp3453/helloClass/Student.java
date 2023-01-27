package ftmk.bitp3453.helloClass;

public class Student {

    private String strFullName, strStudNo, strEmail, strGender, strBirthdate, strState;

    public Student(String strFullName, String strStudNo, String strEmail, String strGender, String strBirthdate, String strState) {
        this.strFullName = strFullName;
        this.strStudNo = strStudNo;
        this.strEmail = strEmail;
        this.strGender = strGender;
        this.strBirthdate = strBirthdate;
        this.strState = strState;
    }

    public Student() {

    }

    public String getStrFullName() {
        return strFullName;
    }

    public String getStrStudNo() {
        return strStudNo;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public String getStrGender() {return strGender;}

    public String getStrBirthdate() {return strBirthdate;}

    public String getStrState() {
        return strState;
    }

    public void setStrFullName(String strFullName) {this.strFullName = strFullName;}

    public void setStrStudNo(String strStudNo) {
        this.strStudNo = strStudNo;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public void setStrBirthdate(String strBirthdate) {
        this.strBirthdate = strBirthdate;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }


}
