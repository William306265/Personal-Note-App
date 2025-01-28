package com.example.personalnoteapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    ArrayList<NoteModel> arrayList = new ArrayList<>();
    NoteHelper noteHelper;

    public NoteAdapter(Context context, ArrayList<NoteModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.noteHelper = new NoteHelper(context);
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvDesc.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void deleteItem(int position) {
        NoteModel note = arrayList.get(position);
        noteHelper.deleteData(note.getId()); // Assuming you have getId() in NoteModel
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public void attachSwipeHelper(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback swipeCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    private final ColorDrawable background = new ColorDrawable(Color.RED);
                    private final Drawable deleteIcon = ContextCompat.getDrawable(context,
                            android.R.drawable.ic_menu_delete);

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        deleteItem(position);
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c,
                                            @NonNull RecyclerView recyclerView,
                                            @NonNull RecyclerView.ViewHolder viewHolder,
                                            float dX, float dY,
                                            int actionState,
                                            boolean isCurrentlyActive) {

                        View itemView = viewHolder.itemView;

                        // Draw red background
                        background.setBounds(
                                itemView.getRight() + (int)dX,
                                itemView.getTop(),
                                itemView.getRight(),
                                itemView.getBottom()
                        );
                        background.draw(c);

                        // Draw delete icon
                        if (deleteIcon != null) {
                            int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                            int iconTop = itemView.getTop() + iconMargin;
                            int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                            int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                            int iconRight = itemView.getRight() - iconMargin;
                            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                            deleteIcon.draw(c);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }
}