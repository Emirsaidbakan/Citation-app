import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> source1Authors = new ArrayList<>();
        String[] source1Info = {"Bütün Bir Ömür", "1989", "Can", "129-130"};
        
        source1Authors.add("Gabriel Garcia Marquez");
        source1Authors.add("Emir Said Bakan");
        source1Authors.add("Beren Irgat");

        ArrayList<String> source2Authors = new ArrayList<>();
        String[] source2Info = {"Kitap işte", "1989", "Can"};
        
        source2Authors.add("Gabriel Garcia Marquez");
        source2Authors.add("Emir Said Bakan");
        source2Authors.add("Aybegüm Fatma Çelebi");

        Source source1 = new Source(source1Authors, source1Info, Source.Type.book);
        Source source2 = new Source(source2Authors, source2Info, Source.Type.book);

        System.out.println(source1.inText());
        System.out.println(source2.inText());
    }
}
