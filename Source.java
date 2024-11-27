import java.util.ArrayList;

public class Source {

    enum Type {
        book,
        article_standalone,
        article_journal,
        video,
        movie,
        wikipedia
    }

    private Type type;
    private ArrayList<String> authors;
    private String[] info;

    private static ArrayList<String> allAuthors;

    public Source(ArrayList<String> authors, String[] info, Type type) {
        this.type = type;
        this.authors = authors;
        this.info = info;
    }

    public String inText() {
        switch (type) {
            case book:
                return "(" + info[3].split(" ")[0] + ", " + authors.get(0).split(" ")[1] + ")" ;
            case article_standalone:
                return "";
            case article_journal:
                return "";
            case video:
                return "";
            case movie: 
                return "";
            case wikipedia:
                return "";
            default:
                return "";
        }
   }

    public String references() {
        switch (type) {
            case book:
                return "**" + info[0] + "**";
            case article_standalone:
                return "";
            case article_journal:
                return "";
            case video:
                return "";
            case movie: 
                return "";
            case wikipedia:
                return "";
            default:
                return "";
        }      
    }
}