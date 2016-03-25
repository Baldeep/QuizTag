package baldeep.quiztagapp.Backend;

/**
 * Created by Baldeep on 08/03/2016.
 */
public class ExhibitTag {

    private String name;
    private String description;
    private String url;
    private String year;

    public ExhibitTag(){
        this.name = "";
        this.description = "";
        this.url = "";
        this.year = "";
    }

    public ExhibitTag(String name, String description, String url, String year){
        this.name = name;
        this.description = description;
        this.url = url;
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYear() {
        return year;
    }
}
