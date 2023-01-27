package ftmk.bitp3453.helloClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Vector<Student> students;

    public StudentAdapter(LayoutInflater layoutInflater, Vector<Student> students) {
        this.layoutInflater = layoutInflater;
        this.students = students;
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new
                StudentViewHolder(layoutInflater.inflate(R.layout.student_items,parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        holder.setStudent(students.get(position));
    }

    @Override
    public int getItemCount() {

        return students.size();
    }

    // View Holder Class to handle Recycler View.
   /* public class StudentViewHolder extends RecyclerView.ViewHolder{

        // creating a variable for our text view.
        private TextView lblFullName;
        private TextView lblStudNum;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }*/

}
