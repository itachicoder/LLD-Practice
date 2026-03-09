package pu.com.ay.HeadFirstImplementation.Composite;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem {

    List<FileSystem> fileSystemList;
    String directoryName;

    public Directory(String name){
        this.directoryName = name;
        fileSystemList = new ArrayList<>();
    }

    public void add(FileSystem fileSystemObj) {
        fileSystemList.add(fileSystemObj);
    }


    @Override
    public void ls(){
        for(FileSystem files : fileSystemList){
            files.ls();
        }

    }
    
    
}
