package makdroid.voxpopuli.interactors;

/**
 * Created by Grzecho on 26.05.2016.
 */
public interface ISendVote {

    interface Callback {
        void onSendVoteSuccessful();

        void onError(String title, String error);
    }

    void execute(Callback callback);
}
