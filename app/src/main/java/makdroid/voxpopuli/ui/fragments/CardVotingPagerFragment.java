package makdroid.voxpopuli.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.dagger.components.DaggerInterActorComponent;
import makdroid.voxpopuli.dagger.modules.InterActorModule;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.mvp.presenters.impl.CardVotingPresenter;
import makdroid.voxpopuli.mvp.views.CardVotingListView;
import makdroid.voxpopuli.ui.adapters.CardVotingAdapter;

public class CardVotingPagerFragment extends Fragment implements CardVotingListView {

    public static final String FRAGMENT_TAG = "CARD_VOTING_PAGER_FRAGMENT_TAG";
    private static final String CARD_VOTING_COLLECTION = "card_voting_collection";

    @Bind(R.id.pb_loading)
    ProgressBar pb_loading;
    @Bind(R.id.viewPagerVotingMain)
    ViewPager viewPager;
    @Bind(R.id.tabVotingMain)
    TabLayout tabLayout;

    @Inject
    CardVotingPresenter cardVotingPresenter;
    private CardVotingPagerAdapter cardVotingPagerAdapter;
    private Collection<CardVoting> cardVotingCollection = new ArrayList<>();
    private CardVoting cardVoting;
    private Callback mCallback;

    public CardVotingPagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_voting_pager, container, false);
        ButterKnife.bind(this, view);
        setUpPresenter();
        initializeTabLayout();
        initializeViewPager();
        return view;
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    private void initializeDependencyInjector() {
        VoxPopuliApplication voxPopuliApplication = (VoxPopuliApplication) getActivity().getApplication();
        DaggerInterActorComponent.builder().rootComponent(voxPopuliApplication.getComponent())
                .interActorModule(new InterActorModule()).build().inject(this);
    }

    private void setUpPresenter() {
        cardVotingPresenter.setView(this);
        cardVotingPresenter.initialize();
    }

    public void initializeTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_menu_item_trwajace));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_menu_item_zakonczone));
    }

    public void initializeViewPager() {
        cardVotingPagerAdapter = new CardVotingPagerAdapter(tabLayout.getTabCount(), getActivity(), this.cardVotingCollection);
        viewPager.setAdapter(cardVotingPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    private void updatePresenterWithSavedCardVotingCollection(Collection<CardVoting> cardVotingCollection) {
        if (cardVotingCollection != null) {
            cardVotingPresenter.restoreCatalog(cardVotingCollection);
        }
    }

    /*View methods*/
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
        this.cardVotingCollection.clear();
        this.cardVotingCollection.addAll(cardVotingCollection);
        this.cardVotingPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int itemPosition) {
        mCallback.onCardVotingClicked(cardVoting);
    }

    /*PAGER ADAPTER*/
    private class CardVotingPagerAdapter extends PagerAdapter {
        int mNumOfTabs;
        private Context mContext;
        private Collection<CardVoting> cardVotingCollection;

        public CardVotingPagerAdapter(int NumOfTabs, Context mContext, Collection<CardVoting> cardVotingCollection) {
            this.mNumOfTabs = NumOfTabs;
            this.mContext = mContext;
            this.cardVotingCollection = cardVotingCollection;

        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.
                    from(mContext).inflate(R.layout.pager_card_voting,
                    container, false);
            container.addView(view);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCardVoting);
            setUpRecyclerView(recyclerView);
            CardVotingAdapter cardVotingAdapter = new CardVotingAdapter(getCardVotingCollectionForPosition(position));
            recyclerView.setAdapter(cardVotingAdapter);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        private Collection<CardVoting> getCardVotingCollectionForPosition(int position) {
            List<CardVoting> cardVotingCollectionForPosition = new ArrayList<>();
            if (position == 1) {
                for (CardVoting c : cardVotingCollection) {
                    if (c.isEnded())
                        cardVotingCollectionForPosition.add(c);
                }
            } else {
                for (CardVoting c : cardVotingCollection) {
                    if (!c.isEnded())
                        cardVotingCollectionForPosition.add(c);
                }
            }
            return cardVotingCollectionForPosition;
        }

        private void setUpRecyclerView(RecyclerView recyclerView) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView));
        }

        public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
            private GestureDetector mGestureDetector;

            public RecyclerItemClickListener(Context context, final RecyclerView recyclerView) {
                mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        int position = recyclerView.getChildLayoutPosition(view);
                        CardVotingAdapter adapter = (CardVotingAdapter) recyclerView.getAdapter();
                        cardVoting = adapter.getItem(position);
                        onItemClick(position);
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
                mGestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        }
    }

    public interface Callback {
        void onCardVotingClicked(CardVoting cardVoting);
    }

}
