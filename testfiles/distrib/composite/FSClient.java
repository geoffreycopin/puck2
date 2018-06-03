package client;

import fileSystem.Directory;
import fileSystem.File;

public class FSClient {
   public static void main( java.lang.String[] args ) {
      Directory d = new Directory("dir1");
      File  f = new File("a");
      d.add(f); f.ls(); d.ls();
   }
}