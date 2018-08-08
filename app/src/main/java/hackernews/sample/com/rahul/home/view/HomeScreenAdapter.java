package hackernews.sample.com.rahul.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackernews.sample.com.rahul.Constants;
import hackernews.sample.com.rahul.R;
import hackernews.sample.com.rahul.story.view.StoryActivity;
import hackernews.sample.com.rahul.home.model.StoriesModel;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ViewHolder> {
    private final ArrayList<StoriesModel> list;
//    private Context mContext;

    public HomeScreenAdapter( ArrayList<StoriesModel> list) {

//        mContext = context;

        this.list = list;
        //recyclerView = recyclerV;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_screen_adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StoriesModel story =  list.get(position);

        String title;
//        String url = "";
        Integer commentsNo = 0;
        Integer points = 0;
        Integer time = 0;

        if (story.getTitle() != null)
            title = story.getTitle();
        else if(story.getText() != null)
            title = story.getText();
        else
            title=story.getUrl();
//        if (story.getUrl() != null) url = story.getUrl();
        if (story.getDescendants() != null) commentsNo = story.getDescendants();
        if (story.getScore() != null) points = story.getScore();
        if (story.getTime() != null) time = story.getTime();

        int tLength = title.length();
        if(tLength >= 80){
            title = title.substring(0, 80).toLowerCase() + "...";
            title = title.substring(0, 1).toUpperCase() + title.substring(1);
        } else {
            if(tLength > 3) {
                title = title.substring(0, tLength).toLowerCase();
                title = title.substring(0, 1).toUpperCase() + title.substring(1);
            }
        }

        holder.countTextView.setText(position+".");
        holder.titleTextView.setText(title);
        holder.pointsTextView.setText(points + " time:"+ time);
        holder.commentsTextView.setText("|"+commentsNo+" comments");

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra(Constants.POSITION_EXTRA, holder.getAdapterPosition());
                intent.putExtra(Constants.STORY_OBJECT_EXTRA, story);
//                intent.putExtra("title", aTitle);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.story_count)
        public TextView countTextView;
        @BindView(R.id.story_title)
        public TextView titleTextView;
        @BindView(R.id.story_points)
        public TextView pointsTextView;
        @BindView(R.id.story_comments)
        public TextView commentsTextView;

        final View view;
        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            view = v;
        }
    }
}
