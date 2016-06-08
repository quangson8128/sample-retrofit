package vnzit.com.sampleretrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sh on 6/8/16.
 */
public class Repo {
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private String description;
    @SerializedName("html_url")
    private String htmlUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
