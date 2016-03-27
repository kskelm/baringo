/**
 * Candy
 */
package com.github.kskelm.imgurapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author kskelm
 *
 */
public class Utils {

	/**
	 * Returns a string representing the object as a simple field list.
	 * Doesn't pay attention to string formatting or quote escaping;
	 * you get what you get.
	 * @param obj - object to return as simple declaration
	 * @return output
	 */
	public static String toString( Object obj ) {
		HashMap<String, Object> fields = toHashMap( obj, obj.getClass() );

		return toString( fields, obj.getClass().getSimpleName() );
	} // toString

	/**
	 * Returns a string representing an object as a simple field list.
	 * Doesn't pay attention to string formatting or quote escaping;
	 * you get what you get.
	 * @param fields - a map of fields and values to return
	 * @param clsName = the name of the class to use
	 * @return output
	 */
	public static String toString( HashMap<String,Object> fields, String clsName ) {
		StringBuffer buf = new StringBuffer();

		buf.append( clsName ).append( " { ");
		int num = 0;
		for( String key : fields.keySet() ) {
			if( num++ > 0 ) {
				buf.append( ", " );
			} // if
			buf.append( key )
			.append( " = " )
			.append( fields.get( key ) );

		} // for
		buf.append( " }\n");

		return buf.toString();
	} // toString
	
	/**
	 * Get the declared field values for the given object at the
	 * given class level. Shallow.
	 * @param obj -- object to fetch fields from
	 * @param cls -- class level to fetch for
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String,Object>toHashMap( Object obj, Class cls ) {
		HashMap<String,Object> map = new HashMap<>();
		for( Field f : cls.getDeclaredFields() ) {
			if( Modifier.isStatic( f.getModifiers() ) ) {
				continue;
			} // if
			f.setAccessible( true );
			try {
				map.put( f.getName(), f.get( obj ) );
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} // try-catch
		} // for
		return map;
	} // toHashMap

} // Utils
