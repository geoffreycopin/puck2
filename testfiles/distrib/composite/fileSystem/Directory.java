package fileSystem;

import java.util.ArrayList;
import java.util.List;

public class Directory {
   public Directory( String name ) { this.name = name; }
   public void add( File f ) { files.add( f ); }
   public void add( Directory d ) { directories.add( d ); }
   public void display(String path) {
      System.out.println(path + name);
      String npath = path + name +"/";
      for(File f: files)
         f.display(npath);
      for(Directory d: directories)
         d.display(npath); 
   }
   public void ls(){ display(""); }
   private String    name;
   private List<File> files = new ArrayList<File>();
   private List<Directory> directories = new ArrayList<Directory>();
}