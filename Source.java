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
        String result = "(" + authorInfo.get(0)[0]; // İlk yazarın soyadı ile başla
    
        switch (type) {
            case book:
                if (authors.size() > 2) {
                    result += " et al."; // Üç veya daha fazla yazar varsa "et al." ekle
                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0]; // İkinci yazarın soyadını ekle
                }
                result += ", " + info[1] + ")"; // Yıl bilgisini ekle ve parantezi kapa
                break;
    
            case article_standalone:
                if (authors.size() > 2) {
                    result += " et al.";
                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0];
                }
                result += ", " + info[1] + ")"; // Yıl bilgisi ekleniyor
                break;
    
            case article_journal:
                if (authors.size() > 2) {
                    result += " et al.";
                } else if (authors.size() == 2) {
                    result += " & " + authorInfo.get(1)[0];
                }
                result += ", " + info[1] + ")"; // Yıl bilgisi ekleniyor
                break;
    
            case video:
                result = "(" + authors.get(0) + ", " + info[1] + ")"; // Kanal veya oluşturucu adı ve yıl
                break;
    
            case movie:
                result = "(" + authors.get(0) + " (Director), " + info[1] + ")"; // Yönetmen adı ve yıl bilgisi
                break;
    
            case wikipedia:
                result = "(" + "**" + info[0] + "**, " + info[1] + ")"; // Başlık ve yıl bilgisi
                break;
    
            default:
                result = ""; // Desteklenmeyen türlerde boş döndür
                break;
        }
    
        return result;
    }
    

   public String references(ArrayList<String> existingCitations) {
    switch (type) {

        // Author, A. A. (Year). **Title**. Publisher.
        case book:
            String bookAuthors = getFormattedAuthors(authors, existingCitations);
            return bookAuthors + " (" + resolveYearConflict(info[1], bookAuthors, existingCitations) + "). **" + info[0] + ".** " + info[2] + ".";

        // Author, A. A. (Year, Month Day). **Title**. Publication. URL 
        case article_standalone:
            String articleAuthors = getFormattedAuthors(authors, existingCitations);
            return articleAuthors + " (" + resolveYearConflict(info[1], articleAuthors, existingCitations) + ", " + info[2] + "). " + info[0] + ". **" + info[3] + "**. " + info[4] + ".";

        // Author, A. A. (Year). Title. Journal Name, Volume(Issue), pages. DOI
        case article_journal:
            String journalAuthors = getFormattedAuthors(authors, existingCitations);
            return journalAuthors + " (" + resolveYearConflict(info[1], journalAuthors, existingCitations) + "). " + info[0] + ". **" + info[2] + "**, " + info[3] + "(" + info[4] + "), " + info[5] + ". " + info[6] + ".";

        // Author/Channel. (Year, Month Day). Title [Video]. Platform. URL
        case video:
            return authors.get(0) + ". (" + info[1] + ", " + info[2] + "). " + info[0] + " [Video]. **" + info[3] + "**. " + info[4] + ".";

        // Director, A. A. (Director). (Year). Title [Film]. Prod Company.
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

private String getFormattedAuthors(ArrayList<String> authors, ArrayList<String> existingCitations) {
    if (authors.size() > 2) {
        return authors.get(0) + ", " + authors.get(1) + " et al.";
    } else {
        StringBuilder formattedAuthors = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            formattedAuthors.append(authors.get(i));
            if (i < authors.size() - 1) {
                formattedAuthors.append(", ");
            }
        }
        return formattedAuthors.toString();
    }
}

private String resolveYearConflict(String year, String authorList, ArrayList<String> existingCitations) {
    int conflictCount = 0;
    for (String citation : existingCitations) {
        if (citation.contains(authorList) && citation.contains(year)) {
            conflictCount++;
        }
    }
    if (conflictCount > 0) {
        char suffix = (char) ('a' + conflictCount);
        return year + suffix;
    }
    return year;
}
}