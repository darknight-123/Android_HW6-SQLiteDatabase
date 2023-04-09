package ttu.mis.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private String[] name;
    private String[] sex;
    private  String[] address;
    //private int[] icons;


    public MyRecyclerViewAdapter(String[] name/*, int[] icons*/, String[] sex, String[] address) {
        this.name = name;
        //this.icons = icons;
        this.sex=sex;
        this.address=address;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(name[position]);
        holder.textView_1.setText(sex[position]);
        holder.textView_2.setText(address[position]);
        //holder.icon.setImageResource(icons[position]);
    }

    public String[] getName() {
        return name;
    }

    public String[] getSex() {
        return sex;
    }
    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] date) {
        this.address = address;
    }
    public void setSex(String[] sex) {
        this.sex = sex;
    }

    public void setName(String[] name) {
        this.name = name;
    }

   // public int[] getIcons() {
   //     return icons;
   // }

   // public void setIcons(int[] icons) {
   //     this.icons = icons;
   // }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final TextView textView_1 ;
        private  final TextView textView_2;
        //private  final ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView2);
            textView_1=itemView.findViewById(R.id.textView3);
            textView_2=itemView.findViewById(R.id.textView1);
            //icon=itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(),getAdapterPosition()+":"+textView.getText(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
