package makdroid.voxpopuli.mvp.presenters;

import java.util.Collection;

import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 20.04.2016.
 */
//// TODO: 07.06.2016  chyba do usuniecia
@Deprecated
public interface ICardVotingPresenter {

    Collection<CardVoting> getCardVotingCollection();

    void restoreCatalog(final Collection<CardVoting> cards);
}
