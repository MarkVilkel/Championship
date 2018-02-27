/**
 * The file RulesManagerFactory.java was created on 27 февр. 2018 г. at 23:39:51
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;

public class RulesManagerFactory {
	
	private static final Map<String, RulesManager> map = new ConcurrentHashMap<>();

	public static RulesManager getRulesManager(String rules, UIC uic) {
		if (rules == null) {
			rules = SC.RULES.JOSUI_STYLE;
		}
		return map.computeIfAbsent(rules, (r) -> {
			if (SC.RULES.ALL_STYLE.equals(r)) {	
				return new AllStyleRulesManager(uic);
			} else if (SC.RULES.JOSUI_STYLE.equals(r)) {	
				return new JosuiStyleRulesManager(uic);
			} else {
				throw new IllegalArgumentException("Unsupported rules " + r);
			}
		});
	}
	
}
