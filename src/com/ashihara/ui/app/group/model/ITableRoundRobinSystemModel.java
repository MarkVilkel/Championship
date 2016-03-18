/*
 * Copyright 2011 Dukascopy Bank SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ashihara.ui.app.group.model;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

/**
 * @author Mark Vilkel
 */
public interface ITableRoundRobinSystemModel<T extends UIView<?>> extends UIModel<T> {

	void linkClicked(FightResult value, String columnId);

	void reset();

}
