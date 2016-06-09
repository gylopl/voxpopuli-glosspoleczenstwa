package makdroid.voxpopuli.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Grzecho on 26.05.2016.
 */
public class VotingDetails implements Parcelable {

    private float yesPercent;
    private float noPercent;
    private boolean mUserVoted;
    private int mUserAnswer;

    private VotingDetails(float yesPercent, float noPercent, boolean mUserVoted, int mUserAnswer) {
        this.yesPercent = yesPercent;
        this.noPercent = noPercent;
        this.mUserVoted = mUserVoted;
        this.mUserAnswer = mUserAnswer;
    }

    public static VotingDetails newInstance(float yesPercent, float noPercent, boolean mUserVoted, int mUserAnswer) {
        return new VotingDetails(yesPercent, noPercent, mUserVoted, mUserAnswer);
    }

    public float getYesPercent() {
        return yesPercent;
    }

    public float getNoPercent() {
        return noPercent;
    }

    public boolean ismUserVoted() {
        return mUserVoted;
    }

    public int getmUserAnswer() {
        return mUserAnswer;
    }

    public void setmUserVoted(boolean mUserVoted) {
        this.mUserVoted = mUserVoted;
    }

    public void setmUserAnswer(int mUserAnswer) {
        this.mUserAnswer = mUserAnswer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.yesPercent);
        dest.writeFloat(this.noPercent);
        dest.writeByte(this.mUserVoted ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mUserAnswer);
    }

    protected VotingDetails(Parcel in) {
        this.yesPercent = in.readFloat();
        this.noPercent = in.readFloat();
        this.mUserVoted = in.readByte() != 0;
        this.mUserAnswer = in.readInt();
    }

    public static final Parcelable.Creator<VotingDetails> CREATOR = new Parcelable.Creator<VotingDetails>() {
        @Override
        public VotingDetails createFromParcel(Parcel source) {
            return new VotingDetails(source);
        }

        @Override
        public VotingDetails[] newArray(int size) {
            return new VotingDetails[size];
        }
    };
}
