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

            //Author, A. A. (Year). **Title. Publisher.
            case book:
            String bookAuthors = "";
            for (int i = 0; i < authors.size(); i++) {
                bookAuthors += authors.get(i);
                if (i < authors.size() - 1) {
                    bookAuthors += ", ";
                }
            }
            return bookAuthors + " (" + info[1] + "). **" + info[0] + ".**" + info[2] + ".";

            //Author, A. A. (Year, Month Day). Title. Publication. URL
            case article_standalone:
            String articleAuthors = "";
            for (int i = 0; i < authors.size(); i++) {
                articleAuthors += authors.get(i);
                if( i < authors.size()-1) {
                    articleAuthors += ", ";
                }
            }
            return articleAuthors + " (" + info[1] + ", " + info[2] + "). " + info[0] + ". **" + info[3] + "**. " + info[4] + ".";
            
            //Author, A. A. (Year). Title. Journal Name, Volume(Issue), pages. DOI
            case article_journal:
                String journalAuthors = "";
                for (int i = 0; i < authors.size(); i++) {
                    journalAuthors += authors.get(i);
                    if (i < authors.size() - 1) {
                        journalAuthors += ", ";
                    }
                }
                return journalAuthors + " (" + info[1] + "). " + info[0] + ". **" + info[2] + "**, " + info[3] + "(" + info[4] + "), " + info[5] + ". " + info[6] + ".";
            
            //Author/Channel. (Year, Month Day). Title [Video]. Platform. URL
            case video:
                return authors.get(0) + ". (" + info[1] + ", " + info[2] + "). " + info[0] + " [Video]. **" + info[3] + "**. " + info[4] + ".";

            //Director, A. A. (Director). (year). Title [Film]. Prod Company.
            case movie: 
                return authors.get(0) + " (Director). (" + info[1] + "). " + info[0] + " [Film]. **" + info[2] + "**.";
            
            //Entry Title. (Year, Month day). In Wikipedia. URL
            case wikipedia:
                return "**" + info[0] + "**. (" + info[1] + ", " + info[2] + "). In **Wikipedia**. " + info[3] + ".";
            
            //default
            default:
                return "";
        }      
    }
}