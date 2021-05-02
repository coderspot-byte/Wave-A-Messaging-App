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


### Screenshots :

1. Splash Screen / Welcome Screen :
![Screenshot_2021-05-02-16-03-55-77_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810500-b7dfb180-ab61-11eb-91a2-c09ef7ca3387.jpg)


2. OTP Authentication :
![Screenshot_2021-05-02-16-17-14-09_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810551-0d1bc300-ab62-11eb-82cf-be363b494510.jpg)
![Screenshot_2021-05-02-16-04-34-06_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810557-13aa3a80-ab62-11eb-9e7d-04add60bde45.jpg)


3. Register with Validation :
![Screenshot_2021-05-02-16-05-56-59_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810580-2ae92800-ab62-11eb-841e-79da1b4d33e6.jpg)
![Screenshot_2021-05-02-16-29-06-04_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810862-b57e5700-ab63-11eb-9938-963cb38cc5fa.jpg)
![Screenshot_2021-05-02-16-09-07-98_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810582-2c1a5500-ab62-11eb-8bb6-cd5fc4fb4109.jpg)


4. Login :
![Screenshot_2021-05-02-16-09-29-93_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810659-b2cf3200-ab62-11eb-8880-e0b40329eaca.jpg)


5. Users & Chats :
![Screenshot_2021-05-02-16-10-04-35_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810667-bbc00380-ab62-11eb-92f1-752a1fd99bfd.jpg)


6. Personal Chatting Window : 
![Screenshot_2021-05-02-16-10-54-48_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810706-edd16580-ab62-11eb-9730-aa506cacfb91.jpg)

7. Cryptography Alogrithms :
![Screenshot_2021-05-02-16-11-08-85_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810722-0477bc80-ab63-11eb-935a-081e94af4c5f.jpg)

8. Encrypted Text (When you use AES , FED becomes encrypted on screen and vice versa , in database both are encrypted )  : 
![Screenshot_2021-05-02-16-11-17-44_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810735-13f70580-ab63-11eb-80b6-3990af5d0b83.jpg)
![Screenshot_2021-05-02-16-11-28-40_aa90a2d1b9b24e6079074fe64a8de64e](https://user-images.githubusercontent.com/47007703/116810736-15283280-ab63-11eb-8ec2-ec1f73f889ff.jpg)
![Screenshot_20210502-164457](https://user-images.githubusercontent.com/47007703/116811340-71408600-ab66-11eb-8e4a-03ac7a3c2950.png)
![Screenshot_20210502-164736](https://user-images.githubusercontent.com/47007703/116811341-7271b300-ab66-11eb-9a2a-a68467da20f7.png)


9. Status Online / Offline with Logout :
![Screenshot_20210502-161342](https://user-images.githubusercontent.com/47007703/116811367-9503cc00-ab66-11eb-8fca-d26a22bec9c4.png)
![Screenshot_20210502-161354](https://user-images.githubusercontent.com/47007703/116811369-9634f900-ab66-11eb-9bfd-7e5a03900a67.png)


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
