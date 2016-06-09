package makdroid.voxpopuli.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.mvp.presenters.impl.CardVotingPresenter;
import makdroid.voxpopuli.ui.activities.DetailsCardVotingActivity;
import makdroid.voxpopuli.ui.adapters.CardVotingAdapter;
import makdroid.voxpopuli.mvp.views.CardVotingListView;
import makdroid.voxpopuli.utils.Constants;

/**
 */
@Deprecated
public class CardVotingListFragmentWitoutPager extends Fragment implements CardVotingListView {

    private static final String CARD_VOTING_COLLECTION = "card_voting_collection";

    @Bind(R.id.pb_loading)
    ProgressBar pb_loading;
    @Bind(R.id.recyclerViewCardVoting)
    RecyclerView recyclerView;

    private GestureDetectorCompat gestureDetector;
    private CardVotingPresenter cardVotingPresenter;
    private CardVotingAdapter cardVotingAdapter;

    public CardVotingListFragmentWitoutPager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_voting_witout_pager, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        setUpPresenter();
        return view;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gestureDetector = new GestureDetectorCompat(getContext(), new RecyclerViewOnGestureListener());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener());
    }

    private void setUpPresenter() {
//        cardVotingPresenter = new ICardVotingPresenter();
        cardVotingPresenter.setView(this);
        cardVotingPresenter.initialize();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(CARD_VOTING_COLLECTION, new ArrayList<>(cardVotingPresenter.getCardVotingCollection()));
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            showLoading();
            final Collection<CardVoting> cardVotingCollection =
                    (Collection<CardVoting>) savedInstanceState.getSerializable(CARD_VOTING_COLLECTION);
            updatePresenterWithSavedCardVotingCollection(cardVotingCollection);
        }
    }

    @Override
    public void hideLoading() {
        pb_loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        pb_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCardVotingCollectionToAdapter(Collection<CardVoting> cardVotingCollection) {
        cardVotingAdapter = new CardVotingAdapter(cardVotingCollection);
        cardVotingAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cardVotingAdapter);
    }

    @Override
    public void onItemClick(int itemPosition) {
//        int position = recyclerView.getChildLayoutPosition(view);
        //       openDetailsCardVotingActivity(position);
    }

    private void updatePresenterWithSavedCardVotingCollection(Collection<CardVoting> cardVotingCollection) {
        if (cardVotingCollection != null) {
            cardVotingPresenter.restoreCatalog(cardVotingCollection);
        }
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            //           onItemClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
        }
    }

    public void openDetailsCardVotingActivity(int position) {
        CardVoting cardVoting = cardVotingAdapter.getItem(position);
        Intent startIntent = new Intent(getContext(), DetailsCardVotingActivity.class);
        startIntent.putExtra(Constants.EXTRA_CARD_VOTING, cardVoting);
        getActivity().startActivity(startIntent);
    }

}
