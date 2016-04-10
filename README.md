***Baringo, an Imgur Client Library***
---
*Baringo is an implementation of Imgur API 3 as a Java client library.*

*Why Use Baringo?*
---
Too much API documentation dives straight into usage instead of stopping to explain why you're here.  Presumably, you're here because you have a Java project and you need it to be able to interface with Imgur.com's API.  Maybe you're writing an Android client, maybe you're making a desktop application, or maybe you're writing a server that uses Imgur as a resource.


Using Baringo is easy:
1. Register your application there (*Mandatory; Imgur requires this*).  You will receive a clientId key and a clientSecret key.  As noted, keep these secret. **Read the documentation below before doing this!**
2. Set up your Java project to include this library.
3. In your code, create a client and start calling services.

```java
import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.BaringoApiException;

class ShowBaringo {

  public static void main( String[] args ) { 
    String clientId = "PUT YOUR CLIENT ID HERE!"; // from registration
    String clientSecret = "PUT YOUR CLIENT SECRET HERE!"; // from registration

    // Set up an authenticated APIClient with your clientKey and 
    // clientSecret as issued by Imgur.
    BaringoClient client = new BaringoClient( clientId, clientSecret );

    // Get the info about an image
    try {
      Image image = client.imageService().getImageInfo( "bHEb5Sw" );
      System.out.println( image );
    } catch (BaringoApiException e) {
      e.printStackTrace();
    }   
  } 
}
```

There are two modes in which services can be called.
* _"Anonymous" mode_ -- no specific user is logged in.  As a result, only "public" services can be called, like listing galleries, downloading images, and uploading anonymous images.  Many methods are unavailable, and others that are return only a subset of the fields available to a logged-in user.  The above example demonstrates the amount of effort necessary to access the Imgur API using this level of authentication. It's easy. Methods in the javadoc will say "ACCESS: ANONYMOUS"
* _"Authenticated" mode_ -- This means you've authenticated to a specific account via OAuth2.  More about this later.  More methods are available, and more data is sometimes available in the results. Methods in the javadoc will say "ACCESS: AUTHENTICATED USER"

**Services**
===
Services on the client are broken up into domain-specific categories to keep things simple:
* **AccountService** -- Working accessing resources for an account
* **AlbumService** -- Working with albums of images
* **AuthService** -- Manages access to the API, either in anonymous mode or authenticated mode
* **CommentService** -- Add/delete comments on albums and images
* **ConversationService** -- Used for communications directly between users
* **CustomGalleryService** -- Each user can have a custom version of the Imgur gallery that uses tags.  They can also globally block specific tags.
* **GalleryService** -- Galleries are selections of topics sorted by virality (viralness?), age, vote score, etc.
* **ImageService** -- Why we're here, right?  Upload/download images and query info about them
* **MemeService** -- Get a list of the current default memes
* **NotificationService** -- Fetch notifications for the user and mark them as viewed
* **TopicService** -- Work with topic-specific content that's been marked funny, aww, reaction, etc

Imgur actually has a number of different endpoints in multiple services that acheive the same thing. For instance adding a comment on an image or album has an image service endpoint, an album service endpoint, and a comment service endpoint.  I've simplified it to just one for clarity.  In this case, you'll find ```client.commentService().addComment()``` is what you're looking for.

