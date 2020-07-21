package com.example.project2.ui.present.presentlist;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import java.util.ArrayList;
import java.util.List;

public class PresentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Present> presents = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private int layout;

    public PresentAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        switch(layout) {
            default:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.present_item,
                        parent, false);
                return new PresentViewHolder(itemView);
            case 2:
                View sponsorItemView =
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.present_sponsor_item,
                                parent, false);
                return new PresentSponsorViewHolder(sponsorItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Present currentPresent = presents.get(position);
        switch(layout) {
            default:
                PresentViewHolder presentViewHolder = (PresentViewHolder) holder;
                presentViewHolder.presentTitle.setText(currentPresent.getTitle());
                presentViewHolder.presentPrice.setText(currentPresent.getPrice() + "원");
                presentViewHolder.presentRank.setText(String.valueOf(position+1));
                break;
            case 2:
                final PresentSponsorViewHolder presentSponsorViewHolder = (PresentSponsorViewHolder) holder;
                presentSponsorViewHolder.presentSponsorTitle.setText(currentPresent.getTitle());
                presentSponsorViewHolder.presentSponsorPrice.setText(currentPresent.getPrice() + "원");
                presentSponsorViewHolder.presentSponsorNumFilled.setText(currentPresent.getNumFilled() + " / " + currentPresent.getNumNeeded());
                presentSponsorViewHolder.presentSponsorLink.setMovementMethod(LinkMovementMethod.getInstance());

                presentSponsorViewHolder.sponsorAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(currentPresent.getNumFilled() >= currentPresent.getNumNeeded()) {
                            Toast.makeText(view.getContext(), "이미 필요한 수가 찼습니다!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            holder.itemView.setBackgroundColor(Color.GREEN);
                            currentPresent.setNumFilled(currentPresent.getNumFilled() + 1);
                            presentSponsorViewHolder.presentSponsorNumFilled.setText(currentPresent.getNumFilled() + " / " + currentPresent.getNumNeeded());
                            if(currentPresent.getNumFilled() >= currentPresent.getNumNeeded()) {
                                Toast.makeText(view.getContext(), "축하합니다! 친구의 선물이 완성되었습니다!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            showPresentMessageDialog();
                        }
                        presents.set(position, currentPresent);
                    }
                });

                presentSponsorViewHolder.sponsorSubtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        currentPresent.setNumFilled(currentPresent.getNumFilled() - 1);
                        presentSponsorViewHolder.presentSponsorNumFilled.setText(currentPresent.getNumFilled() + " / " + currentPresent.getNumNeeded());
                        presents.set(position, currentPresent);
                    }
                });
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPresentDetailFragment();
            }
        } );
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public int getItemCount() {
        return presents.size();
    }

    public void setPresents(List<Present> presents) {
        this.presents = presents;
        notifyDataSetChanged();
    }

    public Present getPresentAt(int position) {
        return presents.get(position);
    }

    public void openPresentDetailFragment() {
        PresentDetailFragment fragment =
                PresentDetailFragment.newInstance();
        FragmentManager fragmentManager =
                ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_fade_exit);
        transaction.replace(R.id.fragment_present_detail_container, fragment,
                "PRESENT_DETAIL_FRAGMENT").commit();
        transaction.addToBackStack(null);
    }

    public void showPresentMessageDialog() {
        DialogFragment dialog = new PresentMessageDialogFragment();
        dialog.setTargetFragment(fragment, 0);
        dialog.show(fragment.getParentFragmentManager(), "PresentMessageDialog");
    }

    class PresentViewHolder extends RecyclerView.ViewHolder {
        private ImageView presentPic;
        private TextView presentTitle;
        private TextView presentPrice;
        private TextView presentRank;

        public PresentViewHolder(@NonNull View itemView) {
            super(itemView);
            presentPic = itemView.findViewById(R.id.present_pic);
            presentTitle = itemView.findViewById(R.id.present_title);
            presentPrice = itemView.findViewById(R.id.present_price);
            presentRank = itemView.findViewById(R.id.present_rank);
        }
    }

    class PresentSponsorViewHolder extends RecyclerView.ViewHolder {
        private ImageView presentSponsorPic;
        private TextView presentSponsorTitle;
        private TextView presentSponsorPrice;
        private TextView presentSponsorNumFilled;
        private TextView presentSponsorLink;
        private Button sponsorAdd;
        private Button sponsorSubtract;

        public PresentSponsorViewHolder(@NonNull View itemView) {
            super(itemView);
            presentSponsorPic = itemView.findViewById(R.id.present_sponsor_pic);
            presentSponsorTitle = itemView.findViewById(R.id.present_sponsor_title);
            presentSponsorPrice = itemView.findViewById(R.id.present_sponsor_price);
            presentSponsorNumFilled = itemView.findViewById(R.id.present_sponsor_num_filled);
            presentSponsorLink = itemView.findViewById(R.id.link_to_present);
            sponsorAdd = itemView.findViewById(R.id.add_sponsor);
            sponsorSubtract = itemView.findViewById(R.id.subtract_sponsor);
        }
    }
}
