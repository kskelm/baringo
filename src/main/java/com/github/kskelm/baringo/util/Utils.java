/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

/**
 * Just some internal candy.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class Utils {

	/**
	 * Returns a string representing the object as a simple field list.
	 * Doesn't pay attention to string formatting or quote escaping;
	 * you get what you get.
	 * @param obj object to return as simple declaration
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
	 * @param fields a map of fields and values to return
	 * @param clsName = the name of the class to use
	 * @return output
	 */
	public static String toString( HashMap<String,Object> fields, String clsName ) {
		StringBuffer buf = new StringBuffer();

		buf.append( clsName ).append( " [ ");
		int num = 0;
		for( String key : fields.keySet() ) {
			if( num++ > 0 ) {
				buf.append( ", " );
			} // if
			buf.append( key )
			.append( " = " )
			.append( fields.get( key ) );

		} // for
		buf.append( " ]\n");

		return buf.toString();
	} // toString
	
	/**
	 * Get the declared field values for the given object at the
	 * given class level. Shallow.
	 * @param obj -- object to fetch fields from
	 * @param cls -- class level to fetch for
	 * @return A map of fields and their values
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

	/**
	 * effectively, List.join(",") like modern languages have.
	 * @param values - list of values to join
	 * @return a single string with them all separated by commas.
	 */
	public static String joinCSV(List<String> values) {
		// form a comma-separated list of id's
		StringBuffer buf = new StringBuffer();
		for( String value : values ) {
			if( buf.length() > 0 ) {
				buf.append( "," );
			} // if
			buf.append( value );
		} // for
		return buf.toString();
	}

} // Utils