**Authorization**
===
In order to access the authenticated operations, your client (registered with Imgur at https://api.imgur.com/oauth2/addclient ) needs to gain the user's permission to access the site on their behalf.

If you already know how to deal with OAuth2, you can skip this introductory "Getting Started With Auth" section.

*Getting Started With OAuth2*
---
***Baringo, an Imgur Client Library***
---
*Baringo is an implementation of Imgur API 3 as a Java client library.*

*Why Use Baringo?*
---
Too much API documentation dives straight into usage instead of stopping to explain why you're here.  Presumably, you're here because you have a Java project and you need it to be able to interface with Imgur.com's API.  Maybe you're writing an Android client, maybe you're making a desktop application, or maybe you're writing a server that uses Imgur as a resource.


Using Baringo is easy:
1. Register your application there (*Mandatory; Imgur requires this*).  You will receive a clientId key and a clientSecret key.  As noted, keep these secret. **Read the documentation below before doing this!**
2. Set up your Java project to include this library.
3. In your code, create a client and start calling services.

```java
import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.BaringoApiException;

class ShowBaringo {

  public static void main( String[] args ) { 
    String clientId = "PUT YOUR CLIENT ID HERE!"; // from registration
    String clientSecret = "PUT YOUR CLIENT SECRET HERE!"; // from registration

    // Set up an authenticated APIClient with your clientKey and 
    // clientSecret as issued by Imgur.
    BaringoClient client = new BaringoClient( clientId, clientSecret );

    // Get the info about an image
    try {
      Image image = client.imageService().getImageInfo( "bHEb5Sw" );
      System.out.println( image );
    } catch (BaringoApiException e) {
      e.printStackTrace();
    }   
  } 
}
```

There are two modes in which services can be called.
* _"Anonymous" mode_ -- no specific user is logged in.  As a result, only "public" services can be called, like listing galleries, downloading images, and uploading anonymous images.  Many methods are unavailable, and others that are return only a subset of the fields available to a logged-in user.  The above example demonstrates the amount of effort necessary to access the Imgur API using this level of authentication. It's easy. Methods in the javadoc will say "ACCESS: ANONYMOUS"
* _"Authenticated" mode_ -- This means you've authenticated to a specific account via OAuth2.  More about this later.  More methods are available, and more data is sometimes available in the results. Methods in the javadoc will say "ACCESS: AUTHENTICATED USER"

**Services**
===
Services on the client are broken up into domain-specific categories to keep things simple:
* **AccountService** -- Working accessing resources for an account
* **AlbumService** -- Working with albums of images
* **AuthService** -- Manages access to the API, either in anonymous mode or authenticated mode
* **CommentService** -- Add/delete comments on albums and images
* **ConversationService** -- Used for communications directly between users
* **CustomGalleryService** -- Each user can have a custom version of the Imgur gallery that uses tags.  They can also globally block specific tags.
* **GalleryService** -- Galleries are selections of topics sorted by virality (viralness?), age, vote score, etc.
* **ImageService** -- Why we're here, right?  Upload/download images and query info about them
* **MemeService** -- Get a list of the current default memes
* **NotificationService** -- Fetch notifications for the user and mark them as viewed
* **TopicService** -- Work with topic-specific content that's been marked funny, aww, reaction, etc

Imgur actually has a number of different endpoints in multiple services that acheive the same thing. For instance adding a comment on an image or album has an image service endpoint, an album service endpoint, and a comment service endpoint.  I've simplified it to just one for clarity.  In this case, you'll find ```client.commentService().addComment()``` is what you're looking for.

**Authorization**
===
In order to access the authenticated operations, your client (registered with Imgur at https://api.imgur.com/oauth2/addclient ) needs to gain the user's permission to access the site on their behalf.

If you already know how to deal with OAuth2, you can skip this introductory "Getting Started With Auth" section.

*Getting Started With OAuth2*
---

Basically, OAuth2 is a mechanism for allowing applications to operate on various websites without having to manage accounts everywhere. https://en.wikipedia.org/wiki/OAuth does a good job explaining in more depth.  You'll need this in order to access Imgur resources associated with specific user accounts.

Once you've got an Imgur account and you've gone through registering your application, log into your Imgur account and go to the settings menu item from the upper right.  Select "applications" from the sections on the left and click "edit" on the app client you've set up.

This will give you a dialog that lets you set the redirect URL users go to once they've authorized your application to use their account.  For now this is just a test, so enter ```http://localhost:54321/test``` . Ultimately you'll change this to point to some page on your internet-accessible application site.  Enter this URL and click the 'update' button.

Baringo includes a totally simple, bare-bones test HTTP server meant for experimentation with receiving OAuth2 authorization codes and tokens. This should help get you started.

To use it, run the application on your localhost machine.  Find the Baringo jar file and execute it as follows (the name and version may be different from below):
```
java -classpath baringo-1.0.0-jar-with-dependencies.jar com.github.kskelm.baringo.testauth.BaringoAuthDemo
```
This starts a simple HTTP server on your computer running on http://localhost:54321/

Visit it in your browser to get started with understanding how to use OAuth2 with Imgur via Baringo.

**Running Tests**
===

Currently the JUnit tests are all end-to-end in the sense tht they require a functioning client id and client secret, and (account-specific) refresh token in order to run.  Pass these on the command line as -D parameters like so:
```
    "-Dbaringoclient.clientid=YOUR_CLIENT_ID_HERE" \
    "-Dbaringoclient.clientsecret=YOUR_CLIENT_SECRET_HERE" \
    "-Dbaringoclient.refreshtoken=A_USER_SPECIFIC_OAUTH2_REFRESH_TOKEN_HERE"
```


**Notes**
===

* After any successful service call, client.getQuota() will return a Quota object with updated limit info on the current authenticated client.  See http://api.imgur.com/#limits for more information.
* As of version 0.9.1, all calls are synchronous.  This may change in the future to allow async versions.


Basically, OAuth2 is a mechanism for allowing applications to operate on various websites without having to manage accounts everywhere. https://en.wikipedia.org/wiki/OAuth does a good job explaining in more depth.  You'll need this in order to access Imgur resources associated with specific user accounts.

Once you've got an Imgur account and you've gone through registering your application, log into your Imgur account and go to the settings menu item from the upper right.  Select "applications" from the sections on the left and click "edit" on the app client you've set up.

This will give you a dialog that lets you set the redirect URL users go to once they've authorized your application to use their account.  For now this is just a test, so enter ```http://localhost:54321/test``` . Ultimately you'll change this to point to some page on your internet-accessible application site.  Enter this URL and click the 'update' button.

Baringo includes a totally simple, bare-bones test HTTP server meant for experimentation with receiving OAuth2 authorization codes and tokens. This should help get you started.

To use it, run the application on your localhost machine.  Find the Baringo jar file and execute it as follows (the name and version may be different from below):
```
java -classpath baringo-1.0.0-jar-with-dependencies.jar com.github.kskelm.baringo.testauth.BaringoAuthDemo
```
This starts a simple HTTP server on your computer running on http://localhost:54321/

Visit it in your browser to get started with understanding how to use OAuth2 with Imgur via Baringo.

**Running Tests**
===

Currently the JUnit tests are all end-to-end in the sense tht they require a functioning client id and client secret, and (account-specific) refresh token in order to run.  Pass these on the command line as -D parameters like so:
```
    "-Dbaringoclient.clientid=YOUR_CLIENT_ID_HERE" \
    "-Dbaringoclient.clientsecret=YOUR_CLIENT_SECRET_HERE" \
    "-Dbaringoclient.refreshtoken=A_USER_SPECIFIC_OAUTH2_REFRESH_TOKEN_HERE"
```


**Notes**
===

* After any successful service call, client.getQuota() will return a Quota object with updated limit info on the current authenticated client.  See http://api.imgur.com/#limits for more information.
* As of version 0.9.1, all calls are synchronous.  This may change in the future to allow async versions.

