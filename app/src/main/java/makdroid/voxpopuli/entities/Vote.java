package makdroid.voxpopuli.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Grzecho on 15.05.2016.
 */
public class Vote implements Parcelable {

    private int id;
    private int cardVotingId;
    private String email;
    private int answer;

    public Vote(int id, int cardVotingId, String email, int answer) {
        this.id = id;
        this.cardVotingId = cardVotingId;
        this.email = email;
        this.answer = answer;
    }

    public Vote(int cardVotingId, String email, int answer) {
        this.cardVotingId = cardVotingId;
        this.email = email;
        this.answer = answer;
    }

    public int getCardVotingId() {
        return cardVotingId;
    }

    public String getEmail() {
        return email;
    }

    public int getAnswer() {
        return answer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.cardVotingId);
        dest.writeString(this.email);
        dest.writeInt(this.answer);
    }

    protected Vote(Parcel in) {
        this.id = in.readInt();
        this.cardVotingId = in.readInt();
        this.email = in.readString();
        this.answer = in.readInt();
    }

    public static final Parcelable.Creator<Vote> CREATOR = new Parcelable.Creator<Vote>() {
        @Override
        public Vote createFromParcel(Parcel source) {
            return new Vote(source);
        }

        @Override
        public Vote[] newArray(int size) {
            return new Vote[size];
        }
    };
}
