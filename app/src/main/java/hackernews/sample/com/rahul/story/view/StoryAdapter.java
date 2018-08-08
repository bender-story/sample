package hackernews.sample.com.rahul.story.view;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackernews.sample.com.rahul.R;
import hackernews.sample.com.rahul.story.model.StoryAdapterModel;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<StoryAdapterModel> list;
//    private Context mContext;
    public static final int HEADER_TYPE=0;
    public static final int COMMENT_TYPE=1;

    public StoryAdapter( ArrayList<StoryAdapterModel> list) {

//        mContext = context;

        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_screen_adapter_header, parent, false);
                return new ViewHolderHeader(view);
            case COMMENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_screen_adapter_comments, parent, false);
                return new ViewHolderComments(view);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        return list.get(position).getType()==HEADER_TYPE ? HEADER_TYPE : COMMENT_TYPE;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        StoryAdapterModel model = list.get(listPosition);
        if (model != null) {
            switch (model.getType()) {
                case HEADER_TYPE:
                    if(model.getMainStory().getTitle()!=null && model.getMainStory().getTitle()!="")
                        ((ViewHolderHeader) holder).headerTitleTextView.setText(Html.fromHtml(model.getMainStory().getTitle()));
                    else
                        ((ViewHolderHeader) holder).headerTitleTextView.setVisibility(View.GONE);

                    if(model.getMainStory().getText()!=null && model.getMainStory().getText()!="")
                        ((ViewHolderHeader) holder).headerTextView.setText(Html.fromHtml(model.getMainStory().getText()));
                    else
                        ((ViewHolderHeader) holder).headerTextView.setVisibility(View.GONE);
//                    ((ViewHolderHeader) holder).headerTextView.setText(model.getMainStory().getText());

                    break;
                case COMMENT_TYPE:
                    if(model.getCommentsModel().getParentComment()!=null && model.getCommentsModel().getParentComment().getText()!=null)
                    ((ViewHolderComments) holder).parentTextView.setText(Html.fromHtml(model.getCommentsModel().getParentComment().getText()));
                    else
                        ((ViewHolderComments) holder).parentTextView.setVisibility(View.GONE);

                    if(model.getCommentsModel().getChildComment()==null )
                        ((ViewHolderComments) holder).childTextView.setVisibility(View.GONE);
                    else if(model.getCommentsModel().getChildComment().getText()!=null)
                    ((ViewHolderComments) holder).childTextView.setText(Html.fromHtml(model.getCommentsModel().getChildComment().getText()));


                    break;

            }
        }


    }
    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.story_header_text)
        public TextView headerTextView;
        @BindView(R.id.story_header_title)
        public TextView headerTitleTextView;


        final View view;
        ViewHolderHeader(View v) {
            super(v);
            ButterKnife.bind(this, v);
            view = v;
        }
    }


    public static class ViewHolderComments extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.story_parent_comment_text)
        public TextView parentTextView;
        @BindView(R.id.story_child_comment_text)
        public TextView childTextView;

        final View view;
        ViewHolderComments(View v) {
            super(v);
            ButterKnife.bind(this, v);
            view = v;
        }
    }
}
