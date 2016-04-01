***Baringo, an Imgur Client Library***
---
*Baringo is an implementation of Imgur API 3 as a Java client library.*

*Why Use Baringo?*
---
Too much API documentation dives straight into usage instead of stopping to explain why you're here.  Presumably, you're here because you have a Java project and you need it to be able to interface with Imgur.com's API.  Among other things, this allows you to take advantage of their powerful system for storing images on the internet, arranging them into albums, and sharing them as you see fit.

Using Baringo is easy:
1. Register your application there (*this is mandatory*).  You will receive a clientId and clientSecret key.  As noted, keep these secret. **Read the documentation below before doing this!**
2. Set up your Java project to include this library.
3. In your code, create a client and start calling services.

```java
import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.Account;
...
String clientId = "PUT YOUR CLIENT ID HERE!"; // from registration
String clientSecret = "PUT YOUR CLIENT SECRET HERE!"; // from registration

// Setup an authenticated APIClient with your application key and
// application master secret.
BaringoClient client = new BaringoClient( clientId, clientSecret );

// Get the publicly-accessible details of somebody's account
try {
Account acct = svc.accountService().getAccount( "SOME ACCOUNT NAME HERE" );
System.out.println( acct );
} catch (ImgurApiException e) {
e.printStackTrace();
}
```

There are two modes in which services can be called.
* _"Anonymous" mode_ -- no specific user is logged in.  As a result, only "public" services can be called, like listing galleries, downloading images, and uploading anonymous images.  Many methods are unavailable, and others that are return only a subset of the fields available to a logged-in user.  The above example demonstrates the amount of effort necessary to access the Imgur API using this level of authentication. It's easy.
* _"Authenticated" mode_ -- This means you've authenticated to a specific account via OAuth2.  More about this later.  More methods are available, and more data is sometimes available in the results.

**Services**
===
Services on the client are broken up into domain-specific categories to keep things simple:
* accountService -- everything to do with accessing resources for an account
* TODO more here...

**Authorization**
===
In order to access the authenticated operations, your client (registered with Imgur at https://api.imgur.com/oauth2/addclient ) needs to gain the user's permission to access the site on their behalf.

If you already know how to deal with OAuth, you can skip this introductory "Getting Started With Auth" section

**Getting Started With Auth**

Once you've got an Imgur account and you've gone through the process of registering your application, log into your Imgur account and go to the settings menu item from the upper right.  Select "applications" from the sections on the left and click "edit" on the app client you've set up.

This will give you a dialog that lets you set the redirect URL users go to once they've authorized your application to use their account.  For now this is just a test, so enter ```http://localhost:54321/test``` . Ultimately you'll change this to point to some page on your internet-accessible application site.  Enter this URL and click the 'update' button.

For now, this is just a test running on localhost so that you can capture a test authorization code to play with.

We built a totally simple, kit-bashed test HTTP server meant for experimentation with receiving OAuth2 authorization codes and tokens. This should help get you started.

1. go to your src/main directory in the application.
2. Compile the java application:
```
javac com.github.kskelm.baringo.testauth.BaringoAuthDemo
```
Run the app on localhost:
```
java com.github.kskelm.baringo.testauth.BaringoAuthDemo
```
This starts a simple HTTP server on your computer running on http://localhost:54321/test



**Notes**
===
* After any service call, client.getQuota() will return a Quota object with updated limit info on the current authenticated client.  See http://api.imgur.com/#limits for more information.

