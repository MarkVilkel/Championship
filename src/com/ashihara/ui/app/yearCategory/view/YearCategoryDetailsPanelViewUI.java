/**
 * The file StudentDetailsDialog.java was created on 2008.30.1 at 23:57:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.yearCategory.model.IYearCategoryDetailsModelUI;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.editor.ComboBoxTableCellEditor;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.AddDeleteRowPanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.core.renderer.KASSmallHeaderRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class YearCategoryDetailsPanelViewUI extends KASPanel implements UIView<IYearCategoryDetailsModelUI>{
	private static final long serialVersionUID = 1L;
	
	private KASTextField txtFrom, txtTo;
	private KASPanel studentDetailsPanel;
	private IYearCategoryDetailsModelUI modelUI;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	private YearWeightCategoryTable yearWeightCategoryTable;
	private JScrollPane yearWeightCategoryTableScroll;
	private KASComboBox cmbWeightCategory;
	
	public YearCategoryDetailsPanelViewUI(IYearCategoryDetailsModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getDetailsUIPanel(), BorderLayout.CENTER);
		add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
	}

	public KASPanel getDetailsUIPanel() {
		if (studentDetailsPanel == null){
			studentDetailsPanel = new KASPanel();
			FormLayout fl = new FormLayout(
					"right:90dlu, 4dlu, pref, 20dlu, pref, 4dlu, pref, 90dlu",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 150dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, studentDetailsPanel);
			
			builder.addSeparator("  " + uic.YEAR_CATEGORY_INFO(), cc.xyw(1, 1, 8));
			
			builder.addLabel(uic.FROM_YEAR()+": ", cc.xy(1, 3));
			builder.add(getTxtFrom(), cc.xy(3, 3));
			
			builder.addLabel(uic.TO_YEAR()+": ", cc.xy(1, 5));
			builder.add(getTxtTo(), cc.xy(3, 5));
			
			builder.addLabel(uic.WEIGHT_CATEGORIES()+": ", cc.xy(1, 7));
			builder.add(getYearWeightCategoryTableScroll(), cc.xywh(3, 7, 6, 3));
		}
		return studentDetailsPanel;
	}

	public KASTextField getTxtFrom() {
		if (txtFrom == null){
			txtFrom = UIFactory.createKASTextField();
			txtFrom.setMaxSymbolsCount(255);
			txtFrom.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtFrom;
	}

	public KASTextField getTxtTo() {
		if (txtTo == null){
			txtTo = UIFactory.createKASTextField();
			txtTo.setMaxSymbolsCount(255);
			txtTo.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtTo;
	}

	public IYearCategoryDetailsModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IYearCategoryDetailsModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public SaveCancelResetButtonPanel getSaveCancelResetButtonPanel() {
		if (saveCancelResetButtonPanel == null) {
			ActionListener saveAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().save();
				}
			};
			ActionListener resetAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().reset();
				}
			};
			ActionListener cancelAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			saveCancelResetButtonPanel = new SaveCancelResetButtonPanel(saveAl, cancelAl, resetAl);
		}
		return saveCancelResetButtonPanel;
	}
	
	public class YearWeightCategoryTable extends AddDeleteButtonsTablePanel<YearWeightCategoryLink>{
		private static final long serialVersionUID = 1L;

		public YearWeightCategoryTable() {
			super(YearWeightCategoryLink.class, AddDeleteRowPanel.RIGHT);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());
			getHeaderPanel().setVisible(false);
		}
		
		public void onAdd(Integer countToAdd) {
			super.onAdd(countToAdd);
		}
		
		public void onDelete() {
			super.onDelete();
		}
	}

	public YearWeightCategoryTable getYearWeightCategoryTable() {
		if (yearWeightCategoryTable == null) {
			yearWeightCategoryTable = new YearWeightCategoryTable();
			
			CM.YearWeightCategoryLink cmYearWeightCategoryLink = new CM.YearWeightCategoryLink(); 
			
			yearWeightCategoryTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT_CATEGORY(), cmYearWeightCategoryLink.getWeightCategory(), true, new ComboBoxTableCellEditor(getCmbWeightCategory())));
			
			yearWeightCategoryTable.setPreferredSize(new Dimension(200, 200));
		}
		return yearWeightCategoryTable;
	}

	public JScrollPane getYearWeightCategoryTableScroll() {
		if (yearWeightCategoryTableScroll == null) {
			yearWeightCategoryTableScroll = new JScrollPane(getYearWeightCategoryTable());
			yearWeightCategoryTableScroll.setPreferredSize(new Dimension(200, 200));
		}
		return yearWeightCategoryTableScroll;
	}

	public KASComboBox getCmbWeightCategory() {
		if (cmbWeightCategory == null) {
			cmbWeightCategory = UIFactory.createKasComboBox20(100);
		}
		return cmbWeightCategory;
	}

}