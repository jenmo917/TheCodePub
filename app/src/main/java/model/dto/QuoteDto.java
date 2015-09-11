package model.dto;

/**
 * Created by n06963 on 11/09/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class QuoteDto {
    private String source;
    private String text;
    private String category;

    public QuoteDto(String text, String source, String category) {
        this.text = text;
        this.source = source;
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
