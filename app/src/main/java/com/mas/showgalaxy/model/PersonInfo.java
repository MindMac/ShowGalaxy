package com.mas.showgalaxy.model;


public class PersonInfo {
    private int mId;
    private String mName;
    private String mCharacter;
    private String mProfilePath;

    private String mBiography;
    private String mBirthday;
    private String mBirthPlace;

    public PersonInfo(int id, String name, String profilePath){
        this.mId = id;
        this.mName = name;
        this.mProfilePath = profilePath;
    }

    public PersonInfo(int id, String name, String character, String profilePath){
        this.mId = id;
        this.mName = name;
        this.mCharacter = character;
        this.mProfilePath = profilePath;
    }

    public PersonInfo(int id, String name, String biography, String birthday, String birthPlace,
                      String profilePath){
        this.mId = id;
        this.mName = name;
        this.mBiography = biography;
        this.mBirthday = birthday;
        this.mBirthPlace = birthPlace;
        this.mProfilePath = profilePath;
    }

    public int getPersonId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getCharacter(){
        return mCharacter;
    }

    public String getProfilePath(){
        return mProfilePath;
    }

    public String getBiography(){
        return mBiography;
    }

    public String getBirthday(){
        return mBirthday;
    }

    public String getBirthPlace(){
        return mBirthPlace;
    }
}
