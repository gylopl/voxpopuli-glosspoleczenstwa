package makdroid.voxpopuli.interactors;

import java.util.Collection;

import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 20.04.2016.
 */
public interface IGetCardVoting {

    interface Callback {
        void onCardsLoaded(final Collection<CardVoting> cardVotingCollection);

        void onError();
    }

    void execute(Callback callback);
}
