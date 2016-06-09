package makdroid.voxpopuli.mvp.views;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 07.05.2016.
 */
public interface CardVotingDetailsView {
    void hideLoading();

    void showLoading();

    void showDialog(String title, String message);

    void showToastMessageVotedSuccessfull();

    void hideYesNoButtons();

    void showTextViewUserVote(int answer);

    void showDetails(CardVoting cardVoting, ArrayList<Entry> yVals1);

    void clickAnswerVoteYes();

    void clickAnswerVoteNo();
}
