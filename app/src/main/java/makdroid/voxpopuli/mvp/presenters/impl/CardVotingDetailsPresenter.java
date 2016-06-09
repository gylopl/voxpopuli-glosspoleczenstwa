package makdroid.voxpopuli.mvp.presenters.impl;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import javax.inject.Inject;

import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.entities.VotingDetails;
import makdroid.voxpopuli.interactors.IGetCardVotingDetails;
import makdroid.voxpopuli.interactors.ISendVote;
import makdroid.voxpopuli.interactors.impl.GetCardVotingDetailsInteractor;
import makdroid.voxpopuli.interactors.impl.SendVoteInteractor;
import makdroid.voxpopuli.mvp.presenters.ICardVotingDetailsPresenter;
import makdroid.voxpopuli.mvp.presenters.IPresenter;
import makdroid.voxpopuli.mvp.views.CardVotingDetailsView;

/**
 * Created by Grzecho on 07.05.2016.
 */
public class CardVotingDetailsPresenter implements ICardVotingDetailsPresenter, IPresenter<CardVotingDetailsView> {

    private CardVotingDetailsView view;
    private final GetCardVotingDetailsInteractor getCardVotingDetailsInteractor;
    private final SendVoteInteractor mSendVoteInteractor;
    private CardVoting mCardVoting;
    private VotingDetails mVotingDetails;

    @Inject
    CardVotingDetailsPresenter(GetCardVotingDetailsInteractor getCardVotingDetailsInteractor,
                               SendVoteInteractor sendVoteInteractor) {
        this.getCardVotingDetailsInteractor = getCardVotingDetailsInteractor;
        this.mSendVoteInteractor = sendVoteInteractor;
    }

    @Override
    public void initialize(CardVoting cardVoting) {
        this.mCardVoting = cardVoting;
        initialize();
    }

    @Override
    public void setView(CardVotingDetailsView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        checkViewAlreadySetted();
        calculateVotes();
    }

    @Override
    public void restoreVotingDetails(VotingDetails votingDetails) {
        this.view.showLoading();
        mVotingDetails = votingDetails;
        createArrayToDisplay(votingDetails);
    }

    @Override
    public void onYesNoButtonClicked(int answer) {
        mVotingDetails.setmUserVoted(true);
        mVotingDetails.setmUserAnswer(answer);
        mSendVoteInteractor.setmAnswer(answer);
        mSendVoteInteractor.execute(new ISendVote.Callback() {
            @Override
            public void onSendVoteSuccessful() {
                showHideFooter();
                view.showToastMessageVotedSuccessfull();
            }

            @Override
            public void onError(String title, String error) {
                view.showDialog(title, error);
            }
        });
    }

    private void checkViewAlreadySetted() {
        if (this.view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    private void calculateVotes() {
        this.view.showLoading();
        getCardVotingDetailsInteractor.execute(new IGetCardVotingDetails.Callback() {
            @Override
            public void onVotesCalculated(VotingDetails votingDetails) {
                mVotingDetails = votingDetails;
                createArrayToDisplay(votingDetails);
            }

            @Override
            public void onError(String title, String error) {
                view.showDialog(title, error);
            }
        });
    }

    private void createArrayToDisplay(VotingDetails votingDetails) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(votingDetails.getYesPercent(), 1));
        yVals1.add(new Entry(votingDetails.getNoPercent(), 0));
        showHideFooter();
        view.showDetails(mCardVoting, yVals1);
        view.hideLoading();
    }

    private void showHideFooter() {
        if (mCardVoting.isEnded()) {
            view.hideYesNoButtons();
        }
        if (mVotingDetails.ismUserVoted()) {
            view.hideYesNoButtons();
            view.showTextViewUserVote(mVotingDetails.getmUserAnswer());
        }
    }

    public VotingDetails getmVotingDetails() {
        return mVotingDetails;
    }
}
