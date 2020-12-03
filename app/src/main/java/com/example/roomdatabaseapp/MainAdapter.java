package com.example.roomdatabaseapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;


   public MainAdapter(Activity context,List<MainData> dataList)
   {
       this.context = context;
       this.dataList = dataList;
       notifyDataSetChanged();
   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       MainData data = dataList.get(position);

       database = RoomDB.getInstance(context);
       holder.textView.setText(data.getText());


       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MainData d = dataList.get(holder.getAdapterPosition());
               int sID = d.getID();
               String sText = d.getText();


               Dialog dialog = new Dialog(context);
               dialog.setContentView(R.layout.update_dialog);

               int width = WindowManager.LayoutParams.MATCH_PARENT;
               int height = WindowManager.LayoutParams.WRAP_CONTENT;

               dialog.getWindow().setLayout(width,height);
               dialog.show();

               EditText editText = dialog.findViewById(R.id.edit_text);
               Button btnUpdates = dialog.findViewById(R.id.UpdateButton);

               editText.setText(sText);

               btnUpdates.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();

                       String uText = editText.getText().toString();

                       database.mainDao().update(sID,uText);
                       dataList.clear();
                       dataList.addAll(database.mainDao().getAll());
                       notifyDataSetChanged();
                   }
               });

           }
       });

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MainData d = dataList.get(holder.getAdapterPosition());



               database.mainDao().delete(d);

               int positions = holder.getAdapterPosition();
               dataList.remove(positions);
               notifyItemRangeChanged(positions,dataList.size());
           }
       });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView textView;
       private ImageView edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.text_view);
            edit =itemView.findViewById(R.id.edit_btn);
            delete =itemView.findViewById(R.id.btn_delete);
        }
    }
}
