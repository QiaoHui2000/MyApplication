package ftmk.bitp3453.helloClass;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder{

    private final TextView lblFullName, lblStudNo, lblGender, lblBirthdate,
            lblEmail, lblState;

    //private Student student;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.lblFullName = itemView.findViewById(R.id.lblFullName);
        this.lblStudNo = itemView.findViewById(R.id.lblStudNo);
        this.lblGender = itemView.findViewById(R.id.lblGender);
        this.lblBirthdate = itemView.findViewById(R.id.lblBirthdate);
        this.lblEmail = itemView.findViewById(R.id.lblEmail);
        this.lblState = itemView.findViewById(R.id.lblState);

        //itemView.setOnClickListener(this::fnEdit);
    }

    /*private void fnEdit(View view){
        Toast.makeText(view.getContext(), "The Student Name: " + student.getStrFullname(),Toast.LENGTH_SHORT).show();
    }*/

    public void setStudent(Student student){
        lblFullName.setText(student.getStrFullName());
        lblStudNo.setText(student.getStrStudNo());
        lblGender.setText(student.getStrGender());
        lblBirthdate.setText(student.getStrBirthdate());
        lblEmail.setText(student.getStrEmail());
        lblState.setText(student.getStrState());
    }



}
