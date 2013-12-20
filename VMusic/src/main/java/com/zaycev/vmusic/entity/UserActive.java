package com.zaycev.vmusic.entity;

public class UserActive {

    private static long uID = Account.getUserID();
    private static long aID;

//    private static void saveState() {
//        SharedPreferences sharedPreferences = VMusicApp.getContext().getSharedPreferences("userActive", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong("userID", uID);
//        editor.putLong("albumID", aID);
//        editor.commit();
//    }
//
//    private static void restoreState() {
//        SharedPreferences sharedPreferences = VMusicApp.getContext().getSharedPreferences("userActive", Context.MODE_PRIVATE);
//        uID = sharedPreferences.getLong("userID", 0);
//        aID = sharedPreferences.getLong("albumID", 0);
//    }

    public static long getID() {
        return uID;
    }

    public static void setID(long userID) {
        uID = userID;
    }

    public static void setAlbumID(long albumID) {
        aID = albumID;
    }

    public static long getAlbumID() {
        return aID;
    }
}
