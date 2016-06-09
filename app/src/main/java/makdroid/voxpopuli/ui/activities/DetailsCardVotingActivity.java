package makdroid.voxpopuli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.ui.fragments.DetailsVoteFragment;
import makdroid.voxpopuli.utils.Constants;
import makdroid.voxpopuli.utils.FragmentUtils;
import makdroid.voxpopuli.utils.UiUtils;

public class DetailsCardVotingActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.orzelBackground)
    ImageView orzelBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        loadCardVoting(savedInstanceState);
    }

    private void initUi() {
        setContentView(R.layout.activity_details_card_voting);
        ButterKnife.bind(this);
        UiUtils.setUpToolBarWithBackNavigation(mToolbar, this);
        UiUtils.setSizeOrzelBackground(orzelBackground, this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadCardVoting(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        CardVoting cardVoting = extras.getParcelable(Constants.EXTRA_CARD_VOTING);
        if (savedInstanceState == null) {
            FragmentUtils.setFragment(this, DetailsVoteFragment.newInstance(cardVoting), R.id.activity_detail_container, DetailsVoteFragment.FRAGMENT_TAG);
        }
        setTitle(cardVoting.getTitle());
    }

    public static void start(Context context, CardVoting cardVoting) {
        Intent detailsCardVotingItent = new Intent(context, DetailsCardVotingActivity.class);
        detailsCardVotingItent.putExtra(Constants.EXTRA_CARD_VOTING, cardVoting);
        context.startActivity(detailsCardVotingItent);
    }

}
