Baringo, an Imgur Java Client Library
===================================

Baringo is an implementation of Imgur API 3 as a Java client library.

Using Baringo is easy:
1. Go to http://api.imgur.com/ and read about their services.
2. Register your application there (*this is mandatory*).  You will receive a clientId and clientSecret key.  As noted, keep these secret.
3. Set up your Java project to include this library.
4. In your code, create a client and start calling services.

```java
import com.github.kskelm.baringo.ImgurClient;
import com.github.kskelm.baringo.Account;
...
String clientId = "clientId";  // PUT YOUR CLIENT ID HERE!
String clientSecret = "clientSecret"; // PUT YOUR CLIENT SECRET HERE!

// Setup an authenticated APIClient with your application key and
// application master secret.
ImgurClient client = new ImgurClient( clientId, clientSecret );

// Get details of somebody's account
try {
    Account acct = svc.accountService().getAccount( "SOME ACCOUNT NAME HERE" );
    System.out.println( acct );
} catch (ImgurApiException e) {
    e.printStackTrace();
}
```

There are two kinds of services, or rather two modes in which services can be called.
* "Anonymous" mode -- no specific user is logged in.  As a result, only "public" services can be called, like listing galleries, downloading images, and uploading anonymous images.  Many methods are unavailable, and others that are return only a subset of the fields available to a logged-in user.
* "Authenticated" mode.  This means you've authenticated to a specific account via OAuth2.  More about this later.  More methods are available, and more data is sometimes available in the results.


Notes
=============
* After any service call, client.getQuota() will return a Quota object with updated limit info on the current authenticated client.  See http://api.imgur.com/#limits for more information.



