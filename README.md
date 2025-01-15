# Journal App

A simple **Journal App** that allows users to create an account and login to add images and write thoughts, storing all data using **Firebase**. The app is built with **Java** and follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure clean and maintainable code.

## Features

- **Add Images**: Users can capture or select images from their device and add them to their journal entries.
- **Write Thoughts**: Users can add text to describe or reflect on the image.
- **Firebase Authentication**: Users must create an account to use the app. Authentication is handled using Firebase, ensuring secure login and registration.
- **Firebase Integration**: All journal entries, including images and thoughts, are stored in **Firebase** for cloud synchronization.
- **MVVM Architecture**: The app uses the **MVVM** design pattern to separate the UI from business logic, making the app scalable and easier to maintain.

## Technologies Used

- **Java**: Programming language used for Android development.
- **Android SDK**: Android development tools and libraries.
- **Firebase Authentication**: Used for user account creation and login, enabling secure user authentication.
- **Firebase Database**: For real-time storage and synchronization of journal entries, including images and thoughts.
- **Firebase Storage**: For storing and retrieving images uploaded by users.
- **MVVM Architecture**: Model-View-ViewModel design pattern for cleaner code structure and separation of concerns.

## Screenshots

_Add some screenshots of the app here (you can upload images to GitHub or any other image hosting platform and add the URLs)_

![Screenshot 1](https://github.com/MaddyRizvi/journal_app/blob/main/assets/login.png)
![Screenshot 2](https://github.com/MaddyRizvi/journal_app/blob/main/assets/loginscreen.png)
![Screenshot 3](https://github.com/MaddyRizvi/journal_app/blob/main/assets/add.png)

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/MaddyRizvi/journal_app

# Journal App

## 2. Open the project in Android Studio:

1. Open Android Studio and select Open an existing project.
2. Navigate to the project directory and open the project folder.

## 3. Add Firebase to the project:

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Create a new Firebase project.
3. Add your app to the Firebase project.
4. Download the `google-services.json` file and place it in the `app` directory of your project.

## 4. Sync Firebase dependencies:

1. In Android Studio, open the `build.gradle` files and ensure that the necessary Firebase dependencies are added.
2. Use Firebase Authentication, Firebase Database, and Firebase Storage for syncing images and data.

## 5. Build and Run the App:

1. Once Firebase is configured and dependencies are synced, build and run the app on an emulator or device.

## 6. App Usage

1. Open the app to start journaling.
2. Add a new entry by tapping the "Add" button.
3. Choose or capture an image for your journal entry.
4. Write your thoughts in the text field provided.
5. Save the entry to store it in Firebase. You can view all your past journal entries in the app.

## 7. Firebase Database Structure

- **Users:**
  - Each user’s journal entries are stored under their unique user ID.
  
- **Journal Entries:**
  - Each entry contains:
    - `imageUrl`: URL of the uploaded image.
    - `thoughts`: Text of the user's thoughts.
    - `timestamp`: Date and time of the journal entry.

## 8. Contributing

If you'd like to contribute to the Journal App, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Create a pull request with a description of the changes.

## 9. License

This project is licensed under the MIT License - see the LICENSE file for details.

## 10. Acknowledgements

- **Firebase**: For providing powerful backend services like database, authentication, and file storage.
- **MVVM**: For helping maintain clean and scalable architecture.
- **Android SDK**: For providing tools to build Android apps.

