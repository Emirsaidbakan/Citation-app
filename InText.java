public class InText {
    
    public static String book(String author, String name, String publisher, String city, String date, String page, boolean first) {
        if (first) {
            return author + ", " + name + "(" + city + ": " + publisher + ", " + date + "), " + page + ".";
        }

        
    }

}
