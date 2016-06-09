package makdroid.voxpopuli.ui.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.dagger.components.DaggerInterActorComponent;
import makdroid.voxpopuli.dagger.modules.InterActorModule;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.entities.VotingDetails;
import makdroid.voxpopuli.mvp.presenters.impl.CardVotingDetailsPresenter;
import makdroid.voxpopuli.mvp.views.CardVotingDetailsView;
import makdroid.voxpopuli.utils.Constants;
import makdroid.voxpopuli.utils.UiUtils;
import makdroid.voxpopuli.utils.piechart.PieChartVP;

public class DetailsVoteFragment extends Fragment implements CardVotingDetailsView {
    public static final String FRAGMENT_TAG = "DETAILS_VOTE_FRAGMENT_TAG";

    @Bind(R.id.tv_card_title)
    TextView mTvCardTitle;
    @Bind(R.id.tv_card_add_date)
    TextView mTvCardAddDate;
    @Bind(R.id.tv_card_end_date)
    TextView mTvCardEndDate;
    @Bind(R.id.tv_card_content)
    TextView mTvCardContent;
    @Bind(R.id.btn_yes)
    Button mBtnYes;
    @Bind(R.id.btn_no)
    Button mBtnNo;
    @Bind(R.id.tv_card_user_vote)
    TextView mTvCardUserVote;
    @Bind(R.id.chart)
    PieChartVP mChart;
    @Bind(R.id.pb_loading)
    ProgressBar mPbarLoading;

    @Inject
    CardVotingDetailsPresenter cardVotingDetailsPresenter;

    public DetailsVoteFragment() {
    }

    public static DetailsVoteFragment newInstance(@NonNull CardVoting cardVoting) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_CARD_VOTING, cardVoting);
        DetailsVoteFragment fragment = new DetailsVoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_vote, container, false);
        ButterKnife.bind(this, view);
        setUpUI();
        showLoading();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            CardVoting cardVoting = (CardVoting) getArguments().get(Constants.EXTRA_CARD_VOTING);
            if (cardVoting != null) {
                setUpPresenter(cardVoting);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.VOTE_DETAILS, cardVotingDetailsPresenter.getmVotingDetails());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            showLoading();
            final VotingDetails votingDetails =
                    savedInstanceState.getParcelable(Constants.VOTE_DETAILS);
            updatePresenterWithSavedCardVotingCollection(votingDetails);
        }
    }

    /*initialize methods*/
    private void initializeDependencyInjector() {
        int cardVotingId = -1;
        CardVoting cardVoting = (CardVoting) getArguments().get(Constants.EXTRA_CARD_VOTING);
        if (cardVoting != null)
            cardVotingId = cardVoting.getId();
        VoxPopuliApplication voxPopuliApplication = (VoxPopuliApplication) getActivity().getApplication();
        DaggerInterActorComponent.builder().rootComponent(voxPopuliApplication.getComponent())
                .interActorModule(new InterActorModule(cardVotingId)).build().inject(this);
    }

    private void setUpUI() {
        if (getActivity().findViewById(R.id.orzelBackground) != null)
            getActivity().findViewById(R.id.orzelBackground).setVisibility(View.GONE);
        setButtonsWidth();
        addYesOnClickListener();
        addNoOnClickListener();
    }

    private void setButtonsWidth() {
        double dzielnik = 4.5;
        if (getActivity().findViewById(R.id.base_detail_container) != null)
            dzielnik = 10.5;
        int screenWidth = UiUtils.getScreenWidth(getActivity());
        int btnWidth = (int) (screenWidth / dzielnik);
        mBtnYes.setWidth(btnWidth);
        mBtnNo.setWidth(btnWidth);
    }

    private void addYesOnClickListener() {
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickAnswerVoteYes();
            }
        });
    }

    private void addNoOnClickListener() {
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickAnswerVoteNo();
            }
        });
    }

    private void setUpPresenter(CardVoting cardVoting) {
        cardVotingDetailsPresenter.setView(this);
        cardVotingDetailsPresenter.initialize(cardVoting);
    }

    private void updatePresenterWithSavedCardVotingCollection(VotingDetails votingDetails) {
        if (votingDetails != null) {
            cardVotingDetailsPresenter.restoreVotingDetails(votingDetails);
        }
    }

    /*View methods*/
    @Override
    public void hideLoading() {
        mPbarLoading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mPbarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.answer_ok, null);
        builder.show();
    }

    @Override
    public void showToastMessageVotedSuccessfull() {
        Toast.makeText(getActivity(), R.string.message_vote_successfull, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideYesNoButtons() {
        mBtnYes.setVisibility(View.GONE);
        mBtnNo.setVisibility(View.GONE);
    }

    @Override
    public void showTextViewUserVote(int answer) {
        if (answer == Constants.ANSWER_YES)
            mTvCardUserVote.setText(R.string.answer_your_vote_yes);
        else
            mTvCardUserVote.setText(R.string.answer_your_vote_no);
        mTvCardUserVote.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetails(CardVoting cardVoting, ArrayList<Entry> yVals1) {
        mTvCardTitle.setText(cardVoting.getTitle());
        mTvCardAddDate.setText(cardVoting.getAddDateAsString());
        mTvCardEndDate.setText(cardVoting.getEndDateAsString());
        mTvCardContent.setText(cardVoting.getContent());
        setUpChart(yVals1);
    }

    @Override
    public void clickAnswerVoteYes() {
        showConfirmDialog(Constants.ANSWER_YES);
    }

    @Override
    public void clickAnswerVoteNo() {
        showConfirmDialog(Constants.ANSWER_NO);
    }

    public void showConfirmDialog(final int asnwer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        builder.setTitle(R.string.answer_confirm);
        builder.setMessage(R.string.answer_confirm_content);
        builder.setPositiveButton(R.string.answer_vote, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cardVotingDetailsPresenter.onYesNoButtonClicked(asnwer);
            }
        });
        builder.setNegativeButton(R.string.answer_cancel, null);
        builder.show();
    }

    private void setUpChart(ArrayList<Entry> yVals1) {
        mChart.setVisibility(View.VISIBLE);
        PieDataSet dataSet = new PieDataSet(yVals1, " Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(getString(R.string.answer_yes));
        xVals.add(getString(R.string.answer_no));
        ArrayList<Integer> colorsChart = new ArrayList<Integer>();
        dataSet.resetColors();
        colorsChart.add(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        colorsChart.add(ContextCompat.getColor(getActivity(), R.color.white_alpha));
        dataSet.setColors(colorsChart);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(9f);
        ArrayList<Integer> colorstText = new ArrayList<Integer>();
        colorstText.add(ContextCompat.getColor(getActivity(), R.color.white_alpha));
        colorstText.add(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        data.setValueTextColors(colorstText);
        mChart.setData(data);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(ColorTemplate.COLOR_NONE);
        mChart.setTransparentCircleColor(Color.BLACK);
        if (getActivity().findViewById(R.id.base_detail_container) == null) {
            ViewGroup.LayoutParams lp = mChart.getLayoutParams();
            lp.width = UiUtils.getBestWidthForPieChart(getActivity());
            lp.height = mChart.getLayoutParams().width;
            mChart.setLayoutParams(lp);
        }
        mChart.setTransparentCircleAlpha(100);
        mChart.setHoleRadius(70f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setmCenterImage(ContextCompat.getDrawable(getActivity(), R.drawable.transparentorzel));
        mChart.getLegend().setEnabled(false);
        mChart.setDescription(null);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);
    }
}
