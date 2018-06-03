package fileSystem;

public class File {
   public File( String name ) { this.name = name; }
   public void display(String path){ System.out.println(path+name); }
   public void ls() { display(""); }
   private String name;
}