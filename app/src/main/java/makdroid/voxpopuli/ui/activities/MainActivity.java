package makdroid.voxpopuli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.ui.base.BaseContainer;
import makdroid.voxpopuli.ui.base.BaseContainerImpl;
import makdroid.voxpopuli.ui.fragments.CardVotingPagerFragment;
import makdroid.voxpopuli.ui.fragments.DetailsVoteFragment;
import makdroid.voxpopuli.utils.Constants;
import makdroid.voxpopuli.utils.FragmentUtils;
import makdroid.voxpopuli.utils.UiUtils;

public class MainActivity extends AppCompatActivity implements CardVotingPagerFragment.Callback {

    private BaseContainer baseContainer;
    private boolean twoPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBasicUI(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentUtils.setFragment(this, new CardVotingPagerFragment(), R.id.base_container, CardVotingPagerFragment.FRAGMENT_TAG);
        }
        twoPaneMode = UiUtils.isTwoPaneMode(this);
    }

    private BaseContainer createBaseContainer() {
        return new BaseContainerImpl();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CURRENT_MENU_NAVIGATION, baseContainer.getmCurrentMenuId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                baseContainer.getDrawerLayout().openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardVotingClicked(CardVoting cardVoting) {
        if (twoPaneMode) {
            loadDetailsFragment(cardVoting);
        } else {
            openDetailsCardVotingActivity(cardVoting);
        }
    }

    private void initBasicUI(Bundle savedInstanceState) {
        baseContainer = createBaseContainer();
        baseContainer.setmCurrentMenuId((savedInstanceState != null) ? savedInstanceState.getInt(Constants.CURRENT_MENU_NAVIGATION, R.id.nav_item_zaglosuj) : R.id.nav_item_zaglosuj);
        baseContainer.bindForActivity(this);
    }

    private void openDetailsCardVotingActivity(CardVoting cardVoting) {
        DetailsCardVotingActivity.start(this, cardVoting);
    }

    private void loadDetailsFragment(CardVoting cardVoting) {
        FragmentUtils.setFragment(this, DetailsVoteFragment.newInstance(cardVoting), R.id.base_detail_container, DetailsVoteFragment.FRAGMENT_TAG);
    }

    public static void start(Context context) {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainActivityIntent);
    }

}
