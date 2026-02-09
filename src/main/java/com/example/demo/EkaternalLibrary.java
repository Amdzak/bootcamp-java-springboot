package com.example.demo;

import java.util.UUID;

public class EkaternalLibrary {
    private final String libraryUniqueId;
    private final String libraryName;

    public EkaternalLibrary(String name){
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
