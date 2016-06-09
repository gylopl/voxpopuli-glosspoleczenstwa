package makdroid.voxpopuli.mvp.views;

import java.util.Collection;

import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 20.04.2016.
 */
public interface CardVotingListView {

    void hideLoading();

    void showLoading();

    void setCardVotingCollectionToAdapter(Collection<CardVoting> cardVotingCollection);

    void onItemClick(int itemPosition);
}
