package projet.projet_android_movies;

public class MovieModelClass {
    //déclaration des variables
    String id;
    String title;
    String description;
    String rating;
    String img;
    String video;

    //constructeur vide
    public MovieModelClass() {
    }

    //constructeur avec tous les paramétres
    public MovieModelClass(String id, String title, String description, String rating, String img, String video) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.img = img;
        this.video = video;
    }

    //les getters et les setters
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
