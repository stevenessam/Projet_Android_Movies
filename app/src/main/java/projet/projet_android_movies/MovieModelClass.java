package projet.projet_android_movies;

public class MovieModelClass {

    String title;
    String rating;
    String img;

    public MovieModelClass(String title, String rating, String img) {
        this.title = title;
        this.rating = rating;
        this.img = img;
    }


    public MovieModelClass() {
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
