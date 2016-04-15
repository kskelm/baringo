***Baringo, an Imgur Client Library***
---
*Baringo is an implementation of Imgur API 3 as a Java client library.*

*Why Use Baringo?*
---
Too much API documentation dives straight into usage instead of stopping to explain why you're here.  Presumably, you're here because you have a Java project and you need it to be able to interface with Imgur.com's API.  Maybe you're writing an Android client, maybe you're making a desktop application, or maybe you're writing a server that uses Imgur as a resource.


Using Baringo is easy:
* Register your application there (*Mandatory; Imgur requires this*).  You will receive a clientId key and a clientSecret key.  As noted, keep these secret. **Read the documentation below before doing this!**
* Set up your Java project to include this library.
* In your code, create a client and start calling services.

Here's an example:

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
    try {
        client = new BaringoClient.Builder()
                .clientAuth( clientId, clientSecret )
                .build();
    } catch (BaringoApiException e) {
        e.printStackTrace();
    }       


    // Get the info about an image
    try {
      Image image = client.imageService().getImageInfo( "v24g7o7" );
      System.out.println( image );
    } catch (BaringoApiException e) {
      e.printStackTrace();
    }   
  } 
}
```

There are two modes in which services can be called.
* _"Anonymous" mode_ -- no specific user is logged in.  As a result, only "public" services can be called, like listing galleries, downloading images, and uploading anonymous images.  Many methods are unavailable, and others that are return only a subset of the fields available to a logged-in user.  The above example demonstrates the amount of effort necessary to access the Imgur API using this level of authentication. It's easy. Methods in the javadoc will say **ACCESS: ANONYMOUS**
* _"Authenticated" mode_ -- This means you've authenticated to a specific account via OAuth2.  More about this later.  More methods are available, and more data is sometimes available in the results. Methods in the javadoc will say **ACCESS: AUTHENTICATED USER**

Some services behave differently depending on whether a user is logged in.  For instance ```deleteAlbum()``` will work if the user owns the album in question, but if there is no logged in user, it will only work if the ```deleteHash``` is set.

**Services**
===
Services on the client are broken up into domain-specific categories to keep things simple:
* **AccountService** -- Accessing resources for an account
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

Imgur actually has a number of different endpoints in multiple services that achieve the same thing. For instance adding a comment on an image or album has an image service endpoint, an album service endpoint, and a comment service endpoint.  I've simplified it to just one for clarity.  In this case, you'll find ```client.commentService().addComment()``` is what you're looking for.

**Authorization**
===
In order to access the authenticated operations, your client (registered with Imgur at https://api.imgur.com/oauth2/addclient ) needs to gain the user's permission to access the site on their behalf.

If you already know how to deal with OAuth2, you can skip this introductory "Getting Started With OAuth2" section below.

Authorizing as a user is simple with Baringo.  After setting up the BaringoClient, just call ```client.authService().setRefreshToken()```.

For an example, see the source at ```com.github.kskelm.baringo.test.Setup#switchToUserAuth()```.

Alternatively, if you've received an Authorization Code from Imgur, client.authService().tradeAuthCodeForTokens() will fetch the access token and refresh token for you, and simultaneously ensure that it has a current access token with a known expiration. _You should immediately call ```client.authService().getRefreshToken()``` and store the token somewhere-- encrypted-- so you can henceforth call ```setRefreshToken()``` instead during startup._  Auth codes only live a few minutes.

Baringo uses passive access code maintenance; when you pass it a refresh token, it immediately asks for a new access token so it knows the expiration date.  This allows it to request a new one as necessary, rather than use OkHttp interceptors to automatically update tokens and retry calls.  We can do this in the future if the current approach turns out to be problematic.


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

**Data Consistency**
===
Imgur's systems seem to be eventually-consistent in a number of scenarios.

This means that if-- for example-- you update an album's title and immediately re-fetch that album from Imgur, the new object may or may not include the changes you made.  I've seen it take several minutes in some circumstances.



***Occasional Test Issues***
===
A persistent issue with our Imgur access has been calls sporadically failing during tests.  One run will be all green, and the next will see one or two of the calls go red.  The next run might bring them green again.

This may be due to throttling/rate limiting, but the jury is still out.  It's probably going to be fine if you use it in normal application circumstances. I've added a 1 second delay between tests to attempt to solve this.


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
* As of version 1.0.0 all calls are synchronous.  This may change in the future to allow async versions.
* Baringo relies on Retrofit2 and OkHttp3. I tried using Baringo with another project that required Retrofit 1.x and it didn't go well.
* Why "Baringo?"  Baringo is an endangered sub-species of giraffe, found around the Lake Baringo area of Kenya.  Therefore Baringo seemed like an appropriate name for an Imgur API client.  https://en.wikipedia.org/wiki/Rothschild's_giraffe 




