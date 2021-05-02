# WAVE - A SIGNAL TO YOU

### Technology Stack :
1. Android ( Java )
2. Firebase


### Description :
1. Wave is two-way chatting application with features of high level Encryption - Decryption.
2. This app is very secured as it uses password hashing , chat Encryption-Decryption using various Cryptography Algorithms. 
3. This app allows to communicate over cloud environment.

### Features :
1. OTP Authentication using Firebase with SHA-1 & SHA-256.
2. User Login / Registration with Email & Password Validation using Firebase.
3. Using Firebase Authentication to store Email & Password in Encrypted Form.
4. Using Firebase Real Time Database to store Personal Details.
5. Using Firebase Storage to store Images.
6. Fragments to display Chats and Users.
7. Firebase RecyclerView and Firebase RecyclerAdapter to display Users & Chats.
8. Cryptography Algorithms such as AES (Advanced Encryption Standard) and FED (Firebase Encryption Decryption).
9. Online / Offline status using Firebase.
10. Auto Login Using Firebase.
11. Use of Activity LifeCycle Methods to handle application status.
12. Use of Custom Array List to temporarily store Users and Chats.
13. Use of Custom XML Components using Material Design for great User Experience.
14. Use of Essential User Permission to experience full functionality of app.
15. Use of Dexter - A Android library that simplifies the process of requesting permissions at runtime.
16. Use of Glide - A fast and efficient open source media management and image loading framework.
17. Use of Lottie - A mobile library for Android and iOS that parses Adobe After Effects animations exported as json.


### Dependencies :
```
    implementation 'br.com.simplepass:loading-button-android:1.14.0'
    implementation "androidx.cardview:cardview:1.0.0"
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'com.airbnb.android:lottie:3.7.0'
    implementation 'com.karumi:dexter:6.2.2'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation platform('com.google.firebase:firebase-bom:26.8.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.android.gms:play-services-safetynet:17.0.0'
    implementation "androidx.browser:browser:1.3.0"
    implementation 'com.google.android:flexbox:2.0.1'
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation 'com.google.firebase:firebase-database:19.7.0'
    implementation 'com.firebaseui:firebase-ui-database:6.2.1'
    implementation "androidx.recyclerview:recyclerview:1.2.0"
    implementation 'com.android.support:multidex:1.0.3'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.support:appcompat-v7:28.0.0'
```
