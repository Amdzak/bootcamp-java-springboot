package com.example.demo;

import java.util.UUID;

public class EksternalLibrary {
    private final String libraryUniqueId;
    private final String libraryName;

    public EksternalLibrary(String name){
        this.libraryName = name;
        this.libraryUniqueId = UUID.randomUUID().toString();
    }

    public String getLibraryName(){
        return this.libraryName;
    }

    public String getLibraryUniqueId(){
        return this.libraryUniqueId;
    }
}
