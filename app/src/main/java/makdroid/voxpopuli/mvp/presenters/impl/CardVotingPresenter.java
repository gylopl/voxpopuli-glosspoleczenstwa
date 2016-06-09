package makdroid.voxpopuli.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.interactors.IGetCardVoting;
import makdroid.voxpopuli.interactors.impl.GetCardVotingInteractor;
import makdroid.voxpopuli.mvp.presenters.ICardVotingPresenter;
import makdroid.voxpopuli.mvp.presenters.IPresenter;
import makdroid.voxpopuli.mvp.views.CardVotingListView;

/**
 * Created by Grzecho on 20.04.2016.
 */
public class CardVotingPresenter implements ICardVotingPresenter, IPresenter<CardVotingListView> {

    private final GetCardVotingInteractor getCardVotingInteractor;
    private CardVotingListView view;
    private Collection<CardVoting> cardVotingCollection = new ArrayList<>();

    @Inject
    public CardVotingPresenter(GetCardVotingInteractor getCardVotingInteractor) {
        this.getCardVotingInteractor = getCardVotingInteractor;
    }

    @Override
    public void setView(CardVotingListView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        checkViewAlreadySetted();
        loadCardVotingCollection();
    }

    @Override
    public Collection<CardVoting> getCardVotingCollection() {
        return cardVotingCollection;
    }

    @Override
    public void restoreCatalog(final Collection<CardVoting> cards) {
        cardVotingCollection = cards;
        sendCardVotingCollectionToView(cards);
    }

    private void checkViewAlreadySetted() {
        if (this.view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    private void loadCardVotingCollection() {
        this.view.showLoading();
        getCardVotingInteractor.execute(new IGetCardVoting.Callback() {
            @Override
            public void onCardsLoaded(Collection<CardVoting> cards) {
                cardVotingCollection = cards;
                sendCardVotingCollectionToView(cards);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void sendCardVotingCollectionToView(Collection<CardVoting> cards) {
        view.setCardVotingCollectionToAdapter(cards);
        view.hideLoading();
    }
}
