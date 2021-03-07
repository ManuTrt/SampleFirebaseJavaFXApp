package ebnkapp.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DatabaseManager {
    private Firestore database;
    private static DatabaseManager databaseManager = null;

    private DatabaseManager(){
        database = FirebaseConnection.getDatabase();
    }

    public static DatabaseManager getDatabaseManager() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }

        return databaseManager;
    }

    public boolean addData(String name, String password) {
        boolean status = false;
        Map<String, Object> docData = new HashMap<>();

        docData.put("username", name);
        docData.put("password", password);
        ApiFuture<WriteResult> future = database.collection("users").document(name).set(docData);
        try {
            System.out.println("Update time : " + future.get().getUpdateTime());
            status = true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean deleteData(String doc) {
        boolean status = false;

        ApiFuture<WriteResult> writeResult = database.collection("users").document(doc).delete();
        try {
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            status = true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return status;
    }
}

