package ftmk.bitp3453.helloClass.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ftmk.bitp3453.helloClass.Student;

public class StudentSQLite extends SQLiteOpenHelper {

    // declare number class variable
    public static final String dbName = "dbstudent";
    public static final String tableName = "student";
    public static final String studName = "stud_name";
    public static final String studNo = "stud_no";
    public static final String studEmail = "stud_email";
    public static final String studGender = "stud_gender";
    public static final String studState = "stud_state";
    public static final String studDob = "stud_dob";

    public static final int intVrsn = 1;
    // create table
    public static final String strCrtTbl = "CREATE TABLE " + tableName + " (" + studName + " TEXT, "
            + studNo + " VARCHAR(50), " + studEmail + " VARCHAR(100), " + studGender + " VARCHAR(10), "
            + studState + " VARCHAR(20), " + studDob + " DATE)";

    // drop table
    public static final String strDropTbl = "DROP TABLE IF EXISTS " + tableName;

    public StudentSQLite(Context context){

        super(context, dbName,null,intVrsn);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(strCrtTbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(strDropTbl);
        onCreate(db);
    }

    // insert date
    public float fnInsertStudent(Student student)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(studName, student.getStrFullName());
        values.put(studNo, student.getStrStudNo());
        values.put(studEmail, student.getStrEmail());
        values.put(studGender, student.getStrGender());
        values.put(studState, student.getStrState());
        values.put(studDob, student.getStrBirthdate());


        /*// convert the dob String to date
        String birth = student.getStrBirthdate();
        String str[] = birth.split("/");
        String birthDate = str[2] + "-" + str[1] + "-" + str[0];
        values.put(studDob, String.valueOf(birthDate));*/

        //db.insert(tableName, null, values);
        retResult = db.insert(tableName, null, values);
        return retResult;
    }

    // select data
    @SuppressLint("Range")
    public Student fnGetStudent(String stud_id)
    {

        Student student = new Student();

        String strSelectQry = "Select * from " + tableName + " where " + studNo + " = '" + stud_id + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelectQry, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        student.setStrFullName(cursor.getString(cursor.getColumnIndex(studName)));
        student.setStrStudNo(cursor.getString(cursor.getColumnIndex(studNo)));
        student.setStrEmail(cursor.getString(cursor.getColumnIndex(studEmail)));
        student.setStrGender(cursor.getString(cursor.getColumnIndex(studGender)));
        student.setStrState(cursor.getString(cursor.getColumnIndex(studState)));
        student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(studDob)));

        /*String fullname = cursor.getString(cursor.getColumnIndex(studName));
        String number = cursor.getString(cursor.getColumnIndex(studNo));
        String email = cursor.getString(cursor.getColumnIndex(studEmail));
        String gender = cursor.getString(cursor.getColumnIndex(studGender));
        String state = cursor.getString(cursor.getColumnIndex(studState));
        String birthDate = cursor.getString(cursor.getColumnIndex(studDob));*/

        /*String str[] = birthDate.split("-");
        String finDate = str[2] + "/" + str[1] + "/" + str[0];*/
        //Student student = new Student(fullname, number, email, gender, birthDate, state);

        return student;
    }

    @SuppressLint("Range")
    public List<Student> fnGetAllStudents()
    {
        List<Student> listStud = new ArrayList<Student>();

        String strSelAll = "Select * from " + tableName;

        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst())
        {
            do{
                Student student = new Student();

                student.setStrFullName(cursor.getString(cursor.getColumnIndex(studName)));
                student.setStrStudNo(cursor.getString(cursor.getColumnIndex(studNo)));
                student.setStrEmail(cursor.getString(cursor.getColumnIndex(studEmail)));
                student.setStrGender(cursor.getString(cursor.getColumnIndex(studGender)));
                student.setStrState(cursor.getString(cursor.getColumnIndex(studState)));
                student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(studDob)));
                listStud.add(student);

            }while (cursor.moveToNext());
        }
        return listStud;
    }

}
