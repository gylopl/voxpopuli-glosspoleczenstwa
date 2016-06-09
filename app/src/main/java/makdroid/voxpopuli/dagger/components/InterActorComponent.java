package makdroid.voxpopuli.dagger.components;

import dagger.Component;
import makdroid.voxpopuli.dagger.PerFragment;
import makdroid.voxpopuli.dagger.modules.InterActorModule;
import makdroid.voxpopuli.ui.activities.SignInActivity;
import makdroid.voxpopuli.ui.fragments.CardVotingPagerFragment;
import makdroid.voxpopuli.ui.fragments.DetailsVoteFragment;

/**
 * Created by Grzecho on 11.05.2016.
 */
@PerFragment
@Component(dependencies = RootComponent.class, modules = {InterActorModule.class})
public interface InterActorComponent {

    void inject(CardVotingPagerFragment fragment);

    void inject(DetailsVoteFragment fragment);

    void inject(SignInActivity activity);
}
