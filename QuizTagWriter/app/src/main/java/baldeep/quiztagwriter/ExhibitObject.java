package baldeep.quiztagwriter;

/**
 * Created by Baldeep on 08/03/2016.
 */
public class ExhibitObject {

    private String name;
    private String description;
    private String url;

    public ExhibitObject(){
        this.name = "";
        this.description = "";
        this.url = "";
    }

    public ExhibitObject(String name, String description, String url){
        this.name = name;
        this.description = description;
        this.url = url;
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
}
