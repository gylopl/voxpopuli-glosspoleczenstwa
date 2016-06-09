package makdroid.voxpopuli.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 21.04.2016.
 */
public class CardVotingAdapter extends RecyclerView.Adapter<CardVotingAdapter.CardVotingViewHolder> {
    private List<CardVoting> cardVotingCollection;

    public CardVotingAdapter(Collection<CardVoting> cardVotingCollection) {
        this.cardVotingCollection = new ArrayList<>(cardVotingCollection);
    }

    @Override
    public CardVotingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_view_card_voting_row,
                        parent,
                        false);
        return new CardVotingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardVotingViewHolder holder, int position) {
        final CardVoting card = cardVotingCollection.get(position);
        holder.title.setText(card.getTitle());
        holder.addDate.setText(card.getAddDateAsString());
        holder.endDate.setText(card.getEndDateAsString());
    }

    @Override
    public int getItemCount() {
        return cardVotingCollection.size();
    }

    public CardVoting getItem(int position) {
        return cardVotingCollection.get(position);
    }

    final static class CardVotingViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title_card)
        TextView title;
        @Bind(R.id.add_date)
        TextView addDate;
        @Bind(R.id.end_date)
        TextView endDate;

        public CardVotingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
