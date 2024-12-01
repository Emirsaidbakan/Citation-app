import java.util.ArrayList;

public class Source {

    enum Type {
        book,
        /*info order:
        * 2: publisher 
        */
        article_standalone,
        /*info order:
        * 2: month day
        * 3: publication
        * 4: URL
        */
        article_journal,
        /*
         * info order:
         * 2: journal
         * 3: volume(issue)
         * 4: pages
         * 5: DOI
         */
        video,
        /*
         * info order:
         * 2: platform
         * 3: URL
         */
        movie,
        /*
         * info order:
         * 2: production company
         */
        wikipedia
        /*
         * info order:
         * 2: Month day
         * 3: URL
         */
    }

    private Type type;
    private ArrayList<String> authors;
    private ArrayList<String[]> authorInfo;
    private String[] info;
    /*info order:
     * 0: title
     * 1: year
     * the rest depends on the source 
     */

    private static ArrayList<Source> sources;

    public Source(ArrayList<String> authors, String[] info, Type type) {
        this.type = type;
        this.authors = authors;
        this.info = info;
        sources.add(this);

        /*
         * authorInfo[a] carries info for authors[a]
         * 
         * authorInfo[0] is the surname
         * the rest is the first name, middle name, etc.
         */
        for (int i = 0; i < authors.size(); i++) {
            authorInfo.add( new String[authors.get(i).split(" ").length] );
            authorInfo.get(i)[0] = authors.get(i).split(" ")[ authors.get(i).split(" ").length - 1 ];
            for (int j = 1; j < authorInfo.get(i).length; j++) {
                authorInfo.get(i)[j] = authors.get(i).split(" ")[j];
            }
        }
    }

    public String inText() {
        switch (type) {
            case book:

                String result = "(" + authorInfo.get(0)[0];         

                for (int i = 0; i < sources.size(); i++) {

                    for (int j = 1; j < sources.get(i).authors.size(); j++) {
   
                        if ( !sources.get(i).authorInfo.get(0)[0].equals(authorInfo.get(0)[0]) ) {
                            continue;
                        }

                        if ( authors.get(j) != null && sources.get(i).authors.get(j).equals(authors.get(j)) ) {

                            String[] authorInfo = authors.get(0).split(" ");
                            result += authorInfo[authorInfo.length - 1] + ", ";
                            continue;

                        }
                    }
                    
                }

                return result;
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

            //Author, A. A. (Year). **Title**. Publisher.
            case book:
            String bookAuthors = "";
            for (int i = 0; i < authors.size(); i++) {
                bookAuthors += authors.get(i);
                if (i < authors.size() - 1) {
                    bookAuthors += ", ";
                }
            }
            return bookAuthors + " (" + info[1] + "). **" + info[0] + ".**" + info[2] + ".";

            //Author, A. A. (Year, Month Day). **Title**. Publication. URL 
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