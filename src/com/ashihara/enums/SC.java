/**
 * The file SC.java was created on 2010.26.1 at 23:21:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.enums;

import java.util.ArrayList;
import java.util.List;


public class SC {

	public static interface UI_PREFERENCES {
		
		public static String DEFAULT_LOOK_AND_FEEL = "Plastic3D";
		public static String DEFAULT_THEME = "DesertBluer";
		
		public static class UI_LANGUAGE {
			public static final String ENGLISH = "ENGLISH";
			public static final String LATVIAN = "LATVIAN";
			public static final String DEFAULT = ENGLISH;
			private static final UIC english = new UICEnglish();
			
			public static UIC getUIC(String language) {
				if (ENGLISH.equals(language)) {
					return english;
				}
				else {
					throw new IllegalArgumentException("Unsupported language");
				}
			}
			
		}
	}
	
	public static class USER_ROLE {
		public static final String ADMINISTRATOR = "ADMINISTRATOR";
	}
	
	public static class GROUP_TYPE implements Captionable<String> {
		public static final String OLYMPIC = "OLYMPIC";
		public static final String ROUND_ROBIN = "ROUND_ROBIN";

		@Override
		public List<String> getAllValues() {
			List<String> all = new ArrayList<String>();
			all.add(OLYMPIC);
			all.add(ROUND_ROBIN);
			return all;
		}

		@Override
		public String getUICaption(String param, UIC uic) {
			if (OLYMPIC.equals(param)) {
				return uic.CUP();
			}
			if (ROUND_ROBIN.equals(param)) {
				return uic.ROUND_ROBIN();
			}
			else {
				throw new IllegalArgumentException("Unsupported group type");
			}
		}
	}
	
	public static class GROUP_STATUS implements Captionable<String> {
		public static final String INITIAL = "INITIAL";
		public static final String STARTED = "STARTED";

		@Override
		public List<String> getAllValues() {
			List<String> all = new ArrayList<String>();
			all.add(INITIAL);
			all.add(STARTED);
			return all;
		}

		@Override
		public String getUICaption(String param, UIC uic) {
			if (INITIAL.equals(param)) {
				return uic.INITIAL();
			}
			if (STARTED.equals(param)) {
				return uic.STARTED();
			}
			else {
				throw new IllegalArgumentException("Unsupported group status");
			}
		}
	}
	
	public static class GENDER implements Captionable<String> {
		public static final String MALE = "MALE";
		public static final String FEMALE = "FEMALE";
		public static final String MIXED = "MIXED";

		@Override
		public List<String> getAllValues() {
			List<String> all = new ArrayList<String>();
			all.add(MALE);
			all.add(FEMALE);
			all.add(MIXED);
			return all;
		}

		@Override
		public String getUICaption(String param, UIC uic) {
			return getCaption(param, uic);
		}

		public static String getCaption(String param, UIC uic) {
			if (MALE.equals(param)) {
				return uic.MALE();
			} else if (FEMALE.equals(param)) {
				return uic.FEMALE();
			} else if (MIXED.equals(param)) {
				return uic.MIXED();
			} else {
				throw new IllegalArgumentException("Unsupported gender");
			}
		}

	}
	
	public interface Captionable<T> {
		public T getUICaption(T param, UIC uic);
		public List<T> getAllValues();
	}

}