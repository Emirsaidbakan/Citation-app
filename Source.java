import java.security.AuthProvider;
import java.util.ArrayList;

public class Source {

    enum Type {
        book,
        /*
         * info order:
         * 2: publisher
         * 3: pages
         */
        article_standalone,
        /*
         * info order:
         * 2: month day
         * 3: publication
         * 4: URL
         * 5: pages
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
    /*
     * info order:
     * 0: title
     * 1: year
     * the rest depends on the source (see above)
     */

    private static ArrayList<Source> sources = new ArrayList<>();

    public Source(ArrayList<String> authors, String[] info, Type type) {
        this.type = type;
        this.authors = authors;
        this.info = info;
        sources.add(this);
        authorInfo = new ArrayList<>();

        /*
         * authorInfo[a] carries info for authors[a]
         * 
         * authorInfo[i][0] is the surname
         * the rest is the first name, middle name, etc.
         */
        for (int i = 0; i < authors.size(); i++) {
            authorInfo.add(new String[authors.get(i).split(" ").length]);
            authorInfo.get(i)[0] = authors.get(i).split(" ")[authors.get(i).split(" ").length - 1];
            for (int j = 1; j < authorInfo.get(i).length; j++) {
                authorInfo.get(i)[j] = authors.get(i).split(" ")[j];
            }
        }
    }

    // Getters and Setters
    public ArrayList<String[]> getAuthorInfo() {
        return authorInfo;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String[] getInfo() {
        return info;
    }

    public static ArrayList<Source> getSources() {
        return sources;
    }

    public Type getType() {
        return type;
    }

    public String inText() {

        String result = "(" + authorInfo.get(0)[0];

        switch (type) {
            
            case book:

                if (authors.size() > 2) {

                    result += " et al., " + info[1];

                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0] + ", " + info[1];

                } else {
                    result += ", " + info[1];

                }

                if (info.length > 3) {
                    result += ", " + info[3] + ")";
                } else {
                    result += ")";
                }

                return result;

            case article_standalone:

                result = "(" + authorInfo.get(0)[0];

                if (authors.size() > 2) {

                    result += " et al., " + info[1];

                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0] + ", " + info[1];

                } else {
                    result += ", " + info[1];

                }

                if (info.length > 4) {
                    result += ", " + info[5] + ")";
                } else {
                    result += ")";
                }

                return result;

            case article_journal:
            
                result = "(" + authorInfo.get(0)[0];

                if (authors.size() > 2) {

                    result += " et al., " + info[1];

                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0] + ", " + info[1];

                } else {
                    result += ", " + info[1];

                }

                if (info.length > 4) {
                    result += ", " + info[5] + ")";
                } else {
                    result += ")";
                }

                return result;

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

            // Author, A. A. (Year). **Title**. Publisher.
            case book:
            String bookAuthors = "";
            if (authors.size() > 2) {
                bookAuthors = authors.get(0) + ", " + authors.get(1) + " et al.";
            } 
            else {
                for (int i = 0; i < authors.size(); i++) {
                bookAuthors += authors.get(i);
                    if (i < authors.size() - 1) {
                    bookAuthors += ", ";
                    }
                }
            }
            return bookAuthors + " (" + info[1] + "). **" + info[0] + ".**" + info[2] + ".";

            // Author, A. A. (Year, Month Day). **Title**. Publication. URL
            case article_standalone:
            String articleAuthors = "";
            if(authors.size() > 2) {
                articleAuthors = authors.get(0) + ", " + authors.get(1) + "et al.";
            }
            else {
                for (int i = 0; i < authors.size(); i++) {
                    articleAuthors += authors.get(i);
                        if( i < authors.size()-1) {
                            articleAuthors += ", ";
                        }
                }
            }
            return articleAuthors + " (" + info[1] + ", " + info[2] + "). " + info[0] + ". **" + info[3] + "**. " + info[4] + ".";
            
            //Author, A. A. (Year). Title. Journal Name, Volume(Issue), pages. DOI
            case article_journal:
                String journalAuthors = "";
                if (authors.size() > 2) {
                    journalAuthors = authors.get(0) + ", " + authors.get(1) + " et al.";
                }
                else {
                    for (int i = 0; i < authors.size(); i++) {
                        journalAuthors += authors.get(i);
                        if (i < authors.size() - 1) {
                            journalAuthors += ", ";
                        }
                    }
                }
                return journalAuthors + " (" + info[1] + "). " + info[0] + ". **" + info[2] + "**, " + info[3] + "(" + info[4] + "), " + info[5] + ". " + info[6] + ".";
            
            //Author/Channel. (Year, Month Day). Title [Video]. Platform. URL
            case video:
                return authors.get(0) + ". (" + info[1] + ", " + info[2] + "). " + info[0] + " [Video]. **" + info[3]
                        + "**. " + info[4] + ".";

            // Director, A. A. (Director). (year). Title [Film]. Prod Company.
            case movie:
                return authors.get(0) + " (Director). (" + info[1] + "). " + info[0] + " [Film]. **" + info[2] + "**.";

            // Entry Title. (Year, Month day). In Wikipedia. URL
            case wikipedia:
                return "**" + info[0] + "**. (" + info[1] + ", " + info[2] + "). In **Wikipedia**. " + info[3] + ".";

            // default
            default:
                return "";
        }
    }

//WIP Implementation for distinctions
//e.g. (Emir, Beren, Aybegüm et al., 2012) vs (Emir, Beren, Merve et al., 2012)
/*     public int whichNamesToInclude() {

        int count = 0;

        for (int i = 0; i < sources.size(); i++) {

            for (int j = 0; j < authorInfo.size(); j++) {
                if (authorInfo.get(j)[0].equals(sources.get(i).authorInfo.get(j)[0])) {
                    count++;
                }
            }

        }

        return count;

    } */
}