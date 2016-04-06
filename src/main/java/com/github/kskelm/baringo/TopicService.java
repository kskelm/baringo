/**
 * Imgur API service for topics {@link https://api.imgur.com/endpoints/topic}
 */
package com.github.kskelm.baringo;

import java.io.IOException;
import java.util.List;

import com.github.kskelm.baringo.model.GalleryItem;
import com.github.kskelm.baringo.model.GalleryItemProxy;
import com.github.kskelm.baringo.model.ImgurResponseWrapper;
import com.github.kskelm.baringo.model.Topic;
import com.github.kskelm.baringo.util.BaringoApiException;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Response;


/**
 * @author kskelm
 *Imgur API service for topics {@link https://api.imgur.com/endpoints/topic}
 */
public class TopicService {

	/**
	 * Return a list of the default topics available on Imgur.
	 * @return list of Topic objects
	 * @throws BaringoApiException - d'oh
	 */
	public List<Topic> listDefaultTopics() throws BaringoApiException {
		
		Call<ImgurResponseWrapper<List<Topic>>> call =
				client.getApi().listDefaultTopics();

		try {
			Response<ImgurResponseWrapper<List<Topic>>> res = call.execute();
			ImgurResponseWrapper<List<Topic>> out = res.body();

			client.throwOnWrapperError( res );
			List<Topic> list = out.getData();
			
			// convert all their #)(*$)#% proxy items into real items
			// TODO: REFACTOR THIS
			for( Topic topic : list ) {
				GalleryItemProxy proxy = topic.getItemProxy();
				if( proxy == null ) {
					continue;
				} // if
				topic.setConvertedItem( client.galleryService()
						.convertProxyItem( proxy ) );
			} // for
			return list;
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
	} 

	/**
	 * Return a list of GalleryItems pertinent to this topic
	 * @param topicId - the id or URL-formatted name of the topic
	 * @param sort - the sort -- viral | time | top
	 * @param window - the windowing mechanism if top: day | week | month | year | all
	 * @param page - the page number, starting at 0
	 * @return a list of GalleryItem objects
	 * @throws BaringoApiException - I think I need to lie down
	 */
	public List<GalleryItem> listTopic(
			int topicId,
			GalleryItem.Sort sort,
			GalleryItem.Window window,
			int page ) throws BaringoApiException {
	
		String sortStr = sort.name().toLowerCase();
		String windowStr = window.name().toLowerCase();

		Call<ImgurResponseWrapper<List<GalleryItemProxy>>> call =
				client.getApi().listTopicItems(
						topicId,
						sortStr,
						windowStr,
						page );

		try {
			Response<ImgurResponseWrapper<List<GalleryItemProxy>>> res = call.execute();
			ImgurResponseWrapper<List<GalleryItemProxy>> list = res.body();

			client.throwOnWrapperError( res );

			// TODO: Look into refactoring this
			return client.galleryService().convertToGalleryItems( list.getData() );
		} catch (IOException e) {
			throw new BaringoApiException( e.getMessage() );
		} // try-catch
		
	}
	
	// ================================================
	protected TopicService( BaringoClient imgurClient, GsonBuilder gsonBuilder ) {
		this.client = imgurClient;
	} // constructor

	private BaringoClient client = null;

} // class AccountService
