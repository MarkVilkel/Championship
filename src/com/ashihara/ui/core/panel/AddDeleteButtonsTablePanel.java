/**
 * The file AddDeleteButtonsTablePanel.java was created on 2007.18.8 at 18:06:56
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.ashihara.ui.core.interfaces.AddDeleteInterface;
import com.ashihara.ui.core.layout.OrientableFlowLayout;
import com.ashihara.ui.core.table.KASTable;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.TableToExcelExporter;
import com.ashihara.ui.tools.UIFactory;

public class AddDeleteButtonsTablePanel<T> extends KASPanel implements AddDeleteInterface{
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("Arial",Font.BOLD,11);
	
	private KASTable<T> table;
	private JPanel mainPanel, headerPanel, headerLeftPanel, headerRightPanel, headerCenterPanel; 
	private AddDeleteRowPanel buttonsPanel;
	private KASPanel rightButtonPanel, commonButtonsPanel;
	private final Class<T> rowClass;
	private boolean canDelete = true;
	private String notNumberErrorMessage = null, notPositiveNumberErrorMessage = null;
	private boolean showDeleteConfirmation = true;
	private JLabel lblRowsCount, lblHeaderCenter;
	
	private String noDataForThisTableCaption = uic.NO_DATA_FOR_THIS_TABLE();
	private String pleaseRefineYourSearchCriteriaAndTryAgainCaption = uic.PLEASE_REFINE_YOUR_SEARCH_CRITERIA_AND_TRY_AGAIN();
	private String searchCriteriaIsEnabledSoResultMightBeNotFullCaption = uic.SEARCH_CRITERIA_IS_ENABLED_SO_THERE_COULD_BE_SOME_MORE_ENTRIES();
	
	private String addDeleteButtonsPanelPosition = AddDeleteRowPanel.BOTTOM;
	protected JButton btnExportToExcel;
	
	public AddDeleteButtonsTablePanel(Class<T> rowClass, String notNumberErrorMessage, String notPositiveNumberErrorMessage){
		this(rowClass);
		this.notNumberErrorMessage = notNumberErrorMessage;
		this.notPositiveNumberErrorMessage = notPositiveNumberErrorMessage;
	}

	public AddDeleteButtonsTablePanel(Class<T> rowClass){
		super();
		this.rowClass = rowClass;
		init();
	}
	
	public AddDeleteButtonsTablePanel(Class<T> rowClass, String addDeletePanelPosition){
		super();
		this.rowClass = rowClass;
		addDeleteButtonsPanelPosition = addDeletePanelPosition;
		init();
	}
	public AddDeleteButtonsTablePanel(Class<T> rowClass, boolean canDelete){
		super();
		this.rowClass = rowClass;
		this.canDelete = canDelete;
		init();
	}

	private void init() {
//		setHeaderCaption("");
		setLayout(new BorderLayout());
		add(getMainPanel(), BorderLayout.CENTER);
		
		getHeaderRightPanel().add(getBtnExportToExcel());
	}

	public KASTable<T> getTable() {
		if (table == null){
			table = new KASTable<T>(getRowClass());
			table.getKASModel().addTableModelListener(new TableModelListener(){
				@Override
				public void tableChanged(TableModelEvent event) {
					tableModelChanged(event);
					
				}
			});
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
				@Override
				public void valueChanged(ListSelectionEvent event) {
					selectedValueChanged(event);
				}
			});
		}
		return table;
	}
	
	protected void tableModelChanged(TableModelEvent event) {
		long count = table.getKASModel().getRowCountWithoutFake();
		updateRowCount(count);
	}

	protected void updateRowCount(long rowCount) {
		getLblRowsCount().setText(String.valueOf(rowCount));		
	}

	public void selectedValueChanged(ListSelectionEvent event) {
		getButtonsPanel().getBtnDelete().setEnabled(table.getSelectedRowCount() != 0);
	}

	@Override
	public void onAdd(Integer countToAdd) {
		getTable().getKASModel().addNewRow(countToAdd);
	}

	@Override
	public void onDelete() {
		getTable().getKASModel().deleteRows(getTable().getSelectedRows());
	}

	public AddDeleteRowPanel getButtonsPanel() {
		if (buttonsPanel == null){
			buttonsPanel = new AddDeleteRowPanel(this, addDeleteButtonsPanelPosition);
			buttonsPanel.getBtnDelete().setVisible(canDelete);
		}
		return buttonsPanel;
	}

	public JPanel getMainPanel() {
		if (mainPanel == null){
			mainPanel = UIFactory.createJPanelBL();
			mainPanel.add(new JScrollPane(getTable()), BorderLayout.CENTER);
			if (AddDeleteRowPanel.BOTTOM.equals(addDeleteButtonsPanelPosition)){
				getCommonButtonsPanel().add(getButtonsPanel(), BorderLayout.WEST);
				getCommonButtonsPanel().add(getRightButtonPanel(), BorderLayout.EAST);
				mainPanel.add(getCommonButtonsPanel(),BorderLayout.SOUTH);
			}
			else if (AddDeleteRowPanel.LEFT.equals(addDeleteButtonsPanelPosition)){
				getCommonButtonsPanel().add(getButtonsPanel(), BorderLayout.NORTH);
				getCommonButtonsPanel().add(getRightButtonPanel(), BorderLayout.SOUTH);
				mainPanel.add(getCommonButtonsPanel(),BorderLayout.WEST);
			}
			else if (AddDeleteRowPanel.RIGHT.equals(addDeleteButtonsPanelPosition)){
				getCommonButtonsPanel().add(getButtonsPanel(), BorderLayout.NORTH);
				getCommonButtonsPanel().add(getRightButtonPanel(), BorderLayout.SOUTH);
				mainPanel.add(getCommonButtonsPanel(),BorderLayout.EAST);
			}
			
			mainPanel.add(getHeaderPanel(),BorderLayout.NORTH);
		}
		return mainPanel;
	}

	public Class<T> getRowClass() {
		return rowClass;
	}

	public String getNotNumberErrorMessage() {
		if (notNumberErrorMessage != null && notNumberErrorMessage.length()>0)
			return notNumberErrorMessage;
		return "Should be integer number!";
	}

	public String getNotPositiveNumberErrorMessage() {
		if (notPositiveNumberErrorMessage != null && notPositiveNumberErrorMessage.length()>0)
			return notPositiveNumberErrorMessage;
		return "Should be a positive number!";
	}

	public JPanel getHeaderPanel() {
		if (headerPanel == null){
			headerPanel = new JPanel(new BorderLayout());
			headerPanel.add(getHeaderLeftPanel(), BorderLayout.WEST);
			headerPanel.add(getHeaderCenterPanel(), BorderLayout.CENTER);
			headerPanel.add(getHeaderRightPanel(), BorderLayout.EAST);
		}
		return headerPanel;
	}

	@Override
	public boolean doesSelectionExist() {
		return getTable().getSelectedRowCount()>0;
	}

	@Override
	public boolean showDeleteConfirmation() {
		return showDeleteConfirmation;
	}
	
	public void setShowDeleteConfirmation(boolean show) {
		showDeleteConfirmation = show ;
	}

	@Override
	public void setEnabled(boolean b){
		getButtonsPanel().setEnabled(b);
		getTable().setEnabled(b);
		getRightButtonPanel().setEnabled(b);
		super.setEnabled(b);
		
		if(b) {
			selectedValueChanged(null);
		}
		
		getBtnExportToExcel().setEnabled(true);
	}

	public JLabel getLblRowsCount() {
		if (lblRowsCount == null){
			lblRowsCount = new JLabel();
			lblRowsCount.setFont(FONT);
		}
		return lblRowsCount;
	}

	public JPanel getHeaderLeftPanel() {
		if (headerLeftPanel == null){
			headerLeftPanel = UIFactory.createJPanelFLL();
		}
		return headerLeftPanel;
	}

	public JPanel getHeaderRightPanel() {
		if (headerRightPanel == null){
			headerRightPanel = UIFactory.createJPanelFRL();
		}
		return headerRightPanel;
	}
	
	public void showRowCount(String prefix){
		JLabel lbl = new JLabel(prefix+" : ");
		lbl.setFont(FONT);
		getHeaderLeftPanel().removeAll();
		getHeaderLeftPanel().add(lbl);
		getHeaderLeftPanel().add(getLblRowsCount());
	}

	public KASPanel getRightButtonPanel() {
		if (rightButtonPanel == null){
			rightButtonPanel = new  KASPanel(new FlowLayout(OrientableFlowLayout.RIGHT));
//			rightButtonPanel.setPreferredSize(new Dimension(100,10));
		}
		return rightButtonPanel;
	}

	public KASPanel getCommonButtonsPanel() {
		if (commonButtonsPanel == null){
			commonButtonsPanel = new KASPanel();
			commonButtonsPanel.setBorder(BorderFactory.createEtchedBorder(1));
		}
		return commonButtonsPanel;
	}

	public JPanel getHeaderCenterPanel() {
		if (headerCenterPanel == null){
			headerCenterPanel = new  JPanel(new FlowLayout(OrientableFlowLayout.CENTER));
		}
		return headerCenterPanel;
	}
	
	public void resetHeaderCenterResultCaption(Boolean isSearchCriteriaFilled){
		getHeaderCenterPanel().remove(getLblHeaderCenter());
		repaint();
		if (isSearchCriteriaFilled == null)
			return;
		
		String text = "";
		if (getTable().getKASModel().getRowCount() <= 0){
			text = getNoDataForThisTableCaption() + ".";
			if (isSearchCriteriaFilled) {
				text += " " + getPleaseRefineYourSearchCriteriaAndTryAgainCaption();
			}
		}
		else if (isSearchCriteriaFilled){
			text += getSearchCriteriaIsEnabledSoResultMightBeNotFullCaption();
		}
		getLblHeaderCenter().setText(text);
		getHeaderCenterPanel().add(getLblHeaderCenter());
		getLblHeaderCenter().repaint();
	}

	public JLabel getLblHeaderCenter() {
		if (lblHeaderCenter == null){
			lblHeaderCenter = new JLabel();
			lblHeaderCenter.setForeground(Color.BLUE);
		}
		return lblHeaderCenter;
	}

	public String getNoDataForThisTableCaption() {
		return noDataForThisTableCaption;
	}

	public void setNoDataForThisTableCaption(String noDataForThisTableCaption) {
		this.noDataForThisTableCaption = noDataForThisTableCaption;
	}

	public String getPleaseRefineYourSearchCriteriaAndTryAgainCaption() {
		return pleaseRefineYourSearchCriteriaAndTryAgainCaption;
	}

	public void setPleaseRefineYourSearchCriteriaAndTryAgainCaption(String pleaseRefineYourSearchCriteriaAndTryAgainCaption) {
		this.pleaseRefineYourSearchCriteriaAndTryAgainCaption = pleaseRefineYourSearchCriteriaAndTryAgainCaption;
	}

	public String getSearchCriteriaIsEnabledSoResultMightBeNotFullCaption() {
		return searchCriteriaIsEnabledSoResultMightBeNotFullCaption;
	}

	public void setSearchCriteriaIsEnabledSoResultMightBeNotFullCaption(String searchCriteriaIsEnabledSoResultMightBeNotFullCaption) {
		this.searchCriteriaIsEnabledSoResultMightBeNotFullCaption = searchCriteriaIsEnabledSoResultMightBeNotFullCaption;
	}
	
	public JButton getBtnExportToExcel() {
		if (btnExportToExcel == null){
			btnExportToExcel = UIFactory.createExcelButton();
			btnExportToExcel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String caption = AddDeleteButtonsTablePanel.this.getClass().getSimpleName();
						caption = caption.replace("Table", "");
						TableToExcelExporter.drawTableToExcel(getTable(), caption, uic);
					} catch (IOException e) {
						MessageHelper.handleException(null, e);
					}
				}
			});
		}
		return btnExportToExcel;
	}

}
