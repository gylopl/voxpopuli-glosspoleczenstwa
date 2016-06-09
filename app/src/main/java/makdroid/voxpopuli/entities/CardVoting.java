package makdroid.voxpopuli.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import makdroid.voxpopuli.utils.UiUtils;

/**
 * Created by Grzecho on 20.04.2016.
 */
public class CardVoting implements Parcelable {

    private int id;
    private String title;
    private String content;
    private long addDate;
    private long endDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.addDate);
        dest.writeLong(this.endDate);
    }

    public CardVoting() {
    }

    public CardVoting(int id, String title, String content, long addDate, long endDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.addDate = addDate;
        this.endDate = endDate;
    }

    public CardVoting(String title, long addDate, long endDate) {
        this.title = title;
        this.addDate = addDate;
        this.endDate = endDate;
        generateContent();
    }

    protected CardVoting(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.addDate = in.readLong();
        this.endDate = in.readLong();
    }

    public static final Parcelable.Creator<CardVoting> CREATOR = new Parcelable.Creator<CardVoting>() {
        @Override
        public CardVoting createFromParcel(Parcel source) {
            return new CardVoting(source);
        }

        @Override
        public CardVoting[] newArray(int size) {
            return new CardVoting[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getAddDate() {
        return addDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public String getAddDateAsString() {
        return UiUtils.getIso8601Format().format(new Date(addDate));
    }

    public String getEndDateAsString() {
        return UiUtils.getIso8601Format().format(new Date(endDate));
    }

    public String getContent() {
        return content;
    }

    public boolean isEnded() {
        return endDate < new Date().getTime();
    }

    private void generateContent() {
        this.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sed dui venenatis, eleifend tortor ut, maximus massa. " +
                "Duis faucibus sapien tempus eros faucibus, sed cursus ante maximus. Cras at lectus ligula. Duis eu diam ipsum. " +
                "In eu auctor mauris, eu porta elit. Aenean semper leo sed rhoncus suscipit. Maecenas finibus pulvinar facilisis. " +
                "Mauris nunc lorem, interdum et gravida tristique, ullamcorper nec est. Donec dapibus condimentum nulla";

    }
}
