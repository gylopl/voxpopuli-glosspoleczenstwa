package makdroid.voxpopuli.mvp.presenters;

import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.entities.VotingDetails;

/**
 * Created by Grzecho on 07.05.2016.
 */
//// TODO: 07.06.2016  chyba do usuniecia
@Deprecated
public interface ICardVotingDetailsPresenter {

    void initialize(CardVoting cardVoting);

    void restoreVotingDetails(final VotingDetails votingDetails);

    void onYesNoButtonClicked(int answer);

}
