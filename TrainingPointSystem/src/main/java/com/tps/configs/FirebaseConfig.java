package com.tps.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("serviceAccountKey.json").getFile());
        FileInputStream serviceAccount =
                new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://training-point-70ac7-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public Firestore firestore() throws IOException {
        FirebaseApp firestoreApp = firebaseApp();

        return FirestoreClient.getFirestore(firestoreApp);
    }

}
