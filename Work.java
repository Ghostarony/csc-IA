import java.io.Serializable;
import java.time.LocalDate;

public class Work implements Serializable{

    private String title, author, published, length, type, comments;
    private LocalDate finished;

    public Work(String title, String author, String published, LocalDate finished, 
    String length, String type, String comments){
        this.title = title;
        this.author = author;
        this.published = published;
        this.finished = finished;
        this.length = length;
        this.type = type;
        this.comments = comments;

    }

    //getters
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublished(){
        return published;
    }
    public LocalDate getFinished(){
        return finished;
    }
    public String getLength(){
        return length;
    }
    public String getType(){
        return type;
    }
    public String getComments(){
        return comments;
    }

    //setters
    public void setTitle(String title){
        this.title=title;
    }
    public void setAuthor(String author){
       this.author=author;
    }
    public void setPublished(String published){
        this.published=published;
    }
     public void setFinished(LocalDate finished){
        this.finished=finished;
    }
    public void setLength(String length){
        this.length=length;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setComments(String comments){
        this.comments=comments;
    }
}
