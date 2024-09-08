public class Work {

    private String title, author, published, finished, length, type, comments;

    public Work(String title, String author, String published, String finished, 
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
    public String getFinished(){
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
     public void setFinished(String finished){
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
