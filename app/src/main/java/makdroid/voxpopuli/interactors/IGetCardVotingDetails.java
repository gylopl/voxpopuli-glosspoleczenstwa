package makdroid.voxpopuli.interactors;

import makdroid.voxpopuli.entities.VotingDetails;

/**
 * Created by Grzecho on 15.05.2016.
 */
public interface IGetCardVotingDetails {

    interface Callback {
        void onVotesCalculated(final VotingDetails votingDetails);

        void onError(String title, String error);
    }

    void execute(Callback callback);
}
